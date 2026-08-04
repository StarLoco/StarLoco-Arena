/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class avF
implements aho_0 {
    private static final int dfD = 3;
    public static String dfE = "events";
    public static String dfF = "day";
    public static String dfG = "month";
    public static String nN = "style";
    public static String dfH = "hasMoreEventsToShow";
    public static String[] ce = new String[]{dfE, dfF, dfG, nN, dfH};
    private ArrayList byq = new ArrayList();
    private byte dfI = 0;
    private byte dfJ = 0;
    private boolean dfK = false;

    public String[] getFields() {
        return ce;
    }

    public avF(byte by, byte by2, ArrayList arrayList, boolean bl2) {
        this.dfI = by;
        this.dfJ = by2;
        this.dfK = bl2;
        this.byq = arrayList;
    }

    public Object getFieldValue(String string) {
        if (string.equals(dfE)) {
            ArrayList arrayList = new ArrayList();
            tx_1 tx_12 = de_2.Mc().Mg();
            for (int j = 0; j < this.byq.size(); ++j) {
                if (!tx_12.contains(amu.lu(((iz_0)this.byq.get(j)).getType()))) continue;
                arrayList.add(this.byq.get(j));
            }
            return arrayList;
        }
        if (string.equals(dfF)) {
            if (this.dfI == 1) {
                return this.dfI + " " + aon_0.aYc().getString("month" + (this.dfJ - 1) % 12);
            }
            return this.dfI;
        }
        if (string.equals(dfG)) {
            return this.dfJ;
        }
        if (string.equals(nN)) {
            rd_1 rd_12 = rd_1.aF(System.currentTimeMillis());
            if (this.dfI == rd_12.getDay() && this.dfJ == rd_12.getMonth()) {
                return "calendarToday";
            }
            return this.dfK ? "calendarDayDefault" : "calendarDayGrey";
        }
        if (string.equals(dfH)) {
            return this.byq.size() > 3;
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

