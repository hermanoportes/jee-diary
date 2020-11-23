package pt.hermanoportes.diary.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.hermanoportes.diary.dao.ContactDAO;

@WebServlet("/ContactFormServlet")
public class ContactFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
			request.setAttribute("contact", new ContactDAO().getById(Long.valueOf(request.getParameter("id"))));
		}
		
		request.getRequestDispatcher("ContactForm.jsp").forward(request, response);
	}

}
