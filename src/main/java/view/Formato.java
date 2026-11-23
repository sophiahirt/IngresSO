package view;

import java.text.NumberFormat;
import java.util.Locale;

public final class Formato {

    private static final NumberFormat MOEDA
            = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    private Formato() {
    }

    public static String moeda(double valor) {
        return MOEDA.format(valor);
    }
}
