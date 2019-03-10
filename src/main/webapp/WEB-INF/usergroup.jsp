<%--
  Created by IntelliJ IDEA.
  User: przemek
  Date: 10.03.19
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>User Groups</title>
</head>
<body>
<%@ include file="/WEB-INF/header.jsp" %>
    <table border="1" width="250">
        <tr>
            <th>Group ID</th>
            <th>Group Name</th>
        </tr>
    <c:forEach var="userGroup" items="${userGroup}">
        <tr>
            <td>${userGroup.id}</td>
            <td>${userGroup.name}</td>
        </tr>
    </c:forEach>
    </table>
<%@ include file="/WEB-INF/footer.jsp" %>
</body>
</html>
