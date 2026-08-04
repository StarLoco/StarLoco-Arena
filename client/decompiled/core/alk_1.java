/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from alK
 */
public class alk_1 {
    private static final Logger a = Logger.getLogger(alk_1.class);
    private static final boolean cR = false;
    private static alk_1 cFy = new alk_1();
    private final HashMap aPt = new HashMap();

    public af_1 a(abQ abQ2) {
        return (af_1)this.aPt.get(abQ2);
    }

    public boolean b(abQ abQ2) {
        return this.aPt.containsKey(abQ2);
    }

    public void c(abQ abQ2) {
        assert (!this.b(abQ2)) : "textrenderers should be created once";
        vg_2 vg_22 = abQ2.aD() ? new vg_2(abQ2.aqw(), abQ2.isAntiAliased(), false, uu.aqx) : new vg_2(abQ2.aqw(), abQ2.isAntiAliased(), false);
        vg_22.setUseVertexArrays(false);
        this.aPt.put(abQ2, vg_22);
    }

    public static alk_1 aAY() {
        return cFy;
    }
}

