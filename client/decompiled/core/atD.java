/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public abstract class atD
implements aE {
    protected atD() {
    }

    public abstract long TH();

    public boolean isValid() {
        return true;
    }

    public void a(OZ oZ) {
        if (!this.isValid()) {
            return;
        }
        this.c(oZ);
    }

    protected abstract void c(OZ var1);

    protected abstract int TI();

    protected abstract void A(ByteBuffer var1);

    protected abstract void c(ahh_0 var1, ByteBuffer var2);

    public int w() {
        return aap_1.I(this.getClass()).b(this);
    }

    public void c(ByteBuffer byteBuffer) {
        aap_1.I(this.getClass()).a(this, byteBuffer);
    }

    public static atD e(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        atD atD2 = aap_1.L(byteBuffer);
        atD2.c(ahh_02, byteBuffer);
        return atD2;
    }

    public boolean isPersistent() {
        return false;
    }

    public void clean() {
    }
}

