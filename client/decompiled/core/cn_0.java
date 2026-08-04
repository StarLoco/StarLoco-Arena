/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from Cn
 */
public abstract class cn_0
implements PA {
    protected static final Logger a = Logger.getLogger(cn_0.class);
    protected int aFf;
    protected bg_1 aLo;
    protected OZ aLp;
    private ahl_1 auj;
    protected short NC;
    private int aLq = 0;
    protected int aLr;
    private boolean cX;
    protected anz_2 aLs = anz_2.dZu;
    protected boolean aLt;
    private long aLu;

    protected cn_0(int n2, bg_1 bg_12, OZ oZ, ahl_1 ahl_12) {
        this.aFf = n2;
        this.aLo = bg_12;
        this.aLp = oZ;
        this.auj = ahl_12;
    }

    protected cn_0(OZ oZ) {
        this(0, new aGT(), oZ, null);
    }

    protected void a(ahl_1 ahl_12) {
        this.auj = ahl_12;
        this.aLo.a(new we_2(ahl_12));
    }

    public void eP(int n2) {
        this.aLr = n2;
    }

    public int JD() {
        return this.aLr;
    }

    public void start() {
        this.cX = true;
    }

    public void stop() {
        this.cX = false;
    }

    public boolean isRunning() {
        return this.cX;
    }

    public void b(long l2, boolean bl2) {
        if (bl2) {
            this.aLo.a(l2, this.NC);
        } else {
            this.aLo.b(l2, this.NC);
        }
        this.ab(l2);
    }

    public void Y(long l2) {
        if (this.isRunning()) {
            if (this.aLo.r(l2) && (this.aLs == anz_2.dZy || this.aLs == anz_2.dZv || this.aLs == anz_2.dZx)) {
                this.bh(l2);
            }
            this.aLo.l(l2);
        }
        this.ac(l2);
    }

    private void bh(long l2) {
        this.aLt = true;
        this.aLu = l2;
    }

    public boolean JE() {
        return this.aLt;
    }

    private void JF() {
        this.aLt = false;
        this.aLu = 0L;
    }

    protected qa_2 CA() {
        return this.aLo.do();
    }

    public boolean du() {
        return this.aLo.du() || this.aLt;
    }

    public boolean r(long l2) {
        if (this.aLt) {
            return this.aLu == l2;
        }
        return this.aLo.r(l2);
    }

    public boolean bi(long l2) {
        if (this.aLs != anz_2.dZv) {
            return false;
        }
        if (this.aLt) {
            return this.aLu == l2;
        }
        return this.aLo.r(l2);
    }

    public long JG() {
        return this.aLt ? this.aLu : this.aLo.dh();
    }

    public int JH() {
        return this.aLo.dg() + (this.aLt ? (byte)1 : 0);
    }

    public int bj(long l2) {
        return this.bk(l2);
    }

    public int bk(long l2) {
        return this.aLo.do().cw(l2);
    }

    public int bl(long l2) {
        return this.aLo.dp().cw(l2);
    }

    public void dl() {
        this.aLo.dl();
    }

    public void Cz() {
        this.aLo.dn();
    }

    public short JI() {
        return this.NC;
    }

    public void JJ() {
        this.bc(false);
    }

    public void bc(boolean bl2) {
        if (!this.isRunning()) {
            return;
        }
        if (this.NC == 0) {
            if (this.aLs != anz_2.dZu) {
                return;
            }
            this.aLs = anz_2.dZw;
        } else {
            if (!bl2 && !this.JR()) {
                return;
            }
            this.a(wj_1.ajl());
        }
        this.dm();
    }

    public void dm() {
        if (!this.isRunning()) {
            return;
        }
        this.NC = (short)(this.NC + 1);
        this.aLo.dm();
        this.a(ayA.aLB());
        if (!this.isRunning()) {
            return;
        }
        this.JP();
    }

    public int JK() {
        return this.aLq;
    }

    public boolean bm(long l2) {
        if (!this.br(l2)) {
            return false;
        }
        this.JF();
        this.aLs = anz_2.dZy;
        this.aLo.dj();
        this.JQ();
        this.c(this.aLo.dr());
        if (!this.isRunning()) {
            return false;
        }
        if (this.aLs != anz_2.dZy) {
            return true;
        }
        this.aLs = anz_2.dZv;
        ++this.aLq;
        this.bp(l2);
        return true;
    }

    public boolean X(long l2) {
        if (this.aLs == anz_2.dZx) {
            return true;
        }
        if (!this.aLt) {
            if (!this.bs(l2)) {
                return false;
            }
            this.aLs = anz_2.dZx;
            this.c(this.aLo.ds());
            this.aLo.dt();
        } else if (this.aLu != l2) {
            return false;
        }
        if (!this.isRunning()) {
            return false;
        }
        if (this.aLs == anz_2.dZy) {
            this.bn(l2);
            return true;
        }
        this.aLs = anz_2.dZw;
        this.bq(l2);
        return true;
    }

    public void bn(long l2) {
        this.aLs = anz_2.dZw;
        this.Z(l2);
    }

    public OZ JL() {
        return this.aLp;
    }

    public akv_0 a(atD atD2, arm_0 arm_02) {
        if (this.b(atD2, arm_02)) {
            return akv_0.aVB();
        }
        long l2 = atD2.TH();
        return this.a(atD2, arm_02, l2);
    }

    protected akv_0 a(atD atD2, arm_0 arm_02, long l2) {
        boolean bl2 = this.bo(l2);
        return this.aLo.a(atD2, arm_02, bl2);
    }

    public void dq() {
        this.aLo.dq();
    }

    private boolean b(atD atD2, arm_0 arm_02) {
        boolean bl2 = arm_02.aEp();
        if (bl2) {
            this.a(atD2);
        }
        return bl2;
    }

    protected boolean bo(long l2) {
        return this.aLo.r(l2) && this.aLs == anz_2.dZv;
    }

    public nc_2 JM() {
        return new nc_2(this.JH(), this.JI(), false);
    }

    public short a(akv_0 akv_02) {
        if (!this.aLo.m(akv_02.K())) {
            return -1;
        }
        return this.aLo.a(akv_02);
    }

    public int w() {
        return 11 + this.aLo.w();
    }

    public byte[] JN() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.w());
        this.z(byteBuffer);
        return byteBuffer.array();
    }

    protected void z(ByteBuffer byteBuffer) {
        byteBuffer.putShort(this.NC);
        byteBuffer.putInt(this.aLq);
        byteBuffer.put(this.aLs.lV());
        byteBuffer.putInt(this.aLr);
        this.aLo.c(byteBuffer);
    }

    public void a(ea_0 ea_02, byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.JO();
        ahh_0 ahh_02 = this.a(ea_02);
        this.a(ahh_02, byteBuffer);
    }

    protected ahh_0 a(ea_0 ea_02) {
        return new ahh_0(ea_02, this.auj);
    }

    protected void a(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        this.NC = byteBuffer.getShort();
        this.aLq = byteBuffer.getInt();
        byte by = byteBuffer.get();
        this.aLs = anz_2.bu(by);
        this.aLr = byteBuffer.getInt();
        this.aLo.clear();
        this.aLo.a(ahh_02, byteBuffer);
    }

    protected void JO() {
        this.stop();
        this.aLo.clear();
    }

    protected void a(aE aE2) {
        if (aE2 == null) {
            a.error((Object)"On ne peut pas envoyer un timeEvent null");
            return;
        }
        if (this.aLp == null) {
            a.error((Object)"Pas de TimeEventHandler sur la timeline");
            return;
        }
        aE2.a(this.aLp);
    }

    protected abstract void Z(long var1);

    protected abstract void aa(long var1);

    protected abstract void JP();

    protected abstract void ab(long var1);

    protected abstract void ac(long var1);

    protected abstract void JQ();

    private void c(Iterator iterator) {
        while (this.isRunning() && iterator.hasNext()) {
            atD atD2 = (atD)iterator.next();
            this.a(atD2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bp(long l2) {
        als_1 als_12 = als_1.dP(l2);
        try {
            this.a(als_12);
        }
        finally {
            als_12.release();
        }
        if (this.aLo.r(l2)) {
            this.aa(l2);
        }
    }

    private void bq(long l2) {
        aax_0 aax_02 = aax_0.dr(l2);
        this.a(aax_02);
        aax_02.release();
        this.Z(l2);
    }

    private boolean JR() {
        if (this.aLs != anz_2.dZw) {
            a.error((Object)this.dz("Etat de la timeline incorrect : " + (Object)((Object)this.aLs) + ", attendu: " + (Object)((Object)anz_2.dZw) + " at " + bl_0.B(5)));
            return false;
        }
        if (this.aLo.di()) {
            a.error((Object)this.dz("Assertion incorrecte sur la timeline (demande de fin de tour alors que joueur suivant = " + (this.aLo.di() ? Long.valueOf(this.aLo.dk()) : "null") + ')').append(bl_0.B(16)));
            return false;
        }
        return true;
    }

    private boolean br(long l2) {
        if (this.aLs != anz_2.dZw) {
            a.error((Object)this.dz("Etat de la timeline incorrect : " + (Object)((Object)this.aLs) + ", attendu: " + (Object)((Object)anz_2.dZw) + " at " + bl_0.B(5)));
            return false;
        }
        if (!this.aLo.n(l2)) {
            a.error((Object)this.dz("Assertion incorrecte sur la timeline (joueur suivant = " + (this.aLo.di() ? Long.valueOf(this.aLo.dk()) : "null") + ", attendu = " + l2 + ')').append(bl_0.B(16)));
            return false;
        }
        return true;
    }

    private boolean bs(long l2) {
        if (this.aLs != anz_2.dZv) {
            a.error((Object)this.dz("Etat de la timeline incorrect : " + (Object)((Object)this.aLs) + ", attendu: " + (Object)((Object)anz_2.dZv)));
            return false;
        }
        if (!this.aLo.o(l2)) {
            String string = this.du() ? String.valueOf(this.JG()) : "NONE";
            a.error((Object)this.dz("Assertion incorrecte sur la timeline (joueur courant = " + string + ", attendu = " + l2 + ')').append(bl_0.B(16)));
            return false;
        }
        return true;
    }

    public int Gf() {
        return this.aFf;
    }

    public void eC(int n2) {
        this.aFf = n2;
    }

    protected StringBuilder dz(String string) {
        return new StringBuilder().append("[_TL_] fightId=").append(this.aFf).append(" - ").append(string).append(" - ").append((CharSequence)this.JS());
    }

    protected StringBuilder JS() {
        StringBuilder stringBuilder = new StringBuilder(this.aLo.toString());
        if (this.aLt) {
            stringBuilder.append(" R:").append(this.aLu);
        }
        return stringBuilder;
    }

    protected StringBuilder b(String string, Throwable throwable) {
        return this.dz(string).append(' ').append(bl_0.b(throwable));
    }
}

