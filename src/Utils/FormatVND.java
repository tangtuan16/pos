package Utils;

import java.text.DecimalFormat;

public class FormatVND {

    public static String format(double amount) {
        DecimalFormat df = new DecimalFormat("#,### VND");
        return df.format(amount);
    }
}
