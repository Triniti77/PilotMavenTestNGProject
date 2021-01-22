package pageObject.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;

import static pageObject.utils.Price.extractPrice;

@ParametersAreNonnullByDefault
public class MatchPriceBetweenCondition extends Condition {
    private final int minValue;
    private final int maxValue;

    public MatchPriceBetweenCondition(int minValue, int maxValue) {
        super(String.valueOf(minValue)+".."+String.valueOf(maxValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean apply(Driver driver, WebElement webElement) {
        String elementPriceString = webElement.getText();
        int elementPrice = extractPrice(elementPriceString); // из 21 394грн сделать 21394
        return elementPrice >= minValue && elementPrice <= maxValue;
    }
}
