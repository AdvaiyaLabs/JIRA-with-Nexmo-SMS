package com.nexmo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.component.ComponentLocator;
import com.atlassian.sal.api.net.RequestFactory;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueFieldConstants;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.priority.Priority;
import com.atlassian.jira.project.ProjectManager;
import com.google.common.collect.Maps;

public class AdminServlet extends HttpServlet {

	// private final RequestFactory<?> requestFactory;

	String allUsers = "";

	private final UserManager userManager;
	private final LoginUriProvider loginUriProvider;
	private final TemplateRenderer renderer;
	// private final ProjectManager projectManager;
	ProjectManager projectManager;
	private static final Logger log = LoggerFactory
			.getLogger(AdminServlet.class);
	public String allErrors = "";

	public AdminServlet(UserManager userManager,
			LoginUriProvider loginUriProvider, TemplateRenderer renderer,
			ProjectManager projectManager) {

		this.userManager = userManager;
		this.loginUriProvider = loginUriProvider;
		this.renderer = renderer;
		this.projectManager = projectManager;

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		
		String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username))
		{
			redirectToLogin(request, response);
			return;
		}
		Collection<IssueType> listIssueType = (Collection<IssueType>) ComponentAccessor
				.getConstantsManager().getAllIssueTypeObjects();

		Map<String, Object> context = Maps.newHashMap();
		context.put("projectManager", projectManager);
		context.put("contextpath", request.getContextPath());
		context.put("listIssueType", listIssueType);
		Collection<Priority> priorites = (Collection<Priority>) ComponentAccessor
				.getConstantsManager().getPriorities();

		context.put("priorites", priorites);

		renderer.render("admin.vm", context, response.getWriter());

	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(loginUriProvider.getLoginUri(getUri(request))
				.toASCIIString());
	}

	private URI getUri(HttpServletRequest request) {
		StringBuffer builder = request.getRequestURL();
		if (request.getQueryString() != null) {
			builder.append("?");
			builder.append(request.getQueryString());
		}
		return URI.create(builder.toString());
	}
}
