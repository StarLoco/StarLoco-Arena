/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from Wf
 */
public class wf_0
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected wf_0(long l2, boolean bl2) {
        super(FerryJNI.SWIGRefCountedTesterUpcast(l2), bl2);
        this.hf = l2;
    }

    protected wf_0(long l2, boolean bl2, AtomicLong atomicLong) {
        super(FerryJNI.SWIGRefCountedTesterUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(wf_0 wf_02) {
        if (wf_02 == null) {
            return 0L;
        }
        return wf_02.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public wf_0 aja() {
        if (this.hf == 0L) {
            return null;
        }
        return new wf_0(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof wf_0) {
            bl2 = ((wf_0)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public static wf_0 ajb() {
        long l2 = FerryJNI.RefCountedTester_make__SWIG_0();
        return l2 == 0L ? null : new wf_0(l2, false);
    }

    public static wf_0 b(wf_0 wf_02) {
        long l2 = FerryJNI.RefCountedTester_make__SWIG_1(wf_0.a(wf_02), wf_02);
        return l2 == 0L ? null : new wf_0(l2, false);
    }
}

