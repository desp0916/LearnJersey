/**
 * localhost:29998/services/LogCount/TotalsForMinute/123444
 */
package learn.jersey.services.resources;

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
		JSONArray content = new JSONArray();
		JSONObject fileObject = new JSONObject();
		content.add(fileObject);
		fileObject.put("FileName", "aes3g");
		fileObject.put("Total", "1");
		fileObject.put("Minute", "1234444");
		return content.toJSONString();
	}
}
