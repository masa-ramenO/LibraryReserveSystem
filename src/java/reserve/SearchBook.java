/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package reserve;

import booklibrary.*;
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
 * 蔵書検索を行うためのサーブレット．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(name = "SearchBook", urlPatterns = {"/reserve/SearchBook"})
public class SearchBook extends HttpServlet {

    /**
     * 検索ページへのリンクから飛んできたときに呼び出されるメソッド． 検索ページへ飛びます．
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
        java.util.List<User> usrData = (java.util.List<User>) session.getAttribute("usrData");
        if (session == null || usrData.isEmpty()) { // セッションが無ければ
            mypage_eroor.MypageError.SessionTimeOutRedirect(request, response);
        } else {
            /* フォワード */
            RequestDispatcher dispatcher = request.getRequestDispatcher("reserve.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * 検索するときに呼び出されるメソッド． 結果は検索結果ページにて表示されます．
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

                /* フォーム入力値の取得 */
                final String target = request.getParameter("target"); // 検索対象
                final String sachTxt = request.getParameter("searchText"); // 検索文

                String sql = "SELECT * FROM book_library WHERE ";
                sql += target;
                /* ここまででSQL文は SELECT * FROM book_library WHERE (target) */

                switch (target) {
                    /* ISBN */
                    case "isbn":
                        sql += "=?"; // 完全一致
                        stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                        try {
                            stmt.setLong(1, Long.parseLong(sachTxt));
                        } catch (NumberFormatException ex) {
                            session.setAttribute("status", "error");
                            this.doGet(request, response);
                        }
                        break;
                    /* タイトルか著者 */
                    case "title":
                    case "author":
                        /* 検索方法 */
                        switch (request.getParameter("searchType")) {
                            case "PARTIAL": // 部分一致
                                sql += " like ?";
                                stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                                stmt.setString(1, "%" + sachTxt + "%");
                                break;
                            case "EXACT": // 完全一致
                                sql += "=?";
                                stmt = con.prepareStatement(sql); // SQLをプリコンパイル
                                stmt.setString(1, sachTxt);
                                break;
                        }
                        break;
                }

                ResultSet rs = stmt.executeQuery(); // SQL実行
                // 検索結果を格納するリスト
                List<BookLibrary> blist = new ArrayList<>();

                while (rs.next()) {
                    BookLibrary bl = new BookLibrary();

                    bl.setIsbn(rs.getLong("isbn"));
                    bl.setTitle(rs.getString("title"));
                    bl.setAuthor(rs.getString("author"));
                    bl.setPublisher(rs.getString("publisher"));
                    bl.setPublicationYear(rs.getInt("publication_year"));
                    bl.setHoldingCt(rs.getInt("holding_count"));

                    blist.add(bl); // リストに追加
                }
                rs.close();

                /* 検索結果をリダイレクト */
                session.setAttribute("sachBooks", blist);
                response.sendRedirect("BookList");

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
