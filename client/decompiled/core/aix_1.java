/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aix
 */
public class aix_1 {
    private int aX;
    private jv cyM;
    private alr_2 cyN;

    public aix_1(int n2, jv jv2, alr_2 alr_22) {
        this.aX = n2;
        this.cyM = jv2;
        this.cyN = alr_22;
    }

    public int ao() {
        return this.aX;
    }

    public void h(int n2) {
        this.aX = n2;
    }

    public jv ayp() {
        jv jv2 = this.cyM;
        if (jv2 == null) {
            throw new IllegalStateException("@NotNull method com/ankamagames/framework/kernel/core/resource/ResourceFactoryDescriptor.getResourceFactory must not return null");
        }
        return jv2;
    }

    public void a(jv jv2) {
        this.cyM = jv2;
    }

    public alr_2 ayq() {
        alr_2 alr_22 = this.cyN;
        if (alr_22 == null) {
            throw new IllegalStateException("@NotNull method com/ankamagames/framework/kernel/core/resource/ResourceFactoryDescriptor.getContextFactory must not return null");
        }
        return alr_22;
    }

    public void a(alr_2 alr_22) {
        this.cyN = alr_22;
    }
}

