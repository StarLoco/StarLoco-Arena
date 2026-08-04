/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from aOw
 */
public class aow_1
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected aow_1(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIContainerParametersUpcast(l2), bl2);
        this.hf = l2;
    }

    protected aow_1(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIContainerParametersUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long b(aow_1 aow_12) {
        if (aow_12 == null) {
            return 0L;
        }
        return aow_12.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public aow_1 aYi() {
        if (this.hf == 0L) {
            return null;
        }
        return new aow_1(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof aow_1) {
            bl2 = ((aow_1)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public xv_1 HN() {
        long l2 = XugglerJNI.IContainerParameters_getTimeBase(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public void k(xv_1 xv_12) {
        XugglerJNI.IContainerParameters_setTimeBase(this.hf, this, xv_1.b(xv_12), xv_12);
    }

    public int aYj() {
        return XugglerJNI.IContainerParameters_getAudioSampleRate(this.hf, this);
    }

    public void pK(int n2) {
        XugglerJNI.IContainerParameters_setAudioSampleRate(this.hf, this, n2);
    }

    public int aYk() {
        return XugglerJNI.IContainerParameters_getAudioChannels(this.hf, this);
    }

    public void pL(int n2) {
        XugglerJNI.IContainerParameters_setAudioChannels(this.hf, this, n2);
    }

    public int aYl() {
        return XugglerJNI.IContainerParameters_getVideoWidth(this.hf, this);
    }

    public void pM(int n2) {
        XugglerJNI.IContainerParameters_setVideoWidth(this.hf, this, n2);
    }

    public int aYm() {
        return XugglerJNI.IContainerParameters_getVideoHeight(this.hf, this);
    }

    public void pN(int n2) {
        XugglerJNI.IContainerParameters_setVideoHeight(this.hf, this, n2);
    }

    public yi_0 aYn() {
        return yi_0.ev(XugglerJNI.IContainerParameters_getPixelFormat(this.hf, this));
    }

    public void b(yi_0 yi_02) {
        XugglerJNI.IContainerParameters_setPixelFormat(this.hf, this, yi_02.dZ());
    }

    public int aYo() {
        return XugglerJNI.IContainerParameters_getTVChannel(this.hf, this);
    }

    public void pO(int n2) {
        XugglerJNI.IContainerParameters_setTVChannel(this.hf, this, n2);
    }

    public String aYp() {
        return XugglerJNI.IContainerParameters_getTVStandard(this.hf, this);
    }

    public void lO(String string) {
        XugglerJNI.IContainerParameters_setTVStandard(this.hf, this, string);
    }

    public boolean aYq() {
        return XugglerJNI.IContainerParameters_isMPEG2TSRaw(this.hf, this);
    }

    public void fr(boolean bl2) {
        XugglerJNI.IContainerParameters_setMPEG2TSRaw(this.hf, this, bl2);
    }

    public boolean aYr() {
        return XugglerJNI.IContainerParameters_isMPEG2TSComputePCR(this.hf, this);
    }

    public void fs(boolean bl2) {
        XugglerJNI.IContainerParameters_setMPEG2TSComputePCR(this.hf, this, bl2);
    }

    public boolean aYs() {
        return XugglerJNI.IContainerParameters_isInitialPause(this.hf, this);
    }

    public void ft(boolean bl2) {
        XugglerJNI.IContainerParameters_setInitialPause(this.hf, this, bl2);
    }

    public static aow_1 aYt() {
        long l2 = XugglerJNI.IContainerParameters_make();
        return l2 == 0L ? null : new aow_1(l2, false);
    }
}

