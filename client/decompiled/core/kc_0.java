/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from KC
 */
public final class kc_0
extends xN {
    ff_2 boI = null;

    public kc_0(lc_0 lc_02, String string, short s, atu_0 atu_02, String string2, anb_1[] anb_1Array, atu_0[] atu_0Array, List list) {
        super(lc_02, string, s, atu_02, string2, anb_1Array, atu_0Array, list);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.name);
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

