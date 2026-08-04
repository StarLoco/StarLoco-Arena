/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from va
 */
public class va_0
extends alp {
    private volatile long hf;
    public static final TimeUnit arI = TimeUnit.MICROSECONDS;
    public static final long arJ = XugglerJNI.Global_NO_PTS_get();
    public static final long arK = XugglerJNI.Global_DEFAULT_PTS_PER_SECOND_get();

    private void noop() {
        di.a(null, 1);
    }

    protected va_0(long l2, boolean bl2) {
        super(XugglerJNI.SWIGGlobalUpcast(l2), bl2);
        this.hf = l2;
    }

    protected va_0(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGGlobalUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(va_0 va_02) {
        if (va_02 == null) {
            return 0L;
        }
        return va_02.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public va_0 Bd() {
        if (this.hf == 0L) {
            return null;
        }
        return new va_0(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof va_0) {
            bl2 = ((va_0)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public static long Be() {
        return XugglerJNI.Global_getVersion();
    }

    public static int Bf() {
        return XugglerJNI.Global_getVersionMajor();
    }

    public static int Bg() {
        return XugglerJNI.Global_getVersionMinor();
    }

    public static int Bh() {
        return XugglerJNI.Global_getVersionRevision();
    }

    public static String Bi() {
        return XugglerJNI.Global_getVersionStr();
    }

    public static int Bj() {
        return XugglerJNI.Global_getAVFormatVersion();
    }

    public static String Bk() {
        return XugglerJNI.Global_getAVFormatVersionStr();
    }

    public static int Bl() {
        return XugglerJNI.Global_getAVCodecVersion();
    }

    public static String Bm() {
        return XugglerJNI.Global_getAVCodecVersionStr();
    }

    public static void init() {
        XugglerJNI.Global_init();
    }

    public static void dQ(int n2) {
        XugglerJNI.Global_setFFmpegLoggingLevel(n2);
    }
}

