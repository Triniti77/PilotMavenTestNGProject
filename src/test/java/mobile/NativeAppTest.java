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
    WebElement one, two, three, four, five, six, nine, eight, point, div, plus, sub, del, mult, eq, results;

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

    private void findElements() {
        one = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_1')]"));
        two = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_2')]"));
        three = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_3')]"));
        four = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_4')]"));
        five = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_5')]"));
        six = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_6')]"));
        eight = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_8')]"));
        nine = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'digit_9')]"));
        plus = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'op_add')]"));
        div = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'op_div')]"));
        point = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'dec_point')]"));
        eq = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'eq')]"));
        results = appiumDriver.findElement(By.xpath("//android.widget.TextView[contains(@resource-id, 'result')]"));
        mult = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'op_mul')]"));
        del = appiumDriver.findElement(By.xpath("//android.widget.Button[contains(@resource-id, 'del')]"));
    }

    @Test
    public void testCalc() throws Exception {
        findElements();
        two.click();
        plus.click();
        three.click();
        eq.click();
        assertEquals("5", results.getText(), "Check the calculator result is 5");
    }

    @Test
    public void testCalcMult() throws Exception {
        findElements();
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

    @Test
    public void testCalcFloat() throws Exception {
        findElements();
        eight.click();
        point.click();
        six.click();
        sub.click();
        six.click();
        point.click();
        two.click();
        eq.click();
        assertEquals("2.4", results.getText(), "Check the calculator result is 2.4");
    }

    @Test
    public void testCalcFloat2() throws Exception {
        findElements();
        five.click();
        point.click();
        one.click();
        one.click();
        mult.click();
        nine.click();
        point.click();
        five.click();
        div.click();
        three.click();
        del.click();
        four.click();
        eq.click();
        assertEquals("12.13625", results.getText(), "Check the calculator result is 12.13625");
    }

}
