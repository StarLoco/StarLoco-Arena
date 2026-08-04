/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Renamed from ud
 */
public class ud_1
extends aht_1
implements gx_1 {
    public static final String TAG = "TabbedContainer";
    private static final String aoF = "tab";
    private static final String aoG = "separator";
    private static final String aoH = "backgroundTabsContainer";
    private static final String aoI = "increaseButton";
    private static final String aoJ = "decreaseButton";
    private HashMap aoK;
    private ArrayList aoL;
    private aht_1 aoM;
    private aht_1 aoN;
    private azc_0 aoO;
    private aht_1 aoP;
    private dl_1 aoQ;
    private Rectangle aoR = new Rectangle();
    private double aoS = 0.0;
    private bo_0 aoT;
    private BP aoU;
    private bo_0 aoV = bo_0.aJv;
    private BT aoW = BT.aJX;
    private aiq_0 lX = aiq_0.cxW;
    private awz_0 aoX;
    private dl_1 aoY;
    private boolean aoZ = false;
    private boolean apa = false;
    private aqq_0 apb;
    private aqq_0 apc;
    private int apd = 0;
    private boolean ape = false;
    private boolean apf = true;
    private boolean apg = true;
    private boolean aph = false;
    public static final String api = "tabsRadiogGroup";
    public static int apj = 0;
    public static final int apk = "pixmapAlignment".hashCode();
    public static final int apl = "scrollButtonsNearby".hashCode();
    public static final int apm = "scrollButtonsPositionedBeforeTabs".hashCode();
    public static final int apn = "selectedTabIndex".hashCode();
    public static final int apo = "tabsAlignment".hashCode();
    public static final int app = "tabsLabelAlignment".hashCode();
    public static final int apq = "tabsPosition".hashCode();
    public static final int apr = "tabsSizesEquilibrate".hashCode();
    public static final int ml = "textOrientation".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ajb_1) {
            ajb_1 ajb_12 = (ajb_1)na_12;
            dl_1 dl_12 = new dl_1();
            dl_12.b();
            dl_12.setElementMap(this.blb);
            dl_12.setGroupId(this.aoX.getId());
            dl_12.setValue(Integer.toString(this.aoK.size()));
            dl_12.setTextOrientation(this.lX);
            dl_12.setPixmapAlign(this.aoV);
            dl_12.setNeedsScissor(true);
            dl_12.setChildrenAdded(true);
            dl_12.setClickSoundId(-2);
            dl_12.setOverrideSoundClick(false);
            dl_12.setStyle(TAG + this.getStyle() + "$" + aoF + (Object)((Object)this.aoT), true);
            ajb_12.setButton(dl_12);
            ajb_12.a(this);
            dl_12.a(qe_1.bFz, new dz_0(this, dl_12), false);
            this.aoK.put(dl_12, ajb_12);
            this.aoL.add(dl_12);
            this.aoP.a(dl_12);
        }
    }

    public String getTag() {
        return TAG;
    }

    public void setElementMap(aji_1 aji_12) {
        super.setElementMap(aji_12);
        this.aoX.setElementMap(aji_12);
    }

    public boolean isScrollButtonsNearby() {
        return this.apa;
    }

    public void setScrollButtonsNearby(boolean bl2) {
        this.apa = bl2;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public boolean isScrollButtonsPositionedBeforeTabs() {
        return this.aoZ;
    }

    public void setScrollButtonsPositionedBeforeTabs(boolean bl2) {
        this.aoZ = bl2;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public bo_0 getTabsPosition() {
        return this.aoT;
    }

    public void setTabsSizesEquilibrate(boolean bl2) {
        this.aph = bl2;
    }

    public void setTabsPosition(bo_0 bo_02) {
        ei_1 ei_12 = (ei_1)this.aoP.getLayoutManager();
        switch (bo_02) {
            case aJs: {
                ei_12.setHorizontal(true);
                break;
            }
            case aJt: {
                ei_12.setHorizontal(true);
                break;
            }
            case aJv: {
                ei_12.setHorizontal(false);
                break;
            }
            case aJu: {
                ei_12.setHorizontal(false);
            }
        }
        this.aoT = bo_02;
        this.aoP.Am();
        this.Al();
    }

    public BP getTabsAlignment() {
        return this.aoU;
    }

    public void setTabsAlignment(BP bP) {
        this.aoU = bP;
        this.aoP.Am();
    }

    public BT getTabsLabelAlignment() {
        return this.aoW;
    }

    public void setTabsLabelAlignment(BT bT) {
        this.aoW = bT;
        for (dl_1 dl_12 : this.aoL) {
            dl_12.setAlign(this.aoW);
        }
    }

    public bo_0 getPixmapAlignment() {
        return this.aoV;
    }

    public void setPixmapAlignment(bo_0 bo_02) {
        this.aoV = bo_02;
        for (dl_1 dl_12 : this.aoL) {
            dl_12.setPixmapAlign(bo_02);
        }
    }

    public aiq_0 getTextOrientation() {
        return this.lX;
    }

    public void setTextOrientation(aiq_0 aiq_02) {
        if (this.lX != aiq_02) {
            this.lX = aiq_02;
            for (dl_1 dl_12 : this.aoL) {
                dl_12.setTextOrientation(aiq_02);
            }
        }
    }

    public aqq_0 getSelectedTab() {
        return this.aoQ;
    }

    public int getSelectedTabIndex() {
        for (int j = 0; j < this.aoL.size(); ++j) {
            if (this.aoL.get(j) != this.aoQ) continue;
            return j;
        }
        return -1;
    }

    public void setSelectedTab(dl_1 dl_12) {
        dl_12.getAppearance().abR();
        this.aoQ = dl_12;
        this.aoY = dl_12;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public void setSelectedTabIndex(int n2) {
        assert (n2 >= 0 && n2 < this.aoL.size());
        dl_1 dl_12 = (dl_1)this.aoL.get(n2);
        if (dl_12.getVisible()) {
            this.setSelectedTab(dl_12);
        } else {
            this.An();
        }
    }

    public void setDataContainer(aht_1 aht_12) {
        if (aht_12 == this.aoM) {
            return;
        }
        if (this.aoM != null) {
            this.b((na_1)this.aoM);
        }
        this.a(aht_12);
        this.aoM = aht_12;
        this.aoM.invalidate();
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc || !this.aQv || !this.getAppearance().aY(n2, n3) || ago_2.getInstance().isMovePointMode()) {
            return null;
        }
        adg_2 adg_22 = this.dyc ? null : this;
        int n4 = this.aoP.getX();
        int n5 = this.aoP.getY() + (int)this.aoS;
        if ((n2 -= this.getAppearance().getLeftInset()) > n4 && (double)n2 < (double)n4 + this.aoR.getWidth() && (n3 -= this.getAppearance().getBottomInset()) > n5 && (double)n3 < (double)n5 + this.aoR.getHeight()) {
            adg_22 = this.aoP.getWidget(n2 - this.aoP.dxS.x, n3 - this.aoP.dxS.y);
        }
        if (adg_22 != null) {
            return adg_22;
        }
        for (int j = 0; j < this.dMc.size(); ++j) {
            adg_2 adg_23 = (adg_2)this.dMc.get(j);
            if (adg_23 == this.aoP || adg_23.isUnloading() || (adg_23 = adg_23.getWidget(n2 - adg_23.dxS.x, n3 - adg_23.dxS.y)) == null) continue;
            adg_22 = adg_23;
        }
        return adg_22;
    }

    private void setButtonsMinHeight(int n2) {
        for (dl_1 dl_12 : this.aoL) {
            dl_12.setPrefSize(new agj_1(0, n2));
        }
    }

    public void setStyle(String string, boolean bl2) {
        super.setStyle(string, bl2);
        this.Al();
    }

    private void setButtonsMinWidth(int n2) {
        for (dl_1 dl_12 : this.aoL) {
            dl_12.setPrefSize(new agj_1(n2, 0));
        }
    }

    private int aD(boolean bl2) {
        int n2 = 0;
        n2 = (int)Math.max(bl2 ? this.apb.getMinSize().getWidth() : this.apb.getMinSize().getHeight(), (double)n2);
        n2 = (int)Math.max(bl2 ? this.apc.getMinSize().getWidth() : this.apc.getMinSize().getHeight(), (double)n2);
        for (dl_1 dl_12 : this.aoL) {
            n2 = (int)Math.max(bl2 ? dl_12.getMinSize().getWidth() : dl_12.getMinSize().getHeight(), (double)n2);
        }
        return n2;
    }

    public void Aj() {
        this.aoX.Aj();
        this.aoP.Aj();
        this.aoN.Aj();
        this.aoO.Aj();
        this.apb.Aj();
        this.apc.Aj();
        super.Aj();
    }

    public void j() {
        if (this.aoM != null) {
            this.uA.remove(this.aoM);
        }
        super.j();
        this.aoK.clear();
        this.aoR = null;
    }

    public void b() {
        super.b();
        this.aoK = new HashMap();
        this.aoL = new ArrayList();
        jx_1 jx_12 = new jx_1(this, null);
        jx_12.b();
        this.a(jx_12);
        this.aoX = new awz_0();
        this.aoX.b();
        this.aoX.setId(api + apj++);
        this.aoP = aht_1.checkOut();
        ask ask2 = new ask(this, null);
        this.aoP.getEntity().a(ask2);
        this.aoP.getEntity().b(ask2);
        this.aoN = new aht_1();
        this.aoN.b();
        this.aoO = new azc_0();
        this.aoO.b();
        this.apb = new aqq_0();
        this.apb.b();
        this.apb.a(qe_1.bFB, new da_2(this), false);
        this.apc = new aqq_0();
        this.apc.b();
        this.apc.a(qe_1.bFB, new dx_0(this), false);
        this.aoP.setNeedsScissor(true);
        this.a(this.aoN);
        this.a(this.aoP);
        this.a(this.aoO);
        this.a(this.apb);
        this.a(this.apc);
        this.setTabsPosition(bo_0.aJs);
        this.setTabsAlignment(BP.aJA);
        this.dyu = true;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.apf) {
            this.invalidate();
            this.apf = false;
        }
        return bl2;
    }

    public void validate() {
        if (this.aoQ != null) {
            this.setDataContainer(((ajb_1)this.aoK.get(this.aoQ)).getData());
        } else if (this.aoL.isEmpty()) {
            this.aoQ = null;
            this.aoM = null;
        } else {
            for (dl_1 dl_12 : this.aoL) {
                if (dl_12.getValue() == null || !dl_12.getValue().equalsIgnoreCase(this.aoX.getValue())) continue;
                this.setSelectedTab(dl_12);
            }
            if (this.aoQ == null) {
                this.An();
            }
            if (this.aoQ != null) {
                this.setDataContainer(((ajb_1)this.aoK.get(this.aoQ)).getData());
            }
        }
        super.validate();
    }

    public void Ak() {
        super.Ak();
        this.Al();
    }

    private void Al() {
        if (this.aoK != null) {
            for (ajb_1 ajb_12 : this.aoK.values()) {
                dl_1 dl_12 = ajb_12.getButton();
                dl_12.cLZ.Pj();
                dl_12.setStyle(TAG + this.getStyle() + "$" + aoF + (Object)((Object)this.aoT), true);
                ajb_12.aVm();
            }
        }
        if (this.aoO != null) {
            this.aoO.cLZ.Pj();
            this.aoO.setStyle(TAG + this.getStyle() + "$" + aoG + (Object)((Object)this.aoT), true);
        }
        if (this.aoN != null) {
            this.aoN.cLZ.Pj();
            this.aoN.setStyle(TAG + this.getStyle() + "$" + aoH + (Object)((Object)this.aoT), true);
        }
        if (this.apb != null) {
            this.apb.cLZ.Pj();
            this.apb.setStyle(TAG + this.getStyle() + "$" + aoI + (Object)((Object)this.aoT), true);
        }
        if (this.apc != null) {
            this.apc.cLZ.Pj();
            this.apc.setStyle(TAG + this.getStyle() + "$" + aoJ + (Object)((Object)this.aoT), true);
        }
    }

    public void Am() {
        super.Am();
        switch (this.aoT) {
            case aJs: 
            case aJt: {
                this.aoP.setPrefSize(new agj_1(0, this.aD(false)));
                if (!this.aph) break;
                this.setButtonsMinWidth(this.aD(true));
                break;
            }
            case aJv: 
            case aJu: {
                this.aoP.setPrefSize(new agj_1(this.aD(true), 0));
                if (!this.aph) break;
                this.setButtonsMinHeight(this.aD(false));
            }
        }
    }

    public void a(air_1 air_12) {
        ud_1 ud_12 = (ud_1)air_12;
        super.a((air_1)ud_12);
        ud_12.aoT = this.aoT;
        ud_12.aoU = this.aoU;
        ud_12.aoV = this.aoV;
        ud_12.aoW = this.aoW;
        ud_12.lX = this.lX;
        ud_12.aoZ = this.aoZ;
        ud_12.apa = this.apa;
    }

    public void aE(boolean bl2) {
        this.dMe.a(this.getContainer());
        if (this.aoQ != null) {
            this.An();
        }
    }

    public void An() {
        for (int j = 0; j < this.aoL.size(); ++j) {
            dl_1 dl_12 = (dl_1)this.aoL.get(j);
            if (dl_12 == null || !((ajb_1)this.aoK.get(dl_12)).isVisible()) continue;
            this.setSelectedTab(dl_12);
            return;
        }
    }

    private ArrayList getVisibleTabs() {
        ArrayList<dl_1> arrayList = new ArrayList<dl_1>();
        for (dl_1 dl_12 : this.aoL) {
            if (!((ajb_1)this.aoK.get(dl_12)).isVisible()) continue;
            arrayList.add(dl_12);
        }
        return arrayList;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == apk) {
            this.setPixmapAlignment(bo_0.ds(string));
        } else if (n2 == apl) {
            this.setScrollButtonsNearby(Gr.getBoolean(string));
        } else if (n2 == apn) {
            this.setSelectedTabIndex(Gr.R(string));
        } else if (n2 == apm) {
            this.setScrollButtonsPositionedBeforeTabs(Gr.getBoolean(string));
        } else if (n2 == apo) {
            this.setTabsAlignment(BP.dt(string));
        } else if (n2 == app) {
            this.setTabsLabelAlignment(BT.dv(string));
        } else if (n2 == apq) {
            this.setTabsPosition(bo_0.ds(string));
        } else if (n2 == ml) {
            this.setTextOrientation(aiq_0.il(string));
        } else if (n2 == apr) {
            this.setTabsSizesEquilibrate(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == apk) {
            this.setPixmapAlignment((bo_0)((Object)object));
        } else if (n2 == apn) {
            this.setSelectedTabIndex(Gr.R(object));
        } else if (n2 == apl) {
            this.setScrollButtonsNearby(Gr.getBoolean(object));
        } else if (n2 == apm) {
            this.setScrollButtonsPositionedBeforeTabs(Gr.getBoolean(object));
        } else if (n2 == apo) {
            this.setTabsAlignment((BP)((Object)object));
        } else if (n2 == app) {
            this.setTabsLabelAlignment((BT)((Object)object));
        } else if (n2 == apq) {
            this.setTabsPosition((bo_0)((Object)object));
        } else if (n2 == ml) {
            this.setTextOrientation((aiq_0)((Object)object));
        } else if (n2 == apr) {
            this.setTabsSizesEquilibrate(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ dl_1 a(ud_1 ud_12) {
        return ud_12.aoQ;
    }

    static /* synthetic */ int b(ud_1 ud_12) {
        return ud_12.apd;
    }

    static /* synthetic */ int a(ud_1 ud_12, int n2) {
        ud_12.apd = n2;
        return ud_12.apd;
    }

    static /* synthetic */ ArrayList c(ud_1 ud_12) {
        return ud_12.aoL;
    }

    static /* synthetic */ boolean a(ud_1 ud_12, boolean bl2) {
        ud_12.apf = bl2;
        return ud_12.apf;
    }

    static /* synthetic */ aht_1 d(ud_1 ud_12) {
        return ud_12.aoP;
    }

    static /* synthetic */ HashMap e(ud_1 ud_12) {
        return ud_12.aoK;
    }

    static /* synthetic */ dl_1 f(ud_1 ud_12) {
        return ud_12.aoY;
    }

    static /* synthetic */ dl_1 a(ud_1 ud_12, dl_1 dl_12) {
        ud_12.aoY = dl_12;
        return ud_12.aoY;
    }

    static /* synthetic */ bo_0 g(ud_1 ud_12) {
        return ud_12.aoT;
    }

    static /* synthetic */ double a(ud_1 ud_12, double d) {
        ud_12.aoS = d;
        return ud_12.aoS;
    }

    static /* synthetic */ ArrayList h(ud_1 ud_12) {
        return ud_12.getVisibleTabs();
    }

    static /* synthetic */ aqq_0 i(ud_1 ud_12) {
        return ud_12.apb;
    }

    static /* synthetic */ aqq_0 j(ud_1 ud_12) {
        return ud_12.apc;
    }

    static /* synthetic */ boolean b(ud_1 ud_12, boolean bl2) {
        ud_12.ape = bl2;
        return ud_12.ape;
    }

    static /* synthetic */ boolean k(ud_1 ud_12) {
        return ud_12.ape;
    }

    static /* synthetic */ Rectangle l(ud_1 ud_12) {
        return ud_12.aoR;
    }

    static /* synthetic */ aht_1 m(ud_1 ud_12) {
        return ud_12.aoN;
    }

    static /* synthetic */ azc_0 n(ud_1 ud_12) {
        return ud_12.aoO;
    }

    static /* synthetic */ aht_1 o(ud_1 ud_12) {
        return ud_12.aoM;
    }

    static /* synthetic */ boolean p(ud_1 ud_12) {
        return ud_12.apa;
    }

    static /* synthetic */ boolean q(ud_1 ud_12) {
        return ud_12.aoZ;
    }

    static /* synthetic */ BP r(ud_1 ud_12) {
        return ud_12.aoU;
    }

    static /* synthetic */ double s(ud_1 ud_12) {
        return ud_12.aoS;
    }
}

