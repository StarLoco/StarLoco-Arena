/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

public class yX
extends atv {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected yX(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIAudioSamplesUpcast(l2), bl2);
        this.hf = l2;
    }

    protected yX(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIAudioSamplesUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long b(yX yX2) {
        if (yX2 == null) {
            return 0L;
        }
        return yX2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public yX FM() {
        if (this.hf == 0L) {
            return null;
        }
        return new yX(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof yX) {
            bl2 = ((yX)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("sample rate:" + this.zv() + ";");
        stringBuilder.append("channels:" + this.getChannels() + ";");
        stringBuilder.append("format:" + (Object)((Object)this.FO()) + ";");
        stringBuilder.append("time stamp:" + this.getTimeStamp() + ";");
        stringBuilder.append("complete:" + this.isComplete() + ";");
        stringBuilder.append("num samples:" + this.FP() + ";");
        stringBuilder.append("size:" + this.getSize() + ";");
        stringBuilder.append("key:" + this.aGD() + ";");
        xv_1 xv_12 = xv_1.bc(1, (int)va_0.arK);
        stringBuilder.append("time base:" + xv_12 + ";");
        if (xv_12 != null) {
            xv_12.delete();
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public boolean isComplete() {
        return XugglerJNI.IAudioSamples_isComplete(this.hf, this);
    }

    public int zv() {
        return XugglerJNI.IAudioSamples_getSampleRate(this.hf, this);
    }

    public int getChannels() {
        return XugglerJNI.IAudioSamples_getChannels(this.hf, this);
    }

    public long FN() {
        return XugglerJNI.IAudioSamples_getSampleBitDepth(this.hf, this);
    }

    public aby FO() {
        return aby.jz(XugglerJNI.IAudioSamples_getFormat(this.hf, this));
    }

    public long FP() {
        return XugglerJNI.IAudioSamples_getNumSamples(this.hf, this);
    }

    public long FQ() {
        return XugglerJNI.IAudioSamples_getMaxBufferSize(this.hf, this);
    }

    public long FR() {
        return XugglerJNI.IAudioSamples_getMaxSamples(this.hf, this);
    }

    public long FS() {
        return XugglerJNI.IAudioSamples_getSampleSize(this.hf, this);
    }

    public long FT() {
        return XugglerJNI.IAudioSamples_getPts(this.hf, this);
    }

    public void aX(long l2) {
        XugglerJNI.IAudioSamples_setPts(this.hf, this, l2);
    }

    public long FU() {
        return XugglerJNI.IAudioSamples_getNextPts(this.hf, this);
    }

    public void a(boolean bl2, long l2, int n2, int n3, aby aby2, long l3) {
        XugglerJNI.IAudioSamples_setComplete(this.hf, this, bl2, l2, n2, n3, aby2.dZ(), l3);
    }

    public int a(long l2, int n2, aby aby2, int n3) {
        return XugglerJNI.IAudioSamples_setSample(this.hf, this, l2, n2, aby2.dZ(), n3);
    }

    public int a(long l2, int n2, aby aby2) {
        return XugglerJNI.IAudioSamples_getSample(this.hf, this, l2, n2, aby2.dZ());
    }

    public static long a(aby aby2) {
        return XugglerJNI.IAudioSamples_findSampleBitDepth(aby2.dZ());
    }

    public static yX g(long l2, long l3) {
        long l4 = XugglerJNI.IAudioSamples_make__SWIG_0(l2, l3);
        return l4 == 0L ? null : new yX(l4, false);
    }

    public static long e(long l2, int n2) {
        return XugglerJNI.IAudioSamples_samplesToDefaultPts(l2, n2);
    }

    public static long f(long l2, int n2) {
        return XugglerJNI.IAudioSamples_defaultPtsToSamples(l2, n2);
    }

    public static yX a(di di2, int n2, aby aby2) {
        long l2 = XugglerJNI.IAudioSamples_make__SWIG_1(di.a(di2), di2, n2, aby2.dZ());
        return l2 == 0L ? null : new yX(l2, false);
    }

    public static yX a(long l2, long l3, aby aby2) {
        long l4 = XugglerJNI.IAudioSamples_make__SWIG_2(l2, l3, aby2.dZ());
        return l4 == 0L ? null : new yX(l4, false);
    }
}

