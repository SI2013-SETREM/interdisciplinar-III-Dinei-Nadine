
package com.br.squemasports.config;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import org.springframework.format.number.CurrencyFormatter;

public class MoneyFormatter extends CurrencyFormatter {

    public MoneyFormatter() {
        setCurrency(Currency.getInstance("BRL"));
        setRoundingMode(RoundingMode.HALF_UP);
        setFractionDigits(2);
    }

}
