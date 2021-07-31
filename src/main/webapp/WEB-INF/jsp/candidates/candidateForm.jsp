<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html>
<jsp:include page='../navBar.jsp'/>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                ${param.action == 'NEW' ? 'New candidate' : 'Edit candidate'}
            </div>
            <div class="card-body">
                <form action="candidates.do" method="post">
                    <input type="hidden" name="id" value="${candidate.id}">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name" value="${candidate.name}>">
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>