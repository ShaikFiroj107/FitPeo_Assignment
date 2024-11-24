package stepDefinitions;

import io.cucumber.java.en.*;
import pageobjects.FitPeoTestClassPage;
import utilsClasses.TestSetup;
import java.io.IOException;

public class FitPeoTestClassSteps {

	public TestSetup testSetup;// to make testContextSetup instance globally available to this class
	public FitPeoTestClassPage fitPeoTestClassPage;

	public FitPeoTestClassSteps() {}
	
	public FitPeoTestClassSteps(TestSetup testSetup) {
		this.testSetup = testSetup;
		this.fitPeoTestClassPage = testSetup.pageObjectManager.getOnboardingDashboardPage(testSetup);
	}
	
	@Given("User lands on Fit Peo website")
	public void User_lands_on_Fit_Peo_website() throws IOException {
		fitPeoTestClassPage.lanchWebsite();
	}
	
	@Then("verify user lands fit peo website successfully or not")
	public void verify_user_lands_fit_peo_website_successfully_or_not() throws InterruptedException {
		fitPeoTestClassPage.verifyUserLandsOnFitPeo();
	}
	
	@When("navigate to revenue calculator website page from home page")
	public void navigate_to_revenue_calculator_website_page_from_home_page() throws IOException {
		fitPeoTestClassPage.navigateRevenueCalculatorPage();
	}
	
	@Then("verify sucessfully user navigate revenue calculator website from home page or not")
	public void verify_sucessfully_user_navigate_revenue_calculator_website_from_home_page_or_not() throws InterruptedException {
		fitPeoTestClassPage.verifyUserLandsOnRevenuePage();
	}
	
	@Then("Store slider WebElement value")
	public void Store_slider_WebElement_value() {
		fitPeoTestClassPage.storeSliderWebElement();
	}
	
	@Then("Scroll until slider section visible")
	public void Scroll_until_slider_section_visible() {
		fitPeoTestClassPage.scrollUntilSliderSection();
	}
	
	@Then("drag and adjust slider value until {int}")
	public void drag_and_adjust_slider_value_until(int SliderRangeValue) {
		fitPeoTestClassPage.adjustSlider(SliderRangeValue);
	}
	
	@Then("Enter {int} in slider box field")
	public void Enter_in_slider_box_field(int targetValue) throws InterruptedException {
		fitPeoTestClassPage.updateSliderValue(targetValue);
	}
	
	@Then("Validate updated slider value with {int}")
	public void Validate_updated_slider_value_with(int targetValue) {
		fitPeoTestClassPage.validateUpdateSliderValue(targetValue);
	}
	
	@Then("Scroll and click all required CTP check boxes")
	public void Scroll_and_click_all_required_CTP_check_boxes() {
		fitPeoTestClassPage.clickCptCodeCheckboxes();
	}
	
	@Then("store reimbursement value after selected required checkboxes")
	public void store_reimbursement_value_after_selected_required_checkboxes() {
		fitPeoTestClassPage.storeReimbursementValue();
	}
	
	
	@Then("Validate getted reimbursement value with {string}")
	public void Validate_getted_reimbursement_value_with(String ExpectedReimbursement) {
		fitPeoTestClassPage.validateReimbursementValues(ExpectedReimbursement);
	}

}
