/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Renamed from aAB
 */
public class aab_2
extends ex_2 {
    public static final String TAG = "Window";
    public static final String dpr = "MessageBox";
    public static final String dps = "titleBar";
    public static final String dpt = "label";
    public static final String dpu = "content";
    public static final String dpv = "closeButton";
    public static final String dpw = "minButton";
    public static final String dpx = "maxButton";
    private boolean dpy;
    private boolean dpz = true;
    private afo dpA;
    private boolean dpB = true;
    private boolean dpC = true;
    private HashMap dpD = null;
    private ArrayList dpE = new ArrayList();
    private ArrayList dpF = null;
    private ly_2 dpG;
    private String dpH;
    private String dpI;
    private final ArrayList dpJ = new ArrayList();
    public static final int dpK = "canBePushedToTop".hashCode();
    public static final int dpL = "movable".hashCode();
    public static final int dpM = "stickWithinDisplayBounds".hashCode();
    public static final int dpN = "stickWithinRootContainer".hashCode();
    public static final int dpO = "horizontalDialog".hashCode();
    public static final int dpP = "verticalDialog".hashCode();

    public void f(na_1 na_12) {
        if (na_12 instanceof dz_2) {
            this.a(na_12);
        } else if (na_12 instanceof Zb) {
            this.a(na_12);
        } else if (na_12 instanceof afz_1) {
            this.a(na_12);
        } else if (na_12 instanceof axf) {
            this.a(na_12);
        } else {
            super.f(na_12);
        }
    }

    void i(qs_1 qs_12) {
        this.dpE.add(qs_12);
    }

    public String getTag() {
        return TAG;
    }

    public ArrayList getMovePoints() {
        return this.dpE;
    }

    public boolean isMovable() {
        return this.dpC;
    }

    public void setMovable(boolean bl2) {
        this.dpC = bl2;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        return null;
    }

    public boolean isStickWithinDisplayBounds() {
        return this.dpz;
    }

    public void setStickWithinDisplayBounds(boolean bl2) {
        this.dpz = bl2;
    }

    public boolean isStickWithinRootContainer() {
        return this.dpz;
    }

    public void setStickWithinRootContainer(boolean bl2) {
        this.dpz = bl2;
    }

    public void setStickData(afo afo2) {
        this.dpA = afo2;
    }

    public afo getStickData() {
        return this.dpA;
    }

    public void setStyle(String string, boolean bl2) {
        super.setStyle(string, bl2);
        if (this.dyh != null) {
            for (adg_2 adg_22 : this.dyh.values()) {
                adg_22.setStyle(this.getTag() + this.getStyle() + "$" + adg_22.getThemeElementName(), bl2);
            }
        }
    }

    public boolean getCanBePushedToTop() {
        return this.dpB;
    }

    public void setCanBePushedToTop(boolean bl2) {
        this.dpB = bl2;
    }

    public ly_2 getWindowState() {
        return this.dpG;
    }

    public void a(ajb_2 ajb_22) {
        if (ajb_22 != null && !this.dpJ.contains(ajb_22)) {
            this.dpJ.add(ajb_22);
        }
    }

    public void b(ajb_2 ajb_22) {
        this.dpJ.remove(ajb_22);
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        for (int j = this.dpJ.size() - 1; j >= 0; --j) {
            ((ajb_2)this.dpJ.get(j)).kk();
        }
        return bl2;
    }

    public String getHorizontalDialog() {
        return this.dpH;
    }

    public void setHorizontalDialog(String string) {
        this.dpH = string;
    }

    public String getVerticalDialog() {
        return this.dpI;
    }

    public void setVerticalDialog(String string) {
        this.dpI = string;
    }

    public boolean aMP() {
        return this.dpD != null;
    }

    public void a(ayu_0 ayu_02) {
        ArrayList<ayu_0> arrayList;
        if (this.dpD == null) {
            this.dpD = new HashMap();
        }
        if ((arrayList = (ArrayList<ayu_0>)this.dpD.get(ayu_02.getName())) == null) {
            arrayList = new ArrayList<ayu_0>();
            this.dpD.put(ayu_02.getName(), arrayList);
            if (this.dpG.toString().equalsIgnoreCase(ayu_02.getName())) {
                this.dpF = arrayList;
            }
        }
        if (!arrayList.contains(ayu_02)) {
            arrayList.add(ayu_02);
            ayu_02.setVisible(this.dpG.toString().equalsIgnoreCase(ayu_02.getName()));
        }
    }

    public void b(ayu_0 ayu_02) {
        if (this.dpD == null) {
            return;
        }
        ArrayList arrayList = (ArrayList)this.dpD.get(ayu_02.getName());
        if (arrayList == null) {
            return;
        }
        arrayList.remove(ayu_02);
    }

    public void setupDefaultLook() {
        this.setupLook(this.dpG.Yk());
    }

    public void setupLook(String string) {
        ly_2 ly_22;
        if (this.dpD == null) {
            return;
        }
        try {
            ly_22 = ly_2.valueOf(string.toUpperCase());
        }
        catch (Exception exception) {
            return;
        }
        ArrayList arrayList = (ArrayList)this.dpD.get(string);
        if (arrayList != null) {
            if (this.dpF != null && arrayList != this.dpF) {
                for (ayu_0 ayu_02 : this.dpF) {
                    ayu_02.setVisible(false);
                }
            }
            this.dpG = ly_22;
            this.dpF = arrayList;
            for (ayu_0 ayu_02 : arrayList) {
                ayu_02.setVisible(true);
            }
        }
    }

    public void aMQ() {
        eq_0 eq_02;
        if (this.dpB && (eq_02 = (eq_0)this.getParentOfType(eq_0.class)) != null) {
            eq_02.a(this);
            add_1.aOG().aOL().q(this.blb.getId(), true);
        }
    }

    private void ade() {
        this.a(qe_1.bFz, new avk(this), true);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        aab_2 aab_22 = (aab_2)air_12;
        aab_22.setMovable(this.dpC);
        aab_22.dpB = this.dpB;
        aab_22.dpy = this.dpy;
        aab_22.dpz = this.dpz;
    }

    public void j() {
        super.j();
        this.dpF = null;
        if (this.dpD != null) {
            for (ArrayList arrayList : this.dpD.values()) {
                arrayList.clear();
            }
            this.dpD.clear();
        }
        this.dpJ.clear();
        this.dpG = null;
        if (this.dpA != null) {
            acv_0.arH().b(this);
            this.dpA = null;
        }
        this.dpE.clear();
    }

    public void b() {
        super.b();
        ei_1 ei_12 = ei_1.checkOut();
        this.a(ei_12);
        this.dpC = true;
        this.dyc = false;
        this.dpG = ly_2.bsO;
        this.ade();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == dpK) {
            this.setCanBePushedToTop(Gr.getBoolean(string));
        } else if (n2 == dpL) {
            this.setMovable(Gr.getBoolean(string));
        } else if (n2 == dpM || n2 == dpN) {
            this.setStickWithinRootContainer(Gr.getBoolean(string));
        } else if (n2 == dpO) {
            this.setHorizontalDialog(if_12.eM(string));
        } else if (n2 == dpP) {
            this.setVerticalDialog(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == dpK) {
            this.setCanBePushedToTop(Gr.getBoolean(object));
        } else if (n2 == dpL) {
            this.setMovable(Gr.getBoolean(object));
        } else if (n2 == dpM || n2 == dpN) {
            this.setStickWithinRootContainer(Gr.getBoolean(object));
        } else if (n2 == dpO) {
            this.setHorizontalDialog((String)object);
        } else if (n2 == dpP) {
            this.setVerticalDialog((String)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

