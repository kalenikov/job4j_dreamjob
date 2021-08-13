<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html>
<jsp:include page='../navBar.jsp'/>
<title>Dreamjob: candidate form</title>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
<script>
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            crossdomain: true,
            url: 'http://localhost:8080/dreamjob/cities',
            dataType: 'json'
        }).done(data => {
            let options = '<option></option>'
            $.each(data, (i, city) => {
                options += '<option value="' + city.id + '">' + city.name + '</option>';
            });
            $('#cities').html(options);
        }).fail(err => console.log(err));
    })

    $(document).ready(function () {
        $('#form').validate({
            rules: {
                name: "required",
                city_id: "required",
            },
            messages: {
                name: "Please specify candidate name",
                city_id: "Please specify the city",
            },
            submitHandler: form => form.submit()
        });
    });
</script>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                ${param.action == 'NEW' ? 'New candidate' : 'Edit candidate'}
            </div>
            <div class="card-body">
                <form action="candidates.do" method="post" id="form">
                    <input type="hidden" name="id" value="${candidate.id}">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name" value="${candidate.name}">
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2">City</label>
                        <div class="col-lg-4">
                            <select class="form-control" id="cities" name="city_id">
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>