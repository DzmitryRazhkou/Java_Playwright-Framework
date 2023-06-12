package com.qa.opencart.factory;

import com.microsoft.playwright.*;
import com.qa.opencart.constants.Paths;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;

    public Page initBrowser(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));
        System.out.println(" =====> Browser Name is: '" + browserName.toUpperCase() + "' <===== .");

        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
                break;

            default:
                System.out.println("Please Pass The Right Browser Name ... ");
        }
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1728, 1117));
        page = browserContext.newPage();
        page.navigate(prop.getProperty("url"));
        return page;
    }

    public Properties initiateProperties() {
        try {
            FileInputStream ip = new FileInputStream(Paths.CONFIG);
            prop = new Properties();
            prop.load(ip);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
