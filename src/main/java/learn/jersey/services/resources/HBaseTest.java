package learn.jersey.services.resources;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTest {

	// cells in the multiple rows
	public static void testAES3G() throws Exception {
		Connection conn = HBase.getConnection();
		Table table = conn.getTable(TableName.valueOf("aes3g"));

		try {

			Scan scan = new Scan();

			byte[] family = Bytes.toBytes("cf");
			byte[] qExecTime = Bytes.toBytes("execTime");
			byte[] qMessage = Bytes.toBytes("message");

			scan.addColumn(family, qExecTime);
			scan.addColumn(family, qMessage);

			ResultScanner rs = table.getScanner(scan);

			int a = 1;
			for (Result r = rs.next(); r != null; r = rs.next()) {
				String sExecTime = new String(r.getValue(family, qExecTime));
				String sMessage = new String(r.getValue(family, qMessage));
				System.out.println("execTime: " + sExecTime + ", message: " + sMessage);
				a++;
			}
			System.out.println("Total of the result: " + a);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			table.close();
			conn.close();
		}
	}

	// single cell value
	public static void testAES3GAGG() throws Exception {
		Connection conn = HBase.getConnection();
		Table table = conn.getTable(TableName.valueOf("aes3g_agg"));

		byte[] family = Bytes.toBytes("min");
		byte[] qualifier = Bytes.toBytes("18-4");

		Get get = new Get(Bytes.toBytes("2015-11-23"));
		get.addColumn(family, qualifier);
		Result res1 = table.get(get);

		try {
			System.out.println(Bytes.toLong(res1.getValue(family, qualifier)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			table.close();
			conn.close();
		}
	}

	public static void testOpenTSDB() throws Exception {
		Connection conn = HBase.getConnection();
		Table table = conn.getTable(TableName.valueOf("tsdb"));

		try {

			byte[] family = Bytes.toBytes("t");

			Scan scan = new Scan();
			scan.addFamily(family);
			ResultScanner rs = table.getScanner(scan);

			for (int i = 0; i < 10; i++) {
				for (Result r = rs.next(); r != null; r = rs.next()) {
					System.out.println(i);
					byte[] row = r.getRow();
					System.out.println(new String(row));
					// byte[] valueObj = r.getValue(family, qualifier);
					// String value = new String(valueObj);
					// System.out.println(value);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			table.close();
			conn.close();
		}
	}

	public static void main(String[] args) throws Exception {
		testAES3G();
		// testAES3GAGG();
		// testOpenTSDB();
	}

}
