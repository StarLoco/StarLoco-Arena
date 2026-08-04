/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from Xv
 */
public class xv_1
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected xv_1(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIRationalUpcast(l2), bl2);
        this.hf = l2;
    }

    protected xv_1(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIRationalUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long b(xv_1 xv_12) {
        if (xv_12 == null) {
            return 0L;
        }
        return xv_12.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public xv_1 akT() {
        if (this.hf == 0L) {
            return null;
        }
        return new xv_1(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof xv_1) {
            bl2 = ((xv_1)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        return "" + this.akU() + "/" + this.akV();
    }

    public boolean uI() {
        if (this.akV() == 0) {
            return false;
        }
        return this.getDouble() > 0.0;
    }

    public static boolean c(xv_1 xv_12) {
        if (xv_12 == null) {
            return false;
        }
        return xv_12.uI();
    }

    public boolean isNegative() {
        if (this.akV() == 0) {
            return false;
        }
        return this.getDouble() < 0.0;
    }

    public static boolean d(xv_1 xv_12) {
        if (xv_12 == null) {
            return false;
        }
        return xv_12.isNegative();
    }

    public int akU() {
        return XugglerJNI.IRational_getNumerator(this.hf, this);
    }

    public int akV() {
        return XugglerJNI.IRational_getDenominator(this.hf, this);
    }

    public xv_1 akW() {
        long l2 = XugglerJNI.IRational_copy(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public int e(xv_1 xv_12) {
        return XugglerJNI.IRational_compareTo(this.hf, this, xv_1.b(xv_12), xv_12);
    }

    public static int a(xv_1 xv_12, xv_1 xv_13) {
        return XugglerJNI.IRational_sCompareTo(xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13);
    }

    public double getDouble() {
        return XugglerJNI.IRational_getDouble(this.hf, this);
    }

    public int e(long l2, long l3, long l4) {
        return XugglerJNI.IRational_reduce(this.hf, this, l2, l3, l4);
    }

    public static int a(xv_1 xv_12, long l2, long l3, long l4) {
        return XugglerJNI.IRational_sReduce(xv_1.b(xv_12), xv_12, l2, l3, l4);
    }

    public xv_1 f(xv_1 xv_12) {
        long l2 = XugglerJNI.IRational_multiply(this.hf, this, xv_1.b(xv_12), xv_12);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 b(xv_1 xv_12, xv_1 xv_13) {
        long l2 = XugglerJNI.IRational_sMultiply(xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public xv_1 g(xv_1 xv_12) {
        long l2 = XugglerJNI.IRational_divide(this.hf, this, xv_1.b(xv_12), xv_12);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 c(xv_1 xv_12, xv_1 xv_13) {
        long l2 = XugglerJNI.IRational_sDivide(xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public xv_1 h(xv_1 xv_12) {
        long l2 = XugglerJNI.IRational_subtract(this.hf, this, xv_1.b(xv_12), xv_12);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 d(xv_1 xv_12, xv_1 xv_13) {
        long l2 = XugglerJNI.IRational_sSubtract(xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public xv_1 i(xv_1 xv_12) {
        long l2 = XugglerJNI.IRational_add(this.hf, this, xv_1.b(xv_12), xv_12);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 e(xv_1 xv_12, xv_1 xv_13) {
        long l2 = XugglerJNI.IRational_sAdd(xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public long a(long l2, xv_1 xv_12) {
        return XugglerJNI.IRational_rescale__SWIG_0(this.hf, this, l2, xv_1.b(xv_12), xv_12);
    }

    public static long a(long l2, xv_1 xv_12, xv_1 xv_13) {
        return XugglerJNI.IRational_sRescale__SWIG_0(l2, xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13);
    }

    public static xv_1 akX() {
        long l2 = XugglerJNI.IRational_make__SWIG_0();
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 x(double d) {
        long l2 = XugglerJNI.IRational_make__SWIG_1(d);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 j(xv_1 xv_12) {
        long l2 = XugglerJNI.IRational_make__SWIG_2(xv_1.b(xv_12), xv_12);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public static xv_1 bc(int n2, int n3) {
        long l2 = XugglerJNI.IRational_make__SWIG_3(n2, n3);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public long a(long l2, xv_1 xv_12, ant_1 ant_12) {
        return XugglerJNI.IRational_rescale__SWIG_1(this.hf, this, l2, xv_1.b(xv_12), xv_12, ant_12.dZ());
    }

    public static long a(long l2, xv_1 xv_12, xv_1 xv_13, ant_1 ant_12) {
        return XugglerJNI.IRational_sRescale__SWIG_1(l2, xv_1.b(xv_12), xv_12, xv_1.b(xv_13), xv_13, ant_12.dZ());
    }

    public static long a(long l2, int n2, int n3, int n4, int n5, ant_1 ant_12) {
        return XugglerJNI.IRational_rescale__SWIG_2(l2, n2, n3, n4, n5, ant_12.dZ());
    }

    public void iV(int n2) {
        XugglerJNI.IRational_setNumerator(this.hf, this, n2);
    }

    public void iW(int n2) {
        XugglerJNI.IRational_setDenominator(this.hf, this, n2);
    }

    public void y(double d) {
        XugglerJNI.IRational_setValue(this.hf, this, d);
    }

    public double getValue() {
        return XugglerJNI.IRational_getValue(this.hf, this);
    }

    public boolean akY() {
        return XugglerJNI.IRational_isFinalized(this.hf, this);
    }

    public void init() {
        XugglerJNI.IRational_init(this.hf, this);
    }
}

