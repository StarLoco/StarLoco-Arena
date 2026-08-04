/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aor
extends kr_0 {
    private final boolean Bg;

    public aor(int n2, int n3, int n4, boolean bl2, long l2) {
        super(n2, n3, n4);
        this.Bg = bl2;
        this.a(Wz.ajg());
        this.a(gp_2.Sb());
        this.a(new aAZ(this));
        er_1 er_12 = (er_1)ame_1.aWP().eN(l2);
        if (er_12 != null) {
            this.bG(er_12.eA());
        } else {
            er_12 = (er_1)ame_1.aWP().eO(l2);
            if (er_12 != null) {
                this.bG(er_12.eA());
            } else {
                Logger.getLogger(aor.class).error((Object)("Effect area null. id=" + l2));
            }
        }
    }

    public long oS() {
        ack_1 ack_12;
        ee_2 ee_22;
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null && (ee_22 = (ee_2)apN.aDK().aDL().eg(this.mS())) != null && (ack_12 = apN.aDK().aDL().gX().bH(this.Nl())) != null) {
            if (this.Bg) {
                ack_12.k(ee_22);
                if (!ee_22.PL().b((aak_2)avx_0.deu)) {
                    return super.oS();
                }
                this.Nn();
                return -1L;
            }
            ack_12.l(ee_22);
        }
        this.Nn();
        return -1L;
    }

    protected void ax() {
    }
}

