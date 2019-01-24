<%-- 
    Document   : regist
    Created on : 2018/01/17, 21:03:55
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザー登録</title>
    </head>
    <body>
        <h2>ユーザー登録</h2>
        <h4>
            ユーザ登録をします．以下のすべての項目を入力してください．<br>
            ※「パスワード」はシステム管理者は入力しないでください！<br>
             　登録希望者が入力するようにしてください．<br>            
        </h4>

        <%
            Object status = session.getAttribute("status");
            if (status != null) {
        %>
        <p>
            エラー！もう一度入力しなおしてください．<br>
            エラーの要因として以下が考えられます．<br>
            ・「ID」：すでに同じIDが登録されている．
            ・「パスワード」：半角英数字以外を入力している．
        </p>
        <%
                session.setAttribute("status", null);
            }
        %>

        <form action="UserRegist" method="POST">
            <table>
                <tr>
                    <td>管理番号</td>
                    <td><input type="text" value="自動的に設定されます" disabled></td>
                </tr>
                <tr>
                    <td>氏名</td>
                    <td><input type="text" name="usrName" required></td>
                </tr>
                <tr>
                    <td>ID</td>
                    <td><input type="text" name="id" required></td>
                </tr>
                <tr>
                    <td>パスワード<br>※システム管理者は入力しないでください！</td>
                    <td><input type="password" name="pass" required></td>
                </tr>
            </table>
            <p><input type="submit" value="登録"></p>
        </form>
    </body>
</html>
