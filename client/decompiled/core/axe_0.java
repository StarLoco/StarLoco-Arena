/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from axE
 */
public class axe_0
extends pr_0 {
    private static final acl_0 aU = new ym_0(new qi_2());
    public static final int ID = Integer.MIN_VALUE;
    private long agL;
    private int djV = 0;
    private long djW;

    public static axe_0 aKC() {
        try {
            axe_0 axe_02 = (axe_0)aU.adr();
            axe_02.a(aU);
            return axe_02;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new axe_0();
        }
    }

    public long aKD() {
        return this.agL;
    }

    public void ek(long l2) {
        this.agL = l2;
    }

    public void mH(int n2) {
        this.djV = n2;
    }

    public int aKE() {
        return this.djV;
    }

    public byte[] encode() {
        return null;
    }

    public boolean a(byte[] byArray) {
        return true;
    }

    public int getId() {
        return Integer.MIN_VALUE;
    }

    public void f(int n2) {
    }

    public void b() {
    }

    public void j() {
    }

    public long getTimeStamp() {
        return this.djW;
    }

    public void setTimeStamp(long l2) {
        this.djW = l2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ClockMessage clockId=").append(this.agL).append(", subClockId=").append(this.djV).append(", timestamp=").append(this.djW);
        return stringBuilder.toString();
    }
}

