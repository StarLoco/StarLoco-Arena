/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;
import java.util.concurrent.atomic.AtomicLong;

public class WK
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected WK(long l2, boolean bl2) {
        super(FerryJNI.SWIGMutexUpcast(l2), bl2);
        this.hf = l2;
    }

    protected WK(long l2, boolean bl2, AtomicLong atomicLong) {
        super(FerryJNI.SWIGMutexUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(WK wK) {
        if (wK == null) {
            return 0L;
        }
        return wK.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public WK ajm() {
        if (this.hf == 0L) {
            return null;
        }
        return new WK(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof WK) {
            bl2 = ((WK)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public static WK ajn() {
        long l2 = FerryJNI.Mutex_make();
        return l2 == 0L ? null : new WK(l2, false);
    }

    public void lock() {
        FerryJNI.Mutex_lock(this.hf, this);
    }

    public void unlock() {
        FerryJNI.Mutex_unlock(this.hf, this);
    }
}

