/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mS
 */
public class ms_0
extends axX {
    private static final acl_0 aU = new ym_0(new gy_2());

    public static ms_0 rz() {
        try {
            ms_0 ms_02 = (ms_0)aU.adr();
            ms_02.a(aU);
            return ms_02;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new ms_0();
        }
    }

    private ms_0() {
    }

    public byte[] encode() {
        return this.x(new byte[]{0});
    }

    public boolean a(byte[] byArray) {
        return true;
    }

    public int getId() {
        return 10;
    }

    public boolean isSecure() {
        return false;
    }

    /* synthetic */ ms_0(gy_2 gy_22) {
        this();
    }
}

