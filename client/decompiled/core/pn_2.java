/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from PN
 */
public class pn_2
extends axX {
    private static final acl_0 aU = new ym_0(new ad_0());
    private String bEp;

    public static pn_2 acu() {
        try {
            pn_2 pn_22 = (pn_2)aU.adr();
            pn_22.a(aU);
            return pn_22;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new pn_2();
        }
    }

    private pn_2() {
    }

    public byte[] encode() {
        byte[] byArray = this.bEp.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.x(byteBuffer.array());
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.bEp = new String(byArray2);
        return true;
    }

    public int getId() {
        return 12;
    }

    public String getPropertyName() {
        return this.bEp;
    }

    public void fF(String string) {
        this.bEp = string;
    }

    public boolean isSecure() {
        return false;
    }

    /* synthetic */ pn_2(ad_0 ad_02) {
        this();
    }
}

