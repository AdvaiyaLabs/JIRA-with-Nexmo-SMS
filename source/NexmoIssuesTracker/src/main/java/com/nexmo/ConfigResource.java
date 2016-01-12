package com.nexmo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class ConfigResource {
	private final UserManager userManager;
	private final PluginSettingsFactory pluginSettingsFactory;
	private final TransactionTemplate transactionTemplate;
	private static final Logger log = LoggerFactory
			.getLogger(ConfigResource.class);

	public ConfigResource(UserManager userManager,
			PluginSettingsFactory pluginSettingsFactory,
			TransactionTemplate transactionTemplate) {
		this.userManager = userManager;
		this.pluginSettingsFactory = pluginSettingsFactory;
		this.transactionTemplate = transactionTemplate;
	}

	public void getSettingsCallBack(Config config) {

	}

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class Config {

		@XmlElement
		private String key;
		@XmlElement
		private String secret;
		@XmlElement
		private String fromuser;
		@XmlElement
		private String fromuserDefault;
		@XmlElement
		private String smsenable;
		@XmlElement
		private String selectedprojects;
		@XmlElement
		private String selectedissuetypes;
		@XmlElement
		private String selectedpriorites;
		@XmlElement
		private String eventissueassigned;
		@XmlElement
		private String eventissueresolved;
		@XmlElement
		private String eventissuereopened;
		@XmlElement
		private String issendtowathers;
		@XmlElement
		private String phonefield;

		
		public String getPhonefield() {
			return phonefield;
		}
		public void setPhonefield(String phonefield) {
			this.phonefield = phonefield;
		}
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getFromuser() {
			return fromuser;
		}

		public void setFromuser(String fromuser) {
			this.fromuser = fromuser;
		}

		public String getSmsenable() {
			return smsenable;
		}

		public void setSmsenable(String smsenable) {
			this.smsenable = smsenable;
		}

		public String getSelectedprojects() {
			return selectedprojects;
		}

		public void setSelectedprojects(String selectedprojects) {
			this.selectedprojects = selectedprojects;
		}

		public String getSelectedissuetypes() {
			return selectedissuetypes;
		}

		public void setSelectedissuetypes(String selectedissuetypes) {
			this.selectedissuetypes = selectedissuetypes;
		}

		public String getSelectedpriorites() {
			return selectedpriorites;
		}

		public void setSelectedpriorites(String selectedpriorites) {
			this.selectedpriorites = selectedpriorites;
		}

		public String getEventissueassigned() {
			return eventissueassigned;
		}

		public void setEventissueassigned(String eventissueassigned) {
			this.eventissueassigned = eventissueassigned;
		}

		public String getEventissueresolved() {
			return eventissueresolved;
		}

		public void setEventissueresolved(String eventissueresolved) {
			this.eventissueresolved = eventissueresolved;
		}

		public String getEventissuereopened() {
			return eventissuereopened;
		}

		public void setEventissuereopened(String eventissuereopened) {
			this.eventissuereopened = eventissuereopened;
		}

		public String getIssendtowathers() {
			return issendtowathers;
		}

		public void setIssendtowathers(String issendtowathers) {
			this.issendtowathers = issendtowathers;
		}

		public void setFromuserDefault(String fromuserDefault) {
			this.fromuserDefault = fromuserDefault;
		}

		public String getFromuserDefault() {
			return fromuserDefault;
		}
		

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpServletRequest request) {

		log.error("GET MET CALLED");
		String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		return Response.ok(
				transactionTemplate.execute(new TransactionCallback() {
					public Object doInTransaction() {
						PluginSettings settings = pluginSettingsFactory
								.createGlobalSettings();
						Config config = new Config();
						config.setKey((String) settings.get(Config.class
								.getName() + ".key"));
						config.setSecret((String) settings.get(Config.class
								.getName() + ".secret"));

						config.setFromuser((String) settings.get(Config.class
								.getName() + ".fromuser"));

						config.setFromuserDefault((String) settings
								.get(Config.class.getName()
										+ ".fromuserDefault"));

						config.setSmsenable((String) settings.get(Config.class
								.getName() + ".smsenable"));
						config.setSelectedprojects((String) settings
								.get(Config.class.getName()
										+ ".selectedprojects"));
						config.setSelectedissuetypes((String) settings
								.get(Config.class.getName()
										+ ".selectedissuetypes"));
						config.setSelectedpriorites((String) settings
								.get(Config.class.getName()
										+ ".selectedpriorites"));
						config.setEventissueassigned((String) settings
								.get(Config.class.getName()
										+ ".eventissueassigned"));
						config.setEventissueresolved((String) settings
								.get(Config.class.getName()
										+ ".eventissueresolved"));
						config.setEventissuereopened((String) settings
								.get(Config.class.getName()
										+ ".eventissuereopened"));
						
						config.setIssendtowathers((String) settings
								.get(Config.class.getName()
										+ ".issendtowathers"));
						
						config.setPhonefield((String) settings
								.get(Config.class.getName()
										+ ".phonefield"));
						IssueCreatedResolvedListener.configSettings = config;
						return config;
					}
				})).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(final Config config, @Context HttpServletRequest request) {

		log.error("PUT MET CALLED");

		String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		return Response.ok(
				transactionTemplate.execute(new TransactionCallback() {

					public Object doInTransaction() {
						PluginSettings pluginSettings = pluginSettingsFactory
								.createGlobalSettings();
						pluginSettings.put(Config.class.getName() + ".key",
								config.getKey().trim());
						pluginSettings.put(Config.class.getName() + ".secret",
								config.getSecret().trim());
						String checking = validateKeys(config.getKey().trim(),
								config.getSecret().trim());

						if (checking.equals("ERROR")) {
							config.setSmsenable("0");
						}

						// config.setFromuser(checking);;
						pluginSettings.put(
								Config.class.getName() + ".fromuser", checking
										+ "");

						pluginSettings.put(Config.class.getName()
								+ ".fromuserDefault",
								config.getFromuserDefault() + "");

						pluginSettings.put(Config.class.getName()
								+ ".smsenable", config.getSmsenable() + "");

						pluginSettings.put(Config.class.getName()
								+ ".selectedprojects",
								config.getSelectedprojects());

						pluginSettings.put(Config.class.getName()
								+ ".selectedissuetypes",
								config.getSelectedissuetypes());

						pluginSettings.put(Config.class.getName()
								+ ".selectedpriorites",
								config.getSelectedpriorites());

						pluginSettings.put(Config.class.getName()
								+ ".eventissueassigned",
								config.getEventissueassigned());

						pluginSettings.put(Config.class.getName()
								+ ".eventissueresolved",
								config.getEventissueresolved());

						pluginSettings.put(Config.class.getName()
								+ ".eventissuereopened",
								config.getEventissuereopened());

						pluginSettings.put(Config.class.getName()
								+ ".issendtowathers",
								config.getIssendtowathers());
						
						pluginSettings.put(Config.class.getName()
								+ ".phonefield",
								config.getPhonefield());
						
						IssueCreatedResolvedListener.configSettings = config;
						return checking;
					}
				})).build();

	}

	public String validateKeys(String apiKey, String apiSecret) {
		try {
			// Here we need to configure settings
			if (apiKey.equals("") == true || apiSecret.equals("") == true) {
				return "ERROR";
			}

			String url = "https://rest.nexmo.com/account/numbers/" + apiKey
					+ "/" + apiSecret + "";
			String from = "ERROR";

			URL google = new URL(url);
			BufferedReader inst = new BufferedReader(new InputStreamReader(
					google.openStream()));
			String inputLine;
			String response = "";

			while ((inputLine = inst.readLine()) != null) {
				// Process each line.
				response += inputLine;

			}

			try {
				JSONObject parent = new JSONObject(response);
				JSONArray numberes = new JSONArray(parent.getString("numbers"));

				// create a loop and return string which contains list of
				// numbers
				from = "";
				for (int i = 0; i < numberes.length(); i++) {

					JSONArray features = new JSONArray(numberes
							.getJSONObject(0).getString("features"));
					String featuresString = "";
					for (int j = 0; j < features.length(); j++) {

						if (j == features.length() - 1) {
							featuresString += features.getString(j) + "";
						} else {
							featuresString += features.getString(j) + ",";
						}

					}
					from += numberes.getJSONObject(i).getString("msisdn") + ":"
							+ featuresString + ";";

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				log.error("ERROR NEXMO : " + response);
				e.printStackTrace();
				return "ERROR";
			}

			log.info("MESSAGE NEXMO : " + response);
			inst.close();
			return from;

		} catch (MalformedURLException me) {
			log.error("ERRROR: " + me);
			return "ERROR";

		} catch (IOException ioe) {
			log.error("ERROR: " + ioe);
			return "ERROR";
		}
	}

}
