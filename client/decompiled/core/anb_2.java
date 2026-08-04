/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/*
 * Renamed from anB
 */
public class anb_2
extends cr_2 {
    private static final ga_2 xa = ga_2.Qo();
    private long cJH = -1L;
    private String cgy = null;
    private boolean cJI = false;
    private long cgA = 0L;
    private String pattern;
    private mm_2 cgz = mm_2.Jx;
    public static final String cJJ = "millis";
    public static final String cJK = "datetime";
    public static final String cJL = "checkdirs";
    public static final String cJM = "granularity";
    public static final String kY = "when";
    public static final String cJN = "pattern";

    public anb_2() {
        this.cgA = xa.Qp();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{dateselector date: ");
        stringBuffer.append(this.cgy);
        stringBuffer.append(" compare: ").append(this.cgz.getValue());
        stringBuffer.append(" granularity: ");
        stringBuffer.append(this.cgA);
        if (this.pattern != null) {
            stringBuffer.append(" pattern: ").append(this.pattern);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void setMillis(long l2) {
        this.cJH = l2;
    }

    public long getMillis() {
        if (this.cgy != null) {
            this.validate();
        }
        return this.cJH;
    }

    public void iM(String string) {
        this.cgy = string;
        this.cJH = -1L;
    }

    public void dK(boolean bl2) {
        this.cJI = bl2;
    }

    public void hI(int n2) {
        this.cgA = n2;
    }

    public void a(ark_0 ark_02) {
        this.a((mm_2)ark_02);
    }

    public void a(mm_2 mm_22) {
        this.cgz = mm_22;
    }

    public void setPattern(String string) {
        this.pattern = string;
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                String string = vj_0Array[j].getName();
                if (cJJ.equalsIgnoreCase(string)) {
                    try {
                        this.setMillis(Long.parseLong(vj_0Array[j].getValue()));
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.eC("Invalid millisecond setting " + vj_0Array[j].getValue());
                    }
                    continue;
                }
                if (cJK.equalsIgnoreCase(string)) {
                    this.iM(vj_0Array[j].getValue());
                    continue;
                }
                if (cJL.equalsIgnoreCase(string)) {
                    this.dK(UI.gh(vj_0Array[j].getValue()));
                    continue;
                }
                if (cJM.equalsIgnoreCase(string)) {
                    try {
                        this.hI(Integer.parseInt(vj_0Array[j].getValue()));
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.eC("Invalid granularity setting " + vj_0Array[j].getValue());
                    }
                    continue;
                }
                if (kY.equalsIgnoreCase(string)) {
                    this.a(new mm_2(vj_0Array[j].getValue()));
                    continue;
                }
                if (cJN.equalsIgnoreCase(string)) {
                    this.setPattern(vj_0Array[j].getValue());
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.cgy == null && this.cJH < 0L) {
            this.eC("You must provide a datetime or the number of milliseconds.");
        } else if (this.cJH < 0L && this.cgy != null) {
            DateFormat dateFormat = this.pattern == null ? DateFormat.getDateTimeInstance(3, 3, Locale.US) : new SimpleDateFormat(this.pattern);
            try {
                this.setMillis(dateFormat.parse(this.cgy).getTime());
                if (this.cJH < 0L) {
                    this.eC("Date of " + this.cgy + " results in negative milliseconds value" + " relative to epoch (January 1, 1970, 00:00:00 GMT).");
                }
            }
            catch (ParseException parseException) {
                this.eC("Date of " + this.cgy + " Cannot be parsed correctly. It should be in" + (this.pattern == null ? " MM/DD/YYYY HH:MM AM_PM" : this.pattern) + " format.");
            }
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        return file2.isDirectory() && !this.cJI || this.cgz.b(file2.lastModified(), this.cJH, this.cgA);
    }
}

