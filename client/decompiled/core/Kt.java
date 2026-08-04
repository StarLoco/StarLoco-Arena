/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public abstract class Kt
implements JG,
hp_1 {
    protected static Logger a = Logger.getLogger(Kt.class);
    protected acl_0 uG;
    protected final zy_0 bnK = new zy_0();

    public void b() {
        this.bnK.clear();
    }

    public void j() {
        this.bnK.clear();
    }

    public boolean b(aak_2 aak_22) {
        Byte by = this.bnK.H(aak_22.lV());
        return by != null && by != 0;
    }

    public byte c(aak_2 aak_22) {
        Byte by = this.bnK.H(aak_22.lV());
        if (by == null) {
            return 0;
        }
        return by;
    }

    public void a(aak_2 aak_22, byte by) {
        this.bnK.e(aak_22.lV(), by);
    }

    public byte g(aak_2 aak_22) {
        if (this.bnK.K(aak_22.lV())) {
            byte by = (byte)(this.bnK.H(aak_22.lV()) + 1);
            this.bnK.e(aak_22.lV(), by);
            return by;
        }
        this.bnK.e(aak_22.lV(), (byte)1);
        return 1;
    }

    public byte[] WD() {
        return this.bnK.GF();
    }

    public byte h(aak_2 aak_22) {
        if (this.bnK.K(aak_22.lV())) {
            byte by = (byte)(this.bnK.H(aak_22.lV()) - 1);
            if (by <= 0) {
                this.bnK.I(aak_22.lV());
                return 0;
            }
            this.bnK.e(aak_22.lV(), by);
            return by;
        }
        return 0;
    }

    public void i(aak_2 aak_22) {
        this.bnK.I(aak_22.lV());
    }

    public void reset() {
        this.bnK.clear();
    }

    public zy_0 WE() {
        return this.bnK;
    }

    public boolean isEmpty() {
        return this.bnK.isEmpty();
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)("Exception dans le release de " + this.getClass().toString() + " normalement impossible"));
            }
            this.uG = null;
        } else {
            a.error((Object)("Double release de " + this.getClass().toString()));
            this.j();
        }
    }
}

