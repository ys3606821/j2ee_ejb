package phasebook.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;

import phasebook.post.PostRemote;
import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class CreateUserForm
 */
public class LoginUserForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginUserForm() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		InitialContext ctx = null;
		HttpSession session = request.getSession();
		try {
			ctx = new InitialContext();
			PhasebookUserRemote user;
						
			user = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			String error = formValidation(email, password);
			if (error != null)
			{
				session.setAttribute("error", error);
				System.out.println(error);
				response.sendRedirect(Utils.url("login"));
			}
			else
			{
				password = Utils.byteArrayToHexString(Utils.computeHash(password + "salt" + email));
				
				int id = user.login(email, password);
				if(id!=-1){
					session.setAttribute("id", id);
					session.setAttribute("password", password);
					response.sendRedirect(Utils.url(""));
				}
				else
				{
					session.setAttribute("error", "Wrong email or password");
					response.sendRedirect(Utils.url("login"));
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			response.sendRedirect(Utils.url("login"));
		}
		
	}
	
	private String formValidation(String email, String password)
	{
		
		// Email can't be blank
		if (email == null || email.length() == 0)
		{
			return "Can't login without an email";
		}
		
		// Email must be valid
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		if (!m.matches())
		{
			return "Can't login without a valid email";
		}
		
		// Password can't be blank
		if (password == null || password.length() == 0)
		{
			return "Can't login without a password";
		}
		
		return null;
	}

}
