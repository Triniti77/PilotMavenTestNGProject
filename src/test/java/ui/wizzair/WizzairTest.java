package ui.wizzair;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class WizzairTest {

    @Test
    public void flySearchResultTest() {
        Configuration.baseUrl = "https://wizzair.com";
        Configuration.timeout = 50000;
        Configuration.startMaximized = true;
//        Configuration.browser = "firefox";

        open("/");
        $("#search-departure-station").val("Vienna");
        $x("//mark").click();
        $("#search-arrival-station").val("Kyiv");
        $x("//mark").click();

        $("#search-departure-date").click();
        $x("//button[@data-pika-year='2021'][@data-pika-month='1'][@data-pika-day='19']").click();
        $x("//button[@data-pika-year='2021'][@data-pika-month='1'][@data-pika-day='26']").click();
        $x("//button[@data-test='calendar-shrinkable-ok-button']").click();

        $("#search-passenger").click();
        $$x("//button[@data-test='services-flight-search-increment']").get(0).click();
        $x("//button[@data-test='flight-search-hide-panel']").click();
        $x("//button[@data-test='flight-search-submit']").click();

        switchTo().window(1);
        $$(".flight-select__fare-selector").shouldHaveSize(2);
        $$x("//*[@data-test='flight-select-flight-info-details-time']").get(0).shouldHave(text(" Fri, 19 Feb 2021 "));
        $$x("//*[@data-test='flight-select-flight-info-details-time']").get(1).shouldHave(text(" Fri, 26 Feb 2021 "));
    }
}
