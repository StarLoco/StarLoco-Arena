/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

public class asj
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected asj(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIErrorUpcast(l2), bl2);
        this.hf = l2;
    }

    protected asj(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIErrorUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(asj asj2) {
        if (asj2 == null) {
            return 0L;
        }
        return asj2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public asj aFf() {
        if (this.hf == 0L) {
            return null;
        }
        return new asj(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof asj) {
            bl2 = ((asj)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        return this.getDescription();
    }

    public xm_2 aFg() {
        return xm_2.iR(XugglerJNI.IError_getType(this.hf, this));
    }

    public String getDescription() {
        return XugglerJNI.IError_getDescription(this.hf, this);
    }

    public int aFh() {
        return XugglerJNI.IError_getErrorNumber(this.hf, this);
    }

    public static asj lY(int n2) {
        long l2 = XugglerJNI.IError_make__SWIG_0(n2);
        return l2 == 0L ? null : new asj(l2, false);
    }

    public static asj a(xm_2 xm_22) {
        long l2 = XugglerJNI.IError_make__SWIG_1(xm_22.dZ());
        return l2 == 0L ? null : new asj(l2, false);
    }

    public static xm_2 lZ(int n2) {
        return xm_2.iR(XugglerJNI.IError_errorNumberToType(n2));
    }

    public static int b(xm_2 xm_22) {
        return XugglerJNI.IError_typeToErrorNumber(xm_22.dZ());
    }
}

