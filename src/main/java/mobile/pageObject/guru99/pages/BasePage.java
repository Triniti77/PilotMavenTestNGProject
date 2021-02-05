package mobile.pageObject.guru99.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class BasePage {
    protected AppiumDriver appiumDriver;
    String baseUrl;
    public BasePage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        baseUrl = "http://demo.guru99.com";
    }

    public String getTitle() {
        return appiumDriver.getTitle();
    }

    public void tap(WebElement element) {
        JavascriptExecutor js = appiumDriver;
        TouchAction t = new TouchAction(appiumDriver);
        PointOption tapOptions = new PointOption();
        tapOptions.withCoordinates(452, 1165);
        t.tap(tapOptions);
    }

}
