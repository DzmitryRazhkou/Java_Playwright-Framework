package com.qa.opencart.tests;

import com.qa.opencart.basetest.BaseTest;
import com.qa.opencart.constants.Paths;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 0)
    public void loginPageTitleTest() {
        String actualTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, Paths.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 1)
    public void loginPageUrlTest() {
        String actualUrl = loginPage.getLoginPageUrl();
        Assert.assertEquals(actualUrl, prop.getProperty("url"));
    }

    @Test(priority = 2)
    public void doLoginCorrectCredentials() {
        String email = prop.getProperty("email");
        String psw = prop.getProperty("psw");
        loginPage.doLogin(email, psw);
    }

    @Test(priority = 3)
    public void doLoginIncorrectCredentials() throws InterruptedException {
        String email = prop.getProperty("email_");
        String psw = prop.getProperty("psw_");
        loginPage.doLogin(email, psw);
        Assert.assertTrue(loginPage.getErrorFrame());
    }

    @Test(priority = 4)
    public void doRegisterNewUser() throws InterruptedException {
        String userEmail = faker.internet().emailAddress();
        loginPage.doRegistrationUser(userEmail);
        Assert.assertTrue(loginPage.isSuccessfulRegistration(Paths.SUCCESSFUL_REGISTRATION));
    }
}
