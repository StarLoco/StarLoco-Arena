/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from adc
 */
public enum adc_0 implements ro_2
{
    clK("rememberLastLogin", ss_2.bLU),
    clL("lastLogin", ss_2.bLU),
    clM("lastServer", ss_2.bLU),
    clN("saveReplays", ss_2.bLU),
    clO("activateParticles", ss_2.bLU),
    clP("specialDeckEquipedCards", ss_2.bLU),
    clQ("shadersActivated", ss_2.bLU),
    clR("shadersEnabled", ss_2.bLU),
    clS("alphaMaskActivated", ss_2.bLU),
    clT("vsyncActivated", ss_2.bLU),
    clU("gridActivated", ss_2.bLU),
    clV("showPrebuildTeam", ss_2.bLU),
    clW("inverseMouseControl", ss_2.bLU),
    clX("showEvolutionBonus", ss_2.bLU),
    clY("showEvolutionLevel", ss_2.bLU),
    clZ("showFighterMoveRange", ss_2.bLU),
    cma("lastSelectedTeamPresetId", ss_2.bLU),
    cmb("lastSelectedGameModeId", ss_2.bLU);

    private final String fZ;
    private final ss_2 cmc;

    /*
     * WARNING - void declaration
     */
    private adc_0() {
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

