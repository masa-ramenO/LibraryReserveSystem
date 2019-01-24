<%-- 
    Document   : regist
    Created on : 2018/01/17, 22:44:16
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>蔵書登録</title>
    </head>
    <body>
        <h2>蔵書登録</h2>
        <p>
            蔵書を登録します．<br>
            すべての項目で入力が必須です．<br>
            また，「蔵書数」は非負整数を指定してください．
        </p>
        <%
            String status = (String) session.getAttribute("status");
            if (status != null) {
                if (status.equals("regist")) {
        %>
        <h3>エラー！入力条件を守って入力してください．</h3>
        <%
                } else {
        %>
        <h3>この本はすでに登録されています．ISBN：<%=status%></h3>
        <%
                }
                session.setAttribute("status", null);
            }
        %>

        <form action="Confirm" method="POST">
            <p><input type="reset" value="入力内容をリセット"></p>

            <table>
                <tr>
                    <td>ISBN</td><td><input type="text" name="isbn" required></td><br>
                ※ISBN-13で指定された13桁のISBNコード(半角数字)を入力してください．
                </tr>
                <tr>
                    <td>タイトル</td><td><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <td>著者</td><td><input type="text" name="author" required></td>
                </tr>
                <tr>
                    <td>出版社</td><td><input type="text" name="publisher" required></td>
                </tr>
                <tr>
                    <td>出版年</td><td><input type="text" name="publicationYear" required></td>
                </tr>
                <tr>
                    <td>蔵書数</td><td><input type="text" name="holdingCt" value="1" required><br>
                        ※蔵書数は非負整数を入力してください．</td>
                </tr>
            </table>
            <p><input type="submit" value="確認"></p>
        </form>

    </body>
</html>
