/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from azK
 */
public class azk_0
extends alp_2
implements oc_0 {
    private static final Logger a = Logger.getLogger(azk_0.class);
    public static final String doj = "poissonDisk";
    public static final String TAG = "map";
    public static final String cJj = "internalPopup";
    public static final String cJk = "internalPopupTextView";
    public static final String dok = "internalPopupTextEditor";
    public static final String dol = "internalPopupValid";
    public static final String dom = "internalPopupCancel";
    private abx_1 don;
    private zA doo;
    private akq_1 dop = null;
    private int doq = 0;
    private int dor = 0;
    private int dos = 0;
    private int dot = 0;
    private lb_0 dou;
    private jg_0 dov;
    private ahp_0 dow = null;
    private ahp_0 dox = null;
    private float doy = 0.0f;
    private float doz = 0.0f;
    private float doA = 0.0f;
    private float doB = 0.0f;
    private akq_1 arn;
    private boolean doC = true;
    private boolean doD = false;
    private boolean doE = false;
    private boolean doF = false;
    private boolean doG = false;
    private boolean dmT = false;
    public static final int doH = "mapPath".hashCode();
    public static final int doI = "knownMaps".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
    }

    protected void pX() {
        this.arC.i(this.don.getEntity());
        super.pX();
    }

    public void setMapBackgroundPixmap(akq_1 akq_12) {
        this.dop = akq_12;
    }

    public void setMapBackgroundStartX(int n2) {
        this.doq = n2;
    }

    public void setMapBackgroundStartY(int n2) {
        this.dor = n2;
    }

    public void setMapBackgroundEndX(int n2) {
        this.dos = n2;
    }

    public void setMapBackgroundEndY(int n2) {
        this.dot = n2;
    }

    public void setPixmap(ur_1 ur_12) {
        akq_1 akq_12;
        String string;
        if (ur_12 != null && (string = ur_12.getName()) != null && string.equals(doj) && (akq_12 = ur_12.getPixmap()) != null && akq_12.azP()) {
            akq_12.azR();
        }
    }

    public void setModulationColor(vP vP2) {
    }

    public vP getModulationColor() {
        return null;
    }

    public String getTag() {
        return TAG;
    }

    protected void setZoom() {
        super.setZoom();
        this.don.bn(this.aaw);
        this.doG = true;
        this.setNeedsToPostProcess();
    }

    public void setKnownMaps(int[] nArray) {
    }

    public void setMapPath(String string) {
        URL uRL;
        if (string == null) {
            return;
        }
        try {
            uRL = new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            a.error((Object)("URL invalide : " + string));
            return;
        }
        aAN aAN2 = new aAN();
        aNe aNe2 = new aNe();
        try {
            aAN2.q(new BufferedInputStream(uRL.openStream()));
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)("Probl\u00e8me lors de la lecture du fichier de map d'url : " + uRL));
            return;
        }
        this.aMl();
        float f = this.dop == null ? 0.0f : (float)(this.dos - this.doq) / (float)this.dop.getWidth();
        float f2 = this.dop == null ? 0.0f : (float)(this.dot - this.dor) / (float)this.dop.getHeight();
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        this.doy = 0.0f;
        this.doz = 0.0f;
        this.doA = 0.0f;
        this.doB = 0.0f;
        ArrayList arrayList = aNe2.aXo().getChildren();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            k_0 k_02 = (k_0)arrayList.get(j);
            if (k_02.getName().equals("#text") || k_02.getName().equals("#comment")) continue;
            double d = 0.0;
            double d2 = 0.0;
            double d3 = -1.0;
            double d4 = -1.0;
            int n3 = -1;
            int n4 = -1;
            ef_1 ef_12 = null;
            k_0 k_03 = k_02.f("isoX");
            if (k_03 != null) {
                d = k_03.getDoubleValue();
            }
            if ((k_03 = k_02.f("isoY")) != null) {
                d2 = k_03.getDoubleValue();
            }
            if ((k_03 = k_02.f("isoWidth")) != null) {
                d3 = k_03.getDoubleValue();
            }
            if ((k_03 = k_02.f("isoHeight")) != null) {
                d4 = k_03.getDoubleValue();
            }
            if ((k_03 = k_02.f("width")) != null) {
                n3 = k_03.getIntValue();
            }
            if ((k_03 = k_02.f("height")) != null) {
                n4 = k_03.getIntValue();
            }
            if ((k_03 = k_02.f("texture")) != null && this.doC) {
                String string2 = k_03.getStringValue();
                try {
                    URL uRL2 = an_2.a(uRL, string2);
                    String string3 = uRL2.toString();
                    ef_12 = this.iL(string3);
                }
                catch (Exception exception) {
                    a.error((Object)"Probl\u00e8me lors de la r\u00e9cup\u00e9ration de la texture de la map");
                }
            }
            this.doA = (float)d3;
            this.doB = (float)d4;
            this.doy = (float)d;
            this.doz = (float)d2;
            if (this.dop != null) {
                f5 = this.doA / f;
                f6 = this.doB / f2;
                f3 = -f5 * (float)this.doq / (float)this.dop.getWidth();
                f4 = -f6 * (float)this.dor / (float)this.dop.getHeight();
            }
            if (ef_12 != null) {
                this.arn = n3 != -1 && n4 != -1 ? new akq_1(ef_12, 0, 0, n3, n4) : new akq_1(ef_12);
                this.don.setPixmap(this.arn);
                if (this.dop != null) {
                    this.don.a(this.dop, (int)f3, (int)f4, (int)f5, (int)f6);
                }
            }
            this.don.cc((int)this.doA, (int)this.doB);
        }
        this.doE = true;
        this.doF = true;
        this.doG = true;
        this.dmT = true;
        this.setNeedsToPreProcess();
        this.setNeedsToPostProcess();
    }

    public void setAllMapZonesVisible(boolean bl2) {
        ll_0 ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            awi awi2 = (awi)ll_02.value();
            ArrayList arrayList = awi2.getChildren();
            int n2 = arrayList.size();
            for (int j = 0; j < n2; ++j) {
                ahp_0 ahp_02 = (ahp_0)arrayList.get(j);
                ahp_02.setVisible(bl2);
            }
        }
        this.doG = true;
        this.setNeedsToPostProcess();
    }

    public void setMapZoneVisibleById(int n2, boolean bl2) {
        ll_0 ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            awi awi2 = (awi)ll_02.value();
            ArrayList arrayList = awi2.getChildren();
            int n3 = arrayList.size();
            for (int j = 0; j < n3; ++j) {
                ahp_0 ahp_02 = (ahp_0)arrayList.get(j);
                if (ahp_02.aTW().getId() != n2) continue;
                ahp_02.setVisible(bl2);
                return;
            }
        }
        this.doG = true;
        this.setNeedsToPostProcess();
    }

    public void setPlayerMapZone(int n2, int n3) {
        ahp_0 ahp_02 = this.getMapZoneFromCell(n2, n3);
        if (ahp_02 == this.dox) {
            return;
        }
        if (this.dox != null) {
            this.dox.b(1.0f);
            this.don.l(this.dox.aLz(), 1.0f);
        }
        this.dox = ahp_02;
        if (this.dox != null) {
            this.dox.b(3.0f);
            this.don.l(this.dox.aLz(), 5.0f);
        }
    }

    public boolean getLoadTexture() {
        return this.doC;
    }

    public void setLoadTexture(boolean bl2) {
        this.doC = bl2;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof kh_2;
    }

    public ahp_0 getSelectedMapZone() {
        return this.dow;
    }

    public void aMg() {
        ll_0 ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            this.dov.add(ll_02.kR());
        }
        this.doD = true;
        this.setNeedsToPostProcess();
    }

    public void nb(int n2) {
        this.dov.add(n2);
        this.doD = true;
        this.setNeedsToPostProcess();
    }

    private void aMh() {
        int n2 = this.dov.size();
        for (int j = 0; j < n2; ++j) {
            int n3 = this.dov.bu(j);
            this.don.nb(n3);
            awi awi2 = (awi)this.dou.remove(n3);
            if (awi2 == null || awi2.getPixmap() == null || awi2.getPixmap().jI() == null) continue;
            awi2.cleanUp();
        }
        this.doD = false;
        this.doG = true;
    }

    public void a(rd_0 rd_02) {
        awi awi2 = new awi(rd_02);
        awi2.bn(this.aaw);
        ArrayList arrayList = rd_02.getChildren();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Object object;
            aur aur2 = (aur)arrayList.get(j);
            ahp_0 ahp_02 = new ahp_0();
            ahp_02.a(aur2);
            int n3 = 774;
            int n4 = 387;
            qt_0 qt_02 = aur2.aHu();
            if (qt_02 != null) {
                object = qt_02.adv().aNm();
                while (((aiz_1)object).hasNext()) {
                    int n5 = ((qk)object).next();
                    short s = asu_0.mi(n5);
                    short s2 = asu_0.mj(n5);
                    int n6 = s * 18;
                    int n7 = s2 * 18;
                    float f = (float)(n6 - n7) * 43.0f + this.doy;
                    float f2 = (float)(-(n6 + n7)) * 21.5f + this.doz;
                    ahp_02.a(s, s2, f - 774.0f, f2 - 387.0f, f, f2, f + 774.0f, f2 - 387.0f, f, f2 - 774.0f);
                }
                ahp_02.aJq();
            }
            if ((object = aur2.Sk()) != null) {
                ef_1 ef_12 = null;
                try {
                    ef_12 = this.iL((String)object);
                }
                catch (Exception exception) {
                    a.error((Object)"Probl\u00e8me lors de la r\u00e9cup\u00e9ration de la texture d'iconUrl");
                }
                if (ef_12 != null) {
                    ahp_02.setPixmap(new akq_1(ef_12));
                }
            }
            awi2.a(ahp_02);
        }
        awi2.aJq();
        this.dou.c(rd_02.getId(), awi2);
        this.don.a(rd_02.getId(), awi2);
        this.doG = true;
        this.setNeedsToPostProcess();
    }

    protected void cb(int n2, int n3) {
        super.cb(n2, n3);
        int n4 = vn.dR(n2);
        int n5 = vn.dS(n3);
        if (this.dow != null) {
            if (this.dow.contains(n4, n5)) {
                return;
            }
            this.dow.setSelected(false);
            this.dow = null;
        }
        this.dow = this.getMapZoneFromPartition(n4, n5);
        if (this.dow != null) {
            this.dow.setSelected(true);
        }
    }

    public ahp_0 getMapZoneFromPartition(int n2, int n3) {
        ll_0 ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            awi awi2 = (awi)ll_02.value();
            ArrayList arrayList = awi2.getChildren();
            int n4 = arrayList.size();
            for (int j = 0; j < n4; ++j) {
                ahp_0 ahp_02 = (ahp_0)arrayList.get(j);
                if (!ahp_02.contains(n2, n3)) continue;
                return ahp_02;
            }
        }
        return null;
    }

    public ahp_0 getMapZoneFromCell(int n2, int n3) {
        return this.getMapZoneFromPartition(vn.dR(n2), vn.dS(n3));
    }

    public void validate() {
        super.validate();
        this.doF = true;
        this.doG = true;
        this.don.setSize(this.cLZ.getContentWidth(), this.cLZ.getContentHeight());
        if (this.dox != null) {
            this.don.l(this.dox.aLz(), 5.0f);
        }
    }

    private boolean zs() {
        if (this.dmT) {
            agj_1 agj_12 = ago_2.getInstance().getSize();
            boolean bl2 = false;
            float f = (float)agj_12.width * 0.9f;
            float f2 = (float)agj_12.height * 0.9f;
            float f3 = f / f2;
            float f4 = 2.0f;
            if (f3 >= f4) {
                f = f2 * f4;
            } else {
                f2 = f / f4;
            }
            if (this.aLb == null || (float)this.aLb.width != f || (float)this.aLb.height != f2) {
                this.setMinSize(new agj_1((int)f, (int)f2));
                bl2 = true;
            }
            this.dmT = false;
            return bl2;
        }
        return false;
    }

    private void aMi() {
        if (this.doF) {
            if (this.cLZ == null) {
                return;
            }
            float f = (float)this.cLZ.getContentWidth() / this.doA;
            float f2 = (float)this.cLZ.getContentHeight() / this.doB;
            float f3 = Math.min(f, f2);
            this.setMinZoom(f3);
            this.setMaxZoom(f3);
            this.doF = false;
            this.doG = true;
            float f4 = -this.doy * this.aaw;
            float f5 = -this.doz * this.aaw;
            float f6 = (float)this.cuD.k(f4, f5);
            float f7 = (float)this.cuD.l(f4, f5);
            this.setIsoCenterX(f6);
            this.setIsoCenterY(f7);
        }
    }

    private void aMj() {
        int n2 = 774;
        int n3 = 387;
        ll_0 ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            awi awi2 = (awi)ll_02.value();
            awi2.bn(this.aaw);
            ArrayList arrayList = awi2.getChildren();
            for (int j = arrayList.size() - 1; j >= 0; --j) {
                ahp_0 ahp_02 = (ahp_0)arrayList.get(j);
                ArrayList arrayList2 = ahp_02.ph();
                for (int i2 = arrayList2.size() - 1; i2 >= 0; --i2) {
                    vb_1 vb_12 = (vb_1)arrayList2.get(i2);
                    short s = vb_12.pi();
                    short s2 = vb_12.pj();
                    int n4 = s * 18;
                    int n5 = s2 * 18;
                    float f = (float)(n4 - n5) * 43.0f + this.doy;
                    float f2 = (float)(-(n4 + n5)) * 21.5f + this.doz;
                    vb_12.av(f - (float)n2);
                    vb_12.aw(f2 - (float)n3);
                    vb_12.ax(f);
                    vb_12.ay(f2);
                    vb_12.az(f + (float)n2);
                    vb_12.aA(f2 - (float)n3);
                    vb_12.aB(f);
                    vb_12.aC(f2 - (float)(2 * n3));
                }
                ahp_02.aJq();
            }
        }
        this.doG = true;
        this.doE = false;
    }

    private void aMk() {
        if (this.doG) {
            if (this.cLZ == null || this.cLZ.getContentWidth() == 0 || this.cLZ.getContentHeight() == 0) {
                return;
            }
            ll_0 ll_02 = this.dou.pK();
            while (ll_02.hasNext()) {
                ll_02.fK();
                ((awi)ll_02.value()).bn(this.aaw);
            }
            this.don.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
            this.aWH();
            this.doG = false;
        }
    }

    private void aMl() {
        if (this.arn != null && this.arn.jI() != null) {
            this.arn.jI().HF();
        }
    }

    private ef_1 iL(String string) {
        if (an_2.o(string)) {
            return cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string), string, new adz_1(), false);
        }
        a.info((Object)("Impossible de trouver le fichier " + string));
        return null;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.dmT && this.zs() && this.dxR != null) {
            this.dxR.Am();
            this.setNeedsToPostProcess();
        }
        return bl2;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (this.doD) {
            this.aMh();
        }
        if (this.doE) {
            this.aMj();
        }
        if (this.doF) {
            this.aMi();
        }
        if (this.doG) {
            this.aMk();
        }
        return bl2;
    }

    public void yx() {
        super.yx();
    }

    public void Aj() {
        super.Aj();
    }

    public void j() {
        super.j();
        this.aMl();
        if (this.don != null) {
            this.don.j();
            this.don = null;
        }
        this.dou.a(new ahj_2(this));
        this.dow = null;
        this.dou = null;
        this.dov = null;
        ali_0.aWv().a(this.doo, true);
    }

    public void b() {
        super.b();
        kh_2 kh_22 = new kh_2();
        kh_22.b();
        kh_22.setWidget(this);
        this.a(kh_22);
        this.setLayoutManager(null);
        this.don = new abx_1();
        this.don.b();
        this.don.setModulationColor(new vP(vP.atL));
        this.dou = new lb_0();
        this.dov = new jg_0();
        this.doC = true;
        this.doD = false;
        this.dWv = false;
        this.doo = new zA(this);
        ali_0.aWv().a(this.doo);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != doH) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setMapPath(string);
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == doI) {
            this.setKnownMaps((int[])object);
        } else if (n2 == doH) {
            this.setMapPath(Gr.getString(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

