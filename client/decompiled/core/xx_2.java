/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Xx
 */
public class xx_2
extends do_1 {
    protected static Logger a = Logger.getLogger(xx_2.class);
    public static xx_2 bYf = null;
    private static final String bYg = null;
    private static final String bYh = "null";
    private static final int bYi = 1;
    private static final String bYj = ";";
    private abe_1 bYk;

    public void j() {
        super.j();
        this.amP = 0;
        this.aQv = false;
        this.mY = false;
        this.mX = false;
        this.bYk = abe_1.dsk;
    }

    public void b() {
        super.b();
        this.amP = 1;
        this.aQv = true;
        this.mY = true;
        this.mX = true;
        this.bYk = abe_1.dsk;
    }

    public String toString() {
        return this.bYk.toString();
    }

    public void gi() {
        String[] stringArray;
        super.gi();
        this.bYk = abe_1.dsl;
        if (this.cmX == bYg || this.cmX.equals(bYh) || (stringArray = this.cmX.split(bYj)).length != 1) {
            a.error((Object)("[GameDesign] Impossible d'initialiser le laboratoire de fusion d'id " + this.nD + " : Mauvais nombre de param\u00e8tres, diff\u00e9rent de " + 1 + "."));
        } else {
            try {
                long l2 = Long.valueOf(stringArray[0].trim());
                abe_1 abe_12 = CN.by(l2);
                if (abe_12 == abe_1.dsk) {
                    a.error((Object)("[GameDesign] Impossible d'initialiser le laboratoire de fusion d'id " + this.nD + " : D\u00e9finition de laboratoire de fusion d'id " + l2 + " non trouv\u00e9e."));
                } else {
                    this.bYk = abe_12;
                }
            }
            catch (Exception exception) {
                a.error((Object)("[GameDesign] Impossible d'initialiser le laboratoire de fusion d'id " + this.nD + " : "), (Throwable)exception);
            }
        }
    }

    public String getName() {
        return aon_0.aYc().getString("fusionLab");
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    public avr_0 dR() {
        return avr_0.dgg;
    }

    public avr_0[] dS() {
        return new avr_0[]{avr_0.dgg};
    }

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02 + "."));
        if (avr_02 == avr_0.dgg && this.gA()) {
            this.b(avr_02);
            this.aYY();
            this.a(avr_02);
            sb_0 sb_02 = new sb_0();
            sb_02.f(20013);
            acu_1.ara().c(sb_02);
            azs_0.aLV().g("fusionTrade", new ajt_1(this.bYk));
        }
        return true;
    }

    static /* synthetic */ void a(xx_2 xx_22, ym_0 ym_02) {
        xx_22.a(ym_02);
    }
}

