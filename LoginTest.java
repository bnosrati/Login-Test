package com.moubitech.tests.login.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {
	private static final String SITE_URL = "http://www.moubitech.com";
	private static final int TEST_TIMEOUT = 5000; // in milliseconds

	private WebDriver driver = null;

	@BeforeMethod
	public void setup() {
		driver = new FirefoxDriver();
		driver.get(SITE_URL);
	}

	@DataProvider(name = "login test properties")
	Object[][] simpleDataProvider() {
		return new Object[][] {
				{ "Verify no email entered", "", "password123",	"Please enter a valid email address" },
				{ "Verify no password entered", "ben.nosrati@moubitech.com","", "A valid password is required" },
				{ "Verify successful login", "ben.nosrati@moubitech.com", "password123", "Signed in" } 
		};
	}

	@Test(enabled = true, dataProvider = "login test properties", timeOut = TEST_TIMEOUT)
	public void loginTest(String testName, String email, String password,
			String expectedMessage) throws InterruptedException {

		doLoginAction(email, password);
		assertExpectation(expectedMessage);
	}

	private void assertExpectation(String expectedMessage) {
		String actualMessage = "";
		try {
			WebElement actualMessageElement = driver.findElement(By
					.id("loginMessage"));
			 actualMessage = actualMessageElement.getText();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			actualMessage = e.getMessage().split("\\r?\\n")[0];
		}
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	private void doLoginAction(String email, String password) {
		try {
			WebElement emailInput = driver.findElement(By.id("userName"));
			emailInput.sendKeys(email);

			WebElement passwordInput = driver.findElement(By.id("password"));
			passwordInput.sendKeys(password);

			WebElement loginButton = driver.findElement(By.id("loginButton"));
			loginButton.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(e.getMessage().split("\\r?\\n")[0]);
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

	@AfterClass(alwaysRun = true)
	public void quit() {
		driver.quit();
	}

}
