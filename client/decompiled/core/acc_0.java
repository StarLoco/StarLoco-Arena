/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Renamed from acC
 */
public final class acc_0
extends xN {
    adi_0 ckw = null;
    public xa ckx = null;
    Map cky = new HashMap();

    public acc_0(lc_0 lc_02, String string, short s, anb_1[] anb_1Array, atu_0[] atu_0Array, xa xa2, List list) {
        super(lc_02, string, s, new gw_1(lc_02, 0), "<init>", anb_1Array, atu_0Array, list);
        this.ckx = xa2;
        if (xa2 != null) {
            xa2.a(this);
        }
    }

    public azV aro() {
        return (azV)this.Dw();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.aro().getClassName());
        stringBuffer.append('(');
        anb_1[] anb_1Array = this.azz;
        for (int j = 0; j < anb_1Array.length; ++j) {
            if (j > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(anb_1Array[j].toString());
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public void a(ea_2 ea_22) {
        ea_22.a(this);
    }
}

