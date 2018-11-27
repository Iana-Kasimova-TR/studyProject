<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateProject</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="<%= request.getContextPath() %>/projectStyle.css" rel="stylesheet" type="text/css">
    </link>
</head>
<body>

<script>
    document.addEventListener('DOMContentLoaded', function(){
	const submitButton = document.getElementById('submit-button');
	const titleField = document.getElementById('title-field');
	const descriptionField = document.getElementById('description-field');

	submitButton.disabled = true;

	const fields = [];
	fields.push(titleField);
	fields.push(descriptionField);

	for (var i = 0; i < fields.length; i++) {
	    const field = fields[i];
	    field.addEventListener('keyup', function(fArray, event){
		let result = true;
		for (var j = 0; j < fArray.length; j++) {
		    const element = fArray[j];
		    if (element.value === '') {
			result = false;
		    }
		}
		submitButton.disabled = !result;
	    }c.bind(this, fields));
	}
    }
    submitButton.addEventListener('keyup', function(){
    window.transfer("projects.jsp","_self");
    }
    });
</script>

<section>
    <form method="post" action="<%= request.getContextPath() %>/createProject">
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