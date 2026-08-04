/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

/*
 * Renamed from Sk
 */
public abstract class sk_1
extends avg
implements mx_2,
Cloneable {
    private static final String bLf = " expects exactly one nested resource collection.";
    private mx_2 bLg;
    private Collection bBX = null;
    private boolean bBY = true;

    public synchronized void bY(boolean bl2) {
        this.bBY = bl2;
    }

    public synchronized boolean abu() {
        return this.bBY;
    }

    public synchronized void a(mx_2 mx_22) {
        UI uI;
        if (this.aId()) {
            throw this.aIi();
        }
        if (mx_22 == null) {
            return;
        }
        if (this.bLg != null) {
            throw this.afg();
        }
        this.bLg = mx_22;
        if (UI.ar(this.bLg) == null && (uI = this.TP()) != null) {
            uI.at(this.bLg);
        }
        this.setChecked(false);
    }

    public final synchronized Iterator iterator() {
        if (this.aId()) {
            return ((sk_1)this.aIg()).iterator();
        }
        this.aIf();
        return new sw_0(this, this.abw().iterator());
    }

    public synchronized int size() {
        if (this.aId()) {
            return ((sk_1)this.aIg()).size();
        }
        this.aIf();
        return this.abw().size();
    }

    public synchronized boolean dE() {
        if (this.aId()) {
            return ((os_0)this.aIg()).dE();
        }
        this.aIf();
        if (this.bLg == null || this.bLg.dE()) {
            return true;
        }
        Iterator iterator = this.abw().iterator();
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
            if (this.bLg instanceof avg) {
                stack.push(this.bLg);
                sk_1.a((avg)((Object)this.bLg), stack, uI);
                stack.pop();
            }
            this.setChecked(true);
        }
    }

    protected final synchronized mx_2 aff() {
        this.aIf();
        if (this.bLg == null) {
            throw this.afg();
        }
        return this.bLg;
    }

    protected abstract Collection getCollection();

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

    private eq_2 afg() {
        return new eq_2(super.toString() + bLf);
    }
}

