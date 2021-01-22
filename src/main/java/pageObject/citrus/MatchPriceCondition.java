package pageObject.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;

import static pageObject.utils.Price.extractPrice;

@ParametersAreNonnullByDefault
public class MatchPriceCondition extends Condition {
    String strPrice;
    int intPrice;
    boolean useInt = false;

    public MatchPriceCondition(int price) {
        super(String.valueOf(price));
        intPrice = price;
        useInt = true;
    }

    public MatchPriceCondition(String price) {
        super(price);
        strPrice = price;
    }

    @Override
    public boolean apply(Driver driver, WebElement webElement) {
        String elementPriceString = webElement.getText();
        int elementPrice = extractPrice(elementPriceString); // из 21 394грн сделать 21394
        int matchPrice = useInt ? intPrice : extractPrice(strPrice);
        return elementPrice == matchPrice;
    }
}
