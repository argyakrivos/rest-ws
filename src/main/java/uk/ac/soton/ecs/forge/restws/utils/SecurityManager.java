package uk.ac.soton.ecs.forge.restws.utils;

import java.util.Properties;

import javax.net.ssl.TrustManager;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;

public class SecurityManager {

	private static boolean isSecure = false;

	public static String getBaseUrl() throws Exception {
		Properties p = PropertiesLoader
				.getPropertiesFromFile("config.properties");
		isSecure = p.get("https").equals("on");
		if (isSecure) {
			return "https://localhost:8443/restws";
		} else {
			return "http://localhost:8080/restws";
		}
	}

	public static void setClientAuthentication(Object client) {
		Properties p = PropertiesLoader
				.getPropertiesFromFile("config.properties");
		ClientConfiguration config = WebClient.getConfig(client);
		HTTPConduit httpConduit = (HTTPConduit) config.getConduit();
		if (p.getProperty("user.name") != null
				&& p.getProperty("user.password") != null) {
			AuthorizationPolicy authorization = new AuthorizationPolicy();
			authorization.setUserName(p.getProperty("user.name"));
			authorization.setPassword(p.getProperty("user.password"));
			httpConduit.setAuthorization(authorization);
		}
		TLSClientParameters tlsParams = new TLSClientParameters();
		TrustManager[] trustAllCerts = new TrustManager[] { new FakeTrustManager() };
		tlsParams.setTrustManagers(trustAllCerts);
		// disables verification of the common name (the host for which the certificate has been issued)
		tlsParams.setDisableCNCheck(true);
		httpConduit.setTlsClientParameters(tlsParams);
	}
}
