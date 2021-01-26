package ui.citrus.steps;

import io.qameta.allure.Step;
import pageObject.citrus.ComparePage;
import pageObject.citrus.ProductCompareCardFragment;

import java.util.List;

public class CompareSteps {
    private final ComparePage comparePage;

    public CompareSteps() {
        comparePage = new ComparePage();
    }

    @Step("Add {number} of products to basket from Compare Page")
    public CompareSteps addToBasket(int number) {
        List<ProductCompareCardFragment> cproducts = comparePage.open().getProductsList();
        for (int i=0; i<number; i++) {
            cproducts.get(i).addToBasket();
        }
        return this;
    }

    public ComparePage getPage() {
        return comparePage;
    }
}
