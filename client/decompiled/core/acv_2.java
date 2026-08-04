/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCv
 */
public class acv_2
extends o_0 {
    private static int azG = 2500;
    private static int cUu = 200;
    private static int aAu = 90;

    public acv_2(String string) {
        super(abw_1.e("coprgtb", 0, 12), string, azG);
    }

    protected void init() {
        super.init();
        this.setMaxWidth(cUu);
        this.setYOffset(aAu);
        this.a(0.0f, 0.0f, 0.0f, 0.8f);
        this.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void setText(String string) {
        this.EL();
        this.setDuration(azG);
        super.setText(string);
    }
}

