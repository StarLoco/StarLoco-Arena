/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public abstract class axX
extends pr_0 {
    public byte[] x(byte[] byArray) {
        int n2;
        byte[] byArray2 = byArray;
        if (this.isSecure()) {
            byArray2 = um_1.x(byArray);
        }
        if ((n2 = byArray2.length + 2 + 4 + 1) <= 7 && n2 > Short.MAX_VALUE) {
            a.error((Object)("Longueur de message incorrecte : " + n2));
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putShort((short)n2);
        byteBuffer.putInt(this.getId());
        byteBuffer.put((byte)(this.isSecure() ? 1 : 0));
        byteBuffer.put(byArray2);
        return byteBuffer.array();
    }

    public void f(int n2) {
    }

    public void b() {
    }

    public void j() {
    }

    public abstract boolean isSecure();
}

