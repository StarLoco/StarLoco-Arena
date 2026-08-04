/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class HW
extends aht_1
implements Fc,
aac,
ajb_0,
wS {
    public static final String aaQ = "selection";
    public static final String TAG = "calendar";
    private static int bfI = 7;
    private static int bfJ = 31;
    private ArrayList bfK;
    private ArrayList bfL = new ArrayList();
    private sn_0 dR = new sn_0();
    private Calendar bfM = new GregorianCalendar();
    private int aUc = 0;
    private int aUb = 0;
    private int bfN;
    private int bfO;
    private agj_1 bfP;
    private String ed = null;
    private aji_1 ee = null;
    private boolean bfQ;
    private boolean bfR;
    private int bfS = -1;
    private int bfT = -1;
    private Insets bfU = null;
    private BT bfV = BT.aKb;
    private af_1 bfW = null;
    private vP bfX = null;
    private apd_0 bfY;
    public static final int bfZ = "cellSize".hashCode();
    public static final int ei = "content".hashCode();
    public static final int aTM = "hgap".hashCode();
    public static final int aTN = "vgap".hashCode();
    public static final int bga = "dateMargin".hashCode();
    public static final int bgb = "calendar".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
    }

    protected void pX() {
        super.pX();
        if (this.bfY != null && this.bfT != -1) {
            this.arC.i(this.bfY.apq());
        }
    }

    public String getTag() {
        return TAG;
    }

    public Calendar getCalendar() {
        return this.bfM;
    }

    public void setCalendar(Calendar calendar) {
        this.bfM = calendar;
        this.bfQ = true;
        this.setNeedsToPreProcess();
    }

    public void setCellSize(agj_1 agj_12) {
        this.bfP = agj_12;
        this.bfQ = true;
        this.setNeedsToPreProcess();
    }

    public agj_1 getCellSize() {
        return this.bfP;
    }

    public int getVgap() {
        return this.aUc;
    }

    public void setVgap(int n2) {
        this.aUc = n2;
        this.bfQ = true;
        this.setNeedsToPreProcess();
    }

    public int getHgap() {
        return this.aUb;
    }

    public void setHgap(int n2) {
        this.aUb = n2;
        this.bfQ = true;
        this.setNeedsToPreProcess();
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public void setContent(le_0[] le_0Array) {
        if (le_0Array != null) {
            this.bfK = new ArrayList(le_0Array.length);
            for (le_0 le_02 : le_0Array) {
                this.bfK.add(le_02);
            }
        } else {
            this.bfK = new ArrayList(0);
        }
        this.bfR = true;
        this.setNeedsToPreProcess();
    }

    public void setContent(Iterable iterable) {
        if (iterable != null) {
            this.bfK = new ArrayList();
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.bfK.add(iterator.next());
            }
        } else {
            this.bfK = new ArrayList(0);
        }
        this.bfR = true;
        this.setNeedsToPreProcess();
    }

    public void setFont(af_1 af_12) {
        if (af_12 == this.bfW) {
            return;
        }
        this.bfW = af_12;
        int n2 = this.bfL.size();
        for (int j = 0; j < n2; ++j) {
            ((np_2)this.bfL.get(j)).aaw().setFont(af_12);
        }
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equals("text")) {
            if (vP2 == this.bfX) {
                return;
            }
            this.bfX = vP2;
            int n2 = this.bfL.size();
            for (int j = 0; j < n2; ++j) {
                ((np_2)this.bfL.get(j)).aaw().setColor(vP2, "text");
            }
        } else if (string.equals(aaQ)) {
            if (vP2 != null) {
                if (this.bfY == null) {
                    this.bfY = new apd_0();
                    this.bfY.b();
                    this.setNeedsToResetMeshes();
                }
                this.bfY.setColor(vP2);
            } else {
                if (this.bfY != null) {
                    this.bfY.j();
                }
                this.bfY = null;
                this.setNeedsToResetMeshes();
            }
        }
    }

    public void setAlign(BT bT) {
        if (this.bfV == bT) {
            return;
        }
        this.bfV = bT;
        int n2 = this.bfL.size();
        for (int j = 0; j < n2; ++j) {
            ((np_2)this.bfL.get(j)).aaw().setAlign(bT);
        }
    }

    public Insets getDateMargin() {
        return this.bfU;
    }

    public void setDateMargin(Insets insets) {
        this.bfU = insets;
    }

    public int getYearOver() {
        return this.bfM.get(1);
    }

    public int getMonthOver() {
        return this.bfM.get(2) + 1;
    }

    public int getDayOver() {
        if (this.bfT != -1) {
            return this.bfT + 1;
        }
        return -1;
    }

    private void setSelectedDate(int n2) {
        this.bfT = n2 - 1;
        this.bfM.set(5, n2);
        if (this.bfY != null) {
            aht_1 aht_12 = ((np_2)this.bfL.get(this.bfT)).getContainer();
            this.bfY.a(aht_12.getPosition(), aht_12.getSize(), this.getAppearance().getTotalInsets());
            this.setNeedsToResetMeshes();
        }
    }

    private void TC() {
        int n2;
        int n3 = this.bfM.getActualMaximum(5);
        if (n3 > (n2 = this.bfL.size())) {
            for (int j = n2; j < n3; ++j) {
                np_2 np_22 = new np_2(null);
                qa_1 qa_12 = new qa_1();
                qa_12.b();
                qa_12.setNonBlocking(this.dyc);
                qa_12.setRendererManager(this.dR);
                aht_1 aht_12 = aht_1.checkOut();
                azC azC2 = new azC();
                azC2.b();
                aht_12.a(azC2);
                aht_12.a(qe_1.bFx, new om_2(this, np_22), false);
                aht_12.a(qe_1.bFy, new oq_2(this), false);
                aht_12.a(qe_1.bFB, new oo_2(this, np_22), false);
                auW auW2 = new auW();
                auW2.b();
                auW2.setSize(new agj_1(100.0f, 100.0f));
                qa_12.a(auW2);
                OE oE = new OE();
                oE.b();
                oE.setNonBlocking(true);
                oE.setFont(this.bfW);
                oE.setColor(this.bfX, "text");
                oE.setAlign(this.bfV);
                oE.setText(String.valueOf(j + 1));
                auW2 = new auW();
                auW2.b();
                auW2.setSize(new agj_1(100.0f, 100.0f));
                oE.a(auW2);
                if (this.bfU != null) {
                    Zb zb = oE.getAppearance();
                    awc_0 awc_02 = awc_0.checkOut();
                    awc_02.setInsets(this.bfU);
                    zb.a(awc_02);
                }
                aht_12.a(qa_12);
                aht_12.a(oE);
                np_22.i(qa_12);
                np_22.d(aht_12);
                np_22.setLabel(oE);
                this.bfL.add(np_22);
                this.a(aht_12);
            }
        } else if (n3 < n2) {
            for (int j = n3; j < n2; ++j) {
                np_2 np_23 = (np_2)this.bfL.remove(this.bfL.size() - 1);
                np_23.getContainer().aab();
            }
            if (this.bfS >= this.bfL.size()) {
                this.bfS = -1;
            }
        }
        this.setSelectedDate(this.bfM.get(5));
    }

    public void ca() {
        Object object;
        int n2;
        int n3;
        int[] nArray = new int[this.bfK.size()];
        int n4 = this.bfK.size();
        for (n3 = 0; n3 < n4; ++n3) {
            le_0 le_02 = (le_0)this.bfK.get(n3);
            nArray[n3] = le_02.pR();
            n2 = le_02.pR() - 1;
            qa_1 qa_12 = ((np_2)this.bfL.get(n2)).getRenderable();
            object = le_02.getContent();
            qa_12.setContentProperty(this.ed + "#" + n2, this.ee);
            qa_12.setContent(object);
        }
        n3 = 0;
        n4 = this.bfL.size();
        int n5 = 0;
        int n6 = this.bfK.size();
        for (n2 = 0; n2 < n6; ++n2) {
            n5 = ((le_0)this.bfK.get(n2)).pR() - 1;
            while (n3 < n5 && n3 < n4) {
                object = ((np_2)this.bfL.get(n3)).getRenderable();
                ((qa_1)object).setContentProperty(this.ed + "#" + n3, this.ee);
                ((qa_1)object).setContent(null);
                ++n3;
            }
            n3 = n5 + 1;
        }
        n2 = this.bfK.size() == 0 ? 0 : 1;
        int n7 = this.bfL.size();
        for (n6 = n5 + n2; n6 < n7; ++n6) {
            qa_1 qa_13 = ((np_2)this.bfL.get(n6)).getRenderable();
            qa_13.setContentProperty(this.ed + "#" + n6, this.ee);
            qa_13.setContent(null);
        }
    }

    private void TD() {
        this.bfN = bfI;
        int n2 = bfJ / bfI;
        int n3 = bfJ - n2 * bfI;
        if (n3 > 1) {
            n3 = 2;
        }
        this.bfO = n2 + n3;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        boolean bl3 = false;
        if (this.bfQ) {
            this.TC();
        }
        if (this.bfR) {
            this.ca();
        }
        if (this.bfQ) {
            this.Am();
        }
        this.bfQ = false;
        this.bfR = false;
        return bl2;
    }

    public void a(air_1 air_12) {
        HW hW = (HW)air_12;
        super.a((air_1)hW);
        hW.setAlign(this.bfV);
        hW.setCellSize((agj_1)this.bfP.clone());
        hW.setCalendar(this.bfM);
        hW.setHgap(this.aUb);
        hW.setVgap(this.aUc);
        hW.setDateMargin(this.bfU);
        for (int j = hW.dMc.size() - 1; j >= 0; --j) {
            adg_2 adg_22 = (adg_2)hW.dMc.get(j);
            adg_22.aab();
        }
    }

    public void b() {
        super.b();
        ai_1 ai_12 = new ai_1(this);
        ai_12.b();
        this.a(ai_12);
        auL auL2 = new auL();
        auL2.b();
        this.a(auL2);
        this.bfM.setTime(new Date());
        this.bfM.set(5, 1);
        this.TD();
    }

    public void j() {
        super.j();
        if (this.bfK != null) {
            this.bfK.clear();
            this.bfK = null;
        }
        this.bfL.clear();
        this.bfL = null;
        this.bfP = null;
        this.dR = null;
        this.bfX = null;
        this.bfV = null;
        this.bfW = null;
        this.bfU = null;
        if (this.bfY != null) {
            this.bfY.j();
            this.bfY = null;
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bfZ) {
            this.setCellSize(if_12.eL(string));
        } else if (n2 == aTM) {
            this.setHgap(Gr.R(string));
        } else if (n2 == aTN) {
            this.setVgap(Gr.R(string));
        } else if (n2 == bga) {
            this.setDateMargin(if_12.eN(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ei) {
            if (object == null || object.getClass().isArray()) {
                this.setContent((le_0[])object);
                return true;
            } else {
                if (!(object instanceof Iterable)) return false;
                this.setContent((Iterable)object);
            }
            return true;
        } else {
            if (n2 != bgb) return super.setPropertyAttribute(n2, object);
            this.setCalendar((Calendar)object);
        }
        return true;
    }

    static /* synthetic */ agj_1 a(HW hW) {
        return hW.bfP;
    }

    static /* synthetic */ int b(HW hW) {
        return hW.bfN;
    }

    static /* synthetic */ int c(HW hW) {
        return hW.aUb;
    }

    static /* synthetic */ int d(HW hW) {
        return hW.bfO;
    }

    static /* synthetic */ int e(HW hW) {
        return hW.aUc;
    }

    static /* synthetic */ ArrayList f(HW hW) {
        return hW.bfL;
    }

    static /* synthetic */ Calendar g(HW hW) {
        return hW.bfM;
    }

    static /* synthetic */ int TE() {
        return bfI;
    }

    static /* synthetic */ int h(HW hW) {
        return hW.bfT;
    }

    static /* synthetic */ void a(HW hW, int n2) {
        hW.setSelectedDate(n2);
    }

    static /* synthetic */ int b(HW hW, int n2) {
        hW.bfS = n2;
        return hW.bfS;
    }
}

