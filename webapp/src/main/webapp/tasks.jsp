<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Tasks</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </head>
<body>
<table>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td>
              <c:out value="${task.title}" />
              <c:out value="${task.description}" />
            </td>
        </tr>
    </c:forEach>
</table>
<div class="wrapper">
<input type="button" class="btnCreate" value="Create new task" onclick="location.href='createTask?projectID=${projectID}'"/>
</div>
</body>
</html>