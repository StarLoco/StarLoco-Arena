/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

public enum amA {
    cHA("GL render targets", "renderTargetsSupported"),
    cHB("GL multi-sampling", "multiSamplingSupported"),
    cHC("GL multi-texturing", "multiTexturingSupported"),
    cHD("GL texture compression", "textureCompressionSupported"),
    cHE("GL fragment shaders", "fragmentShadersSupported"),
    cHF("GL vertex shaders", "vertexShadersSupported"),
    cHG("AL effects", "alEffectsSupported"),
    cHH("AL filters", "alFiltersSupported");

    private static final HashMap cHI;
    private final String cHJ;
    private final String bEp;

    /*
     * WARNING - void declaration
     */
    private amA() {
        void var4_2;
        void var3_1;
        this.cHJ = var3_1;
        this.bEp = var4_2;
    }

    public String aBM() {
        return this.cHJ;
    }

    public String getPropertyName() {
        return this.bEp;
    }

    public static amA iI(String string) {
        return (amA)((Object)cHI.get(string));
    }

    static {
        cHI = new HashMap();
        for (amA amA2 : amA.values()) {
            cHI.put(amA2.getPropertyName(), amA2);
        }
    }
}

