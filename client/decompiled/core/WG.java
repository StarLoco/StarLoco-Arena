/*
 * Decompiled with CFR 0.152.
 */
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class WG {
    private static final int bUN = 1000;
    private static final int bUO = 60;
    private static final int bUP = 60;
    private static final int bUQ = 10;
    public static final String bUR = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String bUS = "yyyy-MM-dd";
    public static final String bUT = "HH:mm:ss";
    public static final DateFormat bUU = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ", Locale.US);
    private static final MessageFormat bUV = new MessageFormat("{0}{1}");
    private static final double[] bUW = new double[]{0.0, 1.0, 2.0};
    private static final String[] bUX = new String[]{"", "1 minute ", "{0,number} minutes "};
    private static final String[] bUY = new String[]{"0 seconds", "1 second", "{1,number} seconds"};
    private static final ChoiceFormat bUZ = new ChoiceFormat(bUW, bUX);
    private static final ChoiceFormat bVa = new ChoiceFormat(bUW, bUY);

    private WG() {
    }

    public static String c(long l2, String string) {
        return WG.format(new Date(l2), string);
    }

    public static String format(Date date, String string) {
        DateFormat dateFormat = WG.gC(string);
        return dateFormat.format(date);
    }

    public static String db(long l2) {
        long l3 = l2 / 1000L;
        long l4 = l3 / 60L;
        Object[] objectArray = new Object[]{new Long(l4), new Long(l3 % 60L)};
        return bUV.format(objectArray);
    }

    private static DateFormat gC(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string);
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(timeZone);
        simpleDateFormat.setLenient(true);
        return simpleDateFormat;
    }

    public static int a(Calendar calendar) {
        int n2 = calendar.get(6);
        int n3 = (calendar.get(1) - 1900) % 19 + 1;
        int n4 = (11 * n3 + 18) % 30;
        if (n4 == 25 && n3 > 11 || n4 == 24) {
            ++n4;
        }
        return ((n2 + n4) * 6 + 11) % 177 / 22 & 7;
    }

    public static String ajk() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = calendar.getTimeZone();
        int n2 = timeZone.getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
        StringBuffer stringBuffer = new StringBuffer(n2 < 0 ? "-" : "+");
        n2 = Math.abs(n2);
        int n3 = n2 / 3600000;
        int n4 = n2 / 60000 - 60 * n3;
        if (n3 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(n3);
        if (n4 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(n4);
        return bUU.format(calendar.getTime()) + stringBuffer.toString();
    }

    public static Date gD(String string) {
        return new SimpleDateFormat(bUR).parse(string);
    }

    public static Date gE(String string) {
        return new SimpleDateFormat(bUS).parse(string);
    }

    public static Date gF(String string) {
        try {
            return WG.gD(string);
        }
        catch (ParseException parseException) {
            return WG.gE(string);
        }
    }

    static {
        bUV.setFormat(0, bUZ);
        bUV.setFormat(1, bVa);
    }
}

