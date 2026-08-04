/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from adv
 */
public enum adv_2 {
    cmI((adX)new qv_2(), 9, 3),
    cmJ((adX)new zr(), 9, 3),
    cmK((adX)new vx_0(), 15, 3),
    cmL((adX)new zr(), 2, 0);

    private static final Pattern cmM;
    private static final Pattern cmN;
    private static final Pattern cmO;
    private final adX cmP;
    private final int cmQ;
    private final int cmR;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private adv_2(int n2) {
        void var5_3;
        void var4_2;
        this.cmP = (adX)n2;
        this.cmQ = var4_2;
        this.cmR = var5_3;
    }

    public adX asE() {
        return this.cmP;
    }

    public int asF() {
        return this.cmQ;
    }

    public int asG() {
        return this.cmR;
    }

    public static final pf_0 hz(String string) {
        pf_0 pf_02 = new pf_0();
        adv_2 adv_22 = cmI;
        Matcher matcher = cmM.matcher(string);
        Matcher matcher2 = cmN.matcher(string);
        Matcher matcher3 = cmO.matcher(string);
        if (matcher.matches()) {
            if (string.startsWith("!!")) {
                string = string.replaceFirst("!!", "");
            }
            adv_22 = cmK;
        } else if (matcher2.matches()) {
            string = string.replaceFirst("\\*\\*", "");
            adv_22 = cmJ;
        } else if (matcher3.matches()) {
            string = string.replaceFirst("--", "");
            adv_22 = cmL;
        }
        pf_02.ac((Object)adv_22);
        pf_02.ad(string);
        return pf_02;
    }

    static {
        cmM = Pattern.compile("(.*!!.*)", 32);
        cmN = Pattern.compile("\\*\\*.*", 32);
        cmO = Pattern.compile("--.*", 32);
    }
}

