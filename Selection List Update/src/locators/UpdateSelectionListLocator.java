package locators;

import org.openqa.selenium.By;

public class UpdateSelectionListLocator {

	public static final By selectionListLink = By.linkText("Selection Lists");
	public static final By selectionListSelectBox = By.xpath(".//input[preceding-sibling :: div[contains(.,'Please select a list to edit:')]]");
	public static final By selectionListValue = By.xpath(".//tr/td[@role='menuitem']");
	public static final By searchBox = By.xpath(".//div[@class='headerBar']/div[following-sibling:: a[contains(.,'show deprecated')]]/input[preceding-sibling::div[contains(.,'Text Filter:')]]");
	public static final By firstAttribute = By.xpath(".//tbody/tr[preceding-sibling :: tr/td[contains(.,'Display Text')]]/td[1]");
	public static final By displayText = By.xpath(".//input[preceding-sibling :: div/span[text()='Display Text'] and following-sibling::div/span[contains(.,'Description')]]");
	public static final By Description = By.xpath(".//textarea[preceding-sibling::div/span[text()='Description']]");
	public static final By sortOrder = By.xpath(".//input[preceding-sibling::div[contains(.,'Sort Order')]]");
	public static final By saveButton = By.xpath(".//div[@class='buttonsPanel']/button[text()='Save' and following-sibling::button[contains(.,'Save and New')]]");
	public static final By cancleButton=By.xpath(".//div[@class='buttonsPanel']/a[contains(.,'Cancel') and preceding-sibling::button[contains(.,'Save and New')]]");
	
	
	
	
}
