/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class Xz
extends alp
implements agk_1 {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected Xz(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIVideoResamplerUpcast(l2), bl2);
        this.hf = l2;
    }

    protected Xz(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIVideoResamplerUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(Xz xz) {
        if (xz == null) {
            return 0L;
        }
        return xz.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public Xz akZ() {
        if (this.hf == 0L) {
            return null;
        }
        return new Xz(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof Xz) {
            bl2 = ((Xz)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public Collection ala() {
        LinkedList<String> linkedList = new LinkedList<String>();
        int n2 = this.alf();
        for (int j = 0; j < n2; ++j) {
            aoc_2 aoc_22 = this.iX(j);
            String string = aoc_22.getName();
            linkedList.add(string);
        }
        return linkedList;
    }

    public int alb() {
        return XugglerJNI.IVideoResampler_getInputWidth(this.hf, this);
    }

    public int alc() {
        return XugglerJNI.IVideoResampler_getInputHeight(this.hf, this);
    }

    public yi_0 ald() {
        return yi_0.ev(XugglerJNI.IVideoResampler_getInputPixelFormat(this.hf, this));
    }

    public int wy() {
        return XugglerJNI.IVideoResampler_getOutputWidth(this.hf, this);
    }

    public int wz() {
        return XugglerJNI.IVideoResampler_getOutputHeight(this.hf, this);
    }

    public yi_0 ale() {
        return yi_0.ev(XugglerJNI.IVideoResampler_getOutputPixelFormat(this.hf, this));
    }

    public int a(ayh ayh2, ayh ayh3) {
        return XugglerJNI.IVideoResampler_resample(this.hf, this, ayh.a(ayh2), ayh2, ayh.a(ayh3), ayh3);
    }

    public int alf() {
        return XugglerJNI.IVideoResampler_getNumProperties(this.hf, this);
    }

    public aoc_2 iX(int n2) {
        long l2 = XugglerJNI.IVideoResampler_getPropertyMetaData__SWIG_0(this.hf, this, n2);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public aoc_2 gI(String string) {
        long l2 = XugglerJNI.IVideoResampler_getPropertyMetaData__SWIG_1(this.hf, this, string);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public int K(String string, String string2) {
        return XugglerJNI.IVideoResampler_setProperty__SWIG_0(this.hf, this, string, string2);
    }

    public int a(String string, double d) {
        return XugglerJNI.IVideoResampler_setProperty__SWIG_1(this.hf, this, string, d);
    }

    public int e(String string, long l2) {
        return XugglerJNI.IVideoResampler_setProperty__SWIG_2(this.hf, this, string, l2);
    }

    public int r(String string, boolean bl2) {
        return XugglerJNI.IVideoResampler_setProperty__SWIG_3(this.hf, this, string, bl2);
    }

    public int a(String string, xv_1 xv_12) {
        return XugglerJNI.IVideoResampler_setProperty__SWIG_4(this.hf, this, string, xv_1.b(xv_12), xv_12);
    }

    public String gJ(String string) {
        return XugglerJNI.IVideoResampler_getPropertyAsString(this.hf, this, string);
    }

    public double gK(String string) {
        return XugglerJNI.IVideoResampler_getPropertyAsDouble(this.hf, this, string);
    }

    public long gL(String string) {
        return XugglerJNI.IVideoResampler_getPropertyAsLong(this.hf, this, string);
    }

    public xv_1 gM(String string) {
        long l2 = XugglerJNI.IVideoResampler_getPropertyAsRational(this.hf, this, string);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public boolean gN(String string) {
        return XugglerJNI.IVideoResampler_getPropertyAsBoolean(this.hf, this, string);
    }

    public static Xz a(int n2, int n3, yi_0 yi_02, int n4, int n5, yi_0 yi_03) {
        long l2 = XugglerJNI.IVideoResampler_make(n2, n3, yi_02.dZ(), n4, n5, yi_03.dZ());
        return l2 == 0L ? null : new Xz(l2, false);
    }

    public static boolean a(hb_2 hb_22) {
        return XugglerJNI.IVideoResampler_isSupported(hb_22.dZ());
    }
}

