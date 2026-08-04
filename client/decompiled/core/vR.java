/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class vR
extends do_1 {
    private short atZ;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            this.b(avr_02);
            this.aYY();
            this.a(avr_02);
            aak_0.aME().bu(this.atZ);
            auZ auZ2 = new auZ(apN.aDK().Ln().getId());
            apN.aDK().vJ().b(auZ2);
            aid_1 aid_12 = new aid_1();
            aid_12.bu(this.atZ);
            aid_12.M((short)1);
            apN.aDK().vJ().b(aid_12);
        }
        return true;
    }

    public avr_0 dR() {
        return avr_0.dgg;
    }

    public avr_0[] dS() {
        return new avr_0[]{avr_0.dgg};
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
        if (this.cmX == null || this.cmX.equals("null")) {
            a.error((Object)"[GameDesign] un totem poss\u00e8de 1 param\u00e8tres");
            return;
        }
        this.atZ = Short.valueOf(stringArray[0]);
    }

    public String getName() {
        String string = "";
        try {
            string = afg_1.kn(this.atZ);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            string = "" + this.atZ;
        }
        return aon_0.aYc().getString("ladderInformation.demon") + " " + string;
    }

    static /* synthetic */ void a(vR vR2, ym_0 ym_02) {
        vR2.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

