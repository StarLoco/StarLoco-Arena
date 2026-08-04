/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Nx
 */
public class nx_1
extends ael_2 {
    private long fz;
    private byte bzT;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.fz = byteBuffer.getLong();
        this.bzT = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 26310;
    }

    public long cA() {
        return this.fz;
    }

    public byte aaE() {
        return this.bzT;
    }
}

