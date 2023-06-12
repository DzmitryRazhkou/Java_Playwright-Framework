package com.qa.opencart.constants;

public class Paths {
    private static final String USER_DIRECTORY = System.getProperty("user.dir");
    private static final String CONFIG_MAIN = "src/main/java/com/qa/opencart/";
    private static final String CONFIG_PATH = "config/config.properties";

    public static final String SCREENSHOT_PATH = USER_DIRECTORY + "/screenshots/";

    public static final String CONFIG = CONFIG_MAIN + CONFIG_PATH;
    public static final String LOGIN_PAGE_TITLE = "Koel";
    public static final String SUCCESSFUL_REGISTRATION = "Registration Successful";
}
