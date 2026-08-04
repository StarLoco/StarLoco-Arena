/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from MX
 */
public class mx_1
extends so_0 {
    public static short byz = (short)-1;
    private long bZ;
    private short byA;
    private int byB;
    private int byC;

    public void b() {
        super.b();
        this.byA = byz;
        this.byB = byz;
        this.byC = byz;
    }

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(18);
        byteBuffer.putLong(this.bZ);
        byteBuffer.putShort(this.byA);
        byteBuffer.putInt(this.byB);
        byteBuffer.putInt(this.byC);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 551;
    }

    public void g(long l2) {
        this.bZ = l2;
    }

    public void aK(short s) {
        this.byA = s;
    }

    public void gS(int n2) {
        this.byB = n2;
    }

    public void gT(int n2) {
        this.byC = n2;
    }
}

