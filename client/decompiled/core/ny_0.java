/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from Ny
 */
public class ny_0
implements Iterator {
    private ll_0 bzU;

    public ny_0(lb_0 lb_02) {
        this.bzU = lb_02.pK();
    }

    public boolean hasNext() {
        return this.bzU.hasNext();
    }

    public Object next() {
        this.bzU.fK();
        return this.bzU.value();
    }

    public void remove() {
        this.bzU.remove();
    }
}

