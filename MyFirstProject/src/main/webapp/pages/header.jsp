<%
    
    String cookieUsername = null;
	Cookie[] cookies = request.getCookies();
	
	if(cookies!=null) {
		for (Cookie cookie: cookies) {
			if (cookie.getName().equals("user")) cookieUsername = cookie.getValue();
		}
	}
	HttpSession userSession = request.getSession();
	  Integer notificationCount = (Integer) userSession.getAttribute("notificationCount"); // Retrieve notification count from session
	    if (notificationCount == null) {
	        notificationCount = 0; // Initialize notification count if not found in session
	    }

%>

<header id="header" class="bg-dark py-3">
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <a class="navbar-brand" href="#">
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Logo">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                            href="${pageContext.request.contextPath}/pages/home.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/StudentServlet">Students</a>
                    </li>
                    
                    <% if (cookieUsername != null) { %>
                        <li class="nav-item">
                            <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/LogoutServlet"
                                method="post">
                                <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Logout</button>
                            </form>
                        </li>
                    <% } else { %>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/pages/login.jsp">Login</a>
                        </li>
                    <% } %>
                    
                    <!-- Notification bell icon with badge -->
                    <li class="nav-item">
                        <a class="nav-link" href="#" id="notificationBell">
                            <i class="fa fa-bell"></i>
                            <span class="badge bg-primary">
                                <% if (notificationCount > 0) { %>
                                    <%= notificationCount %>
                                <% } %>
                            </span>
                        </a>
                    </li>
                    
                </ul>
            </div>
        </nav>
    </div>
</header>

<!-- Modal for displaying notification contents -->
<div class="modal fade" id="notificationModal" tabindex="-1" aria-labelledby="notificationModalLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="notificationModalLabel">Notifications</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Notification contents will be displayed here -->
                <p>Notification 1</p>
                <p>Notification 2</p>
                <p>Notification 3</p>
                <!-- Add more notification contents dynamically -->
            </div>
        </div>
    </div>
</div>

<!-- JavaScript to handle the click event for the notification bell -->
<script>
    document.getElementById("notificationBell").addEventListener("click", function(event) {
        event.preventDefault();
        // Show the notification modal
        $('#notificationModal').modal('show');
    });
</script>
