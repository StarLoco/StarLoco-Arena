/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from BA
 */
public class ba_1
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected ba_1(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIPixelFormatUpcast(l2), bl2);
        this.hf = l2;
    }

    protected ba_1(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIPixelFormatUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(ba_1 ba_12) {
        if (ba_12 == null) {
            return 0L;
        }
        return ba_12.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public ba_1 Ir() {
        if (this.hf == 0L) {
            return null;
        }
        return new ba_1(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof ba_1) {
            bl2 = ((ba_1)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public static short a(ayh ayh2, int n2, int n3, qr_1 qr_12) {
        return XugglerJNI.IPixelFormat_getYUV420PPixel(ayh.a(ayh2), ayh2, n2, n3, qr_12.dZ());
    }

    public static void a(ayh ayh2, int n2, int n3, qr_1 qr_12, short s) {
        XugglerJNI.IPixelFormat_setYUV420PPixel(ayh.a(ayh2), ayh2, n2, n3, qr_12.dZ(), s);
    }

    public static int b(ayh ayh2, int n2, int n3, qr_1 qr_12) {
        return XugglerJNI.IPixelFormat_getYUV420PPixelOffset(ayh.a(ayh2), ayh2, n2, n3, qr_12.dZ());
    }
}

