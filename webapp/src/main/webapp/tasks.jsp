<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Tasks</title>
    <link href="http://localhost:8090/webapp-1.0-SNAPSHOT/projectStyle.css" rel="stylesheet" type="text/css">
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
<input type="button" class="btnCreate" value="Create new task" onclick="window.location='createTask'"/>
</div>
</body>
</html>