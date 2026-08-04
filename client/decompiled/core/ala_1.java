/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from alA
 */
public class ala_1
extends atv {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected ala_1(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIPacketUpcast(l2), bl2);
        this.hf = l2;
    }

    protected ala_1(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIPacketUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long d(ala_1 ala_12) {
        if (ala_12 == null) {
            return 0L;
        }
        return ala_12.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public ala_1 aAR() {
        if (this.hf == 0L) {
            return null;
        }
        return new ala_1(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof ala_1) {
            bl2 = ((ala_1)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("complete:" + this.isComplete() + ";");
        stringBuilder.append("dts:" + this.aAS() + ";");
        stringBuilder.append("pts:" + this.FT() + ";");
        stringBuilder.append("size:" + this.getSize() + ";");
        stringBuilder.append("key:" + this.aGD() + ";");
        stringBuilder.append("flags:" + this.getFlags() + ";");
        stringBuilder.append("stream index:" + this.aAT() + ";");
        stringBuilder.append("duration:" + this.getDuration() + ";");
        stringBuilder.append("position:" + this.getPosition() + ";");
        xv_1 xv_12 = this.HN();
        stringBuilder.append("time base:" + xv_12 + ";");
        if (xv_12 != null) {
            xv_12.delete();
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void reset() {
        XugglerJNI.IPacket_reset(this.hf, this);
    }

    public boolean isComplete() {
        return XugglerJNI.IPacket_isComplete(this.hf, this);
    }

    public long FT() {
        return XugglerJNI.IPacket_getPts(this.hf, this);
    }

    public void aX(long l2) {
        XugglerJNI.IPacket_setPts(this.hf, this, l2);
    }

    public long aAS() {
        return XugglerJNI.IPacket_getDts(this.hf, this);
    }

    public void dM(long l2) {
        XugglerJNI.IPacket_setDts(this.hf, this, l2);
    }

    public int getSize() {
        return XugglerJNI.IPacket_getSize(this.hf, this);
    }

    public int getMaxSize() {
        return XugglerJNI.IPacket_getMaxSize(this.hf, this);
    }

    public int aAT() {
        return XugglerJNI.IPacket_getStreamIndex(this.hf, this);
    }

    public int getFlags() {
        return XugglerJNI.IPacket_getFlags(this.hf, this);
    }

    public boolean aAU() {
        return XugglerJNI.IPacket_isKeyPacket(this.hf, this);
    }

    public long getDuration() {
        return XugglerJNI.IPacket_getDuration(this.hf, this);
    }

    public long getPosition() {
        return XugglerJNI.IPacket_getPosition(this.hf, this);
    }

    public int lo(int n2) {
        return XugglerJNI.IPacket_allocateNewPayload(this.hf, this, n2);
    }

    public static ala_1 aAV() {
        long l2 = XugglerJNI.IPacket_make__SWIG_0();
        return l2 == 0L ? null : new ala_1(l2, false);
    }

    public static ala_1 c(di di2) {
        long l2 = XugglerJNI.IPacket_make__SWIG_1(di.a(di2), di2);
        return l2 == 0L ? null : new ala_1(l2, false);
    }

    public void dH(boolean bl2) {
        XugglerJNI.IPacket_setKeyPacket(this.hf, this, bl2);
    }

    public void jo(int n2) {
        XugglerJNI.IPacket_setFlags(this.hf, this, n2);
    }

    public void c(boolean bl2, int n2) {
        XugglerJNI.IPacket_setComplete(this.hf, this, bl2, n2);
    }

    public void lp(int n2) {
        XugglerJNI.IPacket_setStreamIndex(this.hf, this, n2);
    }

    public void dN(long l2) {
        XugglerJNI.IPacket_setDuration(this.hf, this, l2);
    }

    public void bZ(long l2) {
        XugglerJNI.IPacket_setPosition(this.hf, this, l2);
    }

    public long aAW() {
        return XugglerJNI.IPacket_getConvergenceDuration(this.hf, this);
    }

    public void dO(long l2) {
        XugglerJNI.IPacket_setConvergenceDuration(this.hf, this, l2);
    }

    public static ala_1 b(ala_1 ala_12, boolean bl2) {
        long l2 = XugglerJNI.IPacket_make__SWIG_2(ala_1.d(ala_12), ala_12, bl2);
        return l2 == 0L ? null : new ala_1(l2, false);
    }

    public static ala_1 lq(int n2) {
        long l2 = XugglerJNI.IPacket_make__SWIG_3(n2);
        return l2 == 0L ? null : new ala_1(l2, false);
    }
}

