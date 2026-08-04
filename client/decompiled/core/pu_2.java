/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;
import com.xuggle.ferry.JNIMemoryAllocator;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from Pu
 */
public final class pu_2
extends WeakReference {
    private final AtomicLong bDL = new AtomicLong(0L);
    private volatile JNIMemoryAllocator bDM;
    private final boolean bDN;
    private final AtomicLong bDO;
    private static final JNIMemoryAllocator bDP = new JNIMemoryAllocator();
    private final boolean bDQ = true;
    private static volatile boolean bDR = false;
    private final aq_2 bDS;

    static void ce(boolean bl2) {
        bDR = bl2;
    }

    static boolean aca() {
        return bDR;
    }

    private pu_2(Object object, Object object2, long l2, boolean bl2, AtomicLong atomicLong) {
        super(object2, amc_0.acb().getQueue());
        this.bDN = bl2;
        this.bDO = atomicLong;
        this.bDL.set(l2);
        this.bDS = bDR ? new aq_2(object) : null;
        C c = amc_0.aBv();
        if (c == C.ar || c == C.at) {
            return;
        }
        if (this.bDO.get() == 1L && FerryJNI.RefCounted_getCurrentNativeRefCount(l2, null) == 1) {
            this.bDM = bDP;
            JNIMemoryAllocator.setAllocator(l2, this.bDM);
        } else {
            this.bDM = JNIMemoryAllocator.getAllocator(l2);
        }
    }

    public static amc_0 acb() {
        return amc_0.acb();
    }

    static pu_2 a(Object object, Object object2, long l2, boolean bl2, AtomicLong atomicLong) {
        amc_0.acb().aBs();
        pu_2 pu_22 = new pu_2(object, object2, l2, bl2, atomicLong);
        amc_0.acb().a(pu_22);
        return pu_22;
    }

    static pu_2 a(Object object, Object object2, long l2, AtomicLong atomicLong) {
        return pu_2.a(object, object2, l2, true, atomicLong);
    }

    static pu_2 b(Object object, Object object2, long l2, AtomicLong atomicLong) {
        return pu_2.a(object, object2, l2, false, atomicLong);
    }

    public void delete() {
        long l2 = this.bDL.getAndSet(0L);
        if (l2 != 0L) {
            if (this.bDO.decrementAndGet() == 0L) {
                FerryJNI.RefCounted_release(l2, null);
            }
            this.bDM = null;
        }
    }

    boolean acc() {
        return this.bDN;
    }

    boolean acd() {
        return this.bDL.get() == 0L;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[");
        stringBuilder.append("native=").append(this.bDL.get()).append(";");
        if (this.bDS != null) {
            stringBuilder.append("proxyClass=").append(this.bDS.bT().getCanonicalName()).append(";");
            stringBuilder.append("hashCode=").append(this.bDS.bU()).append(";");
        }
        stringBuilder.append("object=[").append(this.get()).append("];");
        stringBuilder.append("];");
        return stringBuilder.toString();
    }
}

