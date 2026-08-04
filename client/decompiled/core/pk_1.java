/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Pk
 */
public class pk_1
extends ani_2 {
    public static final String TAG = "ToggleButtonAppearance";
    public static final String bDp = "selected";
    public static final String bDq = "disabledSelected";
    public static final String bDr = "mouseHoverSelected";
    public static final String bDs = "pressedSelected";
    protected boolean bDt = false;

    public String getTag() {
        return TAG;
    }

    public boolean isChecked() {
        return this.bDt;
    }

    public void abR() {
        this.bDt = !this.bDt;
        vY vY2 = new vY(this.DD, this.bDt);
        this.DD.f(vY2);
        this.aCo();
    }

    protected void abS() {
        if (this.bDt) {
            if (this.OD) {
                if (this.cIB) {
                    if (this.cIA) {
                        this.setEnabled(bDs, true);
                    } else {
                        this.setEnabled(bDr, true);
                    }
                } else {
                    this.setEnabled(bDp, true);
                }
            } else {
                this.setEnabled(bDq, true);
            }
        } else {
            super.abS();
        }
    }

    public void a(air_1 air_12) {
        pk_1 pk_12 = (pk_1)air_12;
        super.a((air_1)pk_12);
        pk_12.bDt = this.bDt;
    }

    public void b() {
        super.b();
        this.bDt = false;
    }
}

