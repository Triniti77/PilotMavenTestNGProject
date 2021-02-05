package mobile.pageObject.guru99.pages;

import io.appium.java_client.AppiumDriver;
import mobile.pageObject.guru99.fragments.LoginFormFragment;

public class AgileProjectPage extends BasePage {
    LoginFormFragment loginFormFragment;

    public AgileProjectPage(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public void navigate() {
        appiumDriver.get(baseUrl+"/Agile_Project/Agi_V1/");
    }

    public void makeLogin(String username, String password) {
        loginFormFragment = new LoginFormFragment(appiumDriver);
        loginFormFragment.makeLogin(username, password);
    }
}
