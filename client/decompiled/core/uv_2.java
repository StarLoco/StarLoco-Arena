/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from uV
 */
public class uv_2
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected uv_2(long l2, boolean bl2) {
        super(XugglerJNI.SWIGITimeValueUpcast(l2), bl2);
        this.hf = l2;
    }

    protected uv_2(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGITimeValueUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(uv_2 uv_22) {
        if (uv_22 == null) {
            return 0L;
        }
        return uv_22.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public uv_2 AY() {
        if (this.hf == 0L) {
            return null;
        }
        return new uv_2(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof uv_2) {
            bl2 = ((uv_2)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public static uv_2 a(long l2, acu_0 acu_02) {
        long l3 = XugglerJNI.ITimeValue_make__SWIG_0(l2, acu_02.dZ());
        return l3 == 0L ? null : new uv_2(l3, false);
    }

    public static uv_2 b(uv_2 uv_22) {
        long l2 = XugglerJNI.ITimeValue_make__SWIG_1(uv_2.a(uv_22), uv_22);
        return l2 == 0L ? null : new uv_2(l2, false);
    }

    public long a(acu_0 acu_02) {
        return XugglerJNI.ITimeValue_get(this.hf, this, acu_02.dZ());
    }

    public int c(uv_2 uv_22) {
        return XugglerJNI.ITimeValue_compareTo(this.hf, this, uv_2.a(uv_22), uv_22);
    }

    public static int a(uv_2 uv_22, uv_2 uv_23) {
        return XugglerJNI.ITimeValue_compare__SWIG_0(uv_2.a(uv_22), uv_22, uv_2.a(uv_23), uv_23);
    }

    public static int e(long l2, long l3) {
        return XugglerJNI.ITimeValue_compare__SWIG_1(l2, l3);
    }
}

