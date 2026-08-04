/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aCt
 */
public class act_2
extends pk_1 {
    private static Logger a = Logger.getLogger(act_2.class);
    public static final String TAG = "RadioButtonAppearance";

    public String getTag() {
        return TAG;
    }

    public void abR() {
        if (this.bDt) {
            return;
        }
        awz_0 awz_02 = this.DD.getElementMap().ir(((dl_1)this.DD).getGroupId());
        if (awz_02 != null) {
            for (dl_1 dl_12 : awz_02.getRadioButtonList()) {
                if (!dl_12.getAppearance().isChecked() || dl_12.getAppearance() == this) continue;
                dl_12.getAppearance().aOo();
            }
            super.abR();
        }
    }

    public void aOo() {
        if (this.bDt) {
            super.abR();
        }
    }
}

