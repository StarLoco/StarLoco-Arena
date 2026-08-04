/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

public class rL
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected rL(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIAudioResamplerUpcast(l2), bl2);
        this.hf = l2;
    }

    protected rL(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIAudioResamplerUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(rL rL2) {
        if (rL2 == null) {
            return 0L;
        }
        return rL2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public rL xz() {
        if (this.hf == 0L) {
            return null;
        }
        return new rL(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof rL) {
            bl2 = ((rL)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public int xA() {
        return XugglerJNI.IAudioResampler_getOutputChannels(this.hf, this);
    }

    public int xB() {
        return XugglerJNI.IAudioResampler_getOutputRate(this.hf, this);
    }

    public int xC() {
        return XugglerJNI.IAudioResampler_getInputChannels(this.hf, this);
    }

    public int xD() {
        return XugglerJNI.IAudioResampler_getInputRate(this.hf, this);
    }

    public int a(yX yX2, yX yX3, long l2) {
        return XugglerJNI.IAudioResampler_resample(this.hf, this, yX.b(yX2), yX2, yX.b(yX3), yX3, l2);
    }

    public static rL o(int n2, int n3, int n4, int n5) {
        long l2 = XugglerJNI.IAudioResampler_make__SWIG_0(n2, n3, n4, n5);
        return l2 == 0L ? null : new rL(l2, false);
    }

    public aby xE() {
        return aby.jz(XugglerJNI.IAudioResampler_getOutputFormat(this.hf, this));
    }

    public aby xF() {
        return aby.jz(XugglerJNI.IAudioResampler_getInputFormat(this.hf, this));
    }

    public int xG() {
        return XugglerJNI.IAudioResampler_getFilterLen(this.hf, this);
    }

    public int xH() {
        return XugglerJNI.IAudioResampler_getLog2PhaseCount(this.hf, this);
    }

    public boolean xI() {
        return XugglerJNI.IAudioResampler_isLinear(this.hf, this);
    }

    public double xJ() {
        return XugglerJNI.IAudioResampler_getCutoffFrequency(this.hf, this);
    }

    public static rL a(int n2, int n3, int n4, int n5, aby aby2, aby aby3) {
        long l2 = XugglerJNI.IAudioResampler_make__SWIG_1(n2, n3, n4, n5, aby2.dZ(), aby3.dZ());
        return l2 == 0L ? null : new rL(l2, false);
    }

    public static rL a(int n2, int n3, int n4, int n5, aby aby2, aby aby3, int n6, int n7, boolean bl2, double d) {
        long l2 = XugglerJNI.IAudioResampler_make__SWIG_2(n2, n3, n4, n5, aby2.dZ(), aby3.dZ(), n6, n7, bl2, d);
        return l2 == 0L ? null : new rL(l2, false);
    }

    public int a(yX yX2) {
        return XugglerJNI.IAudioResampler_getMinimumNumSamplesRequiredInOutputSamples__SWIG_0(this.hf, this, yX.b(yX2), yX2);
    }

    public int du(int n2) {
        return XugglerJNI.IAudioResampler_getMinimumNumSamplesRequiredInOutputSamples__SWIG_1(this.hf, this, n2);
    }
}

