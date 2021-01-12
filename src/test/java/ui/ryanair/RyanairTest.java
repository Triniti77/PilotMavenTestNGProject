package ui.ryanair;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class RyanairTest {
    @Test
    public void RyanairSearchResultTest() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://www.ryanair.com";
        Configuration.timeout = 50000;

        open("/ua/uk");
        $x("//button[@data-ref='cookie.accept-all']").click();
        SelenideElement departure = $("#input-button__departure");

        departure.clear();
        departure.val("Vienna");
        $(".list__airports-scrollable-container fsw-airport-item").click();

        SelenideElement destination = $("#input-button__destination");

        destination.clear();
        destination.val("Kyiv");
        $(".list__airports-scrollable-container fsw-airport-item").click();

        $x("//div[@class='calendar-body__cell'][@data-id='2021-02-16']").click();
        $x("//div[@class='calendar-body__cell'][@data-id='2021-02-23']").click();

        $x("//fsw-passengers-container//div[@data-ref='counter.counter__increment']").click();
        $x("//fsw-passengers-container//button[contains(@class,'passengers__confirm-button')]").click();

        $x("//fsw-flight-search-widget//button[@data-ref='flight-search-widget__cta']").click();

        $$x("//flights-trip-details-container//h4").get(0).shouldHave(text("Vienna"));
        $$x("//flights-trip-details-container//h4").get(1).shouldHave(text("Київ-Бориспіль"));
        $x("//flights-trip-details//div[contains(@class,'details__bottom-bar')]").shouldHave(text("16 лют"));
        $x("//flights-trip-details//div[contains(@class,'details__bottom-bar')]").shouldHave(text("23 лют"));
    }
}
