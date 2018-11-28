<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Projects</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </head>
<body>
<input type="button" class="btnCreate" value="Create new project" onclick="window.location='createProject'"/>
<table>
    <c:forEach var="view" items="${projectViews}">
        <tr>
            <td>
               <a href="tasks?projectID=${view.project.id.value}"><c:out value="${view.project.title}" />
            </td>
            <td>
           <c:out value="${view.status}"/>
            </td>
            <td>
            <c:if test="${view.amountOfCompletedProjectTasks > 0}">
              <p><c:out value="${view.amountOfCompletedProjectTasks}"/> completed tasks</p>
            </c:if>
            <c:if test="${view.amountOfInProgressProjectTasks > 0}">
              <p><c:out value="${view.amountOfInProgressProjectTasks}"/> in progress tasks</p>
            </c:if>
            <c:if test="${view.amountOfTotalProjectTasks > 0}">
              <p><c:out value="${view.amountOfTotalProjectTasks}"/> total tasks</p>
            </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>