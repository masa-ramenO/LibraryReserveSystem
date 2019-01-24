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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 蔵書一覧を表示するためのサーブレット．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
@WebServlet(name = "DisplayBooksAll", urlPatterns = {"/booklibrary/DisplayBooksAll"})
public class DisplayBooksAll extends HttpServlet {

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
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            /* DBへの接続 */
            final String driverUri = "YOUR_DATABASE_DRIVER_URI";
            con = DriverManager.getConnection(driverUri, "DATABASE_USERNAME", "DATABASE_PASSWORD");

            String sql = "SELECT * FROM book_library";
            stmt = con.prepareStatement(sql); // SQLをプリコンパイル
            ResultSet rs = stmt.executeQuery(); // SQL実行
            
            // 検索結果を格納するリスト
            java.util.List<BookLibrary> blist = new ArrayList<>();

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
            HttpSession session = request.getSession(false);
            if (session == null) {
                session = request.getSession(true);
            }
            session.setAttribute("displayType", "all");
            session.setAttribute("sachBooks", blist);
            response.sendRedirect("List");

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
