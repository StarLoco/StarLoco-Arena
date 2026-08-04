/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.apache.log4j.Logger;

/*
 * Renamed from aDg
 */
public class adg_0 {
    private HashMap cwf = new HashMap();
    private static final adg_0 dwM = new adg_0();
    private static final Logger a = Logger.getLogger(adg_0.class);

    private adg_0() {
    }

    public static adg_0 aPh() {
        return dwM;
    }

    public void a(acq_2[] acq_2Array) {
        GL gL = GLU.getCurrentGL();
        if (acq_2Array != null) {
            for (acq_2 acq_22 : acq_2Array) {
                if (acq_22.q(gL)) {
                    a.info((Object)("Effet initialis\u00e9 : " + acq_22.getName()));
                    this.a(acq_22.getName(), acq_22);
                    continue;
                }
                a.warn((Object)("Effet incompatible avec le mat\u00e9riel : " + acq_22.getName()));
            }
        }
    }

    public void a(String string, acq_2 acq_22) {
        this.cwf.put(string, acq_22);
    }

    public acq_2 kS(String string) {
        return (acq_2)this.cwf.get(string);
    }

    public void kT(String string) {
        this.cwf.remove(string);
    }

    public void nq(int n2) {
        for (acq_2 acq_22 : this.cwf.values()) {
            acq_22.no(n2);
        }
    }
}

