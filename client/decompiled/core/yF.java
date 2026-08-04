/*
 * Decompiled with CFR 0.152.
 */
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class yF
extends AbstractCollection {
    private final Iterator aDg;
    private final List elements = new ArrayList();

    public yF(Iterator iterator) {
        this.aDg = iterator;
    }

    public Iterator iterator() {
        return new Pf(this);
    }

    public int size() {
        int n2 = 0;
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            ++n2;
            iterator.next();
        }
        return n2;
    }

    static List a(yF yF2) {
        return yF2.elements;
    }

    static Iterator b(yF yF2) {
        return yF2.aDg;
    }
}

