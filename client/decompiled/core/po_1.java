/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from po
 */
public class po_1
extends axX {
    private static final acl_0 aU = new ym_0(new ajz_1());
    private byte aV;

    public static po_1 uf() {
        try {
            po_1 po_12 = (po_1)aU.adr();
            po_12.a(aU);
            return po_12;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new po_1();
        }
    }

    private po_1() {
    }

    public byte[] encode() {
        return this.x(new byte[]{this.aV});
    }

    public boolean a(byte[] byArray) {
        this.aV = byArray[0];
        return true;
    }

    public int getId() {
        return 3;
    }

    public byte an() {
        return this.aV;
    }

    public void t(byte by) {
        this.aV = by;
    }

    public boolean isSecure() {
        return false;
    }

    /* synthetic */ po_1(ajz_1 ajz_12) {
        this();
    }
}

