package utilsClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericUtils {

	private static final Logger log = LogManager.getLogger(GenericUtils.class);
	public WebDriver driver;
	
	public GenericUtils(WebDriver driver) {
		this.driver = driver;
	}
	
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/configFile/global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getValue(By xpathToGetText) {
		String value = driver.findElement(xpathToGetText).getText();
		System.out.println("text value is  :" + value);
		return value;
	}
	
	public void clear(By xpathToClear) {
		driver.findElement(xpathToClear).sendKeys(Keys.CONTROL + "a");
		driver.findElement(xpathToClear).sendKeys(Keys.DELETE);

	}
	
	public void sendKeysToElement(By locator, String value) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(value);
    }
	
	public void compareTwoIntegerValues(int actualInteger, int expectedInteger) {
		System.out.println("Verifying two integer values matching or not, actual value is = " + actualInteger + ",  API value is = " + expectedInteger);
		Assert.assertEquals(actualInteger, expectedInteger);
	}
	
	public void compareTwoStringValues(String actualString, String expectedString) {
		System.out.println("Verifying two String values matching or not, actual value is = " + actualString + ",  API value is = " + expectedString);
		Assert.assertEquals(actualString, expectedString);
	}
	
	public boolean isElementVisible(WebDriver driver, WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
	
    public static void scrollUntilElementVisible(WebDriver driver, By xpathToScroll) {
        
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
        	
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(xpathToScroll));
            
            new WebDriverWait(driver, Duration.ofSeconds(20))
			.until(ExpectedConditions.visibilityOfElementLocated(xpathToScroll));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
