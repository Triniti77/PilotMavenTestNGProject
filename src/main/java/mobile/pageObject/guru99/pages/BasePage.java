package mobile.pageObject.guru99.pages;

import io.appium.java_client.AppiumDriver;

public class BasePage {
    AppiumDriver appiumDriver;
    String baseUrl;
    public BasePage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        baseUrl = "http://demo.guru99.com";
    }

    public String getTitle() {
        return appiumDriver.getTitle();
    }
}
