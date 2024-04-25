<%@ include file="home.jsp" %>
<%@ page import="model.StudentModel" %>
<%@ page import="java.util.List" %>

<sql:setDataSource var="dbConnection" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/college_app" user="root" password="Belbari890" />

<c:set var="username" value="<%=userName %>"></c:set>

<sql:query var="students" dataSource="${dbConnection}">
    SELECT id, first_name, last_name, user_name, dob, gender, email, phone_number, subject, image, password FROM student_info WHERE role='user'
</sql:query>

<sql:query var="userType" dataSource="${dbConnection}">
    SELECT role from student_info WHERE user_name = '${username}'
</sql:query>

<div class="container mt-5">
    <h1 class="mb-4">Registered Students</h1>
    <div class="row">
        <c:forEach var="student" items="${students.rows}">
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/resources/images/${student.image}" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">${student.first_name} ${student.last_name}</h5>
                        <p class="card-text">${student.role}</p>
                        <c:if test="${userType.rows[0].role == 'admin'}">
                            <div class="d-flex">
                                <button type="button" class="btn btn-primary btn-sm mr-2 edit-btn" onclick="populateUpdateModal('${student.id}', '${student.first_name}', '${student.last_name}', '${student.user_name}', '${student.dob}', '${student.gender}', '${student.email}', '${student.phone_number}', '${student.subject}', '${student.image}', 'Hello')">
                                    <i class="fas fa-edit"></i> Edit
                                </button>
                                <form id="deleteForm-${student.user_name}" method="post" action="${pageContext.request.contextPath}/ModifyServlet">
                                    <input type="hidden" name="deleteId" value="${student.id}" />
                                    <button class="btn btn-danger btn-sm delete-btn" role="button" onclick="return confirmDelete('${student.user_name}')">
                                        <i class="fas fa-trash-alt"></i> Delete
                                    </button>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Bootstrap modal for updating student details -->
<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updateModalLabel">Update Student Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="updateForm" action="${pageContext.request.contextPath}/ModifyServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" id="updateId" name="updateId">
                    <div class="form-group">
                        <label for="firstName">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName">
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName">
                    </div>
                    <div class="form-group">
                        <label for="userName">User Name</label>
                        <input type="text" class="form-control" id="userName" name="userName">
                    </div>
                    <div class="form-group">
                        <label for="dob">Date of Birth</label>
                        <input type="date" class="form-control" id="dob" name="dob">
                    </div>
                    <div class="form-group">
                        <label for="gender">Gender</label>
                        <select class="form-control" id="gender" name="gender">
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email">
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Phone Number</label>
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                    </div>
                    <div class="form-group">
                        <label for="subject">Subject</label>
                        <input type="text" class="form-control" id="subject" name="subject">
                    </div>
                    <div class="form-group">
                        <label for="image">Image</label>
                        <input type="file" class="form-control-file" id="image" name="image">
                    </div>
                    <input type="hidden" class="form-control-file" id="password" name="password">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="updateStudent()">Save changes</button>
            </div>
        </div>
    </div>
</div>

<script>

	function confirmDelete(userName) {
		if(confirm("Are you sure you want to delete this user: " + userName + "?")){
			document.getElementById("deleteForm-" + userName).submit();
		}
	}

    function populateUpdateModal(id, firstName, lastName, userName, dob, gender, email, phoneNumber, subject, image, password) {
    	
        document.getElementById('updateId').value = id;
        document.getElementById('firstName').value = firstName;
        document.getElementById('lastName').value = lastName;
        document.getElementById('userName').value = userName;
        document.getElementById('dob').value = dob;
        document.getElementById('gender').value = gender;
        document.getElementById('email').value = email;
        document.getElementById('phoneNumber').value = phoneNumber;
        document.getElementById('subject').value = subject;
        document.getElementById('password').value = password;
        // For image preview (if needed)
        // document.getElementById('imagePreview').src = "${pageContext.request.contextPath}/resources/images/" + image;
        $('#updateModal').modal('show'); // Show the modal
    }

    function updateStudent() {
        document.getElementById('updateForm').submit();
    }
</script>
