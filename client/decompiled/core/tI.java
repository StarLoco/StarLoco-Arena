/*
 * Decompiled with CFR 0.152.
 */
public class tI
extends axX {
    private static final acl_0 aU = new ym_0(new iu_1());
    private boolean anY;

    public static tI zN() {
        try {
            tI tI2 = (tI)aU.adr();
            tI2.a(aU);
            return tI2;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new tI();
        }
    }

    private tI() {
    }

    public byte[] encode() {
        return this.x(new byte[]{(byte)(this.anY ? 1 : 0)});
    }

    public boolean a(byte[] byArray) {
        this.anY = byArray[0] == 1;
        return true;
    }

    public int getId() {
        return 2;
    }

    public boolean zO() {
        return this.anY;
    }

    public void aC(boolean bl2) {
        this.anY = bl2;
    }

    public boolean isSecure() {
        return false;
    }

    /* synthetic */ tI(iu_1 iu_12) {
        this();
    }
}

