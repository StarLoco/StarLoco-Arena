/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class nv
implements Iterable {
    private final lb_0 Oz = new lb_0();

    public void a(aai_1 aai_12) {
        this.Oz.c(aai_12.getId(), aai_12);
    }

    public final aai_1 cq(int n2) {
        return (aai_1)this.Oz.get(n2);
    }

    public final Object cr(int n2) {
        aai_1 aai_12 = (aai_1)this.Oz.get(n2);
        if (aai_12 != null) {
            return aai_12.getObject();
        }
        return null;
    }

    public Iterator iterator() {
        return new ny_0(this.Oz);
    }
}

