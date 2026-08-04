/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Qs
 */
public class qs_1
extends aht_1 {
    public static final String TAG = "WindowMovePoint";
    public static final String aTJ = "WMP";
    private aab_2 bGA;
    private boolean bGB = false;
    private boolean bGC = false;
    private int bGD;
    private int bGE;
    private ex_2 bGF;
    private ov_1 aDc;
    private boolean ba = true;
    private boolean mH = true;
    public static final int ej = "horizontal".hashCode();
    public static final int bGG = "vertical".hashCode();

    public String getTag() {
        return TAG;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public boolean isVertical() {
        return this.mH;
    }

    public void setVertical(boolean bl2) {
        this.mH = bl2;
    }

    public aab_2 getWindow() {
        return this.bGA;
    }

    public void setDragMousePosition(int n2, int n3) {
        this.bGB = true;
        this.bGD = n2 - this.bGA.getDisplayX();
        this.bGE = n3 - this.bGA.getDisplayY();
    }

    public void ade() {
        this.aDc = new jx_2(this);
        ago_2.getInstance().a(qe_1.bFA, this.aDc, false);
        this.a(qe_1.bFv, new jw_2(this), false);
    }

    public void yx() {
        super.yx();
        this.bGA = (aab_2)this.getWidgetParentOfType(aab_2.class);
        if (this.bGA != null) {
            this.bGF = (ex_2)this.bGA.getWidgetParentOfType(ex_2.class);
            this.bGA.i(this);
        }
    }

    public void j() {
        super.j();
        ago_2.getInstance().b(qe_1.bFA, this.aDc, false);
        this.bGA = null;
    }

    public void b() {
        super.b();
        this.ade();
        this.setCursorType(xy_0.bYn);
        this.dyc = false;
        this.ba = true;
        this.mH = true;
    }

    public void a(air_1 air_12) {
        qs_1 qs_12 = (qs_1)air_12;
        super.a(air_12);
        qs_12.setHorizontal(this.ba);
        qs_12.setVertical(this.mH);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == bGG) {
            this.setVertical(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }

    public adg_2 getWidget(int n2, int n3) {
        return ago_2.getInstance().isMovePointMode() ? this : super.getWidget(n2, n3);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        if (bl2) {
            this.setCursorType(xy_0.bYn);
        } else {
            this.setCursorType(xy_0.bYl);
        }
    }

    static /* synthetic */ boolean a(qs_1 qs_12) {
        return qs_12.bGB;
    }

    static /* synthetic */ aab_2 b(qs_1 qs_12) {
        return qs_12.bGA;
    }

    static /* synthetic */ boolean a(qs_1 qs_12, boolean bl2) {
        qs_12.bGB = bl2;
        return qs_12.bGB;
    }

    static /* synthetic */ boolean b(qs_1 qs_12, boolean bl2) {
        qs_12.bGC = bl2;
        return qs_12.bGC;
    }

    static /* synthetic */ boolean c(qs_1 qs_12) {
        return qs_12.bGC;
    }

    static /* synthetic */ int a(qs_1 qs_12, int n2) {
        qs_12.bGD = n2;
        return qs_12.bGD;
    }

    static /* synthetic */ int b(qs_1 qs_12, int n2) {
        qs_12.bGE = n2;
        return qs_12.bGE;
    }

    static /* synthetic */ boolean d(qs_1 qs_12) {
        return qs_12.ba;
    }

    static /* synthetic */ int e(qs_1 qs_12) {
        return qs_12.bGD;
    }

    static /* synthetic */ boolean f(qs_1 qs_12) {
        return qs_12.mH;
    }

    static /* synthetic */ int g(qs_1 qs_12) {
        return qs_12.bGE;
    }

    static /* synthetic */ ex_2 h(qs_1 qs_12) {
        return qs_12.bGF;
    }
}

