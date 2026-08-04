/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Enumeration;

public class id
extends aen_2 {
    private String xd;
    private String xe;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.lp()) {
            stringBuffer.append("{select");
            if (this.xd != null) {
                stringBuffer.append(" if: ");
                stringBuffer.append(this.xd);
            }
            if (this.xe != null) {
                stringBuffer.append(" unless: ");
                stringBuffer.append(this.xe);
            }
            stringBuffer.append(" ");
            stringBuffer.append(super.toString());
            stringBuffer.append("}");
        }
        return stringBuffer.toString();
    }

    private id lo() {
        Object object = this.k(this.getClass(), "SelectSelector");
        return (id)object;
    }

    public boolean lp() {
        if (this.aId()) {
            return this.lo().lp();
        }
        return super.lp();
    }

    public int lq() {
        if (this.aId()) {
            return this.lo().lq();
        }
        return super.lq();
    }

    public R[] k(UI uI) {
        if (this.aId()) {
            return this.lo().k(uI);
        }
        return super.k(uI);
    }

    public Enumeration lr() {
        if (this.aId()) {
            return this.lo().lr();
        }
        return super.lr();
    }

    public void a(R r) {
        if (this.aId()) {
            throw this.aIi();
        }
        super.a(r);
    }

    public void dQ() {
        int n2 = this.lq();
        if (n2 < 0 || n2 > 1) {
            this.eC("Only one selector is allowed within the <selector> tag");
        }
    }

    public boolean ls() {
        if (this.xd != null && this.TP().getProperty(this.xd) == null) {
            return false;
        }
        return this.xe == null || this.TP().getProperty(this.xe) == null;
    }

    public void w(String string) {
        this.xd = string;
    }

    public void x(String string) {
        this.xe = string;
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        if (!this.ls()) {
            return false;
        }
        Enumeration enumeration = this.lr();
        if (!enumeration.hasMoreElements()) {
            return true;
        }
        R r = (R)enumeration.nextElement();
        return r.a(file, string, file2);
    }
}

