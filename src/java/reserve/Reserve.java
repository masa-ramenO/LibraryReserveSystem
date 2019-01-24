/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package reserve;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
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
@WebServlet(name = "Reserve", urlPatterns = {"/reserve/Reserve"})
public class Reserve extends HttpServlet {

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

        HttpSession session = request.getSession(false); // セッション取得   
        java.util.List<User> usrData = (java.util.List<User>) session.getAttribute("usrData");
        if (session == null || usrData.size() == 0) { // セッションが無ければ
            mypage_eroor.MypageError.SessionTimeOutRedirect(request, response);
        } else {
            /* コネクションとステートメントの設定 */
            Connection con = null;
            PreparedStatement stmt = null;
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                /* DBへの接続 */
                final String driverUri = "YOUR_DATABASE_DRIVER_URI";
                con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

                /* パラメータの設定 */
                int usrNum = 0;
                for (User usr : usrData) {
                    usrNum = usr.getUsrNum();
                }

                final long isbn = Long.parseLong(request.getParameter("reserveIsbn"));

                final String sql = "INSERT INTO reserve (user_number, isbn, reserve_date) "
                        + "values (?, ?, ?)";
                stmt = con.prepareStatement(sql); // SQLをプリコンパイル

                /* 値のセット */
                stmt.setInt(1, usrNum);
                stmt.setLong(2, isbn);
                stmt.setDate(3, getCurDate());

                int count = stmt.executeUpdate();

                /* フォワード */
                RequestDispatcher dispatcher
                        = request.getRequestDispatcher("ReserveComplete");
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

    /* Calendar型の現在の日付を取得するメソッド */
    private Calendar createCurDate() {
        /* 現在の日付を取得 */
        java.util.Date date = new java.util.Date();

        /* カレンダーの取得 */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        /* 時間部分を初期化 */
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // 時
        calendar.set(Calendar.MINUTE, 0);       // 分
        calendar.set(Calendar.SECOND, 0);       // 秒
        calendar.set(Calendar.MILLISECOND, 0);  // ミリ秒

        return calendar;
    }

    /* Calendar型の日付を java.sql.Date型に変換するメソッド */
    private java.sql.Date ConvDate(Calendar cal) {
        /* SQLのDATE型に変換 */
        java.sql.Date sqlDate = new java.sql.Date(cal.getTimeInMillis());
        return sqlDate;
    }

    /**
     * {@code java.sql.Date}型の今日の日付を取得します．
     *
     * @return {@code java.sql.Date}型の今日の日付．
     */
    public java.sql.Date getCurDate() {
        Calendar cal = createCurDate(); // 現在の日付を取得
        return ConvDate(cal);
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
