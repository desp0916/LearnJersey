/**
 * localhost:29998/services/LogCount/TotalsForMinute/123444
 */
package learn.jersey.services.resources;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.hadoop.hbase.client.Connection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/LogCount")
public class LogCount {

	Connection conn;

	@GET
	@Path("/TotalsForMinute/{timestamp}")
	@Produces("application/json")
	public String getMinuteTotals(@PathParam("timestamp") String timestamp) {
//		Long l = 0L;
//		conn = new HBaseUtils().getConnection();
//
//		byte[] family = Bytes.toBytes("min");
//		byte[] qualifier = Bytes.toBytes("18-7");
//
//		Get get = new Get(Bytes.toBytes("2015-11-23"));
//		get.addColumn(family, qualifier);
//
//		try {
//			Table table = conn.getTable(TableName.valueOf("aes3g_agg"));
//			Result res1 = table.get(get);
//			l = Bytes.toLong(res1.getValue(family, qualifier));
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
		int randInt = randInt(0, 40);
		java.util.Date date= new java.util.Date();
//		Timestamp t = new Timestamp(date.getTime());
		long unixTime = System.currentTimeMillis() / 1000L;
		JSONArray content = new JSONArray();
		JSONObject fileObject = new JSONObject();
		content.add(fileObject);
		fileObject.put("FileName", "aes3g");
		fileObject.put("Total", randInt);
		fileObject.put("Minute", unixTime);
		return content.toJSONString();
	}

	public static int randInt(int min, int max) {
	    int randomNum = new Random().nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
