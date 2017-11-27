package locators;

import org.openqa.selenium.By;

public class LoginAndServiceFirm {
	
	public static final By userName=By.xpath("//input[@name='j_username' and @id='j_username']");//xpath for username field
	public static final By password=By.xpath("//input[@name='j_password' and @id='j_password']");//xpath for password field
	public static final By loginButton=By.xpath("//button[@class='btn btn-default btn-block pull-right has-spinner']");//xpath for Login Button
	public static final By searchOrganization=By.xpath("//input[@type='text' and @name='search.organizationName']");// xpath for Search organization textbox
	public static final By switchToLink=By.xpath(".//div[@class='col-sm-9']/a");//xpath for switch to link
	
	public static final By logoutLink=By.xpath("//*[text()='Logout']");
	
	
}
