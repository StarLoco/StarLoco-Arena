/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;

public class bI {
    private volatile long hf;
    protected boolean hg;
    private jr_2 hh;

    protected bI(long l2, boolean bl2) {
        this.hf = l2;
        this.hg = bl2;
    }

    public static long a(bI bI2) {
        if (bI2 == null) {
            return 0L;
        }
        return bI2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof bI) {
            bl2 = ((bI)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    protected void finalize() {
        this.delete();
    }

    public synchronized void delete() {
        if (this.hf != 0L && this.hg) {
            this.hg = false;
            FerryJNI.delete_AtomicInteger(this.hf);
        }
        this.hf = 0L;
    }

    public bI() {
        this(FerryJNI.new_AtomicInteger__SWIG_0(), true);
    }

    public bI(int n2) {
        this(FerryJNI.new_AtomicInteger__SWIG_1(n2), true);
    }

    public int get() {
        return FerryJNI.AtomicInteger_get(this.hf, this);
    }

    public void set(int n2) {
        FerryJNI.AtomicInteger_set(this.hf, this, n2);
    }

    public int getAndSet(int n2) {
        return FerryJNI.AtomicInteger_getAndSet(this.hf, this, n2);
    }

    public int getAndIncrement() {
        return FerryJNI.AtomicInteger_getAndIncrement(this.hf, this);
    }

    public int getAndDecrement() {
        return FerryJNI.AtomicInteger_getAndDecrement(this.hf, this);
    }

    public int getAndAdd(int n2) {
        return FerryJNI.AtomicInteger_getAndAdd(this.hf, this, n2);
    }

    public int incrementAndGet() {
        return FerryJNI.AtomicInteger_incrementAndGet(this.hf, this);
    }

    public int decrementAndGet() {
        return FerryJNI.AtomicInteger_decrementAndGet(this.hf, this);
    }

    public int addAndGet(int n2) {
        return FerryJNI.AtomicInteger_addAndGet(this.hf, this, n2);
    }

    public boolean compareAndSet(int n2, int n3) {
        return FerryJNI.AtomicInteger_compareAndSet(this.hf, this, n2, n3);
    }

    public boolean dw() {
        return FerryJNI.AtomicInteger_isAtomic(this.hf, this);
    }
}

