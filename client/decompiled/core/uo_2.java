/*
 * Decompiled with CFR 0.152.
 */
import java.nio.Buffer;
import java.nio.ByteBuffer;

/*
 * Renamed from uo
 */
public final class uo_2
implements aew_0 {
    private final ByteBuffer apS;
    private final XC aeL;
    private final int aW;

    public uo_2(Buffer buffer, int n2, int n3, XC xC) {
        this.apS = (ByteBuffer)buffer;
        this.aW = n3;
        this.aeL = xC;
    }

    public final Buffer getBuffer() {
        return this.apS;
    }

    public final int getId() {
        return this.aW;
    }

    public final void release() {
        assert (this.aeL != null);
        this.aeL.a(this);
    }
}

