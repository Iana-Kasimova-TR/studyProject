<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateTask</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="<%= request.getContextPath() %>/projectStyle.css" rel="stylesheet" type="text/css">
    </link>
</head>
<body>

<script>
function doSubmit()
{
window.transfer("register.jsp","_self");
}
</script>

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
        <button type="submit" id="submit-button" class="btnSubmit" onclick="doSubmit()">Save</button>
    </form>
</section>

</body>
</html>