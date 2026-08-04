/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/*
 * Renamed from Os
 */
public abstract class os_0
extends avg
implements mx_2,
Cloneable {
    private List bBW = new ArrayList();
    private Collection bBX = null;
    private boolean bBY = true;

    public synchronized void bY(boolean bl2) {
        this.bBY = bl2;
    }

    public synchronized boolean abu() {
        return this.bBY;
    }

    public synchronized void clear() {
        if (this.aId()) {
            throw this.aIi();
        }
        this.bBW.clear();
        sw_0.ao(this);
        this.bBX = null;
        this.setChecked(false);
    }

    public synchronized void a(mx_2 mx_22) {
        UI uI;
        if (this.aId()) {
            throw this.aIi();
        }
        if (mx_22 == null) {
            return;
        }
        if (UI.ar(mx_22) == null && (uI = this.TP()) != null) {
            uI.at(mx_22);
        }
        this.bBW.add(mx_22);
        sw_0.ao(this);
        this.bBX = null;
        this.setChecked(false);
    }

    public synchronized void a(Collection collection) {
        if (this.aId()) {
            throw this.aIi();
        }
        try {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.a((mx_2)iterator.next());
            }
        }
        catch (ClassCastException classCastException) {
            throw new eq_2(classCastException);
        }
    }

    public final synchronized Iterator iterator() {
        if (this.aId()) {
            return ((os_0)this.aIg()).iterator();
        }
        this.aIf();
        return new sw_0(this, this.abw().iterator());
    }

    public synchronized int size() {
        if (this.aId()) {
            return ((os_0)this.aIg()).size();
        }
        this.aIf();
        return this.abw().size();
    }

    public synchronized boolean dE() {
        if (this.aId()) {
            return ((os_0)this.aIg()).dE();
        }
        this.aIf();
        boolean bl2 = true;
        Iterator iterator = this.bBW.iterator();
        while (bl2 && iterator.hasNext()) {
            bl2 = ((mx_2)iterator.next()).dE();
        }
        if (bl2) {
            return true;
        }
        iterator = this.abw().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof ash_0) continue;
            return false;
        }
        return true;
    }

    protected synchronized void a(Stack stack, UI uI) {
        if (this.isChecked()) {
            return;
        }
        if (this.aId()) {
            super.a(stack, uI);
        } else {
            Iterator iterator = this.bBW.iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                if (!(e instanceof avg)) continue;
                stack.push(e);
                os_0.a((avg)e, stack, uI);
                stack.pop();
            }
            this.setChecked(true);
        }
    }

    protected final synchronized List abv() {
        this.aIf();
        return Collections.unmodifiableList(this.bBW);
    }

    protected abstract Collection getCollection();

    public Object clone() {
        try {
            os_0 os_02 = (os_0)super.clone();
            os_02.bBW = new ArrayList(this.bBW);
            os_02.bBX = null;
            return os_02;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new eq_2(cloneNotSupportedException);
        }
    }

    public synchronized String toString() {
        if (this.aId()) {
            return this.aIg().toString();
        }
        if (this.abw().size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterator = this.bBX.iterator();
        while (iterator.hasNext()) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(File.pathSeparatorChar);
            }
            stringBuffer.append(iterator.next());
        }
        return stringBuffer.toString();
    }

    private synchronized Collection abw() {
        if (this.bBX == null || !this.abu()) {
            this.bBX = this.getCollection();
        }
        return this.bBX;
    }
}

