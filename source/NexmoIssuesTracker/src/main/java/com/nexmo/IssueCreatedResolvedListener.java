package com.nexmo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.issuetype.IssueType;

import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.user.ApplicationUser;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.UserPropertyManager;
import com.opensymphony.module.propertyset.PropertySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library and demonstrating
 * plugin lifecycle integration.
 */
public class IssueCreatedResolvedListener implements InitializingBean,
		DisposableBean {

	private static final Logger log = LoggerFactory
			.getLogger(IssueCreatedResolvedListener.class);

	private final EventPublisher eventPublisher;
	public static ConfigResource.Config configSettings;
	ProjectManager projectManager;
	String configuredProjects[];
	String configuredPriorites[];
	String configuredIssueTypes[];

	/**
	 * Constructor.
	 * 
	 * @param eventPublisher
	 *            injected {@code EventPublisher} implementation.
	 */
	public IssueCreatedResolvedListener(EventPublisher eventPublisher,
			ProjectManager projectManager) {
		this.eventPublisher = eventPublisher;
		this.projectManager = projectManager;

	}

	/**
	 * Called when the plugin has been enabled.
	 * 
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// register ourselves with the EventPublisher
		eventPublisher.register(this);
	}

	@Override
	public void destroy() throws Exception {
		// unregister ourselves with the EventPublisher
		eventPublisher.unregister(this);
	}

	public int sendMessage(String receiverNo, String message) {
		try {

			if (configSettings == null) {

				log.error("Please configure the Nexmo Configuration settings");
				return 0;

			}
			String key = configSettings.getKey();
			String secret = configSettings.getSecret();
			String from = configSettings.getFromuserDefault();

			String messageEncoded = URLEncoder.encode(message, "UTF-8");
			URL google = new URL("https://rest.nexmo.com/sms/xml?api_key="
					+ key + "&api_secret=" + secret + "&from=" + from + "&to="
					+ receiverNo + "&text=" + messageEncoded);

			BufferedReader inst = new BufferedReader(new InputStreamReader(
					google.openStream()));
			String inputLine;
			String response = "";

			while ((inputLine = inst.readLine()) != null) {

				response += inputLine;

			}

			inst.close();
			return 1;

		} catch (MalformedURLException me) {
			log.error(me.getMessage());
			return 0;

		} catch (IOException ioe) {
			log.error(ioe.getMessage());
			return 0;
		}
	}

	public boolean isProjectExists(String projectkey) {
		try {

			configuredProjects = configSettings.getSelectedprojects()
					.split(";");

			for (int i = 0; i < configuredProjects.length; i++) {

				if (projectkey.toLowerCase().equals(
						configuredProjects[i].toLowerCase())) {
					return true;
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

		return false;
	}

	public boolean isPriorityExists(String priority) {
		try {
			configuredPriorites = configSettings.getSelectedpriorites().split(
					";");

			for (int i = 0; i < configuredPriorites.length; i++) {

				if (priority.toLowerCase().equals(
						configuredPriorites[i].toLowerCase())) {
					return true;
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

		return false;
	}

	public boolean isIssueTypeExists(String type) {
		try {
			configuredIssueTypes = configSettings.getSelectedissuetypes()
					.split(";");

			for (int i = 0; i < configuredIssueTypes.length; i++) {

				if (type.toLowerCase().equals(
						configuredIssueTypes[i].toLowerCase())) {
					return true;
				}

			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	public void requestNotification(IssueEvent issueEvent) {

		try {

			Long eventTypeId = issueEvent.getEventTypeId();
			Issue issue = issueEvent.getIssue();
			UserPropertyManager userPropManager = ComponentAccessor
					.getUserPropertyManager();

			ApplicationUser reporter = issue.getReporter();
			ApplicationUser assignee = issue.getAssignee();
			IssueType issueType = issue.getIssueType();
			PropertySet reporterPropertySet = userPropManager
					.getPropertySet(reporter);
			PropertySet assigneePropertySet = userPropManager
					.getPropertySet(assignee);

			String key = "jira.meta." + configSettings.getPhonefield();
			String reporterNo = reporterPropertySet.getString(key);
			String assigneeNo = assigneePropertySet.getString(key);

			if (eventTypeId.equals(EventType.ISSUE_CREATED_ID)) {

				// this event is called when issue is created and also assigned
				// to user
				if (assignee != null && assigneeNo != null) {

					if (configSettings.getEventissueassigned().equals("1") == true) {

						String issueSummary = issue.getSummary();
						if (issueSummary.length() > 70) {

							issueSummary = issueSummary.substring(0, 65)
									+ "...";

						}
						sendMessage(assigneeNo,
								"Hi " + assignee.getDisplayName() + ", a new "
										+ issueType.getName() + " \""
										+ issueSummary
										+ "\" is assigned to you, reported by "
										+ reporter.getDisplayName()+".");
					}
					// log.info("Issue {} has been assigned at {}.",
					// issue.getKey(), issue.getCreated());
				} else {
					log.info("Assignee is not configured");
				}
			}
			if (eventTypeId.equals(EventType.ISSUE_ASSIGNED_ID)) {

				if (assignee != null && assigneeNo != null) {

					if (configSettings.getEventissueassigned().equals("1") == true) {

						String issueSummary = issue.getSummary();
						if (issueSummary.length() > 70) {

							issueSummary = issueSummary.substring(0, 65)
									+ "...";

						}
						sendMessage(assigneeNo,
								"Hi " + assignee.getDisplayName() + ", a new "
										+ issueType.getName() + " \""
										+ issueSummary
										+ "\" is assigned to you, reported by "
										+ reporter.getDisplayName()+".");
					}
				} else {
					log.info("Assignee is not configured");
				}

			} else if (eventTypeId.equals(EventType.ISSUE_RESOLVED_ID)) {

				if (reporter != null && reporterNo != null) {

					if (configSettings.getEventissueresolved().equals("1") == true) {

						String issueSummary = issue.getSummary();
						if (issueSummary.length() > 70) {

							issueSummary = issueSummary.substring(0, 65)
									+ "...";

						}
						sendMessage(reporterNo,
								"Hi " + reporter.getDisplayName() + ", the "
										+ issueType.getName() + " \""
										+ issueSummary
										+ "\" reported by you is resolved by  "
										+ assignee.getDisplayName()+".");
					}

				} else {
					log.info("Reporter is not configured");
				}
			} else if (eventTypeId.equals(EventType.ISSUE_REOPENED_ID)) {

				if (assignee != null && assigneeNo != null) {

					if (configSettings.getEventissuereopened().equals("1") == true) {

						String issueSummary = issue.getSummary();
						if (issueSummary.length() > 70) {

							issueSummary = issueSummary.substring(0, 65)
									+ "...";

						}
						sendMessage(
								assigneeNo,
								"Hi "
										+ assignee.getDisplayName()
										+ ", the "
										+ issueType.getName()
										+ " \""
										+ issueSummary
										+ "\" is reopened and assigned to you by "
										+ reporter.getDisplayName()+".");

					}
				} else {
					log.info("Assignee is not configured");
				}

			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	
	@EventListener
	public void onIssueEvent(IssueEvent issueEvent) {

		try {

			if (configSettings.getSmsenable().equals("1") == true) {

				String projectKey = issueEvent.getIssue().getProjectObject()
						.getKey();
				String issueType = issueEvent.getIssue().getIssueType()
						.getName();
				String issuePriority = issueEvent.getIssue().getPriority()
						.getName();

				if (isProjectExists(projectKey) == true) {

					if (isIssueTypeExists(issueType) == true) {

						if (isPriorityExists(issuePriority) == true) {

							requestNotification(issueEvent);

						}

					}

				}

			} else {
				log.info("SMSs are disabled");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}