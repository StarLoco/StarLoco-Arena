/*
 * Decompiled with CFR 0.152.
 */
import java.util.ListIterator;
import java.util.Stack;

public abstract class OK {
    private static final int bCt = 100;
    private Stack bCu = new Stack();
    private ListIterator bCv;

    public void clear() {
        this.bCu.clear();
    }

    public String abD() {
        if (this.bCv != null && this.bCv.hasPrevious()) {
            return (String)this.bCv.previous();
        }
        return "";
    }

    public String abE() {
        if (this.bCv != null && this.bCv.hasNext()) {
            return (String)this.bCv.next();
        }
        return "";
    }

    protected void fz(String string) {
        if (!this.bCu.isEmpty()) {
            String string2 = (String)this.bCu.lastElement();
            if (string2 == null || !string2.equals(string)) {
                if (this.bCu.size() >= 100) {
                    this.bCu.remove(this.bCu.lastElement());
                }
                this.bCu.push(string);
            }
        } else {
            this.bCu.push(string);
        }
        this.bCv = this.bCu.listIterator(this.bCu.size());
    }
}

