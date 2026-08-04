/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ll
 */
public class ll_2
extends axX {
    private static final acl_0 aU = new ym_0(new apJ());
    private String GS;
    private String GT;

    public static ll_2 qb() {
        try {
            ll_2 ll_22 = (ll_2)aU.adr();
            ll_22.a(aU);
            return ll_22;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new ll_2();
        }
    }

    private ll_2() {
    }

    public byte[] encode() {
        byte[] byArray = this.GS.getBytes();
        byte[] byArray2 = this.GT.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length + 1 + byArray2.length);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.put((byte)byArray2.length);
        byteBuffer.put(byArray2);
        return this.x(byteBuffer.array());
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.GS = new String(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray3);
        this.GT = new String(byArray3);
        return true;
    }

    public int getId() {
        return 1;
    }

    public String qc() {
        return this.GS;
    }

    public void aQ(String string) {
        this.GS = string;
    }

    public String getPassword() {
        return this.GT;
    }

    public void setPassword(String string) {
        this.GT = string;
    }

    public boolean isSecure() {
        return false;
    }

    /* synthetic */ ll_2(apJ apJ2) {
        this();
    }
}

