/*
 * Decompiled with CFR 0.152.
 */
import java.util.ListIterator;
import java.util.NoSuchElementException;

public final class atJ
implements ListIterator {
    private int cUq = 0;
    private Wv cUr;
    private Wv cUs;
    final /* synthetic */ acM cUt;

    atJ(acM acM2, int n2) {
        this.cUt = acM2;
        if (n2 < 0 || n2 > acM2._size) {
            throw new IndexOutOfBoundsException();
        }
        this.cUq = n2;
        if (n2 == 0) {
            this.cUr = acM2.ckN;
        } else if (n2 == acM2._size) {
            this.cUr = null;
        } else if (n2 < acM2._size >> 1) {
            this.cUr = acM2.ckN;
            for (int j = 0; j < n2; ++j) {
                this.cUr = this.cUr.uw();
            }
        } else {
            this.cUr = acM2.ckO;
            for (int j = acM2._size - 1; j > n2; --j) {
                this.cUr = this.cUr.ux();
            }
        }
    }

    public final void h(Wv wv) {
        this.cUs = null;
        ++this.cUq;
        if (this.cUt._size == 0) {
            this.cUt.c(wv);
        } else {
            this.cUt.a(this.cUr, wv);
        }
    }

    public final boolean hasNext() {
        return this.cUq != this.cUt._size;
    }

    public final boolean hasPrevious() {
        return this.cUq != 0;
    }

    public final Wv aGP() {
        if (this.cUq == this.cUt._size) {
            throw new NoSuchElementException();
        }
        this.cUs = this.cUr;
        this.cUr = this.cUr.uw();
        ++this.cUq;
        return this.cUs;
    }

    public final int nextIndex() {
        return this.cUq;
    }

    public final Wv aGQ() {
        if (this.cUq == 0) {
            throw new NoSuchElementException();
        }
        this.cUs = this.cUq == this.cUt._size ? (this.cUr = this.cUt.ckO) : (this.cUr = this.cUr.ux());
        --this.cUq;
        return this.cUs;
    }

    public final int previousIndex() {
        return this.cUq - 1;
    }

    public final void remove() {
        if (this.cUs == null) {
            throw new IllegalStateException("must invoke next or previous before invoking remove");
        }
        if (this.cUs != this.cUr) {
            --this.cUq;
        }
        this.cUr = this.cUs.uw();
        this.cUt.remove(this.cUs);
        this.cUs = null;
    }

    public final void i(Wv wv) {
        if (this.cUs == null) {
            throw new IllegalStateException();
        }
        Wv wv2 = wv;
        if (this.cUs == this.cUt.ckN) {
            this.cUt.ckN = wv2;
        }
        if (this.cUs == this.cUt.ckO) {
            this.cUt.ckO = wv2;
        }
        this.c(this.cUs, wv2);
        this.cUs = wv2;
    }

    private void c(Wv wv, Wv wv2) {
        Wv wv3 = wv.ux();
        Wv wv4 = wv.uw();
        if (null != wv3) {
            wv2.b(wv3);
            wv3.a(wv2);
        }
        if (null != wv4) {
            wv2.a(wv4);
            wv4.b(wv2);
        }
        wv.a(null);
        wv.b(null);
    }
}

