import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestClass {

	@Test(dataProvider = "getData")
	public void Test1(Map<String, Object> map) {
		System.out.println(map);
	}

	@DataProvider(name = "getData")
	public Iterator<Object[]> test() {
		ExecuteQuery db = null;
		ArrayList<HashMap<String, Object>> tableData = null;
		try {
			db = new ExecuteQuery("Select * from Testing");// this will give the all data in form of arraylist of map
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			tableData = db.fetchResult();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		Collection<Object[]> dp = new ArrayList<Object[]>();

		for (HashMap<String, Object> map : tableData) {
			dp.add(new Object[] { map });
		}
		return dp.iterator();
	}
}
