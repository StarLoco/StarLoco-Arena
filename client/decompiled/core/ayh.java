/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

public class ayh
extends atv {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected ayh(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIVideoPictureUpcast(l2), bl2);
        this.hf = l2;
    }

    protected ayh(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIVideoPictureUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(ayh ayh2) {
        if (ayh2 == null) {
            return 0L;
        }
        return ayh2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public ayh aKU() {
        if (this.hf == 0L) {
            return null;
        }
        return new ayh(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof ayh) {
            bl2 = ((ayh)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("pixel type:" + (Object)((Object)this.anJ()) + ";");
        stringBuilder.append("width:" + this.getWidth() + ";");
        stringBuilder.append("height:" + this.getHeight() + ";");
        stringBuilder.append("time stamp:" + this.getTimeStamp() + ";");
        stringBuilder.append("complete:" + this.isComplete() + ";");
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

    public boolean Gr() {
        return XugglerJNI.IVideoPicture_isKeyFrame(this.hf, this);
    }

    public void ey(boolean bl2) {
        XugglerJNI.IVideoPicture_setKeyFrame(this.hf, this, bl2);
    }

    public boolean isComplete() {
        return XugglerJNI.IVideoPicture_isComplete(this.hf, this);
    }

    public int getSize() {
        return XugglerJNI.IVideoPicture_getSize(this.hf, this);
    }

    public int getWidth() {
        return XugglerJNI.IVideoPicture_getWidth(this.hf, this);
    }

    public int getHeight() {
        return XugglerJNI.IVideoPicture_getHeight(this.hf, this);
    }

    public yi_0 anJ() {
        return yi_0.ev(XugglerJNI.IVideoPicture_getPixelType(this.hf, this));
    }

    public long FT() {
        return XugglerJNI.IVideoPicture_getPts(this.hf, this);
    }

    public void aX(long l2) {
        XugglerJNI.IVideoPicture_setPts(this.hf, this, l2);
    }

    public int aKV() {
        return XugglerJNI.IVideoPicture_getQuality(this.hf, this);
    }

    public void mM(int n2) {
        XugglerJNI.IVideoPicture_setQuality(this.hf, this, n2);
    }

    public int mN(int n2) {
        return XugglerJNI.IVideoPicture_getDataLineSize(this.hf, this, n2);
    }

    public void a(boolean bl2, yi_0 yi_02, int n2, int n3, long l2) {
        XugglerJNI.IVideoPicture_setComplete(this.hf, this, bl2, yi_02.dZ(), n2, n3, l2);
    }

    public boolean b(ayh ayh2) {
        return XugglerJNI.IVideoPicture_copy(this.hf, this, ayh.a(ayh2), ayh2);
    }

    public static ayh a(yi_0 yi_02, int n2, int n3) {
        long l2 = XugglerJNI.IVideoPicture_make__SWIG_0(yi_02.dZ(), n2, n3);
        return l2 == 0L ? null : new ayh(l2, false);
    }

    public static ayh c(ayh ayh2) {
        long l2 = XugglerJNI.IVideoPicture_make__SWIG_1(ayh.a(ayh2), ayh2);
        return l2 == 0L ? null : new ayh(l2, false);
    }

    public abu_0 aKW() {
        return abu_0.jA(XugglerJNI.IVideoPicture_getPictureType(this.hf, this));
    }

    public void a(abu_0 abu_02) {
        XugglerJNI.IVideoPicture_setPictureType(this.hf, this, abu_02.dZ());
    }

    public static ayh a(di di2, yi_0 yi_02, int n2, int n3) {
        long l2 = XugglerJNI.IVideoPicture_make__SWIG_2(di.a(di2), di2, yi_02.dZ(), n2, n3);
        return l2 == 0L ? null : new ayh(l2, false);
    }
}

