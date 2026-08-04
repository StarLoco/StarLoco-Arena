/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from qD
 */
public abstract class qd_0
extends cf_2 {
    private axp_0 aeP;
    private int gQ = 0;
    private int gP = 0;
    private byte[] aeQ;
    private byte aeR = 0;

    public qd_0() {
        this.a(new alz_2(this));
    }

    public axp_0 vJ() {
        return this.aeP;
    }

    public void a(axp_0 axp_02) {
        this.aeP = axp_02;
    }

    public void Q(int n2, int n3) {
        this.gQ = n2;
        this.gP = n3;
    }

    public byte[] vK() {
        return this.aeQ;
    }

    public void q(byte[] byArray) {
        this.aeQ = byArray;
        a.info((Object)("ticket = " + byArray));
        if (this.aeQ == null) {
            a.info((Object)"desactivate connection retry");
            this.vO();
        } else {
            a.info((Object)"activateConectionRetry");
            this.vN();
        }
    }

    public byte vL() {
        return this.aeR;
    }

    public void w(byte by) {
        a.info((Object)("Raison de la d\u00e9connection de l'entit\u00e9 " + this.getId() + " : " + by));
        this.aeR = by;
    }

    public void vM() {
        this.ig();
        this.a(new alz_2(this));
    }

    public void cleanUp() {
        a.info((Object)"cleanUp() de la ProxyClientEntity, on fait un setTicket \u00e0 null");
        this.q(null);
        this.w((byte)0);
        this.vM();
    }

    public void vN() {
        mu_0 mu_02;
        if (this.vJ() != null && (mu_02 = (mu_0)this.vJ().Ku()) != null) {
            mu_02.A(this.gQ);
            mu_02.z(this.gP);
        }
    }

    public void vO() {
        mu_0 mu_02;
        if (this.vJ() != null && (mu_02 = (mu_0)this.vJ().Ku()) != null) {
            mu_02.A(0);
            mu_02.z(0);
        }
    }

    public abstract void a(int var1, int var2, long var3);

    public abstract void vP();

    public abstract void cV(int var1);

    public abstract void r(byte[] var1);
}

