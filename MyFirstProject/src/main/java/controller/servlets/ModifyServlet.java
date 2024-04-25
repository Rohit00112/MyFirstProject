package controller.servlets;

import java.io.IOException;
import java.time.LocalDate;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DatabaseController;
import model.StudentModel;
import util.StringUtils;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
@MultipartConfig()
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseController dbController = new DatabaseController();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String updateId = request.getParameter("updateId");
		String deleteId = request.getParameter("deleteId");
		
		System.out.println(updateId);
		
		
		
		
		if (updateId != null && !updateId.isEmpty()) {
			doPut(request, response);
		}
		if (deleteId != null && !deleteId.isEmpty()) {
			doDelete(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String updateId = request.getParameter("updateId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"));
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String subject = request.getParameter("subject");
        // You may need to handle the image upload separately

        // Create a StudentModel object with the updated information
        StudentModel updatedStudent = new StudentModel();
        updatedStudent.setId(Integer.parseInt(updateId));
        updatedStudent.setFirstName(firstName);
        updatedStudent.setLastName(lastName);
        updatedStudent.setUsername(userName);
        updatedStudent.setDob(dob);
        updatedStudent.setGender(gender);
        updatedStudent.setEmail(email);
        updatedStudent.setPhoneNumber(phoneNumber);
        updatedStudent.setSubject(subject);

        // Update student information in the database
        int result = dbController.updateStudentInfo(updatedStudent);
        if (result == 1) {
            request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_UPDATE_MESSAGE);
            response.sendRedirect(request.getContextPath() + StringUtils.STUDENTS_SERVLET);
        } else {
            request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_UPDATE_MESSAGE);
            response.sendRedirect(request.getContextPath() + StringUtils.STUDENTS_SERVLET);
        }
    }




	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Delete Trigerred");
		int deleteId = Integer.parseInt(request.getParameter("deleteId"));
		if(dbController.deleteStudentInfo(deleteId) == 1 ) {
			request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_DELETE_MESSAGE);
			response.sendRedirect(request.getContextPath() + StringUtils.STUDENTS_SERVLET);
		}
		else {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_DELETE_MESSAGE);
			response.sendRedirect(request.getContextPath() + StringUtils.STUDENTS_SERVLET);
		}
	}

}
