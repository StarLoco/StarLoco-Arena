/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class Ue
extends ant_2
implements mx_2 {
    private sk_1 bPv = new sn_1(this);

    public synchronized void a(mx_2 mx_22) {
        if (this.aId()) {
            throw this.aIi();
        }
        if (mx_22 == null) {
            return;
        }
        this.bPv.a(mx_22);
    }

    public synchronized void bY(boolean bl2) {
        this.bPv.bY(bl2);
    }

    public synchronized boolean abu() {
        return this.bPv.abu();
    }

    public synchronized void a(wb_2 wb_22) {
        if (wb_22 == null) {
            return;
        }
        super.a(wb_22);
        sw_0.ao(this);
    }

    public final synchronized Iterator iterator() {
        if (this.aId()) {
            return ((Ue)this.aIg()).iterator();
        }
        this.aIf();
        return this.bPv.iterator();
    }

    public synchronized int size() {
        if (this.aId()) {
            return ((Ue)this.aIg()).size();
        }
        this.aIf();
        return this.bPv.size();
    }

    public synchronized boolean dE() {
        if (this.aId()) {
            return ((Ue)this.aIg()).dE();
        }
        this.aIf();
        return this.bPv.dE();
    }

    public synchronized String toString() {
        if (this.aId()) {
            return this.aIg().toString();
        }
        this.aIf();
        return this.bPv.toString();
    }

    static sk_1 a(Ue ue) {
        return ue.bPv;
    }
}

