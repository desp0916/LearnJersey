/**
 * Context 事件、傾聽器
 * http://openhome.cc/Gossip/ServletJSP/ContextEventListener.html
 */
package learn.jersey.services.resources;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import learn.jersey.services.Main;

public class HBase {

	public static final String HBASE_TABLE = "aes3g_agg";
	private static final String HBASE_MASTER = "hbase.master";
	private static final String HBASE_ZOOKEEPER_PORT = "hbase.zookeeper.property.clientPort";
	private static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private static final String HBASE_ZOOKEEPER_ZNODE_PARENT = "zookeeper.znode.parent";

	private static Connection connection = null;

	public static Connection getConnection() {
		Configuration config;
		config = HBaseConfiguration.create();
		Properties properties = new Properties();

		try {
			properties.load(Main.class.getResourceAsStream("/config.properties"));
			config.set(HBASE_MASTER, properties.getProperty(HBASE_MASTER));
			config.set(HBASE_ZOOKEEPER_PORT, properties.getProperty(HBASE_ZOOKEEPER_PORT));
			config.set(HBASE_ZOOKEEPER_QUORUM, properties.getProperty(HBASE_ZOOKEEPER_QUORUM));
			config.set(HBASE_ZOOKEEPER_ZNODE_PARENT, properties.getProperty(HBASE_ZOOKEEPER_ZNODE_PARENT));
			System.setProperty("hadoop.home.dir", "/tmp");
			connection = ConnectionFactory.createConnection(config);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return connection;
	}

}
