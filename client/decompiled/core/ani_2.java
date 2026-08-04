/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ani
 */
public class ani_2
extends auL {
    private static Logger a = Logger.getLogger(ani_2.class);
    public static final String TAG = "ButtonAppearance";
    public static final String DEFAULT = "default";
    public static final String cIw = "disabled";
    public static final String cIx = "mouseHover";
    public static final String cIy = "pressed";
    private static final acl_0 uG = new ym_0(new apo_1());
    protected int cIz = 5;
    protected boolean cIA = false;
    protected boolean cIB = false;
    protected boolean OD = true;
    public static final int cIC = "gap".hashCode();

    public static ani_2 checkOut() {
        ani_2 ani_22;
        try {
            ani_22 = (ani_2)uG.adr();
            ani_22.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            ani_22 = new ani_2();
            ani_22.b();
        }
        return ani_22;
    }

    public String getTag() {
        return TAG;
    }

    public int getGap() {
        return this.cIz;
    }

    public void setGap(int n2) {
        this.cIz = n2;
    }

    public boolean isOver() {
        return this.cIB;
    }

    public boolean isArmed() {
        return this.cIA;
    }

    public void enter() {
        this.cIB = true;
        this.aCo();
    }

    public void exit() {
        this.cIB = false;
        this.aCo();
    }

    public void aCk() {
        this.cIA = true;
        this.aCo();
    }

    public void aCl() {
        if (this.cIA) {
            this.cIA = false;
            this.aCo();
        }
    }

    public void aCm() {
        if (this.OD) {
            this.OD = false;
            this.aCo();
        }
    }

    public void aCn() {
        if (!this.OD) {
            this.OD = true;
            this.aCo();
        }
    }

    protected void aCo() {
        this.anj();
        this.abS();
    }

    protected void abS() {
        if (this.OD) {
            if (this.cIB) {
                if (this.cIA) {
                    this.setEnabled(cIy, true);
                } else {
                    this.setEnabled(cIx, true);
                }
            } else {
                this.setEnabled(DEFAULT, true);
            }
        } else {
            this.setEnabled(cIw, true);
        }
    }

    public void a(air_1 air_12) {
        ani_2 ani_22 = (ani_2)air_12;
        super.a(air_12);
        ani_22.cIz = this.cIz;
    }

    public void b() {
        super.b();
        this.cIz = 5;
        this.cIA = false;
        this.cIB = false;
        this.OD = true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cIC) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setGap(Gr.R(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != cIC) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setGap(Gr.R(object));
        return true;
    }
}

