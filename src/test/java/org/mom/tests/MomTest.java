package org.mom.tests;

import jdk.jfr.Description;
import org.mom.base.BaseTest;
import org.mom.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MomTest extends BaseTest {
    @Test(priority = 1)
    public void loginPageTitleTest() throws InterruptedException {
        String actualTitle = landingPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, Constants.LOGIN_PAGE_TITLE);
        landingPage.doSearch();
    }
}
