package scripts;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import locators.UpdateSelectionListLocator;

public class UpdateSelectionList extends BaseClass {

	static String excelFile;
	static String excelSheet;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		openBrowser();
		Login();
		if (user_type.equalsIgnoreCase("admin")) {
			searchOrganization();
		}

		searchServiceFirm();
		try {
			updateSelectionList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Yes you have a problem in your script");
			e.printStackTrace();
		}
		Logout();
		closeBrowser();

	}

	public static void updateSelectionList() throws InterruptedException, IOException {

		
		excelFile = basicData.getProperty("excelfilename");
		excelSheet = basicData.getProperty("excelsheetname");

		driver.findElement(UpdateSelectionListLocator.selectionListLink).click();

		Thread.sleep(2000);
		driver.findElement(UpdateSelectionListLocator.selectionListSelectBox).click();

		List<WebElement> listOfDropdown = driver.findElements(UpdateSelectionListLocator.selectionListValue);

		int count;
		for (count = 0; count < listOfDropdown.size(); count++) {
			if (listOfDropdown.get(count).getText().equalsIgnoreCase(selectionList.trim())) {

				break;
			}

		}
		if (listOfDropdown.get(count).getText().contains("(core list)")) {
			System.out.println("Core list is not supported.. Team is working on that..");
			driver.close();
			System.exit(0);

			driver.quit();
		} else {
			listOfDropdown.get(count).click();
		}

		String[][] myData = readDataFromFile(excelFile, excelSheet);

		//System.out.println("After reading the data");
		wait.until(ExpectedConditions.elementToBeClickable(UpdateSelectionListLocator.searchBox));

		for (int r = 1; r < myData.length; r++) {

			
		
			driver.findElement(UpdateSelectionListLocator.searchBox).clear();
			try {
				driver.findElement(UpdateSelectionListLocator.searchBox).sendKeys(myData[r][0].trim());
			} catch (Exception e) {
				errorMsg = "Error with the searching of field";
				onAnyError(r);
				continue;
			}
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(UpdateSelectionListLocator.firstAttribute));
				driver.findElement(UpdateSelectionListLocator.firstAttribute).click();
				
			} catch (Exception e) {
				errorMsg = "Attribute not found";
				onAnyError(r);
				continue;
			}

			String distext = driver.findElement(UpdateSelectionListLocator.displayText).getAttribute("value");
			if (distext.equalsIgnoreCase(myData[r][0].trim())) {

				if (myData[r][1].trim().isEmpty()) {

				} else if (myData[r][1].trim().equalsIgnoreCase("clear")) {

					driver.findElement(UpdateSelectionListLocator.Description).clear();

				} else {
					driver.findElement(UpdateSelectionListLocator.Description).clear();
					driver.findElement(UpdateSelectionListLocator.Description).sendKeys(myData[r][1].trim());
				}

				if (myData[r][2].trim().isEmpty()) {
				} else if (myData[r][2].trim().equalsIgnoreCase("clear")) {
					driver.findElement(UpdateSelectionListLocator.sortOrder).clear();
				} else {

					if (myData[r][2].length() < 7) {
						
						//code is used to convert float value into string
						/*String s1 = String.valueOf(myData[r][2]); 
						s1 =(int)Double.parseDouble(s1) + "";*/
						
						driver.findElement(UpdateSelectionListLocator.sortOrder).clear();
						driver.findElement(UpdateSelectionListLocator.sortOrder).sendKeys(myData[r][2]);
					} else {
						errorMsg = "Sort order size must be less than 7";
						onAnyError(r);
						driver.findElement(UpdateSelectionListLocator.cancleButton).click();
						continue;
					}
				}
				Thread.sleep(2000);

				wait.until(ExpectedConditions.elementToBeClickable(UpdateSelectionListLocator.saveButton));
				driver.findElement(UpdateSelectionListLocator.saveButton).click();
				try {
					wait = new WebDriverWait(driver, 3);
					wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					errorMsg = alert.getText();
					alert.accept();
					onAnyError(r);
					driver.findElement(UpdateSelectionListLocator.cancleButton).click();
				} catch (Exception e) {

				}
			} else {
				errorMsg = "Display text is missing";
				onAnyError(r);
				driver.findElement(UpdateSelectionListLocator.cancleButton).click();
			}

		}
		Thread.sleep(2000);

	}

	public static void onAnyError(int r) {
		try {
			writeDataTOFile(r, 3, excelFile, excelSheet);
		} catch (IOException e) {

			System.out.println("Error occured while trying to write into file");
			e.printStackTrace();
		}

	}

}
