package ui.google;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import ui.BaseUITest;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.*;

public class GoogleMailTest extends BaseUITest {
    private final String password = "Zwe7ySsv";
    private final String username = "65788907GT";

    enum OSType {
        Windows, Mac, Linux, Other
    };
    protected static OSType detectedOS;

    @Test
    public void testAccount() throws InterruptedException {
        driver.get("https://mail.google.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // login form - email input
        By emailInputLocator = By.xpath("//body/div[1]//div[@role='presentation']//form//input[@type='email']");
        WebElement emailInput =  wait.until(presenceOfElementLocated(emailInputLocator));
        emailInput.sendKeys(username + Keys.ENTER);

        By emailPasswordLocator = By.xpath("//body/div[1]//div[@role='presentation']//form//input[@type='password']");
        WebElement emailPassword = wait.until(visibilityOfElementLocated(emailPasswordLocator));
        emailPassword.sendKeys(password + Keys.ENTER);

        // wait emails list load
        By emailListTableLocator = By.cssSelector("div[role=main] table[role=grid]");
        wait.until(presenceOfElementLocated(emailListTableLocator));

        // click compose
        By composeButtonLocator = By.cssSelector("div.aic");
        driver.findElement(composeButtonLocator).click();

        By composeDialogLocator = By.cssSelector("*[role=dialog]");
        By emailToLocator = By.cssSelector("form>div:nth-child(1)");
        By emailContentLocator = By.cssSelector("form ~ table div[role=textbox]");
        By emailToInputLocator = By.cssSelector("form>div:nth-child(1) textarea[role=combobox]");
        By filesButtonLocator = By.cssSelector("form ~ table div[command=Files]");
        String email = username + "@gmail.com";
        String randomId = randomString(6);
        String subject = "test email id-"+randomId;
        String message = "test message for id-"+randomId;

        WebElement dialog = driver.findElement(composeDialogLocator);
        dialog.findElement(emailToLocator).click();
        dialog.findElement(emailToInputLocator).sendKeys(email + Keys.TAB + Keys.TAB + subject + Keys.TAB + message);

        // mac only
        if (getOperatingSystemType() == OSType.Mac) {
            // open mac os file select dialog and select test.jpg on desktop
            dialog.findElement(filesButtonLocator).click();
            Thread.sleep(1000);

            try {
                driver.switchTo().window(driver.getWindowHandle());
                final int DELAY = 125;

                Robot rob = new Robot();
                rob.setAutoDelay(20);
                rob.setAutoWaitForIdle(true);
                Dimension win = driver.manage().window().getSize();
                rob.mouseMove(win.getWidth()/2, (int)(win.getHeight()*0.5));
                rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                rob.delay(50);
                rob.keyPress(KeyEvent.VK_TAB);
                rob.keyRelease(KeyEvent.VK_TAB);
                rob.delay(DELAY);
                rob.keyPress(KeyEvent.VK_TAB);
                rob.keyRelease(KeyEvent.VK_TAB);
                rob.delay(DELAY);
                rob.keyPress(KeyEvent.VK_ALT);
                rob.keyPress(KeyEvent.VK_UP);
                rob.keyRelease(KeyEvent.VK_UP);
                rob.keyRelease(KeyEvent.VK_ALT);
                rob.delay(DELAY);
                rob.keyPress(KeyEvent.VK_TAB);
                rob.keyRelease(KeyEvent.VK_TAB);
                rob.delay(DELAY);
                rob.keyPress(KeyEvent.VK_T);
                rob.keyRelease(KeyEvent.VK_T);
                rob.keyPress(KeyEvent.VK_E);
                rob.keyRelease(KeyEvent.VK_E);
                rob.keyPress(KeyEvent.VK_S);
                rob.keyRelease(KeyEvent.VK_S);
                rob.delay(DELAY);
                rob.keyPress(KeyEvent.VK_ENTER);
                rob.keyRelease(KeyEvent.VK_ENTER);
                rob.delay(3000);
            } catch (AWTException e) {
                fail("Robot exception: " + e.getStackTrace());
            }
        }

        // send email
        String keysPressed;
        if (getOperatingSystemType() == OSType.Mac) {
            keysPressed = Keys.chord(Keys.COMMAND, Keys.RETURN);
        } else {
            keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
        }
        dialog.findElement(emailContentLocator).sendKeys(keysPressed);
        Thread.sleep(2000);

        // reload email list
        By emailListReloadLocator = By.cssSelector("div[act='20']");
        driver.findElement(emailListReloadLocator).click();
        By emailListTableTrLocator = By.cssSelector("div[role=main] table[role=grid] tr:first-child");
        // wait and enter message
        wait.until(presenceOfElementLocated(emailListTableTrLocator)).click();

        // message title
        By messageH2Locator = By.cssSelector("div[role=main] table[role=presentation] h2");
        String resultSubject = wait.until(visibilityOfElementLocated(messageH2Locator)).getText();
        assertEquals(subject, resultSubject, "Received message title the same");

        if (getOperatingSystemType() == OSType.Mac) {
            By messageAttachLocator = By.cssSelector("div[role=main] table[role=presentation] div[data-message-id] span>a[role=link]>span");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String attachText = (String)(js.executeScript("return arguments[0].innerText", driver.findElement(messageAttachLocator)));
            assertTrue(attachText.contains("test.jpg"), "Attached file is correct test.jpg");
        }
    }

    // random alpha-numeric string generator
    protected String randomString(int length) {
        char[] arr = new char[length];

        for (int i=0; i<length; i++) {
            int randomInt = (int)(Math.random()*36); // 0-9 a-z, letters=36
            arr[i] = (char)(randomInt >= 26 ? randomInt-26+48 : randomInt+97); // number char ascii from 48 to 58, a-z ascii from 97
        }
        return String.valueOf(arr);
    }

    // https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
    protected static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.Mac;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}
