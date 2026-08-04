/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aiX
 */
public abstract class aix_2
extends qd_0 {
    private String GS;
    private String GT;
    private boolean czy;
    private NM bcs;

    public String qc() {
        return this.GS;
    }

    public void aQ(String string) {
        this.GS = string;
    }

    public String getPassword() {
        return this.GT;
    }

    public void setPassword(String string) {
        this.GT = string;
    }

    public boolean ayH() {
        return this.czy;
    }

    public void dB(boolean bl2) {
        this.czy = bl2;
    }

    public NM RZ() {
        return this.bcs;
    }

    public void a(NM nM) {
        this.bcs = nM;
    }

    public void vM() {
        super.vM();
        axL.aKF().reset();
    }

    public void cleanUp() {
        super.cleanUp();
        this.GS = null;
        this.GT = null;
        this.czy = false;
        this.bcs = null;
    }

    public boolean a(yp_1 yp_12) {
        azX azX2;
        if (this.RZ() != null && (azX2 = this.RZ().aaT()) != null) {
            try {
                a.info((Object)("Connexion au proxy :" + azX2.getHost() + ":" + azX2.aMz()));
                return yp_12.c(azX2.getHost(), azX2.aMz());
            }
            catch (Exception exception) {
                a.error((Object)"connect :", (Throwable)exception);
            }
        }
        this.ayL();
        a.error((Object)"Aucun proxy n'est disponible");
        return false;
    }

    public void ayI() {
        if (!this.czy && this.vJ() != null && this.vJ().Ku().isConnected()) {
            this.ayM();
        }
    }

    public void ayJ() {
        if (this.czy && this.vJ() != null && this.vJ().Ku().isConnected()) {
            this.ayN();
        }
    }

    public void ayK() {
        this.ayO();
        a.error((Object)"La m\u00e9thode gotoWorldSelection() ne devrait plus \u00eatre utilis\u00e9e.");
    }

    public void quit() {
        this.ayP();
    }

    protected abstract void ayL();

    protected abstract void ayM();

    protected abstract void ayN();

    protected abstract void ayO();

    protected abstract void ayP();
}

