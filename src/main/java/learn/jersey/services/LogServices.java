package learn.jersey.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.hadoop.hbase.client.Connection;

import learn.jersey.services.resources.HBaseUtils;
import learn.jersey.services.resources.LogCount;

@ApplicationPath("/services")
public class LogServices extends Application {

	Connection conn;

	public LogServices() {
		conn = new HBaseUtils().getConnection();
	}

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register root resource
		classes.add(LogCount.class);
		return classes;
	}
}