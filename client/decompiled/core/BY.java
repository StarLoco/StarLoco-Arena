/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BY
extends gl
implements nv_2 {
    protected static final int aKo = 8;
    protected static final int aKp = 4;
    protected HashMap aKq = null;
    protected rn_0 aKr = null;
    protected int aKs = 8;
    protected int aKt = 4;
    protected int aKu = 0;
    protected int aKv = 0;
    protected HashMap aKw = null;

    public BY() {
        this(null, 8, 4);
    }

    public BY(int n2) {
        this(null, n2, 4);
    }

    public BY(int n2, int n3) {
        this(null, n2, n3);
    }

    public BY(rn_0 rn_02) {
        this(rn_02, 8);
    }

    public BY(rn_0 rn_02, int n2) {
        this(rn_02, n2, 4);
    }

    public BY(rn_0 rn_02, int n2, int n3) {
        this.aKr = rn_02;
        this.aKs = n2 < 0 ? 8 : n2;
        this.aKt = n3 < 1 ? 4 : n3;
        this.aKq = new HashMap();
        this.aKw = new HashMap();
    }

    public synchronized Object i(Object object) {
        Object object2 = null;
        Stack stack = (Stack)this.aKq.get(object);
        if (null == stack) {
            stack = new Stack();
            stack.ensureCapacity(this.aKt > this.aKs ? this.aKs : this.aKt);
            this.aKq.put(object, stack);
        }
        try {
            object2 = stack.pop();
            --this.aKv;
        }
        catch (Exception exception) {
            if (null == this.aKr) {
                throw new NoSuchElementException();
            }
            object2 = this.aKr.r(object);
        }
        if (null != object2 && null != this.aKr) {
            this.aKr.g(object, object2);
        }
        this.N(object);
        return object2;
    }

    public synchronized void c(Object object, Object object2) {
        this.O(object);
        if (null == this.aKr || this.aKr.f(object, object2)) {
            Stack<Object> stack = (Stack<Object>)this.aKq.get(object);
            if (null == stack) {
                stack = new Stack<Object>();
                stack.ensureCapacity(this.aKt > this.aKs ? this.aKs : this.aKt);
                this.aKq.put(object, stack);
            }
            if (null != this.aKr) {
                try {
                    this.aKr.h(object, object2);
                }
                catch (Exception exception) {
                    this.aKr.e(object, object2);
                    return;
                }
            }
            if (stack.size() < this.aKs) {
                stack.push(object2);
                ++this.aKv;
            } else if (null != this.aKr) {
                this.aKr.e(object, object2);
            }
        } else if (null != this.aKr) {
            this.aKr.e(object, object2);
        }
    }

    public synchronized void d(Object object, Object object2) {
        this.O(object);
        if (null != this.aKr) {
            this.aKr.e(object, object2);
        }
        this.notifyAll();
    }

    public synchronized void j(Object object) {
        Object object2 = this.aKr.r(object);
        this.N(object);
        this.c(object, object2);
    }

    public int jx() {
        return this.aKv;
    }

    public int jy() {
        return this.aKu;
    }

    public synchronized int l(Object object) {
        return this.M(object);
    }

    public synchronized int k(Object object) {
        try {
            return ((Stack)this.aKq.get(object)).size();
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public synchronized void clear() {
        Iterator iterator = this.aKq.keySet().iterator();
        while (iterator.hasNext()) {
            Object k2 = iterator.next();
            Stack stack = (Stack)this.aKq.get(k2);
            this.a(k2, stack);
        }
        this.aKv = 0;
        this.aKq.clear();
        this.aKw.clear();
    }

    public synchronized void m(Object object) {
        Stack stack = (Stack)this.aKq.remove(object);
        this.a(object, stack);
    }

    private synchronized void a(Object object, Stack stack) {
        if (null == stack) {
            return;
        }
        if (null != this.aKr) {
            Iterator iterator = stack.iterator();
            while (iterator.hasNext()) {
                try {
                    this.aKr.e(object, iterator.next());
                }
                catch (Exception exception) {}
            }
        }
        this.aKv -= stack.size();
        this.aKw.remove(object);
        stack.clear();
    }

    public synchronized String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getName());
        stringBuffer.append(" contains ").append(this.aKq.size()).append(" distinct pools: ");
        Iterator iterator = this.aKq.keySet().iterator();
        while (iterator.hasNext()) {
            Object k2 = iterator.next();
            stringBuffer.append(" |").append(k2).append("|=");
            Stack stack = (Stack)this.aKq.get(k2);
            stringBuffer.append(stack.size());
        }
        return stringBuffer.toString();
    }

    public synchronized void close() {
        this.clear();
        this.aKq = null;
        this.aKr = null;
        this.aKw = null;
    }

    public synchronized void a(rn_0 rn_02) {
        if (0 < this.jy()) {
            throw new IllegalStateException("Objects are already active");
        }
        this.clear();
        this.aKr = rn_02;
    }

    private int M(Object object) {
        try {
            return (Integer)this.aKw.get(object);
        }
        catch (NoSuchElementException noSuchElementException) {
            return 0;
        }
        catch (NullPointerException nullPointerException) {
            return 0;
        }
    }

    private void N(Object object) {
        ++this.aKu;
        Integer n2 = (Integer)this.aKw.get(object);
        if (null == n2) {
            this.aKw.put(object, new Integer(1));
        } else {
            this.aKw.put(object, new Integer(n2 + 1));
        }
    }

    private void O(Object object) {
        --this.aKu;
        Integer n2 = (Integer)this.aKw.get(object);
        if (null != n2) {
            if (n2 <= 1) {
                this.aKw.remove(object);
            } else {
                this.aKw.put(object, new Integer(n2 - 1));
            }
        }
    }
}

