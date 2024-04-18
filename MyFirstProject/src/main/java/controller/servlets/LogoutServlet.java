package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Clear existing cookies
	    Cookie[] cookies = request.getCookies();
	    if(cookies != null) {
	        for (Cookie cookie: cookies) {
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
	    }

	    // Invalidate session
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }

	    // Set JSESSIONID cookie to empty
	    Cookie jsessionCookie = new Cookie("JSESSIONID", "");
	    jsessionCookie.setMaxAge(0);
	    response.addCookie(jsessionCookie);

	    // Redirect to login page
	    response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
	}


}
