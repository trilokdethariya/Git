package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.unicodetechnologies.xlsConnectivity.XLSDatatable_Connectivity;

import locators.LoginAndServiceFirm;

public class BaseClass {

	public static WebDriver driver = null;
	static XLSDatatable_Connectivity data;
	static String[][] values;
	public static WebDriverWait wait;
	public static Properties basicData;
	static String username;
	static String password;
	static String environment;
	static String serviceFirm;
	static String organizationName;
	static String selectionList;
	static String errorMsg;
	static String user_type;

	public static void openBrowser() throws IOException {
		basicData = new Properties();
		FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "\\src\\config\\config.properties");
		basicData.load(fi);
		username = basicData.getProperty("username");
		password = basicData.getProperty("password");
		environment = basicData.getProperty("environment");
		organizationName = basicData.getProperty("organizationName");
		serviceFirm = basicData.getProperty("serviceFirm");
		selectionList = basicData.getProperty("Selection_list");
		user_type = basicData.getProperty("user_type");
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\driver\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();

		if (environment.equalsIgnoreCase("demo")) {
			driver.get("http://demo.aravo.com");
		} else if (environment.equalsIgnoreCase("configuration")) {
			driver.get("http://configure.aravo.com");
		} else {
			System.out.println("Problem in environment selection");
		}

	}

	public static void Login() {
		driver.findElement(LoginAndServiceFirm.userName).sendKeys(username);
		driver.findElement(LoginAndServiceFirm.password).sendKeys(password);
		driver.findElement(LoginAndServiceFirm.loginButton).click();
	}

	public static void searchOrganization() {
		driver.findElement(LoginAndServiceFirm.searchOrganization).sendKeys(organizationName);// xpath
																								// for
																								// Search
																								// organization
																								// textbox
		driver.findElement(LoginAndServiceFirm.searchOrganization).sendKeys(Keys.ENTER);// Press
																						// Enter
																						// key
		driver.findElement(LoginAndServiceFirm.switchToLink).click();// Click on
																		// Switch
																		// To
																		// link
	}

	// Test case for searching service firm

	public static void searchServiceFirm() {

		driver.findElement(By.linkText(serviceFirm)).click();// Click on Service
																// firm link
	}

	public static String[][] readDataFromFile(String filename, String sheetName) {
		data = new XLSDatatable_Connectivity(
				System.getProperty("user.dir") + "\\src\\dataFiles\\" + filename + ".xlsx");

		int row = data.totalRow(sheetName);

		int col = data.totalColumn(sheetName);
		values = new String[row][col];
		for (int r = 1; r <= row; r++) {
			for (int c = 0; c < col; c++) {
				values[r - 1][c] = data.getData(sheetName, c, r);
			}
		}
		return values;
	}

	public static void writeDataTOFile(int row, int col, String filename, String sheetName) throws IOException {
		File file1 = new File(System.getProperty("user.dir") + "\\src\\dataFiles\\" + filename + ".xlsx");
		FileInputStream file = new FileInputStream(file1);

		Workbook workbook = null;

		workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheet(sheetName);

		Cell cell = null;

		// Retrieve the row and check for null
		Row sheetrow = sheet.getRow(row);
		if (sheetrow == null) {
			sheetrow = sheet.createRow(row);
		}
		// Update the value of cell
		cell = sheetrow.getCell(col);
		if (cell == null) {
			cell = sheetrow.createCell(col);
		}
		cell.setCellValue(errorMsg);

		file.close();

		FileOutputStream outFile = new FileOutputStream(
				new File(System.getProperty("user.dir") + "\\src\\dataFiles\\" + filename + ".xlsx"));
		workbook.write(outFile);
		outFile.close();

	}

	public static void Logout() {
		driver.findElement(LoginAndServiceFirm.logoutLink).click();

	}

	public static void closeBrowser() {
		driver.close();
		driver.quit();
	}

	

	
}
