/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from AH
 */
public class ah_1
implements aho_0 {
    private int aHm = 0;
    private long lc = -1L;
    private String aHn;
    public static final byte aHo = 16;
    public final lb_0 aHp = new lb_0();
    public static final String aHq = "firstRound";
    public static final String aHr = "quarterFinal";
    public static final String aHs = "semiFinale";
    public static final String aHt = "finale";
    public static final String aHu = "winner";
    public static final String aHv = "coachNameHighlighted";
    public static final String[] ce = new String[]{"firstRound", "quarterFinal", "semiFinale", "finale", "winner", "coachNameHighlighted"};

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aHq)) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (int j = 16; j < 32; ++j) {
                arrayList.add(this.aHp.get(j));
            }
            return arrayList.toArray();
        }
        if (string.equals(aHr)) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (int j = 8; j < 16; ++j) {
                arrayList.add(this.aHp.get(j));
            }
            return arrayList.toArray();
        }
        if (string.equals(aHs)) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (int j = 4; j < 8; ++j) {
                arrayList.add(this.aHp.get(j));
            }
            return arrayList.toArray();
        }
        if (string.equals(aHt)) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (int j = 2; j < 4; ++j) {
                arrayList.add(this.aHp.get(j));
            }
            return arrayList.toArray();
        }
        if (string.equals(aHu)) {
            return this.aHp.get(1);
        }
        if (string.equals(aHv)) {
            return this.aHn;
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

    public lb_0 Hq() {
        return this.aHp;
    }

    public int Hr() {
        return this.aHm;
    }

    public void eE(int n2) {
        this.aHm = n2;
    }

    public long fx() {
        return this.lc;
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public void dq(String string) {
        this.aHn = string;
    }
}

