/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ni
 */
public class ni_0
extends do_1 {
    private String m_name;
    private short NY;
    private short NZ;
    private short Oa;
    private String Ob;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            this.b(avr_02);
            this.aYY();
            this.a(avr_02);
            ed_0 ed_02 = null;
            ed_02 = this.NY != -1 && apN.aDK().Ln().qI().cp(this.NY) > 0 ? Rq.aX(this.NZ) : Rq.aX(this.Oa);
            azs_0.aLV().g("selectedTalkOption", ed_02);
            azs_0.aLV().g("npcGuiStyle", this.Ob);
            sb_0 sb_02 = new sb_0();
            sb_02.f(17000);
            acu_1.ara().c(sb_02);
        }
        return true;
    }

    public avr_0 dR() {
        return avr_0.dgg;
    }

    public avr_0[] dS() {
        return new avr_0[]{avr_0.dgg};
    }

    public void gi() {
        super.gi();
        String[] stringArray = this.cmX.split(";");
        if (this.cmX == null || this.cmX.equals("null")) {
            a.error((Object)"[GameDesign] un NPCTalker poss\u00e8de 5 param\u00e8tres");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
        this.NY = Short.valueOf(stringArray[1]);
        this.Oa = Short.valueOf(stringArray[2]);
        this.NZ = Short.valueOf(stringArray[3]);
        this.Ob = stringArray[4];
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

    public String getName() {
        return this.m_name;
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    static /* synthetic */ void a(ni_0 ni_02, ym_0 ym_02) {
        ni_02.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

