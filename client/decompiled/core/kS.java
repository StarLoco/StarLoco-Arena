/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import org.apache.log4j.Logger;

public class kS
extends QN {
    public static final byte FJ = 2;
    public static final short MINOR = 70;
    protected static final Logger a;
    private static final kS FK;
    public static final String FL;
    public static final String FM;
    public static final String BUILD_VERSION;
    public static final Date FN;
    public static final byte[] FO;

    public static final void display() {
        a.info((Object)FM);
    }

    protected boolean i(byte[] byArray) {
        if (byArray == null) {
            return false;
        }
        if (byArray.length < 4) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        if (byteBuffer.get() != 2) {
            return false;
        }
        return byteBuffer.getShort() == 70;
    }

    protected byte[] pC() {
        return FO;
    }

    public static String j(byte[] byArray) {
        if (byArray == null || byArray.length != 3) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.get() & 0xFF;
        int n3 = byteBuffer.getShort() & 0xFFFF;
        stringBuilder.append(n2).append('.');
        if (n3 < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n3);
        return stringBuilder.toString();
    }

    static {
        String string;
        String string2;
        Object object;
        a = Logger.getLogger(kS.class);
        FK = new kS();
        Date date = new Date();
        try {
            object = ClassLoader.getSystemResource("com/ankamagames/dofusarena/common/constants/build.properties");
            Properties properties = new Properties();
            InputStream inputStream = ((URL)object).openStream();
            properties.load(inputStream);
            string2 = (String)properties.get("build.revision");
            string = (String)properties.get("build.date");
            try {
                inputStream.close();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        catch (Throwable throwable) {
            string2 = "-1";
            string = "";
        }
        BUILD_VERSION = string2;
        try {
            date = new SimpleDateFormat("E MMMM d h:m:s z yyyy", Locale.ENGLISH).parse(string);
        }
        catch (ParseException parseException) {
            a.error((Object)("BUILD_DATE invalide : " + string), (Throwable)parseException);
            date = new Date();
        }
        FN = date;
        FO = new byte[3];
        object = ByteBuffer.wrap(FO);
        ((ByteBuffer)object).put((byte)2);
        ((ByteBuffer)object).putShort((short)70);
        FL = String.format("%d.%02d (build %s)", 2, 70, BUILD_VERSION);
        FM = String.format("%d.%02d (build %s %4$tY-%4$tm-%4$td)", 2, 70, BUILD_VERSION, FN);
    }
}

