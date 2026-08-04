/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Font;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aOD
 */
public class aod_2
extends abS {
    private static final Logger a = Logger.getLogger(aod_2.class);
    public static final String TAG = "interactiveBubble";
    public static final String emk = "buttonContainer";
    public static final String eml = "clickLabel";
    public static final String emm = "text";
    private final ArrayList bBG = new ArrayList();
    private int emn;
    private int emo;
    private int emp;
    private String emq = "Arial Unicode MS";
    private int emr = 0;
    private int ems = 12;
    private boolean emt = false;
    private boolean emu = false;
    private boolean bUh = true;
    private boolean emv = false;
    private boolean emw = true;
    private ov_1 cvS = null;
    public static final int emx = "actAsButton".hashCode();
    public static final int emy = "bubbleText".hashCode();
    public static final int caS = "text".hashCode();
    public static final int emz = "closeOnClick".hashCode();
    public static final int emA = "forcedDisplaySpark".hashCode();

    public void a(String string, ov_1 ov_12, boolean bl2) {
        if (!this.emv) {
            aqq_0 aqq_02 = new aqq_0();
            this.bBG.add(aqq_02);
            aqq_02.b();
            aqq_02.setText(string);
            aqq_02.setExpandable(false);
            aqq_02.a(qe_1.bFB, ov_12, true);
            aqq_02.setEnabled(bl2);
            adg_2 adg_22 = this.getWidgetByThemeElementName(emk);
            if (adg_22 instanceof aht_1) {
                adg_22.a(aqq_02);
            }
            aqq_02.Aj();
            aqq_02.setStyle(TAG + this.getStyle() + "$button", true);
            adg_22 = this.getWidgetByThemeElementName(eml);
            if (adg_22 != null) {
                adg_22.setVisible(false);
            }
        } else {
            this.a(qe_1.bFB, ov_12, true);
            adg_2 adg_23 = this.getWidgetByThemeElementName(eml);
            if (adg_23 != null) {
                adg_23.setVisible(true);
            }
        }
    }

    public aj_0 getAppearance() {
        return (aj_0)this.cLZ;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof aj_0;
    }

    public void c(int n2, String string) {
        aqq_0 aqq_02 = (aqq_0)this.bBG.get(n2);
        if (aqq_02 != null) {
            aqq_02.setText(string);
        }
    }

    public void a(int n2, ov_1 ov_12, ov_1 ov_13) {
        aod_2 aod_22;
        aht_1 aht_12 = aod_22 = this.emv ? this : (aht_1)this.bBG.get(n2);
        if (aod_22 != null) {
            aod_22.b(qe_1.bFB, ov_12, true);
            aod_22.a(qe_1.bFB, ov_13, true);
        }
    }

    public void setText(String string) {
        adg_2 adg_22 = this.getWidgetByThemeElementName(emm);
        if (adg_22 != null && adg_22 instanceof yt_1) {
            ((yt_1)adg_22).setText(string);
        } else {
            a.warn((Object)"Le champ de texte n'a pas \u00e9t\u00e9 d\u00e9fini dans le XML");
        }
    }

    public boolean getActAsButton() {
        return this.emv;
    }

    public void setActAsButton(boolean bl2) {
        if (bl2 != this.emv) {
            this.emv = bl2;
        }
    }

    public final void setBubbleFontName(String string) {
        this.emq = string;
        this.invalidate();
    }

    public final void setBubbleFontStyle(int n2) {
        this.emr = n2;
        this.invalidate();
    }

    public final void setBubbleFontSize(int n2) {
        this.ems = n2;
        this.invalidate();
    }

    public final void setBubbleText(String string) {
        this.setText(string);
    }

    public ArrayList getButtons() {
        return this.bBG;
    }

    public void setForcedDisplaySpark(boolean bl2) {
        this.emu = true;
        this.emt = bl2;
        aj_0 aj_02 = this.getAppearance();
        if (aj_02 != null && aj_02.getBubbleBorder() != null) {
            aj_02.getBubbleBorder().setDisplaySpark(this.emt);
        }
        if ((aj_02 = (aj_0)this.dxQ) != null && aj_02.getBubbleBorder() != null) {
            aj_02.getBubbleBorder().setDisplaySpark(this.emt);
        }
    }

    public boolean isCloseOnClick() {
        return this.emw;
    }

    public void setCloseOnClick(boolean bl2) {
        if (this.emw != bl2) {
            this.emw = bl2;
            this.fu(this.emw);
        }
    }

    private void fu(boolean bl2) {
        if (bl2) {
            if (this.cvS != null) {
                this.b(qe_1.bFB, this.cvS, false);
            }
            this.cvS = new ac_0(this);
            this.a(qe_1.bFB, this.cvS, false);
        } else {
            this.b(qe_1.bFB, this.cvS, false);
        }
    }

    public void clear() {
        this.aab();
    }

    public void reset() {
        this.setText("");
        for (int j = this.bBG.size() - 1; j >= 0; --j) {
            ((aqq_0)this.bBG.get(j)).aab();
        }
        this.bBG.clear();
    }

    public final void show() {
        this.setVisible(true);
    }

    public final void hide() {
        this.setVisible(false);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        aod_2 aod_22 = (aod_2)air_12;
        if (this.emu) {
            aod_22.setForcedDisplaySpark(this.emt);
        }
        this.setActAsButton(this.emv);
        this.setCloseOnClick(this.emw);
    }

    public void yx() {
        super.yx();
        this.fu(this.emw);
    }

    public void j() {
        super.j();
        this.bBG.clear();
        this.cvS = null;
    }

    public void b() {
        super.b();
        Font font = new Font(this.emq, this.emr, this.ems);
        this.emu = false;
        this.emt = false;
        this.bUh = true;
        this.emv = false;
        this.emw = true;
        aj_0 aj_02 = new aj_0();
        aj_02.b();
        aj_02.setWidget(this);
        this.a(aj_02);
        wx_0 wx_02 = new wx_0();
        wx_02.b();
        this.cLZ.a((na_1)wx_02);
        this.setNeedsToPostProcess();
    }

    public final boolean cb(int n2) {
        super.cb(n2);
        return true;
    }

    public final void invalidate() {
        super.invalidate();
        this.a(this.getTarget(), this.emn, this.emo, this.emp);
    }

    public void a(aFy aFy2, int n2, int n3, int n4) {
        aj_0 aj_02;
        boolean bl2;
        if (!this.isUseTargetPositionning()) {
            return;
        }
        this.emn = n2;
        this.emo = n3;
        this.emp = n4;
        agV agV2 = add_1.aOG().aON();
        float f = agV2.adF();
        float f2 = agV2.adG();
        agj_1 agj_12 = this.getSize();
        float f3 = (float)n2 + f * 0.5f;
        float f4 = (float)n3 + f2 * 0.5f + (float)n4;
        int n5 = 0;
        int n6 = 0;
        int n7 = this.getScreenX();
        int n8 = this.getScreenY();
        if (n7 < 0) {
            n5 = -n7;
        } else if (n7 + agj_12.width > this.dxR.getAppearance().getContentWidth()) {
            n5 = this.dxR.getAppearance().getContentWidth() - agj_12.width - n7;
        }
        if (n8 < 0) {
            n6 = -n8;
        } else if (n8 + agj_12.height > this.dxR.getAppearance().getContentHeight()) {
            n6 = this.dxR.getAppearance().getContentHeight() - agj_12.height - n8;
        }
        boolean bl3 = bl2 = this.emu ? this.emt : true;
        if (!this.emt) {
            if (f3 < 0.0f || f3 > f) {
                bl2 = false;
            }
            if (f4 < 0.0f || f4 > f2) {
                bl2 = false;
            }
        }
        if (bl2 != this.bUh && (aj_02 = this.getAppearance()) != null && aj_02.getBubbleBorder() != null) {
            aj_02.getBubbleBorder().setDisplaySpark(bl2);
            this.bUh = bl2;
        }
        super.a(aFy2, (int)f3 - this.getHalfDisplayWidth(), (int)f4 - this.getHalfDisplayHeight(), 0);
    }

    public void setOffset(int n2, int n3) {
        super.setOffset(n2, n3);
    }

    public void setDisplaySpark(boolean bl2) {
        this.bUh = bl2;
    }

    public String toString() {
        adg_2 adg_22 = this.getWidgetByThemeElementName(emm);
        String string = "";
        if (adg_22 != null && adg_22 instanceof yt_1) {
            string = ((yt_1)adg_22).getText();
        }
        return "InteractiveBubble{m_text=" + string + ", m_targetX=" + this.emn + ", m_targetY=" + this.emo + ", m_screenX=" + this.getDisplayX() + ", m_screenY=" + this.getDisplayY() + '}';
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == emx) {
            this.setActAsButton(Gr.getBoolean(string));
        } else if (n2 == emy) {
            this.setBubbleText(if_12.eM(string));
        } else if (n2 == caS) {
            this.setText(if_12.eM(string));
        } else if (n2 == emz) {
            this.setCloseOnClick(Gr.getBoolean(string));
        } else if (n2 == emA) {
            this.setForcedDisplaySpark(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == emx) {
            this.setActAsButton(Gr.getBoolean(object));
        } else if (n2 == emy) {
            if (object == null) {
                this.setBubbleText(null);
            } else {
                this.setBubbleText(String.valueOf(object));
            }
        } else if (n2 == caS) {
            if (object == null) {
                this.setText(null);
            } else {
                this.setText(String.valueOf(object));
            }
        } else if (n2 == emz) {
            this.setCloseOnClick(Gr.getBoolean(object));
        } else if (n2 == emA) {
            this.setForcedDisplaySpark(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ aji_1 a(aod_2 aod_22) {
        return aod_22.blb;
    }
}

