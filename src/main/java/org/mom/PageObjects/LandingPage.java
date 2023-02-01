package org.mom.PageObjects;

import org.mom.DriverFactory.DriverFactory;
import org.mom.utils.Constants;
import org.mom.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends DriverFactory {

    private ElementUtil eleUtil;

    @FindBy(xpath = "//button[contains(text(),'English')]")
    WebElement langBtn;

    @FindBy(xpath = "//button[@data-qa='prelanding_continue_btn']")
    WebElement continueBtn;

    @FindBy(xpath = "//button[@data-qa='landing_start_btn']")
    WebElement startBtn;

    @FindBy(xpath = "//input[@id='search_date_of_birth_input']")
    WebElement dobBtn;

    @FindBy(xpath = "//span[contains(text(),'Foreign Identification Number (FIN)')]")
    WebElement radioFINBtn;

    @FindBy(xpath = "//input[@id='search_fin_input']")
    WebElement inputFIN;

    @FindBy(xpath = "//iframe[@title='reCAPTCHA']")
    WebElement captchaTick;

    @FindBy(xpath = "//button[@data-qa='search_submit_btn']")
    WebElement submitBtn;

    public LandingPage() {
        PageFactory.initElements(getDriver(), this);
        eleUtil = new ElementUtil(getDriver());
    }


    public String getLoginPageTitle() {
        String title = eleUtil.waitForTitleIs(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
        System.out.println("login page title is: " + title);
        return title;
    }

    public void doSearch() throws InterruptedException {
        langBtn.click();
        Thread.sleep(2000);
        continueBtn.click();
        Thread.sleep(2000);
        startBtn.click();
        Thread.sleep(2000);
        dobBtn.sendKeys("09 Oct 1994");
        Thread.sleep(2000);
        radioFINBtn.click();
        Thread.sleep(2000);
        inputFIN.sendKeys("G1664202P");
        Thread.sleep(2000);
//        getDriver().switchTo().frame(captchaTick);
        captchaTick.click();
        Thread.sleep(2000);
        Thread.sleep(2000);
        submitBtn.click();
        Thread.sleep(2000);
    }
}
