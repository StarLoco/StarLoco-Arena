/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Aj
 */
public class aj_0
extends Zb {
    public static final String TAG = "InteractiveBubbleAppearance";
    private wx_0 aGR = null;

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof wx_0) {
            this.aGR = (wx_0)na_12;
        }
    }

    public wx_0 getBubbleBorder() {
        return this.aGR;
    }

    public void j() {
        super.j();
        this.aGR = null;
    }
}

