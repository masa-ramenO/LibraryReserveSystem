<%-- 
    Document   : confirm
    Created on : 2018/01/18, 0:20:01
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>蔵書登録確認</title>
    </head>
    <body>
        <h2>蔵書登録確認</h2>
        <p>以下の内容で登録します．よろしいですか？</p>
        <%  // データの受け取り
            List<booklibrary.BookLibrary> registBook 
                    = (ArrayList<booklibrary.BookLibrary>) session.getAttribute("registBook");
        %>
        <form action="RegistComplete" method="POST">
            <p><button type="submit" name="action" value="CANSEL">戻る</button></p>
            <table border="1">
                <tr>
                    <th>ISBN</th><th>タイトル</th><th>著者</th><th>出版社</th>
                    <th>出版年</th><th>蔵書数</th>
                </tr>
                <%  // 受け取ったデータを出力
                    for (booklibrary.BookLibrary bl : registBook) {
                %>
                <tr>
                    <td> <%= bl.getIsbn()%> </td>
                    <td> <%= bl.getTitle()%> </td>
                    <td> <%= bl.getAuthor()%> </td>
                    <td> <%= bl.getPublisher()%> </td>
                    <td> <%= bl.getPublicationYear()%> </td>
                    <td> <%= bl.getHoldingCt()%> </td>

                </tr>
                <%
                    }
                %>
            </table>

            <p><button type="submit" name="action" value="REGIST">登録</button></p>

        </form>
    </body>
</html>
