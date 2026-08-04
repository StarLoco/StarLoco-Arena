/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;
import java.util.Iterator;

final class aGU
implements Enumeration {
    private final Iterator dJO;

    aGU(Iterator iterator) {
        this.dJO = iterator;
    }

    public boolean hasMoreElements() {
        return this.dJO.hasNext();
    }

    public Object nextElement() {
        return this.dJO.next();
    }
}

