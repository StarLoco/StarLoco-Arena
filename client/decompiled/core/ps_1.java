/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from PS
 */
public class ps_1
implements aho_0 {
    public static final byte aMi = 10;
    public static final String aMj = "exchangeId";
    public static final String aMk = "localCardExchange";
    public static final String aMm = "localCardCount";
    public static final String bEv = "localCardsPrice";
    public static final String bEw = "affiliationPrice";
    public static final String bEx = "canBuyCards";
    public static final String[] ce = new String[]{"exchangeId", "localCardExchange", "localCardCount", "localCardsPrice", "affiliationPrice", "canBuyCards"};
    private static final int[] bEy = new int[1];
    private cp_2 bEz = new cp_2();
    private long bEA = 0L;

    public String[] getFields() {
        return ce;
    }

    public ps_1(long l2) {
        this.bEA = l2;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aMk)) {
            Object[] objectArray = this.bEz.getValues();
            wy_2[] wy_2Array = new wy_2[objectArray.length];
            int n2 = objectArray.length;
            for (int j = 0; j < n2; ++j) {
                wy_2Array[j] = (wy_2)objectArray[j];
            }
            return wy_2Array;
        }
        if (string.equals(aMm)) {
            return this.bEz.size();
        }
        if (string.equals(bEv)) {
            return this.acz();
        }
        if (string.equals(bEw)) {
            return this.acA();
        }
        if (string.equals(bEx)) {
            return this.acA() > 0L && (long)this.acz() - this.acA() >= 0L;
        }
        return null;
    }

    public void b(wy_2 wy_22, short s) {
        wy_2 wy_23 = aoi_0.aXY().ac(ByteBuffer.wrap(wy_22.cd()));
        if (this.bEz.v(wy_23.je())) {
            wy_2 wy_24 = (wy_2)this.bEz.t(wy_23.je());
            wy_24.w(s);
        } else {
            wy_23.q(s);
            this.bEz.a(wy_22.je(), wy_23);
        }
    }

    public void cq(long l2) {
        if (this.bEz.v(l2)) {
            wy_2 wy_22 = (wy_2)this.bEz.t(l2);
            if (wy_22.hG() == 1) {
                this.bEz.u(l2);
            } else {
                wy_22.w((short)-1);
            }
        }
    }

    private int acz() {
        ps_1.bEy[0] = 0;
        this.bEz.a(new ti_0(this));
        return bEy[0];
    }

    private long acA() {
        return this.bEA;
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

    public boolean acB() {
        return this.bEz == null || this.bEz.size() < 10;
    }

    public void clean() {
        this.bEz.clear();
    }

    static /* synthetic */ int[] acC() {
        return bEy;
    }
}

