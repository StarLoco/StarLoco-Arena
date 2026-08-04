/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aDT
 */
public abstract class adt_2
implements JG,
ea_0 {
    protected static final Logger a = Logger.getLogger(adt_2.class);
    protected acl_0 uG;
    protected mv_1 bsq;

    protected adt_2() {
    }

    public void b() {
        this.bsq = null;
    }

    public void j() {
        this.bsq = null;
    }

    public mv_1 aPM() {
        return this.bsq;
    }

    public byte gY() {
        return 1;
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"ne peut arriver normalement");
            }
        } else {
            this.j();
        }
    }

    public anw_2 gS() {
        if (this.bsq != null) {
            return this.bsq;
        }
        return null;
    }

    public aii_0 gT() {
        if (this.bsq != null) {
            return this.bsq;
        }
        return null;
    }

    public cn_0 gU() {
        if (this.bsq != null) {
            return this.bsq.ZB();
        }
        return null;
    }

    public aoq_0 gV() {
        if (this.bsq == null) {
            return null;
        }
        return this.bsq.gV();
    }

    public abr_1 gW() {
        if (this.bsq != null) {
            return this.bsq;
        }
        return null;
    }

    public he_1 gX() {
        if (this.bsq != null) {
            return this.bsq.gX();
        }
        return null;
    }

    public aav_2 gR() {
        return null;
    }

    public abstract void bV(int var1, int var2);

    public afj_0 aPN() {
        return this.bsq.ZA();
    }

    public abstract azk aFF();

    public abstract bs_1 aFG();
}

