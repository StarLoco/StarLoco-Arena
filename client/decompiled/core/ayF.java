/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class ayF
extends do_1 {
    boolean bAy;
    String m_name;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            this.b(avr_02);
            this.aYY();
            this.a(avr_02);
            apN.aDK().a(kv_2.WF());
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
        if (this.cmX == null || this.cmX.equals("null") || stringArray.length < 2) {
            a.error((Object)"[GameDesign] un cardmaster poss\u00e8de 2 param\u00e8tres : un byte indiquant si c'est un kardmaster ou la d\u00e9mone II et l'id de sa liste de cartes");
            return;
        }
        this.bAy = Byte.valueOf(stringArray[0].trim()) == 1;
        this.m_name = stringArray.length == 3 ? aon_0.aYc().a(29, Integer.valueOf(stringArray[2]), new Object[0]) : "Kardmaster";
    }

    public String getName() {
        return this.m_name;
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    static /* synthetic */ void a(ayF ayF2, ym_0 ym_02) {
        ayF2.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

