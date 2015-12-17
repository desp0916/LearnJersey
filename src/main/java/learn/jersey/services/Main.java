/**
 * http://www.codedata.com.tw/java/jax-rs-2-0-xml-json-magic-moxy-jackson/
 *
 * https://jersey.java.net/apidocs/2.9/jersey/org/glassfish/jersey/grizzly2/httpserver/GrizzlyHttpServerFactory.html
 * http://stackoverflow.com/questions/11887321/running-jersey-on-grizzly-on-linux-and-windows
 * http://stackoverflow.com/questions/20924739/grizzly-server-with-static-content-and-rest-resource
 * http://www.mkyong.com/java/how-to-print-out-the-current-project-classpath/
 * http://stackoverflow.com/questions/17711924/disable-cross-domain-web-security-in-firefox
 *
 * http://localhost:29998/services/
 */

package learn.jersey.services;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import org.apache.hadoop.hbase.client.Connection;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import learn.jersey.services.resources.HBase;

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
		return UriBuilder.fromUri("http://localhost/services/").port(getPort(12344)).build();
	}

	private static final URI BASE_URI = getBaseURI();

	protected static HttpServer startServer() throws IOException {
		System.out.println("Starting grizzly...");
		ResourceConfig rc = new ResourceConfig().packages("learn.jersey.services.resources")
				.register(MoxyJsonFeature.class);
		return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
	}

	public static void main(String[] args) throws IOException {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }

		Connection conn = HBase.getConnection();
		if (conn != null) {
			HttpServer httpServer = startServer();
//			HttpHandler httpHandler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/webapp/");
//			httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/");
//			httpServer.getServerConfiguration().addHttpHandler(
//					new CLStaticHttpHandler(
//							new URLClassLoader(new URL[] { new URL("file:///home/username/staticfiles.jar") })),
//					"/");
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
