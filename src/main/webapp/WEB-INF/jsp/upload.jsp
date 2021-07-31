<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<jsp:include page='navBar.jsp'/>
<body>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>URL</th>
            <th>view</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${images}" var="image" varStatus="status">
            <tr valign="top">
                <td><a href="<c:url value='/download?name=${image}'/>">download</a></td>
                <td><img src="<c:url value="/download?name=${image}"/>" width="100px" height="100px"></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h2>upload</h2>
    <form action="<c:url value='/upload' />" method="post" enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="file">
        </div>
        <button type="submit" class="btn btn-default">submit</button>
    </form>
</div>