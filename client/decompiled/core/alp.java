/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;
import java.util.concurrent.atomic.AtomicLong;

public class alp {
    private volatile long hf;
    protected boolean hg;
    private pu_2 cFc;
    private Long cFd;
    private jr_2 cFe;
    private AtomicLong bDO;

    protected alp(long l2, boolean bl2) {
        this(l2, bl2, new AtomicLong(0L));
    }

    protected alp(long l2, boolean bl2, AtomicLong atomicLong) {
        this.hg = bl2;
        this.hf = l2;
        atomicLong.incrementAndGet();
        this.bDO = atomicLong;
        if (this.hf != 0L) {
            this.cFe = new jr_2();
            this.cFd = new Long(this.hf);
            this.cFc = pu_2.a(this, this.cFd, this.hf, this.bDO);
        }
    }

    public static long a(alp alp2) {
        if (alp2 == null) {
            return 0L;
        }
        return alp2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new NullPointerException("underlying native object already deleted");
        }
        return this.hf;
    }

    public void delete() {
        if (this.hf != 0L) {
            alp alp2 = this;
            if (alp2 instanceof alp && this.cFc != null) {
                this.cFc.delete();
            } else if (this.hg) {
                this.hg = false;
            }
        }
        this.bDO = null;
        this.cFc = null;
        this.cFe = null;
        this.cFd = null;
        this.hf = 0L;
    }

    public alp fG() {
        if (this.hf == 0L) {
            return null;
        }
        return new alp(this.hf, this.hg, this.aAJ());
    }

    protected AtomicLong aAJ() {
        return this.bDO;
    }

    public long aAK() {
        return this.bDO.get() + (long)this.aAN() - 1L;
    }

    public int aAL() {
        return FerryJNI.RefCounted_acquire(this.hf, this);
    }

    public int aAM() {
        return FerryJNI.RefCounted_release(this.hf, this);
    }

    private int aAN() {
        return FerryJNI.RefCounted_getCurrentNativeRefCount(this.hf, this);
    }
}

