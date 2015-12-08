/**
 * http://www.codedata.com.tw/java/jax-rs-2-0-xml-json-magic-moxy-jackson/
 *
 * https://jersey.java.net/apidocs/2.9/jersey/org/glassfish/jersey/grizzly2/httpserver/GrizzlyHttpServerFactory.html
 * http://stackoverflow.com/questions/11887321/running-jersey-on-grizzly-on-linux-and-windows
 */

package learn.jersey.services;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import learn.jersey.services.resources.HBaseUtils;

public class Main {

	private static int getPort(int defaultPort) {
		Properties properties = new Properties();
		try {
			properties.load(Main.class.getResourceAsStream("/config.properties"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return Integer.parseInt(properties.getProperty("grizzly.port", Integer.toString(defaultPort)));
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(getPort(12344)).build();
	}

	private static final URI BASE_URI = getBaseURI();

	protected static HttpServer startServer() throws IOException {
		System.out.println("Starting grizzly...");
		ResourceConfig rc = new ResourceConfig().packages("learn.jersey.services.resources")
				.register(MoxyJsonFeature.class);
		return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
	}

	public static void main(String[] args) throws IOException {
		if (HBaseUtils.initHBase()) {
			HttpServer httpServer = startServer();
			System.out.println(String.format(
					"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
					BASE_URI, BASE_URI));
			System.in.read();
		} else {
			System.out.println("The service layer assumes that Keyspace and server are already in place.\n"
					+ " Please run the topology first and ensure that the settings are correct.");
		}
	}
}
