package com.qa.opencart.basetest;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactoryThreadLocal;
import com.qa.opencart.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {
    PlaywrightFactoryThreadLocal playwrightFactory;
    Page page;
    protected Properties prop;
    protected Faker faker;
    protected LoginPage loginPage;

    @Parameters("browser")
    @BeforeMethod //@BeforeTest
    public void setup(String browserName) {
        playwrightFactory = new PlaywrightFactoryThreadLocal();
        prop = playwrightFactory.initiateProperties();
        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }
        page = playwrightFactory.initBrowser(prop);
        faker = new Faker();
        loginPage = new LoginPage(page);
    }

    @AfterMethod //@AfterTest
    public void tearDown() {
        page.context().browser().close();
    }
}
