/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from aOC
 */
public class aoc_2
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected aoc_2(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIPropertyUpcast(l2), bl2);
        this.hf = l2;
    }

    protected aoc_2(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIPropertyUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(aoc_2 aoc_22) {
        if (aoc_22 == null) {
            return 0L;
        }
        return aoc_22.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public aoc_2 aYw() {
        if (this.hf == 0L) {
            return null;
        }
        return new aoc_2(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof aoc_2) {
            bl2 = ((aoc_2)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String getName() {
        return XugglerJNI.IProperty_getName(this.hf, this);
    }

    public String aYx() {
        return XugglerJNI.IProperty_getHelp(this.hf, this);
    }

    public String aYy() {
        return XugglerJNI.IProperty_getUnit(this.hf, this);
    }

    public eK aYz() {
        return eK.ar(XugglerJNI.IProperty_getType(this.hf, this));
    }

    public int getFlags() {
        return XugglerJNI.IProperty_getFlags(this.hf, this);
    }

    public long aYA() {
        return XugglerJNI.IProperty_getDefault(this.hf, this);
    }

    public double aYB() {
        return XugglerJNI.IProperty_getDefaultAsDouble(this.hf, this);
    }

    public int aYC() {
        return XugglerJNI.IProperty_getNumFlagSettings(this.hf, this);
    }

    public aoc_2 pP(int n2) {
        long l2 = XugglerJNI.IProperty_getFlagConstant__SWIG_0(this.hf, this, n2);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public aoc_2 lP(String string) {
        long l2 = XugglerJNI.IProperty_getFlagConstant__SWIG_1(this.hf, this, string);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }
}

