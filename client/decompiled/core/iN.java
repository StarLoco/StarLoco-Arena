/*
 * Decompiled with CFR 0.152.
 */
public class iN
implements ov_1 {
    private final JX nr;
    private final String mO;
    private jJ[] ns;

    iN(JX jX, String string, jJ[] jJArray) {
        this.nr = jX;
        this.mO = string;
        this.ns = jJArray;
    }

    iN(JX jX, String string) {
        this.nr = jX;
        this.mO = string;
        this.ns = null;
    }

    public boolean a(ke ke2) {
        this.nr.a(this.mO, this.ns, this.d(ke2));
        return false;
    }

    public boolean lG() {
        this.nr.a(this.mO, this.ns, new amd_0[0]);
        return false;
    }

    void a(jJ[] jJArray) {
        this.ns = jJArray;
    }

    private amd_0 d(ke ke2) {
        switch (ke2.aV()) {
            case bFB: 
            case bFC: 
            case bFA: 
            case bFz: 
            case bFt: 
            case bFD: 
            case bFx: 
            case bFy: {
                amd_0 amd_02 = new amd_0("event");
                abd_1 abd_12 = (abd_1)ke2;
                amd_02.u("button", abd_12.getButton());
                amd_02.u("x", abd_12.getScreenX());
                amd_02.u("y", abd_12.getScreenY());
                amd_02.u("rotation", abd_12.aNb());
                return amd_02;
            }
            case bEZ: {
                amd_0 amd_03 = new amd_0("event");
                nx_2 nx_22 = (nx_2)ke2;
                return amd_03;
            }
            case bFa: {
                amd_0 amd_04 = new amd_0("event");
                lu_1 lu_12 = (lu_1)ke2;
                vP vP2 = lu_12.qK().getColor();
                amd_04.u("r", Float.valueOf(vP2.Cp()));
                amd_04.u("g", Float.valueOf(vP2.Cq()));
                amd_04.u("b", Float.valueOf(vP2.Cr()));
                amd_04.u("a", Float.valueOf(vP2.getAlpha()));
                return amd_04;
            }
            case bFH: {
                amd_0 amd_05 = new amd_0("event");
                Kf kf = (Kf)ke2;
                amd_05.u("value", Float.valueOf(kf.getValue()));
                return amd_05;
            }
            case bFm: 
            case bFn: 
            case bFo: {
                amd_0 amd_06 = new amd_0("event");
                aqG aqG2 = (aqG)ke2;
                amd_06.u("keyChar", Character.valueOf(aqG2.getKeyChar()));
                amd_06.u("keyCode", aqG2.getKeyCode());
                amd_06.u("modifiers", aqG2.getModifiers());
                return amd_06;
            }
        }
        return null;
    }
}

