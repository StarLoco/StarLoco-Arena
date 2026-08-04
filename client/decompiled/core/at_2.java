/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from AT
 */
public class at_2
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected at_2(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIStreamUpcast(l2), bl2);
        this.hf = l2;
    }

    protected at_2(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIStreamUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(at_2 at_22) {
        if (at_22 == null) {
            return 0L;
        }
        return at_22.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public at_2 HI() {
        if (this.hf == 0L) {
            return null;
        }
        return new at_2(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof at_2) {
            bl2 = ((at_2)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("index:" + this.getIndex() + ";");
        stringBuilder.append("id:" + this.getId() + ";");
        stringBuilder.append("streamcoder:" + this.HL() + ";");
        stringBuilder.append("framerate:" + this.HM() + ";");
        stringBuilder.append("timebase:" + this.HN() + ";");
        stringBuilder.append("direction:" + (Object)((Object)this.HK()) + ";");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public List HJ() {
        int n2 = this.HP();
        ArrayList<zt_0> arrayList = new ArrayList<zt_0>(Math.max(n2, 10));
        for (int j = 0; j < n2; ++j) {
            zt_0 zt_02 = this.eF(j);
            if (zt_02 == null) continue;
            arrayList.add(zt_02);
        }
        return arrayList;
    }

    public mi_1 HK() {
        return mi_1.ce(XugglerJNI.IStream_getDirection(this.hf, this));
    }

    public int getIndex() {
        return XugglerJNI.IStream_getIndex(this.hf, this);
    }

    public int getId() {
        return XugglerJNI.IStream_getId(this.hf, this);
    }

    public zw_2 HL() {
        long l2 = XugglerJNI.IStream_getStreamCoder(this.hf, this);
        return l2 == 0L ? null : new zw_2(l2, false);
    }

    public xv_1 HM() {
        long l2 = XugglerJNI.IStream_getFrameRate(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public xv_1 HN() {
        long l2 = XugglerJNI.IStream_getTimeBase(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public long getStartTime() {
        return XugglerJNI.IStream_getStartTime(this.hf, this);
    }

    public long getDuration() {
        return XugglerJNI.IStream_getDuration(this.hf, this);
    }

    public long HO() {
        return XugglerJNI.IStream_getCurrentDts(this.hf, this);
    }

    public int HP() {
        return XugglerJNI.IStream_getNumIndexEntries(this.hf, this);
    }

    public long HQ() {
        return XugglerJNI.IStream_getNumFrames(this.hf, this);
    }

    public xv_1 HR() {
        long l2 = XugglerJNI.IStream_getSampleAspectRatio(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public void a(xv_1 xv_12) {
        XugglerJNI.IStream_setSampleAspectRatio(this.hf, this, xv_1.b(xv_12), xv_12);
    }

    public String getLanguage() {
        return XugglerJNI.IStream_getLanguage(this.hf, this);
    }

    public void dr(String string) {
        XugglerJNI.IStream_setLanguage(this.hf, this, string);
    }

    public aip_2 HS() {
        long l2 = XugglerJNI.IStream_getContainer(this.hf, this);
        return l2 == 0L ? null : new aip_2(l2, false);
    }

    public int a(zw_2 zw_22) {
        return XugglerJNI.IStream_setStreamCoder__SWIG_0(this.hf, this, zw_2.b(zw_22), zw_22);
    }

    public axJ HT() {
        return axJ.mI(XugglerJNI.IStream_getParseType(this.hf, this));
    }

    public void a(axJ axJ2) {
        XugglerJNI.IStream_setParseType(this.hf, this, axJ2.dZ());
    }

    public aaw_1 HU() {
        long l2 = XugglerJNI.IStream_getMetaData(this.hf, this);
        return l2 == 0L ? null : new aaw_1(l2, false);
    }

    public void a(aaw_1 aaw_12) {
        XugglerJNI.IStream_setMetaData(this.hf, this, aaw_1.b(aaw_12), aaw_12);
    }

    public int a(ala_1 ala_12) {
        return XugglerJNI.IStream_stampOutputPacket(this.hf, this, ala_1.d(ala_12), ala_12);
    }

    public int a(zw_2 zw_22, boolean bl2) {
        return XugglerJNI.IStream_setStreamCoder__SWIG_1(this.hf, this, zw_2.b(zw_22), zw_22, bl2);
    }

    public zt_0 g(long l2, int n2) {
        long l3 = XugglerJNI.IStream_findTimeStampEntryInIndex(this.hf, this, l2, n2);
        return l3 == 0L ? null : new zt_0(l3, false);
    }

    public int h(long l2, int n2) {
        return XugglerJNI.IStream_findTimeStampPositionInIndex(this.hf, this, l2, n2);
    }

    public zt_0 eF(int n2) {
        long l2 = XugglerJNI.IStream_getIndexEntry(this.hf, this, n2);
        return l2 == 0L ? null : new zt_0(l2, false);
    }

    public int b(zt_0 zt_02) {
        return XugglerJNI.IStream_addIndexEntry(this.hf, this, zt_0.a(zt_02), zt_02);
    }
}

