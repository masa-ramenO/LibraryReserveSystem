<%-- 
    Document   : booklist
    Created on : 2018/01/17, 16:26:09
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    session = request.getSession(false);
    String displayType = (String) session.getAttribute("displayType");
    List<booklibrary.BookLibrary> blist
            = (List<booklibrary.BookLibrary>) session.getAttribute("sachBooks");
    String pageTitle = null;
    if (displayType.equals("all")) {
        pageTitle = "蔵書一覧";
    } else {
        pageTitle = "検索結果";
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> <%= pageTitle%> </title>
    </head>
    <body>
        <h2> <%= pageTitle%> </h2>
        <%
            if (blist.size() == 0) {
        %>
        <p>一致する蔵書はありません．</p>
        <%
        } else {
        %>
        <table border="1">
            <tr>
                <th>ISBN</th><th>タイトル</th><th>著者</th><th>出版社</th>
                <th>出版年</th><th>蔵書数</th><th>貸出可否</th>
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
                <td> <%= bl.getHoldingCt()%> </td>
                <td>
                    <%
                        if (bl.isLendable()) {
                            out.print("○");
                        } else {
                            out.print("不可");
                        }
                    %>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </body>
</html>

