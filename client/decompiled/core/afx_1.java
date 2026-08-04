/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/*
 * Renamed from afX
 */
public class afx_1
extends avg
implements mx_2 {
    public static final mx_2 csW = new xm_0();
    public static final Iterator hn = new xl_0();
    private Vector csX;
    private Collection bBX;
    static Class csY;

    public synchronized void a(mx_2 mx_22) {
        if (this.aId()) {
            throw this.aIi();
        }
        if (mx_22 == null) {
            return;
        }
        if (this.csX == null) {
            this.csX = new Vector();
        }
        this.csX.add(mx_22);
        sw_0.ao(this);
        this.bBX = null;
        this.setChecked(false);
    }

    public synchronized Iterator iterator() {
        if (this.aId()) {
            return this.avO().iterator();
        }
        this.validate();
        return new sw_0(this, this.bBX.iterator());
    }

    public synchronized int size() {
        if (this.aId()) {
            return this.avO().size();
        }
        this.validate();
        return this.bBX.size();
    }

    public boolean dE() {
        if (this.aId()) {
            return this.avO().dE();
        }
        this.validate();
        Iterator iterator = this.avP().iterator();
        while (iterator.hasNext()) {
            if (((mx_2)iterator.next()).dE()) continue;
            return false;
        }
        return true;
    }

    public synchronized String toString() {
        if (this.aId()) {
            return this.aIg().toString();
        }
        if (this.bBX == null || this.bBX.isEmpty()) {
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

    protected void a(Stack stack, UI uI) {
        if (this.isChecked()) {
            return;
        }
        if (this.aId()) {
            super.a(stack, uI);
        } else {
            Iterator iterator = this.avP().iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                if (!(e instanceof avg)) continue;
                afx_1.a((avg)e, stack, uI);
            }
            this.setChecked(true);
        }
    }

    private mx_2 avO() {
        return (mx_2)this.k(csY == null ? (csY = afx_1.a("Mx")) : csY, "ResourceCollection");
    }

    private synchronized void validate() {
        this.aIf();
        this.bBX = this.bBX == null ? new nl_2(this) : this.bBX;
    }

    private synchronized List avP() {
        return this.csX == null ? Collections.EMPTY_LIST : this.csX;
    }

    static List a(afx_1 afx_12) {
        return afx_12.avP();
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

