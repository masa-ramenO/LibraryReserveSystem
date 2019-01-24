/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */

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
import user.User;

/**
 * マイページにログインするためのサーブレット．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * ログインページへのリンクをクリックしたときに呼び出されるメソッド． ログインページを表示します．
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* フォワード */
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * ログインフォームへ入力したときに，ログインできるかを判断するメソッド． ログインできる場合はマイページへリダイレクトします．
     * ただし，ログインできなかった場合はログインページへ戻ります．
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
        /* コネクションとステートメントの設定 */
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            /* DBへの接続 */
            final String driverUri = "YOUR_DATABASE_DRIVER_URI";
            con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

            /* フォーム入力値の取得 */
            final String id = request.getParameter("id");
            final String pass = request.getParameter("pass");

            /* SQL文 */
            final String sql = "SELECT * FROM library_user "
                    + "WHERE id=? AND password=?";
            stmt = con.prepareStatement(sql); // SQL文をプリコンパイル

            /* SQL文に値をセット */
            stmt.setString(1, id);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery(); // SQLを実行

            HttpSession session = request.getSession(false); // セッションを取得

            List<user.User> usrData = new ArrayList<>(); // ユーザーデータを格納するリスト
            /* データの取得 */
            if (rs.next()) { // テーブルにデータがあるならば
                /* セッションを破棄し，新規にセッションを開始 */
                session.invalidate();
                session = request.getSession(true); // セッションを開始

                user.User usr = new User(); // インスタンスの生成

                /* 値のセット(パスワード以外) */
                usr.setUsrNum(rs.getInt("user_number"));        // 管理番号
                usr.setUsrName(rs.getString("user_name"));      // ユーザーネーム
                usr.setId(rs.getString("id"));                  // ログインID
                usr.setOverdueCt(rs.getInt("overdue_count"));   // 延滞回数
                usr.setLendCt(rs.getInt("lend_count"));         // 貸出回数

                usrData.add(usr); // リストに追加

                /* セッションとの結びつけ */
                session.setAttribute("usrData", usrData);

                /* リダイレクト */
                response.sendRedirect("Mypage");
            } else { // テーブルに値が無ければ
                /* ログイン失敗 */
                session.setAttribute("status", "error"); // エラー情報を設定
                this.doGet(request, response); // login.jspへフォワード
            }

            rs.close();

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
