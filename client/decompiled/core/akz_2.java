/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aKZ
 */
public class akz_2
extends aht_1 {
    public static final String TAG = "valueSelector";
    public static final String dUp = "increaseButton";
    public static final String dUq = "decreaseButton";
    public static final String dUr = "textEditor";
    private UV dUs;
    private aqq_0 apb;
    private aqq_0 apc;
    private int r;
    private int cTb;
    private int cTc;
    private String dUt;
    public static final int mi = "maxBound".hashCode();
    public static final int mj = "minBound".hashCode();
    public static final int dL = "value".hashCode();
    public static final int dUu = "displayFormat".hashCode();

    public String getTag() {
        return TAG;
    }

    public int getValue() {
        return this.r;
    }

    public void setValue(int n2) {
        boolean bl2 = (n2 = ej_0.e(n2, this.cTb, this.cTc)) != this.r;
        int n3 = this.r;
        this.r = n2;
        this.aVX();
        if (bl2) {
            gm_0 gm_02 = new gm_0(this);
            gm_02.b();
            gm_02.setValue(this.r);
            gm_02.S(n3);
            this.f(gm_02);
        }
    }

    public int getMinBound() {
        return this.cTb;
    }

    public void setMinBound(int n2) {
        if (this.cTb == n2) {
            return;
        }
        this.cTb = n2;
        if (this.r < this.cTb) {
            this.setValue(n2);
        }
    }

    public int getMaxBound() {
        return this.cTc;
    }

    public void setMaxBound(int n2) {
        if (this.cTc == n2) {
            return;
        }
        this.cTc = n2;
        if (this.r > this.cTc) {
            this.setValue(n2);
        }
    }

    public String getDisplayFormat() {
        return this.dUt;
    }

    public void setDisplayFormat(String string) {
        this.dUt = string;
        this.aVX();
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (string.equals(dUp)) {
            return this.apb;
        }
        if (string.equals(dUq)) {
            return this.apc;
        }
        if (string.equals(dUr)) {
            return this.dUs;
        }
        return null;
    }

    public void aVX() {
        this.dUs.setText(String.format(this.dUt, this.r));
    }

    private void aVY() {
        this.dUs = new UV();
        this.dUs.b();
        this.dUs.setText(String.valueOf(this.r));
        this.dUs.a(qe_1.bFo, new ago_1(this), false);
        this.a(this.dUs);
        aht_1 aht_12 = aht_1.checkOut();
        aht_12.setExpandable(false);
        ei_1 ei_12 = ei_1.checkOut();
        ei_12.setHorizontal(false);
        ei_12.setAlign(BT.aJX);
        aht_12.a(ei_12);
        this.apb = new aqq_0();
        this.apb.b();
        this.apb.a(qe_1.bFB, new agi_2(this), false);
        this.apb.setClickSoundId(aek.atD().atM());
        this.apc = new aqq_0();
        this.apc.b();
        this.apc.a(qe_1.bFB, new agl_1(this), false);
        this.apc.setClickSoundId(aek.atD().atN());
        aht_12.a(this.apb);
        aht_12.a(this.apc);
        this.a(aht_12);
    }

    public void a(air_1 air_12) {
        int n2;
        super.a(air_12);
        akz_2 akz_22 = (akz_2)air_12;
        akz_22.dUt = this.dUt;
        akz_22.setMinBound(this.cTb);
        akz_22.setMaxBound(this.cTc);
        akz_22.setValue(this.r);
        ArrayList arrayList = this.apb.getListeners(qe_1.bFB, false);
        if (arrayList != null) {
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                akz_22.apb.b(qe_1.bFB, (ov_1)arrayList.get(n2), false);
            }
        }
        if ((arrayList = this.apc.getListeners(qe_1.bFB, false)) != null) {
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                akz_22.apc.b(qe_1.bFB, (ov_1)arrayList.get(n2), false);
            }
        }
        if ((arrayList = this.dUs.getListeners(qe_1.bFo, false)) != null) {
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                akz_22.dUs.b(qe_1.bFo, (ov_1)arrayList.get(n2), false);
            }
        }
    }

    public void b() {
        super.b();
        this.aVY();
        this.cTb = 0;
        this.cTc = 100;
        this.r = 0;
        this.dUt = "%d";
    }

    public void j() {
        super.j();
        this.dUs = null;
        this.apb = null;
        this.apc = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == mi) {
            this.setMaxBound(Gr.R(string));
        } else if (n2 == mj) {
            this.setMinBound(Gr.R(string));
        } else if (n2 == dL) {
            this.setValue(Gr.R(string));
        } else if (n2 == dUu) {
            this.setDisplayFormat(string);
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == mi) {
            this.setMaxBound(Gr.R(object));
        } else if (n2 == mj) {
            this.setMinBound(Gr.R(object));
        } else if (n2 == dL) {
            this.setValue(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ UV a(akz_2 akz_22) {
        return akz_22.dUs;
    }

    static /* synthetic */ int b(akz_2 akz_22) {
        return akz_22.cTb;
    }

    static /* synthetic */ int c(akz_2 akz_22) {
        return akz_22.r;
    }

    static /* synthetic */ int d(akz_2 akz_22) {
        return akz_22.cTc;
    }
}

