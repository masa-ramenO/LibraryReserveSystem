package mypage_eroor;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
/**
 * マイページのエラーを扱うクラス．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
public class MypageError extends HttpServlet {

    /**
     * 「セッションタイムアウト」ページへリダイレクトするためのメソッド．
     *
     * @param request
     * @param response
     * @throws ServletException
     */
    public static void SessionTimeOutRedirect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            response.sendRedirect("http://localhost:8084/apProg2_ass/SendSessionTimeOut");
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * 「閲覧禁止」ページへフォワードするためのメソッド．
     *
     * @param request
     * @param response
     * @throws ServletException
     */
    public static void ForbiddenForward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            RequestDispatcher dispatcher 
                    = request.getRequestDispatcher("http://localhost:8084/apProg2_ass/Forbidden");
            dispatcher.forward(request, response);
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }
}
