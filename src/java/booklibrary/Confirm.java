/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package booklibrary;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.User;

/**
 * 蔵書登録の際に，重複がないかどうかをチェックするためのサーブレット．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(name = "Confirm", urlPatterns = {"/booklibrary/Confirm"})
public class Confirm extends HttpServlet {

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

        request.setCharacterEncoding("UTF-8");
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
                    Class.forName("org.apache.derby.jdbc.ClientDriver");

                    /* DBへの接続 */
                    final String driverUri = "YOUR_DATABASE_DRIVER_URI";
                    con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

                    String sql = "SELECT count(*) AS ct FROM book_library "
                            + "WHERE isbn=?";
                    stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                    try {
                        stmt.setLong(1, Long.parseLong(request.getParameter("isbn")));
                    } catch (NumberFormatException ex) {
                        session.setAttribute("status", "error");
                        response.sendRedirect("Regist");
                    }
                    ResultSet rs = stmt.executeQuery(); // SQL実行

                    int count = 0; // 検索結果の件数
                    while (rs.next()) {
                        count = rs.getInt("ct");
                    }

                    /* 登録しようとしている蔵書がすでにDBに登録されているかチェック */
                    if (count != 0) { // すでに登録されていれば
                        /* リダイレクト */
                        session.setAttribute("status", request.getParameter("isbn"));
                        response.sendRedirect("Regist");
                    } else { // まだ登録されていなければ
                        java.util.List<BookLibrary> blist = new ArrayList<>();
                        BookLibrary bl = new BookLibrary();
                        /*
                            リストに値のセット．
                            * NullPointerExceptionはスローされない．
                         */
                        try {
                            bl.setIsbn(Long.parseLong(request.getParameter("isbn")));
                            bl.setTitle(request.getParameter("title"));
                            bl.setAuthor(request.getParameter("author"));
                            bl.setPublisher(request.getParameter("publisher"));
                            String pbYear = BookLibrary.validateNum(request.getParameter("publicationYear"));
                            bl.setPublicationYear(Integer.parseInt(pbYear));
                            String holdCt = BookLibrary.validateNum(request.getParameter("holdingCt"));
                            bl.setHoldingCt(Integer.parseInt(holdCt));
                        } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
                            /* リダイレクト */
                            session.setAttribute("status", "error");
                            response.sendRedirect("Regist");
                        }
                        blist.add(bl);
                        /* フォワード */
                        session.setAttribute("registBook", blist);
                        RequestDispatcher dispatcher 
                                = request.getRequestDispatcher("confirm.jsp");
                        dispatcher.forward(request, response);
                    }

                } catch (Exception ex) {
                    throw new ServletException(ex);
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            throw new ServletException(ex);
                        }
                    }
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException ex) {
                            throw new ServletException(ex);
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
