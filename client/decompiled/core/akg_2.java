/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from akG
 */
public class akg_2
extends re_0 {
    private static int cDH = 8000;
    protected static Logger a = Logger.getLogger(aly_0.class);

    public akg_2(int n2, int n3, int n4, boolean bl2, boolean bl3, long l2, int n5, int n6, short s) {
        super(n2, n3, n4, bl2, bl3, l2, n5, n6, s);
        this.bG(cDH);
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
            Hv.info(aon_0.aYc().getString("fight.closeCombat", ee_22.getName(), string));
        }
        return super.oS();
    }
}

