package ui.rozetka;

import org.testng.annotations.BeforeMethod;
import ui.BaseUITest;

public class RozetkaTestBase extends BaseUITest {
    @BeforeMethod
    public void openRozetka() {
        driver.get("https://rozetka.com.ua");
    }
}
