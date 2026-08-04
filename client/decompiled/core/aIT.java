/*
 * Decompiled with CFR 0.152.
 */
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class aIT
extends qp_0
implements acl_0 {
    private List dQM = new ArrayList();
    private sq_1 ciC = null;
    private int ciD = 0;

    public aIT() {
        this.ciC = null;
    }

    public aIT(sq_1 sq_12) {
        this.ciC = sq_12;
    }

    public aIT(sq_1 sq_12, int n2) {
        this.ciC = sq_12;
        if (null != this.ciC) {
            for (int j = 0; j < n2; ++j) {
                Object object = this.ciC.i();
                this.ciC.t(object);
                this.dQM.add(new SoftReference<Object>(object));
            }
        }
    }

    public synchronized Object adr() {
        this.adt();
        Object object = null;
        while (null == object) {
            if (this.dQM.isEmpty()) {
                if (null == this.ciC) {
                    throw new NoSuchElementException();
                }
                object = this.ciC.i();
            } else {
                SoftReference softReference = (SoftReference)this.dQM.remove(this.dQM.size() - 1);
                object = softReference.get();
            }
            if (null != this.ciC && null != object) {
                this.ciC.s(object);
            }
            if (null == this.ciC || null == object || this.ciC.v(object)) continue;
            this.ciC.u(object);
            object = null;
        }
        ++this.ciD;
        return object;
    }

    public synchronized void af(Object object) {
        this.adt();
        boolean bl2 = true;
        if (!this.ciC.v(object)) {
            bl2 = false;
        } else {
            try {
                this.ciC.t(object);
            }
            catch (Exception exception) {
                bl2 = false;
            }
        }
        boolean bl3 = !bl2;
        --this.ciD;
        if (bl2) {
            this.dQM.add(new SoftReference<Object>(object));
        }
        this.notifyAll();
        if (bl3) {
            try {
                this.ciC.u(object);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public synchronized void ag(Object object) {
        this.adt();
        --this.ciD;
        this.ciC.u(object);
        this.notifyAll();
    }

    public synchronized void ads() {
        this.adt();
        Object object = this.ciC.i();
        ++this.ciD;
        this.af(object);
    }

    public synchronized int jx() {
        this.adt();
        return this.dQM.size();
    }

    public synchronized int jy() {
        this.adt();
        return this.ciD;
    }

    public synchronized void clear() {
        this.adt();
        if (null != this.ciC) {
            Iterator iterator = this.dQM.iterator();
            while (iterator.hasNext()) {
                try {
                    Object t = ((SoftReference)iterator.next()).get();
                    if (null == t) continue;
                    this.ciC.u(t);
                }
                catch (Exception exception) {}
            }
        }
        this.dQM.clear();
    }

    public synchronized void close() {
        this.clear();
        this.dQM = null;
        this.ciC = null;
        super.close();
    }

    public synchronized void a(sq_1 sq_12) {
        this.adt();
        if (0 < this.jy()) {
            throw new IllegalStateException("Objects are already active");
        }
        this.clear();
        this.ciC = sq_12;
    }
}

