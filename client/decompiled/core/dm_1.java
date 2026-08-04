/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;

/*
 * Renamed from Dm
 */
public abstract class dm_1
extends aat_0 {
    protected id_2 afp;
    protected String aNz;
    protected String aNA;
    protected fy_2 aft;
    private boolean invalid;
    private rs_0 aNB;

    public void a(id_2 id_22) {
        this.afp = id_22;
    }

    public id_2 LE() {
        return this.afp;
    }

    public void cW(String string) {
        this.aNz = string;
    }

    public String LF() {
        return this.aNz;
    }

    public void dE(String string) {
        this.aNA = string;
    }

    public void init() {
    }

    public void execute() {
    }

    public fy_2 LG() {
        if (this.aft == null) {
            this.aft = new fy_2(this, this.LF());
        }
        return this.aft;
    }

    public void a(fy_2 fy_22) {
        this.aft = fy_22;
    }

    public void LH() {
        if (!this.invalid) {
            if (this.aft != null) {
                this.aft.m(this.TP());
            }
        } else {
            this.LL();
        }
    }

    public void LI() {
        if (this.aft != null) {
            this.aft.n(this.TP());
        }
    }

    protected void dF(String string) {
        this.l(string, 2);
    }

    protected void dG(String string) {
        this.dF(string);
    }

    protected int c(byte[] byArray, int n2, int n3) {
        return this.TP().d(byArray, n2, n3);
    }

    protected void dH(String string) {
        this.l(string, 1);
    }

    protected void dI(String string) {
        this.dH(string);
    }

    public void log(String string) {
        this.l(string, 2);
    }

    public void l(String string, int n2) {
        if (this.TP() != null) {
            this.TP().a(this, string, n2);
        } else {
            super.l(string, n2);
        }
    }

    public void b(Throwable throwable, int n2) {
        if (throwable != null) {
            this.a(throwable.getMessage(), throwable, n2);
        }
    }

    public void a(String string, Throwable throwable, int n2) {
        if (this.TP() != null) {
            this.TP().a(this, string, throwable, n2);
        } else {
            super.l(string, n2);
        }
    }

    public final void perform() {
        if (!this.invalid) {
            this.TP().c(this);
            Throwable throwable = null;
            try {
                this.LH();
                fi_0.g(this);
            }
            catch (eq_2 eq_22) {
                if (eq_22.hW() == axc_0.diY) {
                    eq_22.a(this.hW());
                }
                throwable = eq_22;
                throw eq_22;
            }
            catch (Exception exception) {
                throwable = exception;
                eq_2 eq_23 = new eq_2(exception);
                eq_23.a(this.hW());
                throw eq_23;
            }
            catch (Error error) {
                throwable = error;
                throw error;
            }
            finally {
                this.TP().a(this, throwable);
            }
        } else {
            rs_0 rs_02 = this.LL();
            dm_1 dm_12 = rs_02.adN();
            dm_12.perform();
        }
    }

    final void LJ() {
        this.invalid = true;
    }

    protected final boolean LK() {
        return this.invalid;
    }

    private rs_0 LL() {
        if (this.aNB == null) {
            this.aNB = new rs_0(this.aNA);
            this.aNB.l(this.TP());
            this.aNB.dE(this.aNA);
            this.aNB.cW(this.aNz);
            this.aNB.a(this.pW);
            this.aNB.a(this.afp);
            this.aNB.a(this.aft);
            this.aft.P(this.aNB);
            this.a(this.aft, this.aNB);
            this.afp.a(this, this.aNB);
            this.aNB.LH();
        }
        return this.aNB;
    }

    private void a(fy_2 fy_22, rs_0 rs_02) {
        Enumeration enumeration = fy_22.OZ();
        while (enumeration.hasMoreElements()) {
            fy_2 fy_23 = (fy_2)enumeration.nextElement();
            rs_0 rs_03 = new rs_0(fy_23.Pb());
            rs_02.a(rs_03);
            rs_03.l(this.TP());
            rs_03.a(fy_23);
            fy_23.P(rs_03);
            this.a(fy_23, rs_03);
        }
    }

    public String LM() {
        return this.aNA;
    }

    protected fy_2 LN() {
        return this.aft;
    }

    public final void b(dm_1 dm_12) {
        this.l(dm_12.TP());
        this.a(dm_12.LE());
        this.cW(dm_12.LF());
        this.setDescription(dm_12.getDescription());
        this.a(dm_12.hW());
        this.dE(dm_12.LM());
    }
}

