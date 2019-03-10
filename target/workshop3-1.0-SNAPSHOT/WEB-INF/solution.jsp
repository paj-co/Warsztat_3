<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${solution.id}</title>
</head>
<body>
<%@ include file="/WEB-INF/header.jsp" %>
<div>Id: ${solution.id}</div>
<div>Created: ${solution.created}</div>
<div>Updated ${solution.updated}</div>
<div>Description: ${solution.description}</div>
<div>Exercise id${solution.exercise_id}</div>
<div>Users Id${solution.users_id}</div>

<%@ include file="/WEB-INF/footer.jsp" %>
</body>
</html>
