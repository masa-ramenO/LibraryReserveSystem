<%-- 
    Document   : login
    Created on : 2018/01/17, 9:00:39
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ログインページ</title>
    </head>
    <body>
        <h2>ログインページ</h2>

        <%
            session = request.getSession(false); // セッションを取得
            List usrData = (List) session.getAttribute("usrData"); // ログイン情報を取得

            if (usrData != null) { // すでにログインされていれば
                response.sendRedirect("Mypage"); // マイページへリダイレクト
            } else {
                /* セッションを新規に開始し，statusを取得 */
                session = request.getSession(true);
                Object status = session.getAttribute("status"); // エラー情報の取得
                if (status != null) { // 何かエラーがあればメッセージを表示
        %>
        <h4>エラー！<br>再度，IDとパスワードを入力してください．</h4>
            <%
                session.setAttribute("status", null); // エラー情報をnullに設定
                }
            %>
        
        <form action="Login" method="POST">
            <table>
                <tr>
                    <td>ID</td><td><input type="text" name="id"></td>
                </tr>
                <tr><td></td></tr>
                <tr>
                    <td>パスワード</td><td><input type="password" name="pass"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" name="loginBtn" value="ログイン"></td>
                </tr>
            </table>
            
        </form>

        <%            }
        %>
    </body>
</html>
