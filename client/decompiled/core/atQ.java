/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

public class atQ {
    private static atQ cUx = new atQ();
    private final ArrayList G = new ArrayList();
    private final ArrayList cUy = new ArrayList();
    private final ArrayList cUz = new ArrayList();
    private final ArrayList cUA = new ArrayList();
    private boolean eD = false;
    private boolean bxC = false;
    private final ArrayList cUB = new ArrayList();
    private final ArrayList cUC = new ArrayList();
    private boolean cUD = false;
    private int cUE = 0;

    private atQ() {
    }

    public static atQ aGT() {
        return cUx;
    }

    public void a(wp_1 wp_12) {
        if (!this.bxC) {
            this.G.add(wp_12);
        } else {
            this.cUy.add(wp_12);
            this.cUD = true;
        }
    }

    public void b(wp_1 wp_12) {
        if (!this.bxC) {
            this.G.remove(wp_12);
        } else {
            this.cUz.add(wp_12);
            this.cUD = true;
        }
    }

    public void j(qa_1 qa_12) {
        if (this.bxC) {
            this.cUB.add(qa_12);
            this.cUD = true;
        } else {
            if (!this.cUA.contains(qa_12)) {
                this.cUA.add(qa_12);
            }
            this.eD = true;
        }
    }

    public void k(qa_1 qa_12) {
        if (this.bxC) {
            if (this.cUB.contains(qa_12)) {
                this.cUB.remove(qa_12);
            } else {
                this.cUC.add(qa_12);
            }
            this.cUD = true;
        } else {
            this.cUA.remove(qa_12);
        }
    }

    public void sort() {
        Collections.sort(this.cUA, fR.jp());
        this.eD = false;
    }

    public void cf() {
        this.eD = true;
    }

    public boolean aGU() {
        return this.eD;
    }

    public void aGV() {
        int n2;
        this.lock();
        int n3 = this.cUE;
        this.cUE = 0;
        int n4 = this.cUA.size();
        for (n2 = 0; n2 < n4; ++n2) {
            if (!((qa_1)this.cUA.get(n2)).uV()) continue;
            ++this.cUE;
        }
        if (this.cUE == 0 && n3 > 0) {
            n4 = this.G.size();
            for (n2 = 0; n2 < n4; ++n2) {
                ((wp_1)this.G.get(n2)).aje();
            }
        }
        this.unlock();
    }

    public void lock() {
        this.bxC = true;
    }

    public void unlock() {
        int n2;
        this.bxC = false;
        if (!this.cUD) {
            return;
        }
        int n3 = this.cUB.size();
        if (n3 > 0) {
            for (n2 = 0; n2 < n3; ++n2) {
                this.j((qa_1)this.cUB.get(n2));
            }
            this.cUB.clear();
        }
        if ((n3 = this.cUC.size()) > 0) {
            for (n2 = 0; n2 < n3; ++n2) {
                this.k((qa_1)this.cUC.get(n2));
            }
            this.cUC.clear();
        }
        if ((n3 = this.cUy.size()) > 0) {
            for (n2 = 0; n2 < n3; ++n2) {
                this.a((wp_1)this.cUy.get(n2));
            }
            this.cUy.clear();
        }
        if ((n3 = this.cUz.size()) > 0) {
            for (n2 = 0; n2 < n3; ++n2) {
                this.b((wp_1)this.cUz.get(n2));
            }
            this.cUz.clear();
        }
        this.cUD = false;
    }
}

