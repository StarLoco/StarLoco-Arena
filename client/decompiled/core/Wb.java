/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

class Wb
implements Iterator {
    private Iterator bTO;
    private Iterator bTP;
    private final nl_2 bTQ;

    private Wb(nl_2 nl_22) {
        this.bTQ = nl_22;
        this.bTO = afx_1.a(nl_2.a(this.bTQ)).iterator();
        this.bTP = null;
    }

    public boolean hasNext() {
        boolean bl2;
        boolean bl3 = bl2 = this.bTP != null && this.bTP.hasNext();
        while (!bl2 && this.bTO.hasNext()) {
            this.bTP = ((mx_2)this.bTO.next()).iterator();
            bl2 = this.bTP.hasNext();
        }
        return bl2;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.bTP.next();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    Wb(nl_2 nl_22, xm_0 xm_02) {
        this(nl_22);
    }
}

