<%@ include file="home.jsp" %>
<%@ page import="model.StudentModel" %>
<%@ page import="java.util.List" %>
<%-- 
<div class="container mt-5">
    <h1 class="mb-4">Registered Students</h1>
    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th scope="col">First Name</th>
                <th scope="col">Last Name</th>
                <th scope="col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<StudentModel> students = (List<StudentModel>) request.getAttribute("students");
                if (students != null && !students.isEmpty()) {
                    for (StudentModel student : students) {
            %>
            <tr>
                <td><%= student.getFirstName() %></td>
                <td><%= student.getLastName() %></td>
                <td>
                    <a href="#" id="edit" class="btn btn-primary btn-sm mr-2" role="button"><i class="fas fa-edit"></i> Edit</a>
                    <a href="#" class="btn btn-danger btn-sm" role="button"><i class="fas fa-trash-alt"></i> Delete</a>
                </td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="3">No students found</td>
            </ tr>
            <% 
                }
            %>
        </tbody>
    </table>
</div>
 --%>
 
 
    

<sql:setDataSource var="dbConnection" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/college_app" user="root" password="Belbari890" />

<c:set var="username" value="<%=userName %>"></c:set>

<sql:query var="students" dataSource="${dbConnection}">
    SELECT id, first_name, last_name, user_name, dob, gender, email, phone_number, subject, image FROM student_info WHERE role="user"
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
                                <button type="button" class="btn btn-primary btn-sm mr-2 edit-btn" onclick="openUpdateModal('${student.id}', '${student.first_name}', '${student.last_name}', '${student.dob}', '${student.gender}', '${student.email}', '${student.phone_number}', '${student.subject}')">
                                    <i class="fas fa-edit"></i> Edit
                                </button>
                                <form id="deleteForm-${student.user_name}" method="post" action="${pageContext.request.contextPath}/ModifyServlet">
                                    <input type="hidden" name="deleteId" value="${student.id}" />
                                    <button class="btn btn-danger btn-sm delete-btn" role="button"
                                        onclick="return confirmDelete('${student.user_name}')">
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
                <form id="updateForm">
                    <input type="hidden" id="updateId" name="id">
                    <div class="form-group">
                        <label for="firstName">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName">
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName">
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
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="updateStudent()">Save changes</button>
            </div>
        </div>
    </div>
</div>