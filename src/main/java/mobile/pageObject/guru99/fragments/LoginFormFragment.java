package mobile.pageObject.guru99.fragments;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginFormFragment {
    WebElement loginInput;

    WebElement passwordInput;
    WebElement loginButton;

    public LoginFormFragment(AppiumDriver appiumDriver) {
        loginInput = appiumDriver.findElement(By.xpath("//input[@name='uid']"));
        passwordInput = appiumDriver.findElement(By.xpath("//input[@name='password']"));
        loginButton = appiumDriver.findElement(By.xpath("//input[@name='btnLogin']"));

    }

    public void makeLogin(String login, String password) {
        loginInput.clear();
        loginInput.sendKeys(login);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
