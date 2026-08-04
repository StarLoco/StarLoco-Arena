/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class ajc
extends le_1 {
    public static final String TAG = "basicColorPicker";
    private ArrayList bBG;
    private ArrayList cAb;
    private ArrayList cAc;
    private boolean ba = true;
    private int cAd = 0;
    private ov_1 cvS;
    public static final int ej = "horizontal".hashCode();
    public static final int cAe = "numByLines".hashCode();

    public String getTag() {
        return TAG;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        if (this.ba == bl2) {
            return;
        }
        this.ba = bl2;
        this.Am();
    }

    public int getNumByLines() {
        return this.cAd;
    }

    public void setNumByLines(int n2) {
        if (this.cAd == n2) {
            return;
        }
        this.cAd = n2;
        this.Am();
    }

    protected void qt() {
        na_1 na_12;
        int n2;
        int n3 = this.bBG.size();
        int n4 = this.Hy.size();
        for (n2 = n3; n2 < n4; ++n2) {
            na_12 = aht_1.checkOut();
            ph_0 ph_02 = new ph_0();
            ph_02.b();
            ((aht_1)na_12).getAppearance().a(ph_02);
            aqq_0 aqq_02 = new aqq_0();
            aqq_02.b();
            aqq_02.setElementMap(this.blb);
            String string = this.getStyle();
            aqq_02.setStyle(TAG + (string != null ? string : "") + "$button");
            aqq_02.setPrefSize(new agj_1(20, 20));
            aqq_02.Aj();
            ((adg_2)na_12).a(aqq_02);
            this.a(na_12);
            this.bBG.add(aqq_02);
            this.cAc.add(ph_02);
            this.cAb.add(na_12);
        }
        for (n2 = n4; n2 < n3; ++n2) {
            this.bBG.remove(this.bBG.size() - 1);
            this.cAc.remove(this.cAc.size() - 1);
            na_12 = (aht_1)this.cAb.remove(this.cAb.size() - 1);
            na_12.aab();
        }
        if (n3 != n4) {
            this.Am();
        }
        assert (this.bBG.size() == this.Hy.size()) : "m_buttons devrait avoir la m\u00eame taille que m_colors";
        for (n2 = 0; n2 < n4; ++n2) {
            na_12 = (ph_0)this.cAc.get(n2);
            ((ph_0)na_12).setColor((vP)this.Hy.get(n2));
        }
    }

    private void registerListeners() {
        this.cvS = new azr_0(this);
        this.a(qe_1.bFB, this.cvS, false);
    }

    public void a(air_1 air_12) {
        ajc ajc2 = (ajc)air_12;
        super.a((air_1)ajc2);
        ajc2.cAd = this.cAd;
        ajc2.ba = this.ba;
        ajc2.b(qe_1.bFB, this.cvS, false);
    }

    public void j() {
        super.j();
        this.bBG = null;
        this.cAc = null;
        this.cAb = null;
        this.cvS = null;
    }

    public void b() {
        super.b();
        aav aav2 = new aav(this, null);
        aav2.b();
        this.a(aav2);
        this.cAd = 0;
        this.ba = true;
        this.bBG = new ArrayList();
        this.cAc = new ArrayList();
        this.cAb = new ArrayList();
        this.registerListeners();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == cAe) {
            this.setNumByLines(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(object));
        } else if (n2 == cAe) {
            this.setNumByLines(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ int a(ajc ajc2) {
        return ajc2.cAd;
    }

    static /* synthetic */ ArrayList b(ajc ajc2) {
        return ajc2.cAb;
    }

    static /* synthetic */ boolean c(ajc ajc2) {
        return ajc2.ba;
    }

    static /* synthetic */ ArrayList d(ajc ajc2) {
        return ajc2.bBG;
    }
}

