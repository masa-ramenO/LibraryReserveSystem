/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package booklibrary;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.User;

/**
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(name = "RegistComplete", urlPatterns = {"/booklibrary/RegistComplete"})
public class RegistComplete extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // 文字コード設定

        HttpSession session = request.getSession(false); // セッション取得        
        if (session == null) { // セッションが無ければ
            mypage_eroor.MypageError.SessionTimeOutRedirect(request, response);
        } else {
            java.util.List<User> usrData = (java.util.List<User>) session.getAttribute("usrData");
            String id = null;
            for (User usr : usrData) {
                id = usr.getId();
            }
            if (!id.equals("admin")) { // 管理者でなければ
                mypage_eroor.MypageError.ForbiddenForward(request, response);
            } else {

                /* コネクションとステートメントの設定 */
                Connection con = null;
                PreparedStatement stmt = null;

                try {
                    // 戻る -> tmp = "CANSEL"  ;  登録 -> tmp = "REGIST"

                    /* 「戻る」ボタンが押下されたら登録ページへ リダイレクト */
                    if (request.getParameter("action").equals("CANSEL")) {
                        response.sendRedirect("Regist");
                    } else {
                        Class.forName("org.apache.derby.jdbc.ClientDriver");

                        /* DBへの接続 */
                        final String driverUri = "YOUR_DATABASE_DRIVER_URI";
                        con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

                        String sql = "INSERT INTO book_library "
                                + "(isbn, title, author, publisher, publication_year, holding_count) "
                                + " values (?, ?, ?, ?, ?, ?)";
                        stmt = con.prepareStatement(sql); // SQLをプリコンパイル

                        // 登録する本の情報を取得
                        java.util.List<BookLibrary> registBook
                                = (java.util.List<BookLibrary>) session.getAttribute("registBook");
                        for (BookLibrary bl : registBook) {
                            // SQLに値のセット
                            stmt.setLong(1, bl.getIsbn());
                            stmt.setString(2, bl.getTitle());
                            stmt.setString(3, bl.getAuthor());
                            stmt.setString(4, bl.getPublisher());
                            stmt.setInt(5, bl.getPublicationYear());
                            stmt.setInt(6, bl.getHoldingCt());
                        }

                        /* SQLの実行 */
                        int count = stmt.executeUpdate();

                        /* JSPにフォワード */
                        RequestDispatcher dispatcher
                                = request.getRequestDispatcher("registComplete.jsp");
                        dispatcher.forward(request, response);
                    }
                } catch (Exception e) {
                    throw new ServletException(e);
                } finally {
                    /* ステートメントのクローズ */
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException e) {
                            throw new ServletException(e);
                        }
                    }

                    /* コネクションのクローズ */
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            throw new ServletException(e);
                        }
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
