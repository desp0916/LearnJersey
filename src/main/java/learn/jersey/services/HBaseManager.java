package learn.jersey.services;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseManager extends Thread {

	public Configuration config;
	public HTable table;
	public HBaseAdmin admin;

	public HBaseManager() {
		config = HBaseConfiguration.create();
		config.set("hbase.master", "hdp01.localdomain:16000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "hdp01.localdomain,hdp02.localdomain,hdp03.localdomain");
		try {
			table = new HTable(config, Bytes.toBytes("aes3g"));
//			table = new Connection(config, Bytes.toBytes("aes3g_agg"));
			admin = new HBaseAdmin(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
