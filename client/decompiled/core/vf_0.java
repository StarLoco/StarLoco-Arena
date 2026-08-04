/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vf
 */
public class vf_0
extends KU {
    private boolean asc = false;
    private boolean asd = false;

    public vf_0(adg_2 adg_22) {
        super(adg_22);
    }

    protected void n(String string, String string2) {
        int n2;
        int n3;
        String string3;
        String string4;
        amy_0 amy_02 = add_1.aOG().aOO();
        if (amy_02 == null) {
            return;
        }
        abk_0 abk_02 = amy_02.fd(string);
        if (this.asc) {
            string4 = zu_2.i(string, string2, "x");
            string3 = zu_2.i(string, string2, "y");
            n3 = ((adg_2)this.ade).getX();
            n2 = ((adg_2)this.ade).getY();
            if (abk_02.contains(string4)) {
                n3 = abk_02.getInt(string4);
            }
            if (abk_02.contains(string3)) {
                n2 = abk_02.getInt(string3);
            }
            ((adg_2)this.ade).setPosition(n3, n2);
        }
        if (this.asd) {
            string4 = zu_2.i(string, string2, "width");
            string3 = zu_2.i(string, string2, "height");
            n3 = ((adg_2)this.ade).getWidth();
            n2 = ((adg_2)this.ade).getHeight();
            if (abk_02.contains(string4)) {
                n3 = abk_02.getInt(string4);
            }
            if (abk_02.contains(string3)) {
                n2 = abk_02.getInt(string3);
            }
            ((adg_2)this.ade).setSize(n3, n2);
        }
    }

    protected void o(String string, String string2) {
        String string3;
        String string4;
        abk_0 abk_02 = add_1.aOG().aOO().fd(string);
        if (this.asc) {
            string4 = zu_2.i(string, string2, "x");
            string3 = zu_2.i(string, string2, "y");
            abk_02.r(string4, ((adg_2)this.ade).getX());
            abk_02.r(string3, ((adg_2)this.ade).getY());
        }
        if (this.asd) {
            string4 = zu_2.i(string, string2, "width");
            string3 = zu_2.i(string, string2, "height");
            abk_02.r(string4, ((adg_2)this.ade).getWidth());
            abk_02.r(string3, ((adg_2)this.ade).getHeight());
        }
    }

    public void p(String string, String string2) {
        abk_0 abk_02 = add_1.aOG().aOO().fd(string);
        String string3 = zu_2.i(string, string2, "x");
        String string4 = zu_2.i(string, string2, "y");
        String string5 = zu_2.i(string, string2, "width");
        String string6 = zu_2.i(string, string2, "height");
        abk_02.hh(string3);
        abk_02.hh(string4);
        abk_02.hh(string5);
        abk_02.hh(string6);
    }

    public void Bq() {
        apt_1.aDo().b(this);
    }

    public void Br() {
        apt_1.aDo().a(this);
    }

    public boolean Bs() {
        return this.asc;
    }

    public void aM(boolean bl2) {
        this.asc = bl2;
    }

    public boolean Bt() {
        return this.asd;
    }

    public void aN(boolean bl2) {
        this.asd = bl2;
    }
}

