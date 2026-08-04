/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Ex
 */
public class ex_2
extends aht_1 {
    private static Logger a = Logger.getLogger(ex_2.class);
    public static final String TAG = "RootContainer";
    protected aht_1 aRH;
    protected aht_1 aRI;
    protected eq_0 aRJ;
    protected final eg_1 aRK = new eg_1(this);

    public void f(na_1 na_12) {
        this.aRH.f(na_12);
    }

    public void b(amx_1 amx_12) {
        this.aRH.b(amx_12);
    }

    public void a(adg_2 adg_22, int n2, boolean bl2) {
        if (bl2) {
            this.aRH.c(adg_22, n2);
        } else {
            super.c(adg_22, n2);
        }
    }

    public void c(adg_2 adg_22, int n2) {
        this.a(adg_22, n2, true);
    }

    public String getTag() {
        return TAG;
    }

    public aht_1 getContentContainer() {
        return this.aRH;
    }

    public eq_0 getLayeredContainer() {
        return this.aRJ;
    }

    public eg_1 getWindowManager() {
        return this.aRK;
    }

    public void NP() {
        this.aRJ = new eq_0();
        this.aRJ.b();
        this.aRH = aht_1.checkOut();
        auW auW2 = new auW();
        auW2.b();
        auW2.setSize(new agj_1(100.0f, 100.0f));
        this.aRH.a(auW2);
        this.aRJ.a(this.aRH, -30000);
        super.a(this.aRJ);
    }

    public void b() {
        super.b();
        akw_2 akw_22 = new akw_2(this);
        akw_22.b();
        this.a(akw_22);
        this.NP();
        this.dMd = true;
    }

    public void j() {
        super.j();
        this.aRK.clean();
        lb_2.XL().c(this);
    }
}

