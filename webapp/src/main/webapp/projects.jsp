<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Projects</title>
    <link href="http://localhost:8090/webapp-1.0-SNAPSHOT/projectStyle.css" rel="stylesheet" type="text/css">
    </head>
<body>
<table>
    <c:forEach var="project" items="${projects}">
        <tr>
            <td>
               <a href="tasks?projectID=${project.id}"><c:out value="${project.title}" />
            </td>
        </tr>
    </c:forEach>
</table>
<div class="wrapper">
<input type="button" class="btnCreate" value="Create new project" onclick="window.location='createProject'"/>
</div>
</body>
</html>