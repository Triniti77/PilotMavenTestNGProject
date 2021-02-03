package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class NativeAppTest {
    AppiumDriver appiumDriver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 2 API 27");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");


        URL serverAddress = new URL("http://0.0.0.0:4723/wd/hub");

        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 100);
        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
        capabilities.setCapability("platformName","Android");

        appiumDriver = new AppiumDriver(serverAddress, capabilities);
    }

    @Test
    public void testCalc() throws Exception {
        WebElement two = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_2')]"));
        two.click();
        WebElement plus = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'op_add')]"));
        plus.click();
        WebElement three = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_3')]"));
        three.click();
        WebElement eq = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'eq')]"));
        eq.click();
        WebElement results = appiumDriver.findElement(By.xpath("//android.widget.TextView[contains(@resource-id, 'result')]"));
        assertEquals("5", results.getText(), "Check the calculator result is 5");
    }

    @Test
    public void testCalcMult() throws Exception {
        WebElement nine = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_9')]"));
        WebElement mult = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'op_mul')]"));
        WebElement three = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_3')]"));
        WebElement four = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_4')]"));
        WebElement div = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'op_div')]"));
        WebElement del = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'del')]"));
        WebElement eq = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'eq')]"));
        WebElement results = appiumDriver.findElement(By.xpath("//android.widget.TextView[contains(@resource-id, 'result')]"));
        nine.click();
        mult.click();
        nine.click();
        div.click();
        four.click();
        del.click();
        three.click();
        eq.click();
        assertEquals("27", results.getText(), "Check the calculator result is 27");
    }
}
