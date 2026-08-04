/*
 * Decompiled with CFR 0.152.
 */
import java.nio.Buffer;
import java.nio.ShortBuffer;

public final class qx
implements aew_0 {
    private final ShortBuffer aeK;
    private final XC aeL;
    private final int aW;

    public qx(Buffer buffer, int n2, int n3, XC xC) {
        this.aeK = (ShortBuffer)buffer;
        this.aW = n3;
        this.aeL = xC;
    }

    public final Buffer getBuffer() {
        return this.aeK;
    }

    public final int getId() {
        return this.aW;
    }

    public final void release() {
        assert (this.aeL != null);
        this.aeL.a(this);
    }
}

