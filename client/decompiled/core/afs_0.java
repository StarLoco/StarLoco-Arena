/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afS
 */
public enum afs_0 implements aeo_2
{
    csB(aMK.dYc.aXg()[0], new String[]{"JupeVetement", "Jupe2Vetement", "Ceinture"}),
    csC(aMK.dYd.aXg()[0], new String[]{"CheveuxBas", "CheveuxHaut", "CheveuxArriere", "Natte"}),
    csD(aMK.dYe.aXg()[0], new String[]{"CorpsHabit"}),
    csE(aMK.dYf.aXg()[0], new String[]{"Brassard"}),
    csF(aMK.dYg.aXg()[0], new String[]{"JambeHabit", "PiedHabit01", "PiedHabit02", "CuisseHabit"}),
    csG(aMK.dYh.aXg()[0], new String[]{"Epaulette-D", "Epaulette-G"}),
    csH(aMK.dYi.aXg()[0], new String[]{"Cape", "CapeBas"}),
    csI(aMK.dYj.aXg()[0], new String[]{"BassinVetement", "CuisseVetement", "JambeVetement"}),
    csJ(aMK.dYk.aXg()[0], new String[]{"TroncVetement", "CorpsVetement", "CorpsFemeleVetement", "EpauleVetement", "BrasVetement"}),
    csK(aMK.dYl.aXg()[0], new String[]{"Chapeau"}),
    csL(aMK.dYm.aXg()[0], new String[]{"Sashi"}),
    csM(aMK.dYw.aXg()[0], new String[]{"SymbolBg"}),
    csN(aMK.dYx.aXg()[0], new String[]{"SymbolFg"}),
    csO(aMK.dYn.aXg()[0], new String[]{"Familier"});

    private short nO;
    private String[] aCd;
    private static final zm_1 arf;

    /*
     * WARNING - void declaration
     */
    private afs_0() {
        void var4_2;
        void var3_1;
        this.nO = var3_1;
        this.aCd = var4_2;
    }

    public short ha() {
        return this.nO;
    }

    public String[] ES() {
        return this.aCd;
    }

    public static afs_0 bE(short s) {
        return (afs_0)arf.an(s);
    }

    static {
        arf = new zm_1();
        for (afs_0 afs_02 : afs_0.values()) {
            arf.b(afs_02.ha(), afs_02);
        }
    }
}

