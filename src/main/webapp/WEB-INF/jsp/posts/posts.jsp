<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!doctype html>
<html>
<jsp:include page='../navBar.jsp'/>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Posts
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>name</th>
                        <th>description</th>
                        <th>created</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${posts}" var="post">
                        <tr>
                            <td>${post.id}</td>
                            <td>${post.name}
                                <a href='<c:url value="/posts.do?id=${post.id}&action=EDIT"/>'>
                                    <i class="fa fa-edit mr-3"></i></a>
                            </td>
                            <td>${post.description}</td>
                            <td><fmt:formatDate value="${post.created}" pattern="yyyy-MM-dd HH:mm"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>