package com.redhat.qe.rhq.playground.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.redhat.qe.rhq.playground.service.SamplesService;


@WebServlet("/main")
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
		if (request.getSession().getAttribute("user") != null) {
			// get cliSamples List
			HashMap<String, String> cliSamples = (HashMap<String, String>) request
					.getSession().getAttribute("cliSamples");
			String sample = SamplesService.readFile(cliSamples.get(name));
			System.out.println("sample is this ->>>> "+sample);
			sample = StringEscapeUtils.escapeHtml3(sample);
			request.getSession().setAttribute("sample", sample);
			response.getWriter().write(sample);
		} else {
			response.sendRedirect("index.jsp");
		}
	}

		  
		 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  // TODO Auto-generated method stub
		  
		 }


}
