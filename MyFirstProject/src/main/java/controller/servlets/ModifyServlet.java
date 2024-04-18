package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
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



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String updateId = request.getParameter("updateId");
		String deleteId = request.getParameter("deleteId");
		
		if (updateId != null && !updateId.isEmpty()) {
			doPut(request, response);
		}
		if (deleteId != null && !deleteId.isEmpty()) {
			doDelete(request, response);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter(req.getParameter("updateId"));
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		
		StudentModel updatedStudent = new StudentModel();
		
		updatedStudent.setId(Integer.parseInt(id));
		updatedStudent.setFirstName(firstName);
		updatedStudent.setLastName(lastName);
		
		int result = dbController.updateStudent(updatedStudent);
		
		System.out.println(result);
		
		if (result == 1) {
            // Success
            req.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_UPDATE_MESSAGE);
            resp.sendRedirect(req.getContextPath() + StringUtils.HOME_PAGE);
        } else {
            // Error
            req.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_UPDATE_MESSAGE);
            resp.sendRedirect(req.getContextPath() + StringUtils.HOME_PAGE);
        }
		
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Delete Trigerred");
		int id = Integer.parseInt(req.getParameter("deleteId"));
		if(dbController.deleteStudentInfo(id)==1) {
			req.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_DELETE_MESSAGE);
			resp.sendRedirect(req.getContextPath() + StringUtils.HOME_PAGE);
		}
		else {
			req.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_DELETE_MESSAGE);
			resp.sendRedirect(req.getContextPath() + StringUtils.HOME_PAGE);
		}
	}

}
