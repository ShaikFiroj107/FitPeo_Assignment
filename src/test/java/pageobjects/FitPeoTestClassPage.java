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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
	public WebElement sliderText;
	public int sliderValue;
	public String reimbursementValue;

	public FitPeoTestClassPage(WebDriver driver, TestSetup testSetup) {
		this.testSetup = testSetup;
		this.driver = driver;
		this.genericUtils = testSetup.genericUtils;
	}

	private By verifyLoginPage = By.xpath("//img[@alt='FitPeo']/parent::h4");
	private By verifyRevenueCalculatorPage = By.xpath("//h5[text()='Total Gross Revenue Per Year']/ancestor::div[3]");
	private By sliderTextFiled = By.xpath("//input[@type='number']");
	private By sliderPointer = By.xpath("//span[contains(@class, 'MuiSlider-thumb MuiSlider-thumbSizeMedium')]");
	private By getReimbursementValue = By
			.xpath("//p[text()='Total Recurring Reimbursement for all Patients Per Month:']/child::p");

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
		sliderText = driver.findElement(sliderTextFiled);
	}

	public void clearTextBox() throws InterruptedException {
		log.debug("Clearing the slider text field");
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(sliderTextFiled));
		genericUtils.clear(sliderTextFiled);
		log.info("Successfully cleared the slider text field");
	}

	/*
	 * Using the first two methods below, I am able to achieve the 823 value range.
	 * However, with the third method, I can only achieve the 157 range. I have also
	 * tried using JavaScript to change the input values, but those methods do not
	 * work for this slider either. I'm not sure why it's not working, as I have
	 * seen many tutorials with similar approaches, but none of these methods work
	 * for this particular slider.
	 */

	public void adjustSlider() {
		int targetValue = 11500;
		log.debug("Adjust Slider value until " + targetValue);
		String minValue = slider.getAttribute("min");
		String maxValue = slider.getAttribute("max");
		int min = Integer.parseInt(minValue);
		int max = Integer.parseInt(maxValue);
		log.info("Slider min and max values are " + min + " " + max);

		int sliderWidth = slider.getSize().getWidth();
		log.info("Slider fixel width " + sliderWidth);

		log.debug("Calculate the value per pixel how much value each pixel represents");
		int valuePerPixel = (max - min) / sliderWidth;
		log.info("Value per each pixel  = " + valuePerPixel);

		log.debug("Calculate the position in pixels that corresponds to the target value");
		int targetPosition = (targetValue - min) / valuePerPixel;
		log.info("Sucessfully calculated target position value  = " + targetPosition);

		log.debug("Perform the drag-and-drop action to move the slider");
		Actions actions = new Actions(driver);
		log.info("Location" + slider.getLocation());
		actions.dragAndDropBy(slider, targetPosition, 0).perform();
		log.info("Location" + slider.getLocation());
	}

//	public void adjustSlider() {
//	int targetValue = 9500;
//	 int maxRange = 2000;
//
//     int width = slider.getSize().width;
//
//     double ratio = (double) targetValue / maxRange;
//     int moveToX = (int) (width * ratio);
//
//     Actions actions = new Actions(driver);
//     actions.clickAndHold(slider)
//            .moveByOffset(moveToX, 0)
//            .release() 
//            .perform();
// } 

//	public void adjustSlider() {
//	    int targetValue = 820;
//	    String minValue = slider.getAttribute("min");
//	    String maxValue = slider.getAttribute("max"); 
//	    
//	    int min = Integer.parseInt(minValue);
//	    int max = Integer.parseInt(maxValue);
//	    
//	    int sliderWidth = slider.getSize().getWidth();
//	    log.info("Slider width: " + sliderWidth);
//	    
//	    double positionRatio = (double) (targetValue - min) / (max - min);
//	    int targetPosition = (int) (positionRatio * sliderWidth); 
//	    log.info("Calculated target position (pixels): " + targetPosition);
//	    
//	    Actions actions = new Actions(driver);
//	    actions.clickAndHold(slider)
//	           .moveByOffset(targetPosition, 0) // Move to the calculated position
//	           .release()
//	           .perform();
//	}

	public void getUpdatedValue() {
		String updatedValue = sliderText.getAttribute("value");
		sliderValue = Integer.parseInt(updatedValue);
	}

	public void updateSliderValue(int targetValue) throws InterruptedException {
		log.debug("Update slider value text as " + targetValue);
		String updatedTargetValue = String.valueOf(targetValue);
		log.info("Sucessfully updated slider value is  = " + updatedTargetValue);
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(sliderTextFiled));
		genericUtils.sendKeysToElement(sliderTextFiled, updatedTargetValue);
		String updatedValue = slider.getAttribute("value");
		log.info("Sucessfully getted updated slider value is  = " + updatedValue);
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

			By scrollCheckBox = By.xpath("//p[contains(text(),'" + cptCode + "')]");
			By checkboxXPath = By.xpath("//p[contains(text(),'" + cptCode + "')]//following::input");

			try {
				log.debug("Scroll until the checkbox is visible");
				genericUtils.scrollUntilElementVisible(driver, scrollCheckBox);

				log.debug("clicking all the array list checkboxes");
				WebElement checkbox = driver.findElement(checkboxXPath);
				if (!checkbox.isSelected()) { // Click the checkbox only if it's not already selected
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
		log.info("Sucessfully stored reimbursement Value is = " + reimbursementValue);
	}

	public void validateReimbursementValues(String ExpectedReimbursement) {
		log.debug("Validating both reimbursement Values");
		genericUtils.compareTwoStringValues(reimbursementValue, ExpectedReimbursement);
		log.info("Sucessfully both reimbursement values are matched");
	}

}
