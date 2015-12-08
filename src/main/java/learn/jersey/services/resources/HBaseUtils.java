package learn.jersey.services.resources;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtils {

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
		byte[] qualifier = Bytes.toBytes("18-7");

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
