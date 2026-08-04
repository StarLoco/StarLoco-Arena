/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from aAS
 */
public enum aas_2 {
    dqi(XugglerJNI.IAudioSamples_CH_FRONT_LEFT_get()),
    dqj(XugglerJNI.IAudioSamples_CH_FRONT_RIGHT_get()),
    dqk(XugglerJNI.IAudioSamples_CH_FRONT_CENTER_get()),
    dql(XugglerJNI.IAudioSamples_CH_LOW_FREQUENCY_get()),
    dqm(XugglerJNI.IAudioSamples_CH_BACK_LEFT_get()),
    dqn(XugglerJNI.IAudioSamples_CH_BACK_RIGHT_get()),
    dqo(XugglerJNI.IAudioSamples_CH_FRONT_LEFT_OF_CENTER_get()),
    dqp(XugglerJNI.IAudioSamples_CH_FRONT_RIGHT_OF_CENTER_get()),
    dqq(XugglerJNI.IAudioSamples_CH_BACK_CENTER_get()),
    dqr(XugglerJNI.IAudioSamples_CH_SIDE_LEFT_get()),
    dqs(XugglerJNI.IAudioSamples_CH_SIDE_RIGHT_get()),
    dqt(XugglerJNI.IAudioSamples_CH_TOP_CENTER_get()),
    dqu(XugglerJNI.IAudioSamples_CH_TOP_FRONT_LEFT_get()),
    dqv(XugglerJNI.IAudioSamples_CH_TOP_FRONT_CENTER_get()),
    dqw(XugglerJNI.IAudioSamples_CH_TOP_FRONT_RIGHT_get()),
    dqx(XugglerJNI.IAudioSamples_CH_TOP_BACK_LEFT_get()),
    dqy(XugglerJNI.IAudioSamples_CH_TOP_BACK_CENTER_get()),
    dqz(XugglerJNI.IAudioSamples_CH_TOP_BACK_RIGHT_get()),
    dqA(XugglerJNI.IAudioSamples_CH_STEREO_LEFT_get()),
    dqB(XugglerJNI.IAudioSamples_CH_STEREO_RIGHT_get()),
    dqC(XugglerJNI.IAudioSamples_CH_LAYOUT_MONO_get()),
    dqD(XugglerJNI.IAudioSamples_CH_LAYOUT_STEREO_get()),
    dqE(XugglerJNI.IAudioSamples_CH_LAYOUT_2_1_get()),
    dqF(XugglerJNI.IAudioSamples_CH_LAYOUT_SURROUND_get()),
    dqG(XugglerJNI.IAudioSamples_CH_LAYOUT_4POINT0_get()),
    dqH(XugglerJNI.IAudioSamples_CH_LAYOUT_2_2_get()),
    dqI(XugglerJNI.IAudioSamples_CH_LAYOUT_QUAD_get()),
    dqJ(XugglerJNI.IAudioSamples_CH_LAYOUT_5POINT0_get()),
    dqK(XugglerJNI.IAudioSamples_CH_LAYOUT_5POINT1_get()),
    dqL(XugglerJNI.IAudioSamples_CH_LAYOUT_5POINT0_BACK_get()),
    dqM(XugglerJNI.IAudioSamples_CH_LAYOUT_5POINT1_BACK_get()),
    dqN(XugglerJNI.IAudioSamples_CH_LAYOUT_7POINT1_get()),
    dqO(XugglerJNI.IAudioSamples_CH_LAYOUT_7POINT1_WIDE_get()),
    dqP(XugglerJNI.IAudioSamples_CH_LAYOUT_STEREO_DOWNMIX_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static aas_2 nf(int n2) {
        aas_2[] aas_2Array = (aas_2[])aas_2.class.getEnumConstants();
        if (n2 < aas_2Array.length && n2 >= 0 && aas_2Array[n2].hU == n2) {
            return aas_2Array[n2];
        }
        for (aas_2 aas_22 : aas_2Array) {
            if (aas_22.hU != n2) continue;
            return aas_22;
        }
        throw new IllegalArgumentException("No enum " + aas_2.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aas_2() {
        this.hU = agz_0.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aas_2() {
        void var3_1;
        this.hU = var3_1;
        agz_0.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aas_2() {
        void var3_1;
        this.hU = var3_1.hU;
        agz_0.bF(this.hU + 1);
    }
}

