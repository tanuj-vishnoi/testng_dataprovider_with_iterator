import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ExecuteQuery {

	private Connection conn = ConnectDB.getConnection();
	private ResultSet resultSet;

	/**
	 * pass whole query as parameter like select * from customer
	 * @param query
	 * @throws SQLException
	 */
	public ExecuteQuery(String query) throws SQLException {
		Statement statement = conn.createStatement();
		resultSet = statement.executeQuery(query);
	}

	/**
	 * This code will convert the result set to arraylist of map
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, Object>> fetchResult() throws SQLException {
		ResultSetMetaData md = resultSet.getMetaData();
		
		int columns = md.getColumnCount();
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		while (resultSet.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), resultSet.getObject(i));
			}
			list.add(row);
		}
		return list;
	}
}
