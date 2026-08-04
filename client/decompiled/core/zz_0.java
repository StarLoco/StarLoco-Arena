/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from ZZ
 */
public class zz_0
extends so_0 {
    private byte[] zs;
    private byte zt;
    private byte zu;
    private byte zv;
    private byte[] zw;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + this.zs.length + 1 + 1 + 1 + 2 + this.zw.length);
        byteBuffer.put((byte)this.zs.length);
        byteBuffer.put(this.zs);
        byteBuffer.put(this.zt);
        byteBuffer.put(this.zu);
        byteBuffer.put(this.zv);
        byteBuffer.putShort((short)this.zw.length);
        byteBuffer.put(this.zw);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27525;
    }

    public void G(byte[] byArray) {
        this.zs = byArray;
    }

    public void Q(byte by) {
        this.zt = by;
    }

    public void P(byte by) {
        this.zu = by;
    }

    public void S(byte by) {
        this.zv = by;
    }

    public void S(byte[] byArray) {
        this.zw = byArray;
    }

    public static void a(aez_0 aez_02, aMO aMO2) {
        gw_2 gw_22 = aez_02.aTF();
        ayv_0 ayv_02 = new ayv_0(100, 100, 1.0f, 0.5f, 0.2f);
        ayv_02.a(gw_22, "1_AnimStatique", "Arme");
        ((ajz)ayv_02).a("png", new uM(aMO2));
        ayv_02.cleanup();
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }
}

