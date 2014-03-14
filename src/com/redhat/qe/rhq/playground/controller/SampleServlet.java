package com.redhat.qe.rhq.playground.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.redhat.qe.jon.clitest.tasks.CliTasksException;
import com.redhat.qe.rhq.playground.service.SamplesService;

@WebServlet("/sample")
public class SampleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	final static String cliSamplesPath = File.separator + "WEB-INF"
			+ File.separator + "cliSamples";

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		if (request.getSession().getAttribute("user") != null) {
			// get cliSamples List
			HashMap<String, String> cliSamples = (HashMap<String, String>) request
					.getSession().getAttribute("cliSamples");
			String sample = SamplesService.readFile(cliSamples.get(name));
			sample = StringEscapeUtils.escapeHtml3(sample);
			request.getSession().setAttribute("sample", sample);
			response.getWriter().write(sample);
		} else {
			response.sendRedirect("index.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			if (request.getParameter("sample") != null
					&& !request.getParameter("sample").toString().isEmpty()) {
				boolean writeSuccess = SamplesService.writeFile(request
						.getParameter("sample"));
				if (writeSuccess) {
						String result = SamplesService.runSample();
						response.getWriter().write(result);
					
				}

				System.out.println("writeSuccess  " + writeSuccess);
				System.out.println("test.js = "
						+ SamplesService.readFile("test.js"));
			}
		} catch (CliTasksException e) {
			
			e.printStackTrace();
		}


	}

	public static void main(String[] args) throws IOException {
		String name = "creatingResGroupsAndAddingMembers.js";
		HashMap<String, String> cliSamples = SamplesService
				.listSamples("WebContent/" + cliSamplesPath);
		String sample = SamplesService.readFile(cliSamples.get(name));
		sample = StringEscapeUtils.escapeHtml3(sample);
		System.out.println(sample);

	}
}
