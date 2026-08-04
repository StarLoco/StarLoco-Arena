/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from acb
 */
public abstract class acb_2
extends pr_0 {
    public static final int cjf = 5;

    public void b() {
    }

    public void j() {
    }

    public byte[] a(byte by, byte[] byArray) {
        int n2 = 5 + byArray.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putShort((short)n2);
        byteBuffer.put(by);
        byteBuffer.putShort((short)this.getId());
        byteBuffer.put(byArray);
        return byteBuffer.array();
    }

    public void f(int n2) {
    }
}

