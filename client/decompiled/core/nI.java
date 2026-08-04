/*
 * Decompiled with CFR 0.152.
 */
public abstract class nI
extends axw {
    public void j() {
        super.j();
    }

    public void b() {
        super.b();
    }

    public cn_0 gU() {
        return this.djH;
    }

    public void b(alp_0 alp_02) {
        this.j(alp_02);
        if (this.gU() == null) {
            a.error((Object)("Impossible d'ajouter le fighter " + alp_02 + " dans le match d'id " + this.aW + " : Timeline \u00e9gal \u00e0 null."));
            return;
        }
        if (!alp_02.PR()) {
            this.gU().b(alp_02.getId(), true);
        }
    }

    public void a(long l2, boolean bl2) {
        alp_0 alp_02 = this.eg(l2);
        if (alp_02 != null) {
            this.gU().b(alp_02.getId(), bl2);
        }
    }

    public void a(long l2, boolean bl2, boolean bl3) {
        this.a(l2, bl2);
    }

    public void sB() {
        if (this.gU() != null && this.gU().isRunning()) {
            this.gU().JJ();
        }
    }

    public boolean c(alp_0 alp_02) {
        if (!this.sC()) {
            return false;
        }
        boolean bl2 = this.gU().bm(alp_02.getId());
        if (!bl2) {
            a.error((Object)("Impossible de d\u00e9buter le tour du fighter " + alp_02 + " dans le match d'id " + this.aW + "."));
        }
        return bl2;
    }

    public void d(alp_0 alp_02) {
        if (!this.sC() || this.gU().bj(alp_02.getId()) < -1) {
            return;
        }
        while (!this.gU().bm(alp_02.getId())) {
            a.error((Object)("D\u00e9synchronisation de timeline client/serveur sur le fighter " + alp_02 + " dans le match d'id " + this.aW + "."));
            ((jm_0)this.gU()).nX();
        }
    }

    protected boolean sC() {
        if (this.gU() == null || !this.gU().isRunning()) {
            a.error((Object)("Impossible de continuer le traitement d'un tour dans le match d'id " + this.aW + " : Timeline \u00e9gal \u00e0 null ou stop\u00e9e."));
            return false;
        }
        return true;
    }

    public boolean e(alp_0 alp_02) {
        return alp_02 != null && this.ap(alp_02.getId());
    }

    public boolean ap(long l2) {
        if (!this.sC()) {
            return false;
        }
        boolean bl2 = this.gU().X(l2);
        if (!bl2) {
            a.error((Object)("Impossible de terminer le tour du fighter d'id " + l2 + " dans le match d'id " + this.aW + "."));
        }
        return bl2;
    }

    protected void aq(long l2) {
        alp_0 alp_02 = this.eg(l2);
        if (alp_02 != null) {
            this.f(alp_02);
        }
    }

    protected void f(alp_0 alp_02) {
        alp_02.a(new ajz_2());
    }

    protected void ar(long l2) {
        alp_0 alp_02 = this.eg(l2);
        if (alp_02 != null) {
            this.g(alp_02);
        }
    }

    protected void g(alp_0 alp_02) {
        alp_02.a(new aML());
    }

    public void sD() {
    }

    public void sE() {
    }
}

