/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from bR
 */
public class br_0
extends do_1 {
    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            this.b(avr_02);
            this.aYY();
            this.a(avr_02);
            sb_0 sb_02 = new sb_0();
            sb_02.f(23066);
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
        return aon_0.aYc().getString("graveyard");
    }

    static /* synthetic */ void a(br_0 br_02, ym_0 ym_02) {
        br_02.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

