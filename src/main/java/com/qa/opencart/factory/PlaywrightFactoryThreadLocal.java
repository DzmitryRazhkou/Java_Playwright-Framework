package com.qa.opencart.factory;

import com.microsoft.playwright.*;
import com.qa.opencart.constants.Paths;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PlaywrightFactoryThreadLocal {
    Properties prop;

    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

    public static Browser getBrowser() {
        return tlBrowser.get();
    }
    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }
    public static Page getPage() {
        return tlPage.get();
    }
    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public Page initBrowser(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));
        System.out.println(" =====> Browser Name is: '" + browserName.toUpperCase() + "' <===== .");

        tlPlaywright.set(Playwright.create());
        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "firefox":
                tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "safari":
                tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "chrome":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless)));
                break;
            case "edge":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless)));
                break;
            default:
                System.out.println("Please Pass The Right Browser Name ... ");
        }
        tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(1728, 1117)));
        tlPage.set(getBrowserContext().newPage());
        getPage().navigate(prop.getProperty("url").trim());
        return getPage();
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

    /*
     * Take Screenshot:
     * */

    public static String takeScreenshot() {
        String path = Paths.SCREENSHOT_PATH + System.currentTimeMillis() + ".png";
        getPage().screenshot(new Page.ScreenshotOptions()
                .setPath(java.nio.file.Paths.get(path))
                .setFullPage(true));
        return path;
    }

}
