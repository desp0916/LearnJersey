package learn.jersey.services.resources;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import learn.jersey.services.Main;

public class HBaseUtils {

	public static final String HBASE_TABLE = "aes3g_agg";

	private static final String HBASE_MASTER = "hbase.master";
	private static final String HBASE_ZOOKEEPER_PORT = "hbase.zookeeper.property.clientPort";
	private static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private static final String HBASE_ZOOKEEPER_ZNODE_PARENT = "zookeeper.znode.parent";
	private static Connection connection;

	public static Connection getConnection() {
		Properties properties = new Properties();
		Configuration config;
		config = HBaseConfiguration.create();

		try {
			properties.load(Main.class.getResourceAsStream("/config.properties"));
			config.set(HBASE_MASTER, properties.getProperty(HBASE_MASTER));
			config.set(HBASE_ZOOKEEPER_PORT, properties.getProperty(HBASE_ZOOKEEPER_PORT));
			config.set(HBASE_ZOOKEEPER_QUORUM, properties.getProperty(HBASE_ZOOKEEPER_QUORUM));
			config.set(HBASE_ZOOKEEPER_ZNODE_PARENT, properties.getProperty(HBASE_ZOOKEEPER_ZNODE_PARENT));
			System.setProperty("hadoop.home.dir", "/tmp");
			connection = ConnectionFactory.createConnection(config);
//			Table table = connection.getTable(TableName.valueOf(table_name));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] args) throws Exception {

		Configuration config;
		config = HBaseConfiguration.create();
		config.set("hbase.master", "hdp01.localdomain:16000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "hdp02.localdomain,hdp01.localdomain,hdp03.localdomain");
		config.set("zookeeper.znode.parent", "/hbase-unsecure");

		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("aes3g_agg"));

		byte[] family = Bytes.toBytes("min");
		byte[] qualifier = Bytes.toBytes("18-5");

		Get get = new Get(Bytes.toBytes("2015-11-23"));
		get.addColumn(family, qualifier);
		Result res1 = table.get(get);

		try {
			System.out.println(Bytes.toLong(res1.getValue(family, qualifier)));

//			byte[] family = Bytes.toBytes("cf");
//			byte[] qualifier = Bytes.toBytes("execTime");
//
//			Scan scan = new Scan();
//			scan.addColumn(family, qualifier);
//			ResultScanner rs = table.getScanner(scan);
//
//			for (int i = 0; i < 10; i++) {
//				for (Result r = rs.next(); r != null; r = rs.next()) {
//					System.out.println(i);
//
//					byte[] valueObj = r.getValue(family, qualifier);
//					String value = new String(valueObj);
//					System.out.println(value);
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			table.close();
			connection.close();
		}
	}

}
