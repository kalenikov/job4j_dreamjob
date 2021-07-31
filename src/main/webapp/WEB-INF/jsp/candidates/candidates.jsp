<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html>
<jsp:include page='../navBar.jsp'/>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Candidates
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>name</th>
                        <th>photo</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${candidates}" var="candidate">
                        <tr>
                            <td>${candidate.id}</td>
                            <td>
                                <a href='<c:url value="/candidates.do?id=${candidate.id}&action=EDIT"/>'>
                                    <i class="fa fa-edit mr-3"></i>
                                </a>${candidate.name}
                            </td>
                            <td>
                                <div class="row">
                                    <div class="col">
                                        <img src="<c:url value="/download?name=${candidate.id}"/>"
                                             width="100px" height="100px"
                                             alt="candidate photo"/>
                                    </div>
                                    <div class="col">
                                        <a href='<c:url value="/WEB-INF/jsp/candidates/photoUpload.jsp?id=${candidate.id}"/>'>
                                            <i class="fa fa-plus-circle"></i>
                                        </a>
                                        <a href='<c:url value="/candidates.do?id=${candidate.id}&action=DELETE"/>'>
                                            <i class="fa fa-trash"></i>
                                        </a>
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
</body>
</html>