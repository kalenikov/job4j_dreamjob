<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html>
<jsp:include page='navBar.jsp'/>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Registration form
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name">
                        <label>Email</label>
                        <input type="text" class="form-control" name="email">
                        <label>Password</label>
                        <input type="password" class="form-control" name="password">
                    </div>
                    <c:if test="${not empty requestScope.errorMessage}">
                        <div style="color:red; font-weight: bold;">
                                ${requestScope.errorMessage}
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Register</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>