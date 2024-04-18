package controller.servlets;

import java.io.IOException;
import java.net.HttpCookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.DatabaseController;
import model.StudentModel;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.LOGIN_SERVLET)
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    public LoginServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	 // Increase the notification count
        HttpSession session = req.getSession();
        Integer notificationCount = (Integer) session.getAttribute("notificationCount");
        if (notificationCount == null) {
            notificationCount = 0;
        }
        notificationCount++;
        session.setAttribute("notificationCount", notificationCount);
        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(notificationCount));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter(StringUtils.USER_NAME);
        String password = request.getParameter(StringUtils.PASSWORD);

        // Check login credentials
        int loginResult = dbController.getStudentLoginInfo(userName, password);

        if (loginResult == 1) {

            HttpSession userSession = request.getSession();
            userSession.setAttribute("username", userName);
            userSession.setMaxInactiveInterval(30 * 3);

            Cookie userCookie = new Cookie("user", userName);
            userCookie.setMaxAge(30 * 60);
            response.addCookie(userCookie);
            

            StudentModel student = null;
            try {
                student = dbController.getStudentDetails(userName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            

            if (student != null) {
                request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_LOGIN_MESSAGE);
                request.getRequestDispatcher(StringUtils.HOME_PAGE).forward(request, response);
            } else {
                request.setAttribute(StringUtils.ERROR_MESSAGE, "Student details not found");
                request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
            }
        } else if (loginResult == 0) {
            request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_LOGIN_MESSAGE);
            request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
        } else {
            request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
            request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
        }
    }
}
