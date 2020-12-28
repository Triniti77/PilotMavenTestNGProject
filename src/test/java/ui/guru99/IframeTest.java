package ui.guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class IframeTest extends Guru99Base {

    @Test
    public void videoPlayIframeTest() throws AWTException, InterruptedException {
        String frameId = "flow_close_btn_iframe";

        wait.until(visibilityOfElementLocated(By.id("playBtn"))).click();

        Dimension size = driver.manage().window().getSize();
        WebElement player = driver.findElement(By.xpath("//div[contains(@id,'primis_playerSekindoSPlayer')]"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].scrollIntoView();", player);
        Dimension playerSize = player.getSize();

        driver.switchTo().window(driver.getWindowHandle());
        Robot rob = new Robot();
        // wait until ads start
        // wait.wait(10000); // fall with exception
        Thread.sleep(8000);
        List<WebElement> adsContainers = driver.findElements(By.cssSelector("iframe[src*=imasdk]")); // $(".videoAdUiSkipContainer")
        if (adsContainers.size() > 0) {
            // move mouse over "skip ads" button and click it
            rob.mouseMove(playerSize.getWidth() - playerSize.getWidth() / 10, size.getHeight() - playerSize.getHeight() / 5);
            rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            // without sleep mouse does not click
            Thread.sleep(100);
            rob.mouseMove(size.getWidth()/2, size.getHeight()/2);
            // wait until video start
            // wait.wait(2000); // fall with exception
            Thread.sleep(2000);
            // move mouse out from player
        }
        WebElement playBtn = driver.findElement(By.id("transparentInner")); // id=playBtn does not find by driver
        assertFalse(playBtn.isDisplayed(), "Play button is not visible when mouse outside player");
        rob.mouseMove(playerSize.getWidth() - playerSize.getWidth() / 2, size.getHeight() - playerSize.getHeight() / 2);
        // wait animation
        Thread.sleep(2000);
        playBtn = driver.findElement(By.id("transparentInner"));
        assertTrue(playBtn.isDisplayed(), "Play button is visible when mouse inside player");
    }
}
