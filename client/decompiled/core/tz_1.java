/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from tZ
 */
public class tz_1
extends re_0 {
    protected static Logger a = Logger.getLogger(aly_0.class);
    private ve_0 aor;

    public tz_1(int n2, int n3, int n4, ve_0 ve_02, boolean bl2, boolean bl3, long l2, int n5, int n6, short s) {
        super(n2, n3, n4, bl2, bl3, l2, n5, n6, s);
        this.aor = ve_02;
        this.bG(this.aor.eA());
    }

    public long oS() {
        ee_2 ee_22;
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null && (ee_22 = (ee_2)apN.aDK().aDL().eg(this.Nl())) != null) {
            String string = "";
            if (this.wi()) {
                string = "(" + aon_0.aYc().getString("fight.criticalHit") + ")";
            }
            if (this.wj()) {
                string = "(" + aon_0.aYc().getString("fight.criticalMiss") + ")";
            }
            Hv.info(aon_0.aYc().getString("fight.cardUse", ee_22.getName(), this.aor.getName(), string));
        }
        return super.oS();
    }
}

