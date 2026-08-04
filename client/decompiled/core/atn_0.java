/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atN
 */
public class atn_0
extends lj_0 {
    private static int azG = 2500;
    private static int cUu = 200;
    private static int cUv = 45;
    private static int aAt = -5;
    private static int aAu = 80;

    public atn_0(String string) {
        super(abw_1.e("SansSerif", 0, 12), string);
        int n2 = 0;
        if (string != null) {
            n2 = string.length() * 50;
        }
        this.setDuration(azG + n2);
        this.setMaxWidth(cUu);
        this.setMinWidth(cUv);
        this.setXOffset(aAt);
        this.setYOffset(aAu);
        this.init();
    }

    public void setText(String string) {
        super.setText(string);
        int n2 = 0;
        if (string != null) {
            n2 = string.length() * 50;
        }
        this.setDuration(azG + n2);
    }
}

