/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class aIz
implements yx_2 {
    private List dQc = new ArrayList();

    public void a(rh_0 rh_02) {
        this.b(rh_02.wl());
    }

    public void a(yx_2 yx_22) {
        this.b(yx_22);
    }

    public synchronized void b(yx_2 yx_22) {
        if (this == yx_22 || yx_22 instanceof aIz && ((aIz)yx_22).c(this)) {
            throw new IllegalArgumentException("Circular mapper containment condition detected");
        }
        this.dQc.add(yx_22);
    }

    protected synchronized boolean c(yx_2 yx_22) {
        boolean bl2 = false;
        Iterator iterator = this.dQc.iterator();
        while (iterator.hasNext() && !bl2) {
            yx_2 yx_23 = (yx_2)iterator.next();
            bl2 = yx_23 == yx_22 || yx_23 instanceof aIz && ((aIz)yx_23).c(yx_22);
        }
        return bl2;
    }

    public synchronized List aVf() {
        return Collections.unmodifiableList(this.dQc);
    }

    public void setFrom(String string) {
    }

    public void setTo(String string) {
    }
}

