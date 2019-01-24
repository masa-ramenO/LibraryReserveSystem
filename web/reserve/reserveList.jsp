<%-- 
    Document   : reserveList
    Created on : 2018/01/18, 1:40:51
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>予約一覧</title>
    </head>
    <body>

        <%
            java.util.List<reserve.ReserveData> reserveData
                    = (java.util.List<reserve.ReserveData>) request.getAttribute("reserveData");
        %>
        <table border="1">
            <tr>
                <th>予約番号</th><th>予約者の管理番号</th><th>ISBN</th><th>予約日</th>
            </tr>
            <%
                for (reserve.ReserveData res : reserveData) {
            %>
            <tr>
                <td><%= res.getReserveNum()%></td>
                <td><%= res.getUsrNum()%></td>
                <td><%= res.getIsbn()%></td>
                <td><%= res.getDate()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <p><a href="../Mypage">マイページ</a></p>
    </body>
</html>