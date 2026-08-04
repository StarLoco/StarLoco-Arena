/*
 * Decompiled with CFR 0.152.
 */
import java.nio.Buffer;

/*
 * Renamed from Zf
 */
public final class zf_1
implements aew_0 {
    private final Buffer ccH;
    private final XC aeL;
    private final int aW;

    public zf_1(Buffer buffer, int n2, int n3, XC xC) {
        this.ccH = buffer;
        this.aW = n3;
        this.aeL = xC;
    }

    public final Buffer getBuffer() {
        return this.ccH;
    }

    public final int getId() {
        return this.aW;
    }

    public final void release() {
        assert (this.aeL != null);
        this.aeL.a(this);
    }
}

