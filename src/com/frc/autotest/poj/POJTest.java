package com.frc.autotest.poj;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class POJTest {

	@Test
	public void logon() {

		System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");

		WebDriver driver = new FirefoxDriver();
		driver.get("http://poj.org/");

		driver.manage().window().maximize();

		WebElement txtbox = driver.findElement(By.name("user_id1"));
		txtbox.sendKeys("scorpiowf");

		WebElement pswbox = driver.findElement(By.name("password1"));
		pswbox.sendKeys("november");

		WebElement btn = driver.findElement(By.xpath("//form[action='login']//input[name='B1']"));
		btn.click();
		snapshot((TakesScreenshot)driver,"login.png");
		// driver.close();
	}

	public static void snapshot(TakesScreenshot drivername, String filename) {
		// this method will take screen shot ,require two parameters ,one is
		// driver name, another is file name
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		try {
			System.out.println("save snapshot path is:d:/" + filename);
			FileUtils.copyFile(scrFile, new File("d:\\" + filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {
			System.out.println("screen shot finished");
		}
	}
}
