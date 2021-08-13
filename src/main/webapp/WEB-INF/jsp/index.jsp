<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html>
<jsp:include page='navBar.jsp'/>
<body>

<%--POST BLOCK BEGIN--%>
<div class="container">
    <form method="get" action="${pageContext.request.contextPath}/index.do">
        <label>from
            <input type="date" name="startDate" value="${param.startDate}">
        </label>
        <label>to
            <input type="date" name="endDate" value="${param.endDate}">
        </label>
        <button type="submit">Filter</button>
    </form>
</div>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">Posts</div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>name</th>
                        <th>description</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${posts}" var="post">
                        <tr>
                            <td>${post.name} </td>
                            <td>${post.description}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%--POST BLOCK END--%>

<%--CANDIDATES BLOCK--%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">Candidates</div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>name</th>
                        <th>photo</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${candidates}" var="candidate">
                        <tr>
                            <td>${candidate.name} </td>
                            <td>
                                <div class="row">
                                    <div class="col">
                                        <img src="<c:url value="/download?name=${candidate.id}"/>"
                                             width="100px" height="100px"
                                             alt="candidate photo"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%--CANDIDATES BLOCK END--%>

</body>
</html>