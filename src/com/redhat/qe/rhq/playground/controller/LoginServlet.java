package com.redhat.qe.rhq.playground.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.redhat.qe.rhq.playground.model.User;
import com.redhat.qe.rhq.playground.service.SamplesService;
import com.redhat.qe.rhq.playground.service.LoginService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService(userName, password);
		if (loginService.isValidUser(userName, password)) {
			User user = loginService.getUser();
			HttpSession session = request.getSession();
			session.setAttribute("user", user.getUserName());
			// setting session to expire in 30 mins
			session.setMaxInactiveInterval(1 * 60);

			Cookie loginCookie = new Cookie("user", user.getUserName());
			// setting cookie to expire in 30 mins
			loginCookie.setMaxAge(1 * 60);
			response.addCookie(loginCookie);

			//get cliSamples List
			HashMap<String,String> cliSamples = SamplesService.listSamples(session.getServletContext().getRealPath(SampleServlet.cliSamplesPath));
			session.setAttribute("cliSamples", cliSamples);
			
			response.sendRedirect("playground.jsp");
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/index.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Either user name or password are empty.</font>");
			rd.include(request, response);
		}
	}
	
}
