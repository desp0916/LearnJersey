/**
 * http://localhost:29998/services/LogCount/TotalsForMinute/123444
 * http://localhost:29998/services/LogCount/TotalsForMinute/aes3g/1213243
 * http://localhost:8081/LearnJersey/services/LogCount/TotalsForMinute/aes3g/123454433454
 */
package learn.jersey.services.resources;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/LogCount")
public class LogCount {

//	private Connection conn = HBase.getConnection();

	@GET
	@Path("/TotalsForMinute/{topic}/{timestamp}")
	@Produces("application/json")
	public String getMinuteTotals(@PathParam("topic") String topic, @PathParam("timestamp") String timestamp) {

//		long count = 0L;
//		byte[] family = Bytes.toBytes("min");
//		byte[] qualifier = Bytes.toBytes("18-7");
//
//		Get get = new Get(Bytes.toBytes("2015-11-23"));
//		get.addColumn(family, qualifier);
//
//		try {
//			Table table = conn.getTable(TableName.valueOf("aes3g_agg"));
//			Result res1 = table.get(get);
//			count = Bytes.toLong(res1.getValue(family, qualifier));
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
//
//		return String.valueOf(count);

		int randInt = randInt(0, 40);
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
