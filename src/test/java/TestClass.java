import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;

import junit.framework.Assert;

@Listeners({ ExtentITestListenerClassAdapter.class })
public class TestClass {

	private static final String IMG_NAME = "screenshot1.png";
	private static final String IMG_NAME2 = "screenshot2.png";
	private static final String IMG_NAME3 = "screenshot3.png";

	// A static image stored under classpath
	private static final String IMG_PATH = "src/test/resources/" + IMG_NAME;
	private static final String IMG_PATH2 = "src/test/resources/" + IMG_NAME2;
	private static final String IMG_PATH3 = "src/test/resources/" + IMG_NAME3;

	// Using the same OUTPUT_PATH as set in extent.properties
	// [extent.reporter.html.out]
	private static final String OUTPUT_PATH = "test-output/HtmlReport/";

	@BeforeMethod
	public void printMethodName(Method method) {
		System.out.println("**************************" + method.getName() + " ***************************8");
	}

	@Test(dataProvider = "getData")
	public void Test1(Map<String, Object> map) throws InterruptedException {
		if (map.get("Id").toString().equals("1"))
			Assert.assertTrue(true);
		if (map.get("Id").toString().equals("2"))
			Assert.assertTrue(false);
		if (map.get("Id").toString().equals("3"))
			throw new SkipException("failed to deal");
		System.out.println(map.get("country"));
		System.out.println(map.get("Id"));
		System.out.println(map);
		System.out.println("waiting.............");
		Thread.sleep(5000);
	}

	@AfterMethod
	public void run(Method name) {
		System.out.println("**************************" + name.getName() + " ***************************9");
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

	@AfterMethod
	public synchronized void afterMethod(ITestResult result) throws IOException {
		switch (result.getStatus()) {
		case ITestResult.FAILURE:
			ExtentTestManager.getTest(result).pass("ITestResult.FAILURE, event afterMethod",
					MediaEntityBuilder.createScreenCaptureFromPath(getImage()).build());
			break;

		case ITestResult.SUCCESS:
			ExtentTestManager.getTest(result).pass("ITestResult.SUCCESS, event afterMethod",
					MediaEntityBuilder.createScreenCaptureFromPath(getImage2()).build());
			break;

		case ITestResult.SKIP:
			ExtentTestManager.getTest(result).pass("ITestResult.FAILURE, event afterMethod",
					MediaEntityBuilder.createScreenCaptureFromPath(getImage3()).build());
			break;
		default:
			break;
		}
	}

	/**
	 * !!This code block is just an example only!! !!Real-world implementation would
	 * require capturing a screenshot!!
	 * 
	 * @return Image path
	 * @throws IOException
	 */
	private String getImage() throws IOException {
		Files.copy(new File(IMG_PATH).toPath(), new File(OUTPUT_PATH + IMG_NAME).toPath());
		return IMG_NAME;
	}

	private String getImage2() throws IOException {
		Files.copy(new File(IMG_PATH2).toPath(), new File(OUTPUT_PATH + IMG_NAME2).toPath());
		return IMG_NAME2;
	}

	private String getImage3() throws IOException {
		Files.copy(new File(IMG_PATH3).toPath(), new File(OUTPUT_PATH + IMG_NAME3).toPath());
		return IMG_NAME3;
	}
}
