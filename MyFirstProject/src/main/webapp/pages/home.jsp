<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession userSession = request.getSession();
    String userName = (String) userSession.getAttribute("username");
    Integer notificationCount = (Integer) userSession.getAttribute("notificationCount");
    if (notificationCount == null) {
        notificationCount = 0;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registered Students</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/footer.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>


<jsp:include page="header.jsp"></jsp:include>

<jsp:include page="footer.jsp"></jsp:include>

<script src="https://kit.fontawesome.com/0d2ae3546c.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    function openUpdateModal(id, firstName, lastName, dob, gender, email, phoneNumber, subject) {
        document.getElementById('updateId').value = id;
        document.getElementById('firstName').value = firstName;
        document.getElementById('lastName').value = lastName;
        document.getElementById('dob').value = dob;
        document.getElementById('gender').value = gender;
        document.getElementById('email').value = email;
        document.getElementById('phoneNumber').value = phoneNumber;
        document.getElementById('subject').value = subject;
        $('#updateModal').modal('show');
    }
    
    
    function updateStudent() {
        var updateData = $('#updateForm').serialize();
        console.log(updateData);
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/ModifyServlet',
            data: updateData,
            success: function(response) {
                // Handle response from server if needed
                // For example, you can display a success message or reload the page
                console.log('Update successful');
                // Reload the page after successful update
                
            },
            error: function(xhr, status, error) {
                // Handle error if needed
                console.error('Error updating student:', error);
                // Display an error message to the user
                alert('Error updating student. Please try again later.');
            }
        });
    }


    function confirmDelete(userName) {
        var result = confirm("Are you sure you want to delete this student?");
        if (result) {
        	document.getElementById("deleteForm-" + userName).submit();
        }
        return false;
    }
</script>

</body>
</html>
