/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class oq
extends do_1 {
    private boolean Va = false;
    private int Vb;
    private short Vc;
    private short Vd;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        boolean bl2 = true;
        if (this.Vc != 0) {
            bl2 = apN.aDK().Ln().c(avq_0.ce(this.Vc));
        }
        boolean bl3 = false;
        if (this.Vd != 0) {
            bl3 = apN.aDK().Ln().c(avq_0.ce(this.Vd));
        }
        if (!this.Va && avr_02 == avr_0.dgp && this.gA() && bl2 && !bl3) {
            this.Va = true;
            this.b(avr_02);
            this.aYY();
            if (anr_0.aXN().j(this.Vb, true) != null) {
                anr_0.aXN().a(this.Vb, 0, ug_2.bQg, false);
            }
        }
        return true;
    }

    public avr_0 dR() {
        return avr_0.dgp;
    }

    public avr_0[] dS() {
        return new avr_0[]{avr_0.dgp};
    }

    public void j() {
        super.j();
    }

    public void b() {
        super.b();
        this.amP = 1;
        this.aQv = true;
        this.mY = true;
        this.mX = true;
    }

    public void gi() {
        super.gi();
        String[] stringArray = this.cmX.split(";");
        if (this.cmX == null || this.cmX.equals("null") || stringArray.length == 0) {
            a.error((Object)"[GameDesign] un ZoneTrigger poss\u00e8de 3 param\u00e8tres, le script qu'il lance, l'achievement n\u00e9cessaire pour l'activer et l'achievement apr\u00e8s lequel il ne s'active plus");
            return;
        }
        this.Vb = Integer.parseInt(stringArray[0]);
        this.Vc = Short.parseShort(stringArray[1]);
        this.Vd = Short.parseShort(stringArray[2]);
    }

    public boolean gC() {
        return !this.Va;
    }

    static /* synthetic */ void a(oq oq2, ym_0 ym_02) {
        oq2.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

