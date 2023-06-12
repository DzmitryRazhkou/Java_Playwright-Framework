package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    Page page;

    private final String registrationBtn = "#hel";
    private final String emailRegistrationField = "#email";
    private final String errorFrame = ".error";
    private final String registrationSubmitBtn = "#button";
    private final String successfulRegistration = "h3:has-text('Registration Successful')";

    //    2. Page Constructor:
    public LoginPage(Page page) {
        this.page = page;
    }

    //    3. Page Action/Methods:
    public String getLoginPageTitle() {
        String title = page.title();
        System.out.println(" ====> The Page Title is: " + title + " <===== ");
        return title;
    }

    public String getLoginPageUrl() {
        String url = page.url();
        System.out.println(" ====> The Page Title is: " + url + " <===== ");
        return url;
    }

    public void doLogin(String email, String psw) {
        //    1. String Locators:
        String emailField = "input[type='email']";
        page.fill(emailField, email);
        String pswField = "input[type='password']";
        page.fill(pswField, psw);
        String loginBtn = "button[type='submit']";
        page.click(loginBtn);
    }

    public boolean getErrorFrame() {
        return page.waitForSelector(errorFrame).isVisible();
    }

    public void doRegistrationUser(String userEmail) {
        page.click(registrationBtn);
        page.fill(emailRegistrationField, userEmail);
        page.click(registrationSubmitBtn);
    }

    public boolean isSuccessfulRegistration(String txt) {
        return page.locator(successfulRegistration).textContent().equals(txt)
                && page.locator(successfulRegistration).isVisible();
    }
}
