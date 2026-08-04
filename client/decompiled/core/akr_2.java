/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.TimeZone;
import org.apache.log4j.Logger;

/*
 * Renamed from aKR
 */
public abstract class akr_2 {
    public static final String dUh = "dateFormat.yearMonthDayHourMinute.short";
    public static final String dUi = "dateFormat.monthDayHourMinute.short";
    public static final String dUj = "dateFormat.yearMonthDayHourMinuteSecond";
    public static final String dUk = "durationFormat.yearMonthDayHourMinuteSecond.short";
    private static Logger a = Logger.getLogger(akr_2.class);
    protected static akr_2 dUl = null;
    private aie_0 aCt = akr_2.aVQ();
    private String aJ;
    private alj_1 dUm;

    public static akr_2 aVP() {
        return dUl;
    }

    public void a(aie_0 aie_02) {
        this.aCt = aie_02;
        this.aVR();
    }

    public aie_0 Fd() {
        return this.aCt;
    }

    public void setPath(String string) {
        this.aJ = string;
        this.aVR();
    }

    public String getString(String string) {
        if (this.dUm != null) {
            String string2 = this.dUm.get(string);
            if (string2 == null) {
                string2 = "!" + string + '!';
                a.warn((Object)("Propri\u00e9t\u00e9 introuvable dans le Translator key=" + string));
            }
            return string2;
        }
        return "!" + string + "!";
    }

    public String getString(String string, Object ... objectArray) {
        if (this.dUm != null) {
            String string2 = null;
            try {
                string2 = this.dUm.get(string);
                if (string2 == null) {
                    string2 = "!" + string + "!";
                    a.warn((Object)("Propri\u00e9t\u00e9 introuvable dans le Translator key=" + string));
                }
                if (objectArray.length != 0) {
                    return are_0.format(string2, objectArray);
                }
                return are_0.format(string2, new Object[0]);
            }
            catch (IllegalFormatException illegalFormatException) {
                return string2;
            }
        }
        return "!" + string + "!";
    }

    public String a(Date date, String string) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.format(date);
        }
        catch (Exception exception) {
            a.error((Object)"Erreur dans formatDate :", (Throwable)exception);
            return null;
        }
    }

    public boolean containsKey(String string) {
        if (this.dUm != null) {
            return this.dUm.containsKey(string);
        }
        return false;
    }

    public static aie_0 aVQ() {
        String string = System.getProperty("user.language");
        aie_0 aie_02 = aie_0.ly(string);
        if (aie_02 == null) {
            return aie_0.dOw;
        }
        return aie_02;
    }

    private boolean aVR() {
        if (this.aJ != null && this.aCt != null) {
            try {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.aJ + "_" + this.aCt.getLocale().getLanguage() + ".properties");
                this.dUm = new alj_1(inputStream);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                return false;
            }
        }
        return false;
    }
}

