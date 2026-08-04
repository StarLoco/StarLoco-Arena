/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from qa
 */
public class qa_1
extends aht_1
implements Fc {
    public static final String TAG = "renderableContainer";
    private na_1[] acT;
    private ArrayList acU;
    private final ArrayList acV = new ArrayList();
    private sm_0 acW = null;
    private ie acX = null;
    private sn_0 dR = new sn_0();
    private px_2 acY = null;
    private aji_1 acZ = null;
    private kn_1 xh;
    private boolean dW = true;
    private boolean ada = false;
    private ck_1 adb = null;
    private ov_1 adc = null;
    private String ed = null;
    private aji_1 ee = null;
    public static final int ei = "content".hashCode();
    public static final int add = "enableDND".hashCode();

    public qa_1() {
        this(null);
    }

    public qa_1(px_2 px_22) {
        this.acY = px_22;
        this.setNonBlocking(false);
    }

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
    }

    private void uN() {
        if (this.adb != null) {
            ali_0.aWv().a(this.adb, false);
        }
        this.adb = new ck_1(this);
        ali_0.aWv().a(this.adb);
    }

    public void g(na_1 na_12) {
        this.acV.add(na_12);
        this.a(na_12);
    }

    public void a(qe_1 qe_12, ov_1 ov_12, boolean bl2) {
        super.a(qe_12, ov_12, bl2);
        if (qe_12 == qe_1.bFj && this.adc == null) {
            this.adc = new aHO(this);
            super.a(qe_1.bFC, this.adc, false);
        }
    }

    public void b(qe_1 qe_12, ov_1 ov_12, boolean bl2) {
        super.b(qe_12, ov_12, bl2);
        if (qe_12 == qe_1.bFj && this.b(qe_12)) {
            this.b(qe_1.bFC, this.adc, false);
            this.adc = null;
        }
    }

    public String getTag() {
        return TAG;
    }

    public void setRenderableChildren(na_1[] na_1Array) {
        this.acT = na_1Array;
    }

    public void setItemElements(ArrayList arrayList) {
        this.acU = arrayList;
    }

    public void setRenderer(ie ie2) {
        if (ie2 != this.acX) {
            if (this.acX != null) {
                this.acX.d(this);
            }
            this.acX = ie2;
            for (int j = this.acV.size() - 1; j >= 0; --j) {
                this.k((na_1)this.acV.get(j));
            }
            this.acV.clear();
        }
    }

    public boolean getEnableDND() {
        return this.dW;
    }

    public void setEnableDND(boolean bl2) {
        this.dW = bl2;
    }

    public boolean uO() {
        return this.dW && this.xh.getEnabled();
    }

    public sn_0 getRendererManager() {
        return this.dR;
    }

    public void setRendererManager(sn_0 sn_02) {
        if (sn_02 != null && this.dR != sn_02) {
            if (this.dR != null) {
                this.dR.g(this);
            }
            this.dR = sn_02;
            this.dR.f(this);
            this.b(false, false);
        }
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public void a(abd_1 abd_12) {
        Object object = this.acW == null ? null : this.acW.getValue();
        aGJ aGJ2 = aGJ.a(abd_12, this, qe_1.bFl, object);
        this.f(aGJ2);
    }

    public void b(abd_1 abd_12) {
        Object object = this.acW == null ? null : this.acW.getValue();
        aGJ aGJ2 = aGJ.a(abd_12, this, qe_1.bFk, object);
        this.f(aGJ2);
    }

    public void c(abd_1 abd_12) {
        Object object = this.acW == null ? null : this.acW.getValue();
        aGJ aGJ2 = aGJ.a(abd_12, this, qe_1.bFi, object);
        this.f(aGJ2);
    }

    public void d(abd_1 abd_12) {
        Object object = this.acW == null ? null : this.acW.getValue();
        aGJ aGJ2 = aGJ.a(abd_12, this, qe_1.bFj, object);
        this.f(aGJ2);
    }

    public void uP() {
        if (this.acX != null && this.acT != null && this.acT.length != 0) {
            this.acX.a(this.acT, this.acW);
        }
        this.ada = false;
    }

    public px_2 getCollection() {
        return this.acY;
    }

    public void setCollection(px_2 px_22) {
        this.acY = px_22;
    }

    public void setInnerElementMap(aji_1 aji_12) {
        this.acZ = aji_12;
    }

    public aji_1 getInnerElementMap() {
        return this.acZ;
    }

    public void setContent(Object object) {
        if (this.czn) {
            return;
        }
        if (this.acW == null || this.acW.getValue() != object) {
            this.setItemValue(object);
        }
        this.ada = true;
    }

    public void setItem(sm_0 sm_02) {
        this.setItem(sm_02, false);
    }

    public void setItem(sm_0 sm_02, boolean bl2) {
        if (this.acW != sm_02) {
            this.a(this.acW);
            sm_0.b(this.acW);
            this.acW = sm_02;
            this.b(true, bl2);
        }
    }

    public void uQ() {
        this.b(true, false);
    }

    public void b(boolean bl2, boolean bl3) {
        afl_0 afl_02;
        bl3 = false;
        if (this.dR == null) {
            return;
        }
        if (this.dR.h(this)) {
            this.uR();
            bl2 = true;
        }
        if (this.acU != null && this.acW != null && this.acW.yp() != null && (afl_02 = this.acW.yp()).getValue() instanceof aho_0) {
            int n2 = this.acU.size();
            for (int j = 0; j < n2; ++j) {
                axf axf2 = (axf)this.acU.get(j);
                na_1 na_12 = axf2.getParent() != null ? axf2.getParent() : axf2;
                if (axf2.getField() != null && axf2.getField().contains("/")) {
                    pf_0 pf_02 = afl_0.d(afl_02.getValue(), axf2.getField());
                    String string = axf2.getField().substring(0, axf2.getField().length() - ((String)pf_02.acl()).length() - 1);
                    String string2 = afl_02.getName() + "/" + string;
                    afl_0 afl_03 = azs_0.aLV().l(string2, this.ee);
                    if (afl_03 == null) {
                        afl_03 = new afl_0(string2, afl_02, string, this.ee);
                        afl_03.setValue(pf_02.getFirst());
                        azs_0.aLV().b(afl_03);
                    }
                    afl_03.a(new ahb_0(na_12, ye_2.amJ().ij(na_12.getTag()), axf2.getAttribute(), (String)pf_02.acl(), axf2.getResultProvider()));
                    continue;
                }
                afl_02.a(new ahb_0(na_12, ye_2.amJ().ij(na_12.getTag()), axf2.getAttribute(), axf2.getField(), axf2.getResultProvider()));
            }
        }
        if (bl2) {
            if (bl3) {
                this.uP();
            } else {
                this.ada = true;
            }
        }
    }

    public px_2 getRenderableCollection() {
        return this.acY;
    }

    public void setItemValue(Object object) {
        sm_0 sm_02 = sm_0.a(object, this.ed, this.ee, this);
        this.setItem(sm_02);
    }

    public Object getItemValue() {
        if (this.acW != null) {
            return this.acW.getValue();
        }
        return null;
    }

    public sm_0 getItem() {
        return this.acW;
    }

    public ie getRenderer() {
        return this.acX;
    }

    public void uR() {
        if (this.dMc != null && this.dMc.size() == 0 && this.acX != null) {
            this.acX.e(this);
            bd.ce().ch();
        }
    }

    public kn_1 getDragNDropable() {
        return this.xh;
    }

    public void setDragNDropable(kn_1 kn_12) {
        this.xh = kn_12;
        this.uN();
    }

    public void uS() {
        this.a(qe_1.bFx, new aHP(this), false);
        this.a(qe_1.bFy, new aHK(this), false);
        this.a(qe_1.bFB, new aHL(this), false);
    }

    public void uT() {
        this.a(this.acW);
    }

    private void a(sm_0 sm_02) {
        afl_0 afl_02;
        if (sm_02 != null && sm_02.yp() != null && this.acU != null && (afl_02 = sm_02.yp()).getValue() instanceof aho_0) {
            for (axf axf2 : this.acU) {
                na_1 na_12 = axf2.getParent() != null ? axf2.getParent() : axf2;
                if (axf2.getField() != null && axf2.getField().contains("/")) {
                    pf_0 pf_02 = afl_0.d(afl_02.getValue(), axf2.getField());
                    String string = axf2.getField().substring(0, axf2.getField().length() - ((String)pf_02.acl()).length() - 1);
                    String string2 = afl_02.getName() + "/" + string;
                    afl_0 afl_03 = azs_0.aLV().l(string2, this.ee);
                    if (afl_03 == null) continue;
                    afl_03.i(na_12);
                    continue;
                }
                afl_02.i(na_12);
            }
        }
    }

    public void uU() {
        this.ada = true;
    }

    public boolean uV() {
        boolean bl2 = false;
        if (this.acX == null) {
            this.b(true, false);
            bl2 = true;
        }
        if (this.ada) {
            this.uP();
            bl2 = true;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        qa_1 qa_12 = (qa_1)air_12;
        super.a(air_12);
        qa_12.dW = this.dW;
        for (int j = qa_12.dMc.size() - 1; j >= 0; --j) {
            ((adg_2)qa_12.dMc.get(j)).aab();
        }
    }

    public void j() {
        super.j();
        if (this.adb != null) {
            ali_0.aWv().a(this.adb, true);
        }
        this.adb = null;
        atQ.aGT().k(this);
        this.acV.clear();
        this.acY = null;
        this.xh = null;
        this.acT = null;
        if (this.acW != null) {
            this.a(this.acW);
            sm_0.b(this.acW);
            this.acW = null;
        }
        if (this.acU != null) {
            this.acU.clear();
            this.acU = null;
        }
        this.ed = null;
        this.ee = null;
        this.acZ = null;
        this.acX = null;
        if (this.dR != null) {
            this.dR.g(this);
            this.dR = null;
        }
    }

    public void b() {
        atQ.aGT().j(this);
        super.b();
        this.uS();
        this.dW = true;
        this.dyc = false;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != add) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setEnableDND(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ei) {
            this.setContent(object);
        } else if (n2 == add) {
            this.setEnableDND(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

