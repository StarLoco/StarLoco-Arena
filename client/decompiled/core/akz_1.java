/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akZ
 */
public enum akz_1 implements ro_2
{
    cEu("language", ss_2.bLU),
    cEv("resolution", ss_2.bLU),
    cEw("musicVolume", ss_2.bLU),
    cEx("ambianceSoundsVolume", ss_2.bLU),
    cEy("uiSoundsVolume", ss_2.bLU),
    cEz("musicMute", ss_2.bLU),
    cEA("ambianceSoundsMute", ss_2.bLU),
    cEB("uiSoundsMute", ss_2.bLU),
    cEC("tooltipsDuration", ss_2.bLU),
    cED("tooltipsDisplay", ss_2.bLU),
    cEE("LODLevel", ss_2.bLU);

    private final String fZ;
    private final ss_2 cmc;

    /*
     * WARNING - void declaration
     */
    private akz_1() {
        void var4_2;
        void var3_1;
        this.fZ = var3_1;
        this.cmc = var4_2;
    }

    public String getKey() {
        return this.fZ;
    }

    public abk_0 wp() {
        return sr_0.a(this.cmc);
    }
}

