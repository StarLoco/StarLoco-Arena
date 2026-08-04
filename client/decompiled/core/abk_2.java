/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/*
 * Renamed from abK
 */
public class abk_2
extends qp_0
implements acl_0 {
    protected static final int aKo = 8;
    protected static final int aKp = 4;
    protected Stack ciB = null;
    protected sq_1 ciC = null;
    protected int aKs = 8;
    protected int ciD = 0;

    public abk_2() {
        this(null, 8, 4);
    }

    public abk_2(int n2) {
        this(null, n2, 4);
    }

    public abk_2(int n2, int n3) {
        this(null, n2, n3);
    }

    public abk_2(sq_1 sq_12) {
        this(sq_12, 8, 4);
    }

    public abk_2(sq_1 sq_12, int n2) {
        this(sq_12, n2, 4);
    }

    public abk_2(sq_1 sq_12, int n2, int n3) {
        this.ciC = sq_12;
        this.aKs = n2 < 0 ? 8 : n2;
        int n4 = n3 < 1 ? 4 : n3;
        this.ciB = new Stack();
        this.ciB.ensureCapacity(n4 > this.aKs ? this.aKs : n4);
    }

    public synchronized Object adr() {
        this.adt();
        Object object = null;
        while (null == object) {
            if (!this.ciB.empty()) {
                object = this.ciB.pop();
            } else {
                if (null == this.ciC) {
                    throw new NoSuchElementException();
                }
                object = this.ciC.i();
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
        if (null != this.ciC) {
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
        }
        boolean bl3 = !bl2;
        --this.ciD;
        if (bl2) {
            Object var4_5 = null;
            if (this.ciB.size() >= this.aKs) {
                bl3 = true;
                var4_5 = this.ciB.remove(0);
            }
            this.ciB.push(object);
            object = var4_5;
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
        if (null != this.ciC) {
            this.ciC.u(object);
        }
        this.notifyAll();
    }

    public synchronized int jx() {
        this.adt();
        return this.ciB.size();
    }

    public synchronized int jy() {
        this.adt();
        return this.ciD;
    }

    public synchronized void clear() {
        this.adt();
        if (null != this.ciC) {
            Iterator iterator = this.ciB.iterator();
            while (iterator.hasNext()) {
                try {
                    this.ciC.u(iterator.next());
                }
                catch (Exception exception) {}
            }
        }
        this.ciB.clear();
    }

    public synchronized void close() {
        this.clear();
        this.ciB = null;
        this.ciC = null;
        super.close();
    }

    public synchronized void ads() {
        this.adt();
        Object object = this.ciC.i();
        ++this.ciD;
        this.af(object);
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

