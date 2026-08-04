/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;
import java.util.Iterator;

public class KK
implements Iterator {
    private final Enumeration bpj;

    public KK(Enumeration enumeration) {
        this.bpj = enumeration;
    }

    public boolean hasNext() {
        return this.bpj.hasMoreElements();
    }

    public Object next() {
        return this.bpj.nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

