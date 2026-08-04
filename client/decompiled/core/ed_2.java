/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from eD
 */
public class ed_2
extends do_1 {
    private String m_name;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            this.b(avr_02);
            this.aYY();
            this.a(avr_02);
            apN.aDK().a(mg_0.rq());
            iu_0.Ut().bO(this.getId());
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
        if (stringArray.length != 1) {
            a.error((Object)"[GameDesign] un cardUsingSwitch poss\u00e8de un param\u00e8tre : son nom");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
    }

    public String getName() {
        return this.m_name;
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    static /* synthetic */ void a(ed_2 ed_22, ym_0 ym_02) {
        ed_22.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

