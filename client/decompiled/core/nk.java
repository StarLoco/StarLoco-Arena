/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

public abstract class nk {
    protected static final Logger a = Logger.getLogger(nk.class);
    protected final adq_2[] Oc = new adq_2[2];
    protected final long[] Od = new long[2];
    protected final boolean[] Oe = new boolean[2];
    protected final HashMap[] Of = new HashMap[2];
    protected akh_1 Og;
    protected long Oh;
    protected long nD;

    protected nk(long l2) {
        this.nD = l2;
    }

    public long getId() {
        return this.nD;
    }

    public void an(long l2) {
        this.Oh = l2;
    }

    public long sb() {
        return this.Oh;
    }

    protected void a(adq_2 adq_22, adq_2 adq_23) {
        if (adq_22 == null) {
            throw new NullPointerException("First user of an ItemExchanger can't be null");
        }
        if (adq_23 == null) {
            throw new NullPointerException("Second user of an ItemExchanger can't be null");
        }
        this.Og = akh_1.dTq;
        this.Oc[0] = adq_22;
        this.Od[0] = adq_22.getId();
        this.Oe[0] = false;
        this.Oc[1] = adq_23;
        this.Od[1] = adq_23.getId();
        this.Oe[1] = false;
    }

    public void start() {
        if (this.Og != akh_1.dTq) {
            throw new IllegalStateException("Only an Initializing exchanger can be started. Current State: " + (Object)((Object)this.Og));
        }
        if (!this.Oc[1].ats()) {
            this.Og = akh_1.dTt;
            this.a(0, (ag_2)lq.a(this, oj_1.aaD));
            this.Oc[0].c(null);
            AM.Ht().b(this);
            return;
        }
        if (!AM.Ht().a(this)) {
            this.Og = akh_1.dTt;
            this.a(0, (ag_2)lq.a(this, oj_1.aaD));
            this.Oc[0].c(null);
            AM.Ht().b(this);
            return;
        }
        this.Oc[0].c(this);
        this.Oc[1].c(this);
        if (this.sg()) {
            this.sc();
        } else {
            this.sd();
        }
    }

    protected boolean a(adq_2 adq_22) {
        return adq_22 == this.Oc[0] || adq_22 == this.Oc[1];
    }

    protected int b(adq_2 adq_22) {
        return adq_22 == this.Oc[0] ? 0 : 1;
    }

    private void sc() {
        this.Og = akh_1.dTr;
        this.a(this.Oc[0], ag_2.a(this, i_0.aN));
        this.a(this.Oc[1], ag_2.a(this, i_0.aM));
    }

    private void sd() {
        this.Oc[1].c(this);
        this.Og = akh_1.dTs;
        this.a(ag_2.a(this, i_0.aO));
    }

    protected void se() {
        if (this.Og != akh_1.dTq) {
            this.Oc[0].c(null);
        }
        if (this.Og != akh_1.dTq) {
            this.Oc[1].c(null);
        }
        this.Og = akh_1.dTt;
        this.cl(0);
        this.cl(1);
        AM.Ht().b(this);
    }

    protected void a(ag_2 ag_22) {
        this.a(ag_22, true);
    }

    protected void a(ag_2 ag_22, boolean bl2) {
        if (this.Oc[0] != null) {
            this.Oc[0].b(ag_22);
        }
        if (this.Oc[1] != null) {
            this.Oc[1].b(ag_22);
        }
        if (bl2) {
            try {
                ag_22.release();
            }
            catch (Exception exception) {
                a.error((Object)"Exception lors de la notification d'un \u00e9v\u00e8nement aux utilisateurs d'un ItemExchanger: ", (Throwable)exception);
            }
        }
    }

    protected void a(int n2, ag_2 ag_22) {
        this.a(n2, ag_22, true);
    }

    protected void a(adq_2 adq_22, ag_2 ag_22) {
        this.a(adq_22, ag_22, true);
    }

    protected void a(int n2, ag_2 ag_22, boolean bl2) {
        if (n2 != 0 && n2 != 1) {
            a.error((Object)("Impossible d'envoyer un \u00e9v\u00e8nement \u00e0 l'utilisateur num\u00e9ro " + n2));
            return;
        }
        this.a(this.Oc[n2], ag_22, bl2);
    }

    protected void a(adq_2 adq_22, ag_2 ag_22, boolean bl2) {
        if (adq_22 != null) {
            adq_22.b(ag_22);
        }
        if (bl2) {
            try {
                ag_22.release();
            }
            catch (Exception exception) {
                a.error((Object)"Exception lors de la notification d'un \u00e9v\u00e8nement \u00e0 un user d'un ItemExchanger: ", (Throwable)exception);
            }
        }
    }

    public akh_1 sf() {
        return this.Og;
    }

    protected abstract boolean sg();

    protected abstract boolean sh();

    public void c(adq_2 adq_22) {
        if (!this.a(adq_22)) {
            return;
        }
        this.sd();
    }

    public void a(adq_2 adq_22, byte by) {
        if (!this.a(adq_22)) {
            return;
        }
        byte by2 = this.h(adq_22);
        byte by3 = (byte)(by2 != 1 ? 1 : 0);
        lq lq2 = lq.a(this, oj_1.aaE);
        lq2.N(by2);
        lq lq3 = lq.a(this, oj_1.aaF);
        lq3.N(by2);
        this.Og = akh_1.dTt;
        this.a((int)by3, (ag_2)lq3);
        this.a((int)by2, (ag_2)lq2);
        this.se();
    }

    public void d(adq_2 adq_22) {
        if (!this.a(adq_22)) {
            a.warn((Object)("Impossible d'annuler l'\u00e9change, " + adq_22.getName() + " n'est pas concern\u00e9"));
            return;
        }
        this.Og = akh_1.dTt;
        this.a(0, (ag_2)lq.a(this, oj_1.aaD));
        this.a(1, (ag_2)lq.a(this, oj_1.aaE));
        this.se();
    }

    public void e(adq_2 adq_22) {
        if (!this.a(adq_22)) {
            a.error((Object)("Impossible de retirer l'utilisateur (\"" + adq_22.getName() + "\", " + adq_22.getId() + ") de l'ItemExchangerUser : Il n'est pas concern\u00e9 par cet \u00e9change (\"" + this.Oc[0].getName() + "\", " + this.Oc[0].getId() + "/\"" + this.Oc[1].getName() + "\", " + this.Oc[1].getId() + ")."));
            return;
        }
        int n2 = this.b(adq_22);
        this.Og = akh_1.dTt;
        this.a(n2, (ag_2)lq.a(this, oj_1.aaH));
        this.a(1 - n2, (ag_2)lq.a(this, oj_1.aaG));
        this.se();
    }

    public void f(adq_2 adq_22) {
        if (!this.a(adq_22)) {
            a.error((Object)("Impossible de finir l'\u00e9change " + adq_22 + " de l'ItemExchangerUser n'est pas concern\u00e9 par cet \u00e9change"));
            return;
        }
        if (this.sj()) {
            this.sk();
            this.Og = akh_1.dTt;
            this.a(lq.a(this, oj_1.aaI));
            this.se();
        } else {
            this.e(adq_22);
        }
    }

    public void g(adq_2 adq_22) {
        if (!this.a(adq_22)) {
            a.error((Object)("Impossible de continuer l'\u00e9change " + adq_22 + " de l'ItemExchangerUser n'est pas concern\u00e9 par cet \u00e9change"));
            return;
        }
        if (this.sh()) {
            byte by = this.h(adq_22);
            boolean bl2 = this.Oe[by] = !this.Oe[by];
            if (this.Oe[0] && this.Oe[1]) {
                this.f(adq_22);
            } else {
                ag_2 ag_22 = ag_2.a(this, i_0.aQ);
                ag_22.N(by);
                this.a(ag_22);
            }
        } else {
            this.f(adq_22);
        }
    }

    public adq_2 ck(int n2) {
        if (n2 < 0 || n2 > 1) {
            return null;
        }
        return this.Oc[n2];
    }

    public byte h(adq_2 adq_22) {
        if (this.Oc[0] == adq_22) {
            return 0;
        }
        if (this.Oc[1] == adq_22) {
            return 1;
        }
        return -1;
    }

    public void a(byte by, uh_1 uh_12, short s) {
        if (s < 1) {
            a.error((Object)"On essaye d'ajouter une quantit\u00e9 n\u00e9gative ou nulle d'objets \u00e0 l'\u00e9change");
            return;
        }
        this.Oe[0] = false;
        this.Oe[1] = false;
        if (0 <= by && by < this.Of.length) {
            if (this.Of[by] == null) {
                this.Of[by] = new HashMap();
            }
            if (this.Of[by].containsKey(uh_12.je())) {
                uh_1 uh_13 = (uh_1)this.Of[by].get(uh_12.je());
                if (this.si() && uh_13.hG() + s > uh_12.hG()) {
                    a.info((Object)"On essaye d'ajouter plus de carte qu'il n'en a de disponible dans un \u00e9change");
                    return;
                }
                uh_13.w(s);
            } else {
                if (this.si() && s > uh_12.hG()) {
                    a.error((Object)"On essaye d'\u00e9changer plus de carte qu'il n'en a de disponible");
                    return;
                }
                uh_1 uh_14 = uh_12.jh();
                uh_14.q(s);
                this.Of[by].put(uh_12.je(), uh_14);
            }
            this.a(aak.a(this, acv_1.ckq, by, uh_12, s));
        }
    }

    protected boolean si() {
        return true;
    }

    public void a(byte by, int n2, short s) {
        if (this.Of[by] != null) {
            for (uh_1 uh_12 : this.Of[by].values()) {
                if (uh_12.jf() != n2) continue;
                this.b(by, uh_12, s);
                return;
            }
        }
        a.error((Object)"On demande de supprimer un objet d'un \u00e9change alors qu'il n'y a aucun objet poss\u00e9dant cette id de r\u00e9f\u00e9rence.");
    }

    public void b(byte by, uh_1 uh_12, short s) {
        if (by < 0 || this.Of.length <= by) {
            a.error((Object)"On essaye de retirer un objet de la liste dont l'index n'est pas valide.");
            return;
        }
        if (s < 1) {
            a.error((Object)"On essaye de retirer une quantit\u00e9 n\u00e9gative ou nulle d'objets \u00e0 l'\u00e9change");
            return;
        }
        this.Oe[0] = false;
        this.Oe[1] = false;
        if (this.Of[by] == null || !this.Of[by].containsKey(uh_12.je())) {
            a.error((Object)"On essaye de retirer un objet de l'\u00e9change qui n'existe pas");
            return;
        }
        uh_1 uh_13 = (uh_1)this.Of[by].get(uh_12.je());
        if (uh_13.hG() < s) {
            a.error((Object)"On essaie de retirer plus d'objets qu'il n'y en a dans l'\u00e9change");
        } else if (uh_13.hG() == s) {
            uh_13.release();
            this.Of[by].remove(uh_12.je());
        } else {
            uh_13.w(-s);
        }
        this.a(aak.a(this, acv_1.ckr, by, uh_12, s));
    }

    public boolean a(byte by, uh_1 uh_12) {
        if (by < 0 || this.Of.length <= by) {
            a.error((Object)"On essaye de retirer un objet de la liste dont l'index n'est pas valide.");
            return false;
        }
        if (this.Of[by] == null) {
            a.error((Object)"On essaye de retirer un objet de l'\u00e9change qui n'existe pas");
            return false;
        }
        return this.Of[by].containsKey(uh_12.je());
    }

    private void cl(int n2) {
        if (this.Of[n2] != null) {
            for (uh_1 uh_12 : this.Of[n2].values()) {
                uh_12.release();
            }
        }
    }

    protected abstract boolean sj();

    protected abstract void sk();

    public void i(adq_2 adq_22) {
        switch (this.sf()) {
            case dTs: {
                this.e(adq_22);
                break;
            }
            case dTr: {
                this.a(adq_22, (byte)0);
            }
        }
    }

    public uh_1 b(byte by, int n2) {
        if (this.Of[by] != null) {
            for (uh_1 uh_12 : this.Of[by].values()) {
                if (uh_12.jf() != n2) continue;
                return uh_12;
            }
        }
        return null;
    }
}

