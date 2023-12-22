package com.app.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.app.controller.beans.UserMB;

@WebServlet("/APPW3/*")   
public class W3Servlet extends HttpServlet {

    
	volatile Logger log = Logger.getLogger(W3Servlet.class);
	
	private static final long serialVersionUID = -5082917100572104684L;
	
	@Inject
    UserMB usersMB;
	
	

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // first URL request 
    	//supBidEntryMB.setIncominglinkID(request.getPathInfo().substring(1));
    	log.info("Incoming request link id >> " + request.getPathInfo().substring(1));
    	
        request.getRequestDispatcher("/login.html").forward(request, response); 
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Postprocess request: gather and validate submitted data and display the result in the same JSP.

        // Prepare messages.
        Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);

        // Get and validate name.
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            messages.put("name", "Please enter name");
        } else if (!name.matches("\\p{Alnum}+")) {
            messages.put("name", "Please enter alphanumeric characters only");
        }

        // Get and validate age.
        String age = request.getParameter("age");
        if (age == null || age.trim().isEmpty()) {
            messages.put("age", "Please enter age");
        } else if (!age.matches("\\d+")) {
            messages.put("age", "Please enter digits only");
        }

        // No validation errors? Do the business job!
        if (messages.isEmpty()) {
            messages.put("success", String.format("Hello, your name is %s and your age is %s!", name, age));
        }

        request.getRequestDispatcher("/WEB-INF/hello.jsp").forward(request, response);
    }
    
}
