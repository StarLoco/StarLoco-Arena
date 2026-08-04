/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from QV
 */
public class qv_2
extends aoZ {
    private static final String bHX = "DefaultBold14";
    private static final String bHY = "chatBubble";
    private static final String bHZ = "BubbleArrowLeft";
    private static final String bIa = "BubbleArrowRight";

    public qv_2() {
        super(null);
    }

    public void a(yf_0 yf_02) {
        super.a(yf_02);
        this.iR(bHX);
        this.a(bHY, new agj_1(12, 20));
        this.d(yf_02.isToRight() ? bHZ : bIa, yf_02.isToRight() ? 15 : -15, 0);
        yf_02.setXOffset(yf_02.isToRight() ? -5 : 5);
    }
}

