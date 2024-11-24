package pageobjects;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilsClasses.GenericUtils;
import utilsClasses.TestSetup;

public class FitPeoTestClassPage {
	
	private final Logger log = LogManager.getLogger(FitPeoTestClassPage.class);
	private static GenericUtils genericUtils;
	public WebDriver driver;
	private TestSetup testSetup;
	public WebElement slider;
	public int sliderValue;
	public String reimbursementValue;
	
	public FitPeoTestClassPage(WebDriver driver, TestSetup testSetup) {
		this.testSetup = testSetup;
		this.driver = driver;
		this.genericUtils = testSetup.genericUtils;
	}
	
	private By verifyLoginPage = By.xpath("//img[@alt='FitPeo']/parent::h4");
	private By verifyRevenueCalculatorPage = By.xpath("//h5[text()='Total Gross Revenue Per Year']/ancestor::div[3]");
	private By sliderPointer = By.xpath("//h4[text()='Medicare Eligible Patients']/following::input[1]");
	private By sliderTextFiled = By.xpath("//input[@type='number']");
	private By getReimbursementValue = By.xpath("//p[text()='Total Recurring Reimbursement for all Patients Per Month:']/child::p");
	
	
	public void lanchWebsite() throws IOException {
		log.debug("user opening Fit Peo Website");
		driver.get(genericUtils.getGlobalValue("FitPeo_Url"));
		log.info("Successfully sented url is = " + genericUtils.getGlobalValue("FitPeo_Url"));
	}
	
	public void verifyUserLandsOnFitPeo() throws InterruptedException {
		log.debug("Verifying the User lands sucessfully on fitpeo Dashboard or not");
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(verifyLoginPage));
		boolean verifyFitPeoWebsite = driver.findElement(verifyLoginPage).isDisplayed();
		Assert.assertTrue(verifyFitPeoWebsite);
		log.info("Successfully user lanched FitPeo profile website");
	}
	
	public void navigateRevenueCalculatorPage() throws IOException {
		log.debug("User navigate revenue calculator page from home page");
		driver.navigate().to(genericUtils.getGlobalValue("Revenue_Calculator_Url"));
		log.info("Successfully user navigate revenue calculator page");
	}
	
	public void verifyUserLandsOnRevenuePage() throws InterruptedException {
		log.debug("Verifying the sucessfully User navigate on revenue calculator page from home page or not");
		new WebDriverWait(driver, Duration.ofSeconds(25))
				.until(ExpectedConditions.visibilityOfElementLocated(verifyRevenueCalculatorPage));
		boolean verifyRevenueCalculator = driver.findElement(verifyRevenueCalculatorPage).isDisplayed();
		Assert.assertTrue(verifyRevenueCalculator);
		log.info("Successfully user navigated revenue calculator dashboard from home page");
	}
	
	public void scrollUntilSliderSection() {
		WebElement sliderSection = driver.findElement(sliderPointer);
		 if (!genericUtils.isElementVisible(driver, sliderSection)) {
		        log.debug("Scrolling until slider section is visible");
		        genericUtils.scrollUntilElementVisible(driver, sliderPointer);
		        log.info("Successfully made slider section visible");
		    } else {
		        log.info("Slider section is already visible, no need to scroll");
		    }
	}
	
	public void storeSliderWebElement() {
		log.debug("Storing slider locater");
		slider = driver.findElement(By.cssSelector("input[type='range']")); 
	}
	
	/*I was tried these below methods for adjust slider value at 820 range with 6.2 pixel basically total width of this slider 20 pixel but it's defultly showing 
	  but these methods are not properly able to set 2 pixel because default slider value coming 200. I was tried set that defult value to zero and executed after 
	  that also same issue happens. So after I have tried with below commented method too set offsetx axes as 820 directly that method also not able to set at range 820. */
	public void adjustSlider(int targetValue) {
		log.debug("Adjust Slider value until "+ targetValue);
        String minValue = slider.getAttribute("min");
        String maxValue = slider.getAttribute("max");
        int min = Integer.parseInt(minValue);
        int max = Integer.parseInt(maxValue);
        log.info("Slider min and max values are "+min +" "+max);

        int sliderWidth = slider.getSize().getWidth();
        log.info("Slider fixel width "+sliderWidth);

       log.debug("Calculate the value per pixel how much value each pixel represents");
        int valuePerPixel = (max - min) / sliderWidth;
        log.info("Value per each pixel  = "+valuePerPixel);

        log.debug("Get the current value of the slider default value when the page loads");
        int currentValue = Integer.parseInt(slider.getAttribute("value"));
        log.info("Sucessfully getted defult value when user navigate to revenue calculater page  = "+currentValue);

        log.debug("Calculate the position in pixels that corresponds to the target value");
        int targetPosition = (targetValue - min) / valuePerPixel;
        log.info("Sucessfully calculated target position value  = "+targetPosition);

        log.debug("Calculate the current position in pixels for the slider's current value");
        int currentPosition = (currentValue - min) / valuePerPixel;
        log.info("Sucessfully calculated target position value  = "+currentPosition);

        log.debug("Calculate the offset in pixels distance to move the slider");
        int offset = targetPosition - currentPosition;
        log.info("Sucessfully getted offset pixel value is = "+offset);

        log.debug("Perform the drag-and-drop action to move the slider");
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(slider, offset, 0).perform();

        String updatedValue = slider.getAttribute("value");
        log.info("Sucessfully updated drag distance value is  = "+updatedValue);
        
//        Anoter method for adjusting slider at the range 820 but this method draging slider at the end point.
//        log.debug("Perform the drag-and-drop action to move the slider until " +targetValue);
//        Actions actions = new Actions(driver);
//        actions.dragAndDropBy(slider, targetValue, 0).perform();
	}
	
	public void updateSliderValue(int targetValue) throws InterruptedException {
		log.debug("Update slider value text 560");
		String updatedTargetValue = String.valueOf(targetValue);
		log.info("Sucessfully updated slider value is  = "+updatedTargetValue);
		genericUtils.sendKeysToElement(sliderTextFiled, updatedTargetValue);
		Thread.sleep(5000);
		String updatedValue = slider.getAttribute("value");
		log.info("Sucessfully getted updated slider value is  = "+updatedValue);
		sliderValue = Integer.parseInt(updatedValue);
		log.info("Slider value after entering text field value: " + sliderValue);
	}
	
	public void validateUpdateSliderValue(int targetValue) {
		log.debug("Comapring both updated slider values");
		genericUtils.compareTwoIntegerValues(sliderValue, targetValue);
		log.info("Sucessfully both slider and updated values are matched");
	}
	
    public void clickCptCodeCheckboxes() {
    	log.debug("Storiing all cpt values in array list");
    	ArrayList<String> cptCodes = new ArrayList<>();
        cptCodes.add("CPT-99091");
        cptCodes.add("CPT-99453");
        cptCodes.add("CPT-99454");
        cptCodes.add("CPT-99474");
        
        log.debug("Loop through each CPT code in the list");
        for (String cptCode : cptCodes) {
            // Construct the XPath based on the CPT code
        	By scrollCheckBox = By.xpath("//p[contains(text(),'" + cptCode + "')]");
            By checkboxXPath = By.xpath("//p[contains(text(),'" + cptCode + "')]//following::input");

            try {
            	log.debug("Scroll until the checkbox is visible");
                genericUtils.scrollUntilElementVisible(driver, scrollCheckBox);

                log.debug("clicking all the array list checkboxes");
                WebElement checkbox = driver.findElement(checkboxXPath);
                if (!checkbox.isSelected()) {  // Click the checkbox only if it's not already selected
                    checkbox.click();
                    log.info("Clicked checkbox for CPT code: " + cptCode);
                } else {
                	log.info("Checkbox for CPT code " + cptCode + " is already selected.");
                }

            } catch (NoSuchElementException e) {
            	log.info("Checkbox for CPT code " + cptCode + " not found.");
            }
        }
    }
    
    public void storeReimbursementValue() {
    	log.debug("Storing reimbursementValue");
    	reimbursementValue = genericUtils.getValue(getReimbursementValue);
    	log.info("Sucessfully stored reimbursement Value is = "+reimbursementValue);
    }
    
    public void validateReimbursementValues(String ExpectedReimbursement) {
    	log.debug("Validating both reimbursement Values");
    	genericUtils.compareTwoStringValues(reimbursementValue, ExpectedReimbursement);
    	log.info("Sucessfully both reimbursement values are matched");
    }
	
}
