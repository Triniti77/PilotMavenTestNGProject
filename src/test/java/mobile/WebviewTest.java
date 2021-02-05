package mobile;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.pageObject.guru99.pages.AgileProjectPage;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class WebviewTest {
    AppiumDriver appiumDriver;

    @BeforeClass
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 2 API 27");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");


        URL serverAddress = new URL("http://0.0.0.0:4723/wd/hub");

        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 100);
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("chromedriverExecutable", "/opt/chromedriver/88.0.4324/chromedriver");
        capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));

        appiumDriver = new AppiumDriver(serverAddress, capabilities);
    }

    @Test
    public void agileTest() throws Exception {
        AgileProjectPage agilePage = new AgileProjectPage(appiumDriver);
        agilePage.navigate();
        agilePage.makeLogin("1303", "Guru99");
        try {
            Thread.sleep(2000); // wait page loaded
        } catch (InterruptedException e) {
        }
        assertEquals(agilePage.getTitle(), "Guru99 Bank Customer Page","Check the user is logged in");
    }

    @AfterClass
    public void teardown() {
        appiumDriver.quit();
    }


}
