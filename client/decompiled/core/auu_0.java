/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

/*
 * Renamed from auu
 */
public abstract class auu_0
extends avg
implements Cloneable,
kk_1 {
    private TP cWf = new TP();
    private Vector cWg = new Vector();
    private Vector cWh = new Vector();
    private File bAd;
    private boolean cWi = true;
    private boolean caseSensitive = true;
    private boolean cWj = true;
    private boolean cWk = true;
    private abs_0 cWl = null;

    public auu_0() {
    }

    protected auu_0(auu_0 auu_02) {
        this.bAd = auu_02.bAd;
        this.cWf = auu_02.cWf;
        this.cWg = auu_02.cWg;
        this.cWh = auu_02.cWh;
        this.cWi = auu_02.cWi;
        this.caseSensitive = auu_02.caseSensitive;
        this.cWj = auu_02.cWj;
        this.cWk = auu_02.cWk;
        this.l(auu_02.TP());
    }

    public void a(awq_0 awq_02) {
        if (this.bAd != null || this.cWf.u(this.TP())) {
            throw this.aIh();
        }
        if (!this.cWg.isEmpty()) {
            throw this.aIi();
        }
        if (!this.cWh.isEmpty()) {
            throw this.aIi();
        }
        super.a(awq_02);
    }

    public synchronized void x(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.bAd = file;
        this.cWl = null;
    }

    public File aHv() {
        return this.o(this.TP());
    }

    public synchronized File o(UI uI) {
        return this.aId() ? this.G(uI).o(uI) : this.bAd;
    }

    public synchronized TP aHw() {
        if (this.aId()) {
            throw this.aIi();
        }
        TP tP = new TP();
        this.cWg.addElement(tP);
        this.cWl = null;
        return tP;
    }

    public synchronized bM agj() {
        if (this.aId()) {
            throw this.aIi();
        }
        this.cWl = null;
        return this.cWf.agj();
    }

    public synchronized bM agk() {
        if (this.aId()) {
            throw this.aIi();
        }
        this.cWl = null;
        return this.cWf.agk();
    }

    public synchronized bM agl() {
        if (this.aId()) {
            throw this.aIi();
        }
        this.cWl = null;
        return this.cWf.agl();
    }

    public synchronized bM agm() {
        if (this.aId()) {
            throw this.aIi();
        }
        this.cWl = null;
        return this.cWf.agm();
    }

    public synchronized void e(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.x(file.getParentFile());
        this.agj().setName(file.getName());
    }

    public synchronized void fT(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.cWf.fT(string);
        this.cWl = null;
    }

    public synchronized void v(String[] stringArray) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (stringArray != null) {
            for (int j = 0; j < stringArray.length; ++j) {
                this.cWf.agj().setName(stringArray[j]);
            }
            this.cWl = null;
        }
    }

    public synchronized void fU(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.cWf.fU(string);
        this.cWl = null;
    }

    public synchronized void w(String[] stringArray) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (stringArray != null) {
            for (int j = 0; j < stringArray.length; ++j) {
                this.cWf.agl().setName(stringArray[j]);
            }
            this.cWl = null;
        }
    }

    public synchronized void y(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.cWf.y(file);
        this.cWl = null;
    }

    public synchronized void z(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.cWf.z(file);
        this.cWl = null;
    }

    public synchronized void eh(boolean bl2) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.cWi = bl2;
        this.cWl = null;
    }

    public synchronized boolean aHx() {
        return this.aId() ? this.G(this.TP()).aHx() : this.cWi;
    }

    public synchronized void setCaseSensitive(boolean bl2) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.caseSensitive = bl2;
        this.cWl = null;
    }

    public synchronized boolean aHy() {
        return this.aId() ? this.G(this.TP()).aHy() : this.caseSensitive;
    }

    public synchronized void ei(boolean bl2) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.cWj = bl2;
        this.cWl = null;
    }

    public synchronized boolean aHz() {
        return this.aId() ? this.G(this.TP()).aHz() : this.cWj;
    }

    public void ej(boolean bl2) {
        this.cWk = bl2;
    }

    public abs_0 aHA() {
        return this.F(this.TP());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public abs_0 F(UI uI) {
        if (this.aId()) {
            return this.G(uI).F(uI);
        }
        abs_0 abs_02 = null;
        auu_0 auu_02 = this;
        synchronized (auu_02) {
            if (this.cWl != null && uI == this.TP()) {
                abs_02 = this.cWl;
            } else {
                if (this.bAd == null) {
                    throw new eq_2("No directory specified for " + this.aIe() + ".");
                }
                if (!this.bAd.exists() && this.cWk) {
                    throw new eq_2(this.bAd.getAbsolutePath() + " not found.");
                }
                if (!this.bAd.isDirectory() && this.bAd.exists()) {
                    throw new eq_2(this.bAd.getAbsolutePath() + " is not a directory.");
                }
                abs_02 = new abs_0();
                this.a(abs_02, uI);
                abs_02.ei(this.cWj);
                abs_02.ej(this.cWk);
                this.cWl = uI == this.TP() ? abs_02 : this.cWl;
            }
        }
        abs_02.kA();
        return abs_02;
    }

    public void a(hb_1 hb_12) {
        this.a(hb_12, this.TP());
    }

    public synchronized void a(hb_1 hb_12, UI uI) {
        if (this.aId()) {
            this.G(uI).a(hb_12, uI);
            return;
        }
        if (hb_12 == null) {
            throw new IllegalArgumentException("ds cannot be null");
        }
        hb_12.f(this.bAd);
        TP tP = this.J(uI);
        uI.l(this.aIe() + ": Setup scanner in dir " + this.bAd + " with " + tP, 4);
        hb_12.d(tP.s(uI));
        hb_12.c(tP.t(uI));
        if (hb_12 instanceof ajh_0) {
            ajh_0 ajh_02 = (ajh_0)((Object)hb_12);
            ajh_02.a(this.k(uI));
        }
        if (this.cWi) {
            hb_12.ks();
        }
        hb_12.setCaseSensitive(this.caseSensitive);
    }

    protected auu_0 G(UI uI) {
        return (auu_0)this.O(uI);
    }

    public synchronized boolean lp() {
        return this.aId() && this.TP() != null ? this.G(this.TP()).lp() : !this.cWh.isEmpty();
    }

    public synchronized boolean aHB() {
        if (this.aId() && this.TP() != null) {
            return this.G(this.TP()).aHB();
        }
        if (this.cWf.u(this.TP())) {
            return true;
        }
        Enumeration enumeration = this.cWg.elements();
        while (enumeration.hasMoreElements()) {
            TP tP = (TP)enumeration.nextElement();
            if (!tP.u(this.TP())) continue;
            return true;
        }
        return false;
    }

    public synchronized int lq() {
        return this.aId() && this.TP() != null ? this.G(this.TP()).lq() : this.cWh.size();
    }

    public synchronized R[] k(UI uI) {
        return this.aId() ? this.G(uI).k(uI) : this.cWh.toArray(new R[this.cWh.size()]);
    }

    public synchronized Enumeration lr() {
        return this.aId() && this.TP() != null ? this.G(this.TP()).lr() : this.cWh.elements();
    }

    public synchronized void a(R r) {
        if (this.aId()) {
            throw this.aIi();
        }
        this.cWh.addElement(r);
        this.cWl = null;
    }

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

    public void a(aja_0 aja_02) {
        this.a((R)aja_02);
    }

    public void a(hd_2 hd_22) {
        this.a((R)hd_22);
    }

    public void a(vi vi2) {
        this.a((R)vi2);
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

    public void a(bp_2 bp_22) {
        this.a((R)bp_22);
    }

    public void a(afk_1 afk_12) {
        this.a((R)afk_12);
    }

    public void b(R r) {
        this.a(r);
    }

    public String toString() {
        abs_0 abs_02 = this.F(this.TP());
        String[] stringArray = abs_02.kx();
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < stringArray.length; ++j) {
            if (j > 0) {
                stringBuffer.append(';');
            }
            stringBuffer.append(stringArray[j]);
        }
        return stringBuffer.toString();
    }

    public synchronized Object clone() {
        if (this.aId()) {
            return this.G(this.TP()).clone();
        }
        try {
            auu_0 auu_02 = (auu_0)super.clone();
            auu_02.cWf = (TP)this.cWf.clone();
            auu_02.cWg = new Vector(this.cWg.size());
            Enumeration enumeration = this.cWg.elements();
            while (enumeration.hasMoreElements()) {
                auu_02.cWg.addElement(((TP)enumeration.nextElement()).clone());
            }
            auu_02.cWh = new Vector(this.cWh);
            return auu_02;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new eq_2(cloneNotSupportedException);
        }
    }

    public String[] H(UI uI) {
        return this.J(uI).s(uI);
    }

    public String[] I(UI uI) {
        return this.J(uI).t(uI);
    }

    public synchronized TP J(UI uI) {
        if (this.aId()) {
            return this.G(uI).J(uI);
        }
        TP tP = (TP)this.cWf.clone();
        int n2 = this.cWg.size();
        for (int j = 0; j < n2; ++j) {
            Object e = this.cWg.elementAt(j);
            tP.a((TP)e, uI);
        }
        return tP;
    }
}

