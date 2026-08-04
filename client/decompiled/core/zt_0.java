/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from zt
 */
public class zt_0
extends alp {
    private volatile long hf;
    public static final int aFr = XugglerJNI.IIndexEntry_IINDEX_FLAG_KEYFRAME_get();

    private void noop() {
        di.a(null, 1);
    }

    protected zt_0(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIIndexEntryUpcast(l2), bl2);
        this.hf = l2;
    }

    protected zt_0(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIIndexEntryUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(zt_0 zt_02) {
        if (zt_02 == null) {
            return 0L;
        }
        return zt_02.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public zt_0 Gp() {
        if (this.hf == 0L) {
            return null;
        }
        return new zt_0(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof zt_0) {
            bl2 = ((zt_0)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("position:" + this.getPosition() + ";");
        stringBuilder.append("timestamp:" + this.getTimeStamp() + ";");
        stringBuilder.append("flags:" + this.getFlags() + ";");
        stringBuilder.append("size:" + this.getSize() + ";");
        stringBuilder.append("min-distance:" + this.Gq() + ";");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static zt_0 a(long l2, long l3, int n2, int n3, int n4) {
        long l4 = XugglerJNI.IIndexEntry_make(l2, l3, n2, n3, n4);
        return l4 == 0L ? null : new zt_0(l4, false);
    }

    public long getPosition() {
        return XugglerJNI.IIndexEntry_getPosition(this.hf, this);
    }

    public long getTimeStamp() {
        return XugglerJNI.IIndexEntry_getTimeStamp(this.hf, this);
    }

    public int getFlags() {
        return XugglerJNI.IIndexEntry_getFlags(this.hf, this);
    }

    public int getSize() {
        return XugglerJNI.IIndexEntry_getSize(this.hf, this);
    }

    public int Gq() {
        return XugglerJNI.IIndexEntry_getMinDistance(this.hf, this);
    }

    public boolean Gr() {
        return XugglerJNI.IIndexEntry_isKeyFrame(this.hf, this);
    }
}

