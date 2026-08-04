/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public class arU
implements Ju {
    public static final int cQN = 150;
    public static final int cQO = 150;
    int count = 0;
    protected final List aGQ = new ArrayList();
    protected final ab_0 cQP = new ab_0(150);
    protected final Object cQQ = new Object();
    int level = 0;
    protected final List cQR = new ArrayList();
    protected final Object cQS = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c(amb amb2) {
        this.e(amb2);
        ++this.count;
        if (amb2.getLevel() > this.level) {
            this.level = amb2.getLevel();
        }
        Object object = this.cQQ;
        synchronized (object) {
            if (this.aGQ.size() < 150) {
                this.aGQ.add(amb2);
            } else {
                this.cQP.add(amb2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List VS() {
        Object object = this.cQQ;
        synchronized (object) {
            ArrayList arrayList = new ArrayList(this.aGQ);
            arrayList.addAll(this.cQP.asList());
            return arrayList;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void e(amb amb2) {
        Object object = this.cQS;
        synchronized (object) {
            for (pm_1 pm_12 : this.cQR) {
                pm_12.a(amb2);
            }
        }
    }

    public int getLevel() {
        return this.level;
    }

    public int getCount() {
        return this.count;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(pm_1 pm_12) {
        Object object = this.cQS;
        synchronized (object) {
            this.cQR.add(pm_12);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(pm_1 pm_12) {
        Object object = this.cQS;
        synchronized (object) {
            this.cQR.remove(pm_12);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List VT() {
        Object object = this.cQS;
        synchronized (object) {
            return new ArrayList(this.cQR);
        }
    }
}

