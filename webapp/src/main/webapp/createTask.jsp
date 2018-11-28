<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateTask</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </link>
</head>
<body>


<section>
    <form method="post" action="<%= request.getContextPath() %>/createTask">
        <dl>
            <dt>Title: </dt>
            <dd><input type="text" class="inputText" id="title-field" name="title" placeholder="${title}" /></dd>
        </dl>
        <dl>
            <dt>Description: </dt>
            <dd><input type="text" class="inputText" name="description" id="description-field" value="${defaultDescription}" placeholder="${description}" /></dd>
        </dl>
        <button type="submit" id="submit-button" class="btnSubmit">Save</button>
    </form>
</section>

</body>
</html>