/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

/*
 * Renamed from aen
 */
public abstract class aen_2
extends hx_1
implements kk_1 {
    private Vector coq = new Vector();

    public boolean lp() {
        return !this.coq.isEmpty();
    }

    public int lq() {
        return this.coq.size();
    }

    public R[] k(UI uI) {
        Object[] objectArray = new R[this.coq.size()];
        this.coq.copyInto(objectArray);
        return objectArray;
    }

    public Enumeration lr() {
        return this.coq.elements();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumeration = this.lr();
        if (enumeration.hasMoreElements()) {
            while (enumeration.hasMoreElements()) {
                stringBuffer.append(enumeration.nextElement().toString());
                if (!enumeration.hasMoreElements()) continue;
                stringBuffer.append(", ");
            }
        }
        return stringBuffer.toString();
    }

    public void a(R r) {
        this.coq.addElement(r);
    }

    public void validate() {
        this.dQ();
        String string = this.getError();
        if (string != null) {
            throw new eq_2(string);
        }
        Enumeration enumeration = this.lr();
        while (enumeration.hasMoreElements()) {
            Object e = enumeration.nextElement();
            if (!(e instanceof hx_1)) continue;
            ((hx_1)e).validate();
        }
    }

    public abstract boolean a(File var1, String var2, File var3);

    public void a(id id2) {
        this.a((R)id2);
    }

    public void a(bw_2 bw_22) {
        this.a((R)bw_22);
    }

    public void a(azm azm2) {
        this.a((R)azm2);
    }

    public void a(wu_1 wu_12) {
        this.a((R)wu_12);
    }

    public void a(atb_0 atb_02) {
        this.a((R)atb_02);
    }

    public void a(gl_0 gl_02) {
        this.a((R)gl_02);
    }

    public void a(anb_2 anb_22) {
        this.a((R)anb_22);
    }

    public void a(de_1 de_12) {
        this.a((R)de_12);
    }

    public void a(hd_2 hd_22) {
        this.a((R)hd_22);
    }

    public void a(aok_1 aok_12) {
        this.a((R)aok_12);
    }

    public void a(aop_1 aop_12) {
        this.a((R)aop_12);
    }

    public void a(rb_1 rb_12) {
        this.a((R)rb_12);
    }

    public void a(eX eX2) {
        this.a((R)eX2);
    }

    public void a(ajp_1 ajp_12) {
        this.a((R)ajp_12);
    }

    public void a(aja_0 aja_02) {
        this.a((R)aja_02);
    }

    public void a(vi vi2) {
        this.a((R)vi2);
    }

    public void a(bp_2 bp_22) {
        this.a((R)bp_22);
    }

    public void a(afk_1 afk_12) {
        this.a((R)afk_12);
    }

    public void b(R r) {
        this.a(r);
    }
}

