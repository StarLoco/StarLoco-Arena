/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from alY
 */
public class aly_0
extends re_0 {
    protected static Logger a = Logger.getLogger(aly_0.class);
    private final yp_2 kD;
    private final boolean cFY;

    public aly_0(int n2, int n3, int n4, yp_2 yp_22, boolean bl2, boolean bl3, long l2, int n5, int n6, short s, boolean bl4) {
        super(n2, n3, n4, bl2, bl3, l2, n5, n6, s);
        this.kD = yp_22;
        this.cFY = bl4;
        this.bG(this.kD.eA());
    }

    public long oS() {
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null) {
            ee_2 ee_22 = (ee_2)adu_02.eg(this.Nl());
            if (ee_22 != null) {
                ee_2 ee_23;
                yp_2 yp_22 = (yp_2)ee_22.Oj().F(this.kD.getId());
                if (yp_22 != null) {
                    ee_22.b(yp_22);
                }
                if (!this.afI) {
                    adu_02.a((gn_0)ee_22, adu_02.q(new ry(this.aG, this.aH, this.wp)), this.kD);
                }
                if ((ee_23 = (ee_2)apN.aDK().aDL().eg(this.Nl())) != null) {
                    Object object2;
                    for (Object object2 : ee_23.Or()) {
                        object2.a(ee_23, this.kD, this.aG, this.aH);
                    }
                    ee_23.Os();
                    Object object3 = "";
                    if (this.wi()) {
                        object3 = "(" + aon_0.aYc().getString("fight.criticalHit") + ")";
                    }
                    if (this.wj()) {
                        object3 = "(" + aon_0.aYc().getString("fight.criticalMiss") + ")";
                    }
                    object2 = this.kD.jd() != null ? ((yp_2)this.kD.jd()).getName() : this.kD.getName();
                    Hv.info(aon_0.aYc().getString("fight.spellCast", ee_23.getName(), object2, object3));
                }
                if (this.cFY) {
                    return super.oS();
                }
                this.Nn();
            } else {
                a.error((Object)("Impossible de lancer un sort car le caster n'existe pas : " + this.Nl()));
            }
        }
        return -1L;
    }
}

