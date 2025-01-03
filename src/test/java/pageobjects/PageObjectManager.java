package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilsClasses.TestSetup;

public class PageObjectManager {

	public WebDriver driver;
	public FitPeoTestClassPage fitPeoTestClassPage;
	private static final Logger log = LogManager.getLogger(PageObjectManager.class);

	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}

	public FitPeoTestClassPage getOnboardingDashboardPage(TestSetup testSetup) {
		if (fitPeoTestClassPage == null) {
			fitPeoTestClassPage = new FitPeoTestClassPage(driver, testSetup);
		}
		return fitPeoTestClassPage;
	}

}
