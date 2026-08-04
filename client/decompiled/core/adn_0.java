/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from adN
 */
public class adn_0
extends so_0 {
    private long aj;
    private int nE;
    private int nF;
    private short nG;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(18);
        byteBuffer.putLong(this.aj);
        byteBuffer.putInt(this.nE);
        byteBuffer.putInt(this.nF);
        byteBuffer.putShort(this.nG);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 8021;
    }

    public void by(short s) {
        this.nG = s;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void jW(int n2) {
        this.nE = n2;
    }

    public void jX(int n2) {
        this.nF = n2;
    }
}

