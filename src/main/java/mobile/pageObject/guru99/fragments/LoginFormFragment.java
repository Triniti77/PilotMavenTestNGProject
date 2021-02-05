package mobile.pageObject.guru99.fragments;

import io.appium.java_client.AppiumDriver;
import mobile.pageObject.guru99.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginFormFragment extends BasePage {
    WebElement loginInput;

    WebElement passwordInput;
    WebElement loginButton;

    public LoginFormFragment(AppiumDriver appiumDriver) {
        super(appiumDriver);
        loginInput = appiumDriver.findElement(By.xpath("//input[@name='uid']"));
        passwordInput = appiumDriver.findElement(By.xpath("//input[@name='password']"));
        loginButton = appiumDriver.findElement(By.xpath("//input[@name='btnLogin']"));
    }

    public void makeLogin(String login, String password) {
        loginInput.clear();
        loginInput.sendKeys(login);
        appiumDriver.hideKeyboard(); // hide keyboard
        passwordInput.clear();
        passwordInput.sendKeys(password);
        appiumDriver.hideKeyboard(); // hide keyboard
        try {
            Thread.sleep(1000); // wait keyboard animation
        } catch (InterruptedException e) {
        }
        tap(loginButton);
    }
}
