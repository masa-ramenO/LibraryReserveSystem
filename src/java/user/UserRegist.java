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
@WebServlet(name = "UserRegist", urlPatterns = {"/user/UserRegist"})
public class UserRegist extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

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
                /* フォワード */
                RequestDispatcher dispatcher = request.getRequestDispatcher("regist.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * ユーザーを登録するためのメソッド．
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false); // セッション取得        
        if (session == null) { // セッションが無ければ
            mypage_eroor.MypageError.SessionTimeOutRedirect(request, response);
        } else {
            List<User> usrData = (List<User>) session.getAttribute("usrData");
            String usrId = null;
            for (User usr : usrData) {
                usrId = usr.getId();
            }
            if (!usrId.equals("admin")) { // 管理者でなければ
                mypage_eroor.MypageError.ForbiddenForward(request, response);
            } else {
                /* コネクションとステートメントの設定 */
                Connection con = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {

                    Class.forName("org.apache.derby.jdbc.ClientDriver");

                    /* DBへの接続 */
                    final String driverUri = "YOUR_DATABASE_DRIVER_URI";
                    con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

                    /* フォーム入力値の取得 */
                    final String usrName = request.getParameter("usrName");
                    final String id = request.getParameter("id");
                    final String pass = request.getParameter("pass");

                    String sachSql = "SELECT count(*) AS ct FROM library_user WHERE id=?";
                    stmt = con.prepareStatement(sachSql); // SQLをプリコンパイル   
                    stmt.setString(1, id);
                    rs = stmt.executeQuery(); // SQL実行

                    /* 件数取得 */
                    int count = 0;
                    while (rs.next()) {
                        count = rs.getInt("ct");
                    }

                    /* 登録できるか */
                    if (count != 0) { // すでに登録されていれば
                        session.setAttribute("status", "error");
                        this.doGet(request, response); // 再度，登録ページへ
                    } else {
                        String insertSql = "INSERT INTO library_user "
                                + "(user_name, id, password, overdue_count, lend_count) "
                                + "values (?, ?, ?, ? , ?)";

                        stmt = con.prepareStatement(insertSql); // SQLをプリコンパイル

                        stmt.setString(1, usrName);
                        stmt.setString(2, id);
                        stmt.setString(3, pass);
                        stmt.setInt(4, 0);
                        stmt.setInt(5, 0);

                        int tmp = stmt.executeUpdate(); // SQL実行

                        /* リダイレクト */
                        RequestDispatcher dispatcher
                                = request.getRequestDispatcher("userRegistComplete.jsp");
                        dispatcher.forward(request, response);
                    }

                } catch (Exception ex) {
                    throw new ServletException(ex);
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            throw new ServletException(ex);
                        }
                    }
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
