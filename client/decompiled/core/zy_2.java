/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Zy
 */
public class zy_2 {
    private int cdl;
    private int cdm;
    private boolean cdn;

    public zy_2() {
    }

    public zy_2(int n2, int n3, boolean bl2) {
        this.cdl = n2;
        this.cdm = n3;
        this.cdn = bl2;
    }

    public static int nj() {
        return 9;
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(zy_2.nj());
        byteBuffer.putInt(this.cdl);
        byteBuffer.putInt(this.cdm);
        byteBuffer.put(this.cdn ? (byte)1 : 0);
        return byteBuffer.array();
    }

    public void f(ByteBuffer byteBuffer) {
        this.cdl = byteBuffer.getInt();
        this.cdm = byteBuffer.getInt();
        this.cdn = byteBuffer.get() == 1;
    }

    public int anV() {
        return this.cdl;
    }

    public int anW() {
        return this.cdm;
    }

    public boolean anX() {
        return this.cdn;
    }
}

