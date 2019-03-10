<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<%@ include file="/WEB-INF/header.jsp" %>

<table border="1" width="250">
    <tr>
        <th>ID</th>
        <th>Created</th>
        <th>Updated</th>
        <th>Description</th>
        <th>Exercise ID</th>
        <th>Users ID</th>
        <th>Details</th>
    </tr>
    <c:forEach items="${solutions}" var="solution">
        <tr>
            <td>${solution.id}</td>
            <td>${solution.created}</td>
            <td>${solution.updated}</td>
            <td>${solution.description}</td>
            <td>${solution.exercise_id}</td>
            <td>${solution.users_id}</td>
            <td>
                <a href="/viewSolution?id=${solution.id}">Szczegóły</a>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/footer.jsp" %>
</body>
</html>
