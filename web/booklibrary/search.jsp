<%-- 
    Document   : search
    Created on : 2018/01/17, 16:58:16
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    session = request.getSession(false);
    if (session == null) {
        session = request.getSession(true);
    }
    session.setAttribute("displayType", "search");
    session.setAttribute("sachBooks", null);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>蔵書検索</title>
    </head>
    <body>
        <h2>蔵書検索</h2>
        <form action="Search" method="POST">
            <table border="1">
                <tr>
                    <td>検索対象</td>
                    <td>
                        <input type="radio" name="target" value="isbn" required>ISBN
                        <input type="radio" name="target" value="title" required>タイトル
                        <input type="radio" name="target" value="author" required>著者
                    </td>
                </tr>
                <tr>
                    <td>検索方法</td>
                    <td>
                        <input type="radio" name="searchType" value="PARTIAL" required checked>部分一致
                        <input type="radio" name="searchType" value="EXACT" required>完全一致<br>
                        ※ISBNで検索するときはこの選択に関わらず「完全一致」で検索されます．
                    </td>
                </tr>
                <tr>
                    <td>検索文字列</td>
                    <td><input type="text" name="searchText"></td>
                </tr>
            </table>
            <p><input type="submit" value="検索"></p>
        </form>
    </body>
</html>
