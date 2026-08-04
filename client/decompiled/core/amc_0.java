/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Renamed from amc
 */
public final class amc_0 {
    private static final amc_0 cGc = new amc_0();
    private final Bk ub = LD.p(this.getClass());
    private static C cGd;
    private final ReferenceQueue cGe = new ReferenceQueue();
    private final AtomicBoolean cGf;
    private final Lock cGg = new ReentrantLock();
    private pu_2[] cGh;
    private volatile int cGi;
    private volatile int cGj;
    private int cGk;
    private double cGl;
    private double cGm;
    private double cGn;
    private double cGo;
    private volatile Thread cGp = null;

    public static amc_0 acb() {
        return cGc;
    }

    public static void collect() {
        amc_0.acb().gc();
    }

    amc_0() {
        this.cGf = new AtomicBoolean(false);
        int n2 = 4096;
        this.cGk = 4096;
        this.cGl = 0.2;
        this.cGm = 0.25;
        this.cGh = new pu_2[4096];
        this.cGj = 4096;
        this.cGi = 0;
        this.cGn = 0.7;
        this.cGo = 0.3;
    }

    private void aBi() {
        this.cGg.lock();
        while (!this.cGf.compareAndSet(false, true)) {
        }
    }

    private void aBj() {
        boolean bl2 = this.cGf.compareAndSet(true, false);
        assert (bl2) : "Should never ever be unlocked here";
        this.cGg.unlock();
    }

    public void ls(int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("Must pass in a positive integer");
        }
        this.cGk = n2;
    }

    public int aBk() {
        return this.cGk;
    }

    public void B(double d) {
        if (d <= 0.0) {
            throw new IllegalArgumentException("Must pass in positive percentage");
        }
        this.cGl = d / 100.0;
    }

    public double aBl() {
        return this.cGl * 100.0;
    }

    public void C(double d) {
        if (d <= 0.0 || d >= 100.0) {
            throw new IllegalArgumentException("only 0 < shrinkFactor < 100 allowed");
        }
        this.cGm = d / 100.0;
    }

    public double aBm() {
        return this.cGm * 100.0;
    }

    public void D(double d) {
        this.cGn = d / 100.0;
    }

    public double aBn() {
        return this.cGn * 100.0;
    }

    public void E(double d) {
        this.cGo = d / 100.0;
    }

    public double aBo() {
        return this.cGo * 100.0;
    }

    private int aBp() {
        int n2;
        pu_2[] pu_2Array = new pu_2[this.cGj];
        int n3 = 0;
        int n4 = this.cGj;
        for (n2 = 0; n2 < n4; ++n2) {
            pu_2 pu_22 = this.cGh[n2];
            if (pu_22 == null || pu_22.acd()) continue;
            pu_2Array[n3] = pu_22;
            ++n3;
        }
        n2 = pu_2Array.length;
        int n5 = n2 - n3;
        if ((double)n5 > (double)n2 * this.cGn) {
            int n6 = (int)((double)n2 * (1.0 - this.cGl * this.cGm));
            if (n6 >= this.cGk) {
                pu_2[] pu_2Array2 = new pu_2[n6];
                System.arraycopy(pu_2Array, 0, pu_2Array2, 0, n6);
                pu_2Array = pu_2Array2;
            }
        } else if ((double)n5 <= (double)n2 * this.cGo) {
            int n7 = (int)((double)n2 * (1.0 + this.cGl));
            pu_2[] pu_2Array3 = new pu_2[n7];
            System.arraycopy(pu_2Array, 0, pu_2Array3, 0, n2);
            pu_2Array = pu_2Array3;
        }
        this.cGh = pu_2Array;
        this.cGj = pu_2Array.length;
        this.cGi = n3;
        return n3;
    }

    ReferenceQueue getQueue() {
        return this.cGe;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long aBq() {
        long l2 = 0L;
        this.aBi();
        try {
            int n2 = this.cGi;
            for (int j = 0; j < n2; ++j) {
                pu_2 pu_22 = this.cGh[j];
                if (pu_22 == null || pu_22.acd()) continue;
                ++l2;
            }
        }
        finally {
            this.aBj();
        }
        return l2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void aBr() {
        this.aBi();
        try {
            int n2 = this.cGi;
            this.ub.j("Memory slots in use: {}", n2);
            for (int j = 0; j < n2; ++j) {
                pu_2 pu_22 = this.cGh[j];
                if (pu_22 == null) continue;
                this.ub.b("Slot: {}; Ref: {}", j, (Object)pu_22);
            }
        }
        finally {
            this.aBj();
        }
    }

    public boolean aca() {
        return pu_2.aca();
    }

    public void ce(boolean bl2) {
        pu_2.ce(bl2);
    }

    public void finalize() {
        this.gc();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final boolean a(pu_2 pu_22) {
        int n2;
        boolean bl2 = false;
        bl2 = this.cGf.compareAndSet(false, true);
        if (bl2) {
            if ((n2 = this.cGi++) < this.cGj) {
                this.cGh[n2] = pu_22;
                boolean bl3 = this.cGf.compareAndSet(true, false);
                assert (bl3) : "Should never be unlocked here";
                return true;
            }
            if (!this.cGg.tryLock()) {
                bl2 = false;
                this.cGf.compareAndSet(true, false);
            }
        }
        if (!bl2) {
            this.cGg.lock();
            while (!this.cGf.compareAndSet(false, true)) {
            }
        }
        try {
            n2 = this.cGi++;
            if (n2 >= this.cGj) {
                this.aBp();
                n2 = this.cGi++;
            }
            this.cGh[n2] = pu_22;
        }
        catch (Throwable throwable) {
            boolean bl4 = this.cGf.compareAndSet(true, false);
            assert (bl4) : "Should never ever be unlocked here";
            this.cGg.unlock();
            throw throwable;
        }
        n2 = this.cGf.compareAndSet(true, false) ? 1 : 0;
        assert (n2 != 0) : "Should never ever be unlocked here";
        this.cGg.unlock();
        return true;
    }

    public void gc() {
        this.dI(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dI(boolean bl2) {
        this.aBs();
        if (bl2) {
            this.aBi();
            try {
                this.aBp();
            }
            finally {
                this.aBj();
            }
        }
    }

    public void aBs() {
        pu_2 pu_22 = null;
        while ((pu_22 = (pu_2)this.cGe.poll()) != null) {
            pu_22.delete();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void aBt() {
        amc_0 amc_02 = this;
        synchronized (amc_02) {
            if (this.cGp != null) {
                throw new RuntimeException("Thread already running");
            }
            this.cGp = new Thread((Runnable)new ajj_0(this), "Xuggle Ferry Collection Thread");
            this.cGp.setDaemon(true);
            this.cGp.start();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void aBu() {
        amc_0 amc_02 = this;
        synchronized (amc_02) {
            if (this.cGp != null) {
                this.cGp.interrupt();
            }
        }
    }

    public static C aBv() {
        return cGd;
    }

    public static void a(C c) {
        FerryJNI.setMemoryModel(c.Z());
        cGd = c;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void flush() {
        this.aBi();
        try {
            int n2 = this.aBp();
            for (int j = 0; j < n2; ++j) {
                pu_2 pu_22 = this.cGh[j];
                if (pu_22 == null) continue;
                pu_22.delete();
            }
            this.aBp();
            this.cGh = new pu_2[this.cGk];
            this.cGi = 0;
            this.cGj = this.cGk;
        }
        finally {
            this.aBj();
        }
    }

    static /* synthetic */ ReferenceQueue a(amc_0 amc_02) {
        return amc_02.cGe;
    }

    static /* synthetic */ Thread a(amc_0 amc_02, Thread thread) {
        amc_02.cGp = thread;
        return amc_02.cGp;
    }

    static {
        int n2 = 0;
        cGd = C.aq;
        n2 = FerryJNI.getMemoryModel();
        for (C c : C.values()) {
            if (c.Z() != n2) continue;
            cGd = c;
        }
    }
}

