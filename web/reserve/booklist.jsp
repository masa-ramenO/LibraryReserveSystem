<%-- 
    Document   : booklist
    Created on : 2018/01/17, 16:26:09
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    session = request.getSession(false);
    List<booklibrary.BookLibrary> blist
            = (List<booklibrary.BookLibrary>) session.getAttribute("sachBooks");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>蔵書検索結果</title>
    </head>
    <body>
        <h2>蔵書検索結果</h2>
        <%
            if (blist.size() == 0) {
        %>
        <p>一致する蔵書はありません．</p>
        <p><a href="../Mypage">マイページ</a>へ戻る</p>
        <%
        } else {
        %>
        <form action="Reserve" method="POST">
            <table border="1">
                <tr>
                    <th>ISBN</th><th>タイトル</th><th>著者</th><th>出版社</th>
                    <th>出版年</th><th>予約</th>
                </tr>
                <%
                    for (booklibrary.BookLibrary bl : blist) {
                %>
                <tr>
                    <td> <%= bl.getIsbn()%> </td>
                    <td> <%= bl.getTitle()%> </td>
                    <td> <%= bl.getAuthor()%> </td>
                    <td> <%= bl.getPublisher()%> </td>
                    <td> <%= bl.getPublicationYear()%> </td>
                    <td> <input type="radio" name="reserveIsbn" value="<%=bl.getIsbn()%>"> </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
            <p><input type="submit" value="予約"></p>
        </form>
        <p><a href="../Mypage">マイページ</a></p>
    </body>
</html>

