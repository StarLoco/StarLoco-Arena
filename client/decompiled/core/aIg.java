/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aIg
extends aht_1 {
    private static Logger a = Logger.getLogger(aIg.class);
    public static final String TAG = "ScrollBar";
    public static final String dOJ = "horizontalSlider";
    public static final String dOK = "verticalSlider";
    public static final String dOL = "horizontalIncreaseButton";
    public static final String dOM = "verticalIncreaseButton";
    public static final String dON = "horizontalDecreaseButton";
    public static final String dOO = "verticalDecreaseButton";
    private static final int dOP = 0;
    private static final int SCROLL_UP = 1;
    private static final int SCROLL_DOWN = 2;
    private int dOQ = 0;
    private ov_1 dOR;
    private ov_1 cvS;
    private ov_1 dOS;
    private ov_1 dOT;
    private azP dOU = new azP(this, null);
    private boolean ba = false;
    private float cTk = 0.05f;
    private aqq_0 apb;
    private aqq_0 apc;
    private atg_0 dOV;
    public static final int ej = "horizontal".hashCode();
    public static final int dOW = "buttonJump".hashCode();
    public static final int dL = "value".hashCode();

    public String getTag() {
        return TAG;
    }

    public float getValue() {
        return this.dOV.getValue();
    }

    public void setValue(float f) {
        this.dOV.setValue(f);
    }

    public float getButtonJump() {
        return this.cTk;
    }

    public void setButtonJump(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.cTk = f;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
        this.dOV.setHorizontal(bl2);
    }

    public atg_0 getSlider() {
        return this.dOV;
    }

    public void setSlider(atg_0 atg_02) {
        this.dOV = atg_02;
    }

    public aqq_0 getDecreaseButton() {
        return this.apc;
    }

    public void setDecreaseButton(aqq_0 aqq_02) {
        this.apc = aqq_02;
    }

    public void setIncreaseButton(aqq_0 aqq_02) {
        this.apb = aqq_02;
    }

    public aqq_0 getIncreaseButton() {
        return this.apb;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (this.ba || bl2) {
            if (dOJ.equalsIgnoreCase(string)) {
                return this.dOV;
            }
            if (dON.equalsIgnoreCase(string)) {
                return this.apc;
            }
            if (dOL.equalsIgnoreCase(string)) {
                return this.apb;
            }
        }
        if (!this.ba || bl2) {
            if (dOK.equalsIgnoreCase(string)) {
                return this.dOV;
            }
            if (dOO.equalsIgnoreCase(string)) {
                return this.apc;
            }
            if (dOM.equalsIgnoreCase(string)) {
                return this.apb;
            }
        }
        return null;
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        this.apb.setEnabled(bl2);
        this.apc.setEnabled(bl2);
        this.dOV.setEnabled(bl2);
        this.dOV.setVisible(bl2);
    }

    public void aUC() {
        int n2 = 0;
        int n3 = 0;
        if (this.ba) {
            this.apc.setPosition(n2, n3);
            this.dOV.setPosition(n2 + this.apc.getWidth(), n3);
            n2 = this.getAppearance().getContentWidth() - this.apc.getWidth();
            this.apb.setPosition(n2, n3);
        } else {
            n3 = this.getAppearance().getContentHeight() - this.apb.getHeight();
            this.apb.setPosition(n2, n3);
            n3 = 0;
            this.apc.setPosition(n2, n3);
            this.dOV.setPosition(n2, n3 + this.apc.getHeight());
        }
    }

    private void aUD() {
        if (this.dOQ != 0) {
            aam_1.aMF().b(this.dOU);
            aam_1.aMF().a(this.dOU, 150L, this.dOQ, 1);
        }
    }

    public void aUE() {
        this.dOR = new oz_2(this);
        this.cvS = new ow_1(this);
        this.dOS = new ox_2(this);
        this.dOT = new oP(this);
        ago_2.getInstance().a(qe_1.bFA, this.dOR, false);
        this.a(qe_1.bFB, this.cvS, false);
        this.apb.a(qe_1.bFy, this.dOR, false);
        this.apb.a(qe_1.bFz, this.dOS, false);
        this.apb.a(qe_1.bFx, this.dOS, false);
        this.apc.a(qe_1.bFy, this.dOR, false);
        this.apc.a(qe_1.bFz, this.dOT, false);
        this.apc.a(qe_1.bFx, this.dOT, false);
    }

    public void j() {
        super.j();
        this.apb = null;
        this.apc = null;
        this.dOV = null;
        ago_2.getInstance().b(qe_1.bFA, this.dOR, false);
    }

    public void b() {
        super.b();
        ch ch2 = new ch(this, null);
        ch2.b();
        this.a(ch2);
        this.apb = new aqq_0();
        this.apb.b();
        this.apb.setCanBeCloned(false);
        this.apb.setClickSoundId(aek.atD().atM());
        this.apc = new aqq_0();
        this.apc.b();
        this.apc.setCanBeCloned(false);
        this.apc.setClickSoundId(aek.atD().atN());
        this.dOV = new atg_0();
        this.dOV.b();
        this.dOV.setCanBeCloned(false);
        this.dyc = false;
        this.a(this.apc);
        this.a(this.apb);
        this.a(this.dOV);
        this.aUE();
    }

    public void a(air_1 air_12) {
        aIg aIg2 = (aIg)air_12;
        super.a((air_1)aIg2);
        aIg2.ba = this.ba;
        aIg2.cTk = this.cTk;
        aIg2.b(qe_1.bFB, this.cvS, false);
        aIg2.getIncreaseButton().b(qe_1.bFx, this.dOS, false);
        aIg2.getIncreaseButton().b(qe_1.bFz, this.dOS, false);
        aIg2.getIncreaseButton().b(qe_1.bFy, this.dOR, false);
        aIg2.getDecreaseButton().b(qe_1.bFx, this.dOT, false);
        aIg2.getDecreaseButton().b(qe_1.bFz, this.dOT, false);
        aIg2.getDecreaseButton().b(qe_1.bFy, this.dOR, false);
        aIg2.dyg = true;
        aIg2.setNeedsToPreProcess();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == dOW) {
            this.setButtonJump(Gr.getFloat(string));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(object));
        } else if (n2 == dOW) {
            this.setButtonJump(Gr.getFloat(object));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ void a(aIg aIg2) {
        aIg2.aUD();
    }

    static /* synthetic */ aqq_0 b(aIg aIg2) {
        return aIg2.apb;
    }

    static /* synthetic */ aqq_0 c(aIg aIg2) {
        return aIg2.apc;
    }

    static /* synthetic */ atg_0 d(aIg aIg2) {
        return aIg2.dOV;
    }

    static /* synthetic */ boolean e(aIg aIg2) {
        return aIg2.ba;
    }

    static /* synthetic */ int a(aIg aIg2, int n2) {
        aIg2.dOQ = n2;
        return aIg2.dOQ;
    }

    static /* synthetic */ azP f(aIg aIg2) {
        return aIg2.dOU;
    }

    static /* synthetic */ float g(aIg aIg2) {
        return aIg2.cTk;
    }

    static /* synthetic */ int h(aIg aIg2) {
        return aIg2.dOQ;
    }
}

