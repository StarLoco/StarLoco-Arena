/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class aMg
implements amb {
    private static final List EMPTY_LIST = new ArrayList(0);
    int level;
    final String message;
    final Object dIj;
    List cQH;
    Throwable bhW;
    long dXv;

    aMg(int n2, String string, Object object) {
        this(n2, string, object, null);
    }

    aMg(int n2, String string, Object object, Throwable throwable) {
        this.level = n2;
        this.message = string;
        this.dIj = object;
        this.bhW = throwable;
        this.dXv = System.currentTimeMillis();
    }

    public synchronized void c(amb amb2) {
        if (amb2 == null) {
            throw new NullPointerException("Null values are not valid Status.");
        }
        if (this.cQH == null) {
            this.cQH = new ArrayList();
        }
        this.cQH.add(amb2);
    }

    public synchronized boolean hasChildren() {
        return this.cQH != null && this.cQH.size() > 0;
    }

    public synchronized Iterator iterator() {
        if (this.cQH != null) {
            return this.cQH.iterator();
        }
        return EMPTY_LIST.iterator();
    }

    public synchronized boolean d(amb amb2) {
        if (this.cQH == null) {
            return false;
        }
        return this.cQH.remove(amb2);
    }

    public int getLevel() {
        return this.level;
    }

    public synchronized int aBf() {
        int n2 = this.level;
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            amb amb2 = (amb)iterator.next();
            int n3 = amb2.aBf();
            if (n3 <= n2) continue;
            n2 = n3;
        }
        return n2;
    }

    public String getMessage() {
        return this.message;
    }

    public Object aBg() {
        return this.dIj;
    }

    public Throwable getThrowable() {
        return this.bhW;
    }

    public Long aBh() {
        return this.dXv;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        switch (this.aBf()) {
            case 0: {
                stringBuffer.append("INFO");
                break;
            }
            case 1: {
                stringBuffer.append("WARN");
                break;
            }
            case 2: {
                stringBuffer.append("ERROR");
            }
        }
        if (this.dIj != null) {
            stringBuffer.append(" in ");
            stringBuffer.append(this.dIj);
            stringBuffer.append(" -");
        }
        stringBuffer.append(" ");
        stringBuffer.append(this.message);
        if (this.bhW != null) {
            stringBuffer.append(" ");
            stringBuffer.append(this.bhW);
        }
        return stringBuffer.toString();
    }

    public int hashCode() {
        int n2 = 31;
        int n3 = 1;
        n3 = 31 * n3 + this.level;
        n3 = 31 * n3 + (this.message == null ? 0 : this.message.hashCode());
        return n3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        aMg aMg2 = (aMg)object;
        if (this.level != aMg2.level) {
            return false;
        }
        return !(this.message == null ? aMg2.message != null : !this.message.equals(aMg2.message));
    }
}

