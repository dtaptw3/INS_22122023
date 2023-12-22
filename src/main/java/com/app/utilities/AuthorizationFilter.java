package com.app.utilities;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.app.controller.beans.UserMB;

@WebFilter("/secured/*")
public final class AuthorizationFilter implements Filter {

	volatile Logger log = Logger.getLogger(AuthorizationFilter.class);

	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

	@Inject
	UserMB usersMB;


	@Override
	public void destroy() {
		try {

			final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			externalContext.redirect(externalContext.getRequestContextPath() + "/index.html?faces-redirect=true");
		} catch (IOException e) {
			log.info(e.toString());
		}
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpSession session = request.getSession(false);
		String loginURL = request.getContextPath() + "/index.html";

		try {

			if (session == null) {
				//response.sendRedirect(request.getContextPath() + "/timeout.html");
				response.sendRedirect(loginURL);
				log.info("session timeout");
				return;
			}

			final String currentSessionId = session.getId();
			final boolean loggedIn = (session != null) && (session.getAttribute("user") != null);
			final boolean loginRequest = request.getRequestURI().equals(loginURL);

			boolean resourceRequest = request.getRequestURI()
					.startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");

			// boolean resourceRequest = false;
			if (request.getRequestURI().contains("secured")) {
				resourceRequest = true;
			} else {
				resourceRequest = false;
			}

			final boolean ajaxRequest = "partial/ajax".equals(request.getHeader("Faces-Request"));
			boolean validSession = false;

			if (usersMB.getCurrentUser() == null) {
				validSession = false;
			} else {
				validSession = usersMB != null && usersMB.getCurrentUser() != null
						&& usersMB.getCurrentUser().getSessionID() != null
						&& usersMB.getCurrentUser().getSessionID().equals(currentSessionId);
			}

			if (loggedIn || loginRequest || resourceRequest || validSession) {
				if (resourceRequest && validSession) {

					if (!request.getRequestURI()
							.startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
						response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					}

					chain.doFilter(request, response); // continue request.
				} else if (resourceRequest) {
					log.info("Invalid session. Redirecting...");
					

					final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
					FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
					response.sendRedirect(loginURL);
					//externalContext.redirect(externalContext.getRequestContextPath() + "/index.html");
				}

			} else if (ajaxRequest) {
				response.setContentType("text/xml");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().printf(AJAX_REDIRECT_XML, loginURL); // So, return special XML response instructing
																			// JSF ajax to send a redirect.
			} else {
				response.sendRedirect(loginURL); // So, just perform standard synchronous redirect.
				log.info("Invalid request. Redirecting...");
				PrimeFaces.current().executeScript("location.reload();");

				final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); 
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				externalContext.redirect(externalContext.getRequestContextPath() + "/index.html?faces-redirect=true");
			}
		} catch (Exception e) {
			log.info(e.toString());
		}

	}

	@Override
	public void init(final FilterConfig fConfig) throws ServletException {
		System.out.println("inside Auth filter init ");
	}

}
