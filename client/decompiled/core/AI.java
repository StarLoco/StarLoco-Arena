/*
 * Decompiled with CFR 0.152.
 */
public enum AI implements rk_0
{
    aHw(1, "Bonus ou malus d'XP en % (conso : rien)"),
    aHx(2, "Bonus ou malus d'XP (conso : idem)"),
    aHy(3, "Bonus ou malus d'XP en % pour l'adversaire (conso : rien)"),
    aHz(4, "Bonus ou malus d'XP pour l'adversaire (conso : rien)"),
    aHA(5, "Modification des blessures de ses combattants (conso : soigne toutes les blessures graves)"),
    aHB(6, "Modification des blessures des combattants adverses (conso : rien)"),
    aHC(7, "Modification des chances de mort de ses combattants (conso : rien)"),
    aHD(8, "Modification des chances de mort des combattants adverses (conso : rien)"),
    aHE(9, "Modification du moral de ses combattants (conso : idem)"),
    aHF(10, "Modification du moral des combattants adverses (conso : rien)"),
    aHG(11, "Donne x% de chance d'annuler les blessures en fin de combat (conso : soigne toutes les blessures l\u00e9g\u00e8res)"),
    aHH(12, "Transf\u00e8re de l'XP du combattant le plus exp\u00e9riment\u00e9 vers le moins exp\u00e9riment\u00e9 (conso : rien)"),
    aHI(13, "Donne x% de chance de ressusciter un combattant qui vient de mourir (conso : idem sur combattant du cimeti\u00e8re)"),
    aHJ(14, "Modifie le gain de r\u00e9putation (conso : rien)"),
    aHK(15, "Applique une condition (conso : idem)"),
    aHL(16, "Modification de fatigue de ses combattants (conso : idem)"),
    aHM(17, "Modification de fatigue des combattants adverses (conso : rien)"),
    aHN(18, "Modification du bonus au drop (conso : rien)"),
    aHO(19, "Modification des chances de drop (conso : rien)"),
    aHP(20, "Modification du niveau minimum des objets drop\u00e9s (conso : rien)"),
    aHQ(21, "Modification du niveau maximum des objets drop\u00e9s (conso : rien)");

    private short fL;
    private String fM;

    /*
     * WARNING - void declaration
     */
    private AI() {
        void var4_2;
        void var3_1;
        this.fL = var3_1;
        this.fM = var4_2;
    }

    public short tI() {
        return this.fL;
    }

    public String cC() {
        return String.valueOf(this.fL);
    }

    public String cD() {
        return this.fM;
    }

    public String cE() {
        return this.toString();
    }
}

