/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vX
 */
public class vx_0
extends aoZ {
    private static final String auc = "DefaultBold16";
    private static final String aud = "chatScreamingBubble";
    private static final String aue = "BubbleArrowLeft";
    private static final String auf = "BubbleArrowRight";

    public vx_0() {
        super(null);
    }

    public void a(yf_0 yf_02) {
        super.a(yf_02);
        this.iR(auc);
        this.a(aud, new agj_1(40, 40));
        this.d(yf_02.isToRight() ? aue : auf, yf_02.isToRight() ? 35 : -35, 3);
        yf_02.setXOffset(yf_02.isToRight() ? -5 : 5);
    }
}

