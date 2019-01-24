/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(name = "UserList", urlPatterns = {"/user/UserList"})
public class UserList extends HttpServlet {

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
            List<User> usrData = (List<User>) session.getAttribute("usrData");
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

                    String sql = "SELECT * FROM library_user";
                    stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                    ResultSet rs = stmt.executeQuery(); // SQL実行

                    // 検索結果を格納するリスト
                    java.util.List<User> ulist = new ArrayList<>();

                    while (rs.next()) {
                        User usr = new User();
                        usr.setUsrNum(rs.getInt("user_number"));
                        usr.setUsrName(rs.getString("user_name"));
                        usr.setId(rs.getString("id"));
                        usr.setLendCt(rs.getInt("lend_count"));
                        usr.setOverdueCt(rs.getInt("overdue_count"));
                        ulist.add(usr);
                    }
                    rs.close();

                    /* フォワード */
                    RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
                    request.setAttribute("usrData", ulist);
                    dispatcher.forward(request, response);
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
