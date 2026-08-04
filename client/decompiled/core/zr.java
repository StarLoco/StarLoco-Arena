/*
 * Decompiled with CFR 0.152.
 */
public class zr
extends aoZ {
    private static final String aFn = "grey";
    private static final String aFo = "chatThinkingBubble";
    private static final String aFp = "BubbleThinkingArrowLeft";
    private static final String aFq = "BubbleThinkingArrowRight";

    public zr() {
        super(null);
    }

    public void a(yf_0 yf_02) {
        super.a(yf_02);
        this.iR(aFn);
        this.a(aFo, new agj_1(30, 40));
        this.d(yf_02.isToRight() ? aFp : aFq, yf_02.isToRight() ? 25 : -25, 5);
        yf_02.setXOffset(yf_02.isToRight() ? -5 : 5);
    }
}

