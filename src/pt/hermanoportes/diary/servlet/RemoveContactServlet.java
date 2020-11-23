package pt.hermanoportes.diary.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.hermanoportes.diary.dao.ContactDAO;
import pt.hermanoportes.diary.model.Contact;

@WebServlet("/RemoveContactServlet")
public class RemoveContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contact contact = new Contact();
		if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
			contact.setId(Long.valueOf(request.getParameter("id")));
			new ContactDAO().delete(contact);
		}
		request.setAttribute("contactList", new ContactDAO().findAll());
		request.getRequestDispatcher("ContactList.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
