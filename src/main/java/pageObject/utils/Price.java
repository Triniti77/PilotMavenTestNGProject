package pageObject.utils;

import java.text.DecimalFormat;

public class Price {
    public static int extractPrice(String price) {
        String cleanPrice = price.replaceAll("-?[^\\d.]", "");
        return Integer.parseInt(cleanPrice);
    }

    public static String priceToString(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }
}
