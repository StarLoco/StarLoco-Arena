/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from zX
 */
public class zx_1
extends xs_1 {
    private static final Logger a = Logger.getLogger(zx_1.class);
    private att_0 aGK;
    private adg_2 DD;
    private String aGL;
    private String aJ;

    public zx_1(att_0 att_02, adg_2 adg_22, String string) {
        this.aGK = att_02;
        this.DD = adg_22;
        this.aGL = string;
    }

    public zx_1(att_0 att_02, String string, String string2) {
        this.aGK = att_02;
        this.aJ = string;
        this.aGL = string2;
    }

    public xs_1 getConstraint() {
        if (this.DD == null) {
            afq_1 afq_12 = add_1.aOG().azj();
            if (afq_12 == null) {
                afq_12 = add_1.aOG().azj();
            }
            this.DD = (adg_2)afq_12.R(this.aJ);
            if (this.DD == null) {
                a.warn((Object)("Impossible de trouver le widget " + this.aJ));
            }
        }
        if (this.aGK.getConstraint(this.DD) == null) {
            return null;
        }
        return this.aGK.getConstraint(this.DD).getConstraint(this.aGL);
    }

    public int getValue() {
        xs_1 xs_12 = this.getConstraint();
        return xs_12 != null ? xs_12.getValue() : 0;
    }

    public void setValue(int n2) {
        this.getConstraint().setValue(n2);
    }
}

