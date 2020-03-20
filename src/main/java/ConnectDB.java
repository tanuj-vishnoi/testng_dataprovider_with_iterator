
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	
	static Connection con = null;
	public static Connection getConnection() {
		if (con != null)
			return con;
		String servername = "db4free.net:3306";
		String dbname = "a4utesting";
		String username = "a4utesting";
		String password = "a4u_testing";
		return getConnection(servername, dbname, username, password);
	}

	private static Connection getConnection(String servername, String dbname, String user_name, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + servername + "/" + dbname, user_name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
}
