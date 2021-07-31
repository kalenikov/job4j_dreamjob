<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://use.fontawesome.com/6a99d19799.js"></script>
    <title>Работа мечты</title>
</head>
<%
    String path = request.getContextPath();
%>
<div class="row">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="<%=path%>/WEB-INF/jsp/login.jsp">Login</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=path%>/posts.do">Posts</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=path%>/posts.do?action=NEW">Add post</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=path%>/candidates.do">Candidates</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=path%>/candidates.do?action=NEW">Add candidate</a>
        </li>
    </ul>
</div>