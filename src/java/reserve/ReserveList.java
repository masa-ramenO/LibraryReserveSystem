/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package reserve;

import java.io.IOException;
import java.io.PrintWriter;
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
import user.User;

/**
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(name = "ReserveList", urlPatterns = {"/reserve/ReserveList"})
public class ReserveList extends HttpServlet {

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

        /* コネクションとステートメントの設定 */
        Connection con = null;
        PreparedStatement stmt = null;

        HttpSession session = request.getSession(false); // セッション取得        
        if (session == null) { // セッションが無ければ
            mypage_eroor.MypageError.SessionTimeOutRedirect(request, response);
        } else { // セッションがあれば
            List<User> usrData = (List<User>) session.getAttribute("usrData");
            String id = null;
            int usrNum = 0;
            for (User usr : usrData) {
                id = usr.getId();
                usrNum = usr.getUsrNum();
            }

            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                /* DBへの接続 */
                final String driverUri = "YOUR_DATABASE_DRIVER_URI";
                con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

                String sql = "SELECT * FROM reserve";
                if (!id.equals("admin")) { // 管理者でなければ
                    sql += " WHERE user_number=?";
                    stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                    stmt.setInt(1, usrNum);
                } else {
                    stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                }

                ResultSet rs = stmt.executeQuery(); // SQL実行

                // 検索結果を格納するリスト
                java.util.List<reserve.ReserveData> rlist = new ArrayList<>();

                while (rs.next()) {
                    reserve.ReserveData res = new ReserveData();
                    
                    res.setReserveNum(rs.getInt("reserve_number"));
                    res.setUsrNum(rs.getInt("user_number"));
                    res.setIsbn(rs.getLong("isbn"));
                    res.setDate(rs.getDate("reserve_date"));
                    rlist.add(res);
                }
                rs.close();

                /* フォワード */
                RequestDispatcher dispatcher = request.getRequestDispatcher("reserveList.jsp");
                request.setAttribute("reserveData", rlist);
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
