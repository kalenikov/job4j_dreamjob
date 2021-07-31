<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<jsp:include page='../navBar.jsp'/>
<body>

<div class="container">
    <h2>Upload photo for candidate: <c:out value="${param.id}"/></h2>
    <form action="<c:url value='/upload?fileName=${param.id}' />" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>