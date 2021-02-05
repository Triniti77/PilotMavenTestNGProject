package mobile.pageObject.guru99.pages;

import io.appium.java_client.AppiumDriver;
import mobile.pageObject.guru99.fragments.LoginFormFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    private LoginFormFragment loginFormFragment;
    WebElement loginForm;

    public HomePage(AppiumDriver appiumDriver) {
        super(appiumDriver);
        loginFormFragment = new LoginFormFragment(appiumDriver);
        loginForm = appiumDriver.findElement(By.xpath("//form[@name='frmLogin']"));
    }

    public boolean isLoginFormPresent() {
        return loginForm.isDisplayed();
    }

    public void navigate() {
        appiumDriver.get(baseUrl+"/");
    }


    public void makeLogin(String login, String password) {
        loginFormFragment.makeLogin(login, password);
    }
}
