/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajR
 */
public enum ajr_2 implements rk_0
{
    cBa(1, "Modifie le budget"),
    cBb(2, "Modifie le nombre minimum de combattant"),
    cBc(3, "Modifie le nombre maximum de combattant"),
    cBd(4, "Sort interdit"),
    cBe(5, "Equipement interdit"),
    cBf(6, "Sort autoris\u00e9"),
    cBg(7, "Equipement autoris\u00e9"),
    cBh(8, "Interdit tous les sorts"),
    cBi(9, "Interdit tous les \u00e9quipements"),
    cBj(10, "Modifie la dur\u00e9e en milliseconde du tour de chaque combattant"),
    cBk(11, "Modifie le tour du d\u00e9but de la mort subite"),
    cBl(12, "Lance un effet sur tous les combattants \u00e0 la cr\u00e9ation du combat"),
    cBm(13, "Multiplie les effets des cases bonus"),
    cBn(14, "Condition de victoire"),
    cBo(15, "Modifie le nombre de combattants de m\u00eame classe"),
    cBp(16, "Modifie le nombre de combattants d'une classe en particulier"),
    cBq(17, "Interdit une classe"),
    cBr(18, "Interdit toutes les classes"),
    cBs(19, "Modifie le prix des combattants d'une classe"),
    cBt(20, "Modifie le prix des combattants"),
    cBu(21, "Autorise une classe"),
    cBv(22, "Modifie le prix de tous les sorts"),
    cBw(23, "Modifie le prix de tous les \u00e9quipements"),
    cBx(24, "Modifie le prix d'un sort"),
    cBy(25, "Modifie le prix d'un \u00e9quipement"),
    cBz(26, "Choisir la liste d'\u00e9v\u00e8nements"),
    cBA(27, "Ajouter un sort de coach"),
    cBB(28, "Changer le nombre maximal de classes diff\u00e9rentes"),
    cBC(29, "Choisir une ar\u00e8ne"),
    cBD(30, "League maximale"),
    cBE(31, "Emp\u00eacher l'affichage des statistiques des opposants."),
    cBF(32, "Choisir la liste d'\u00e9v\u00e8nements de la mort subite."),
    cBG(900, "Param\u00e8tre de classe"),
    cBH(901, "Param\u00e8tre de sort de F\u00e9ca"),
    cBI(902, "Param\u00e8tre de sort d'Osamodas"),
    cBJ(903, "Param\u00e8tre de sort d'Enutrof"),
    cBK(904, "Param\u00e8tre de sort de Sram"),
    cBL(905, "Param\u00e8tre de sort de Xelor"),
    cBM(906, "Param\u00e8tre de sort d'Ecaflip"),
    cBN(907, "Param\u00e8tre de sort d'Eniripsa"),
    cBO(908, "Param\u00e8tre de sort de Iop"),
    cBP(909, "Param\u00e8tre de sort de Cra"),
    cBQ(910, "Param\u00e8tre de sort de Sadida"),
    cBR(911, "Param\u00e8tre de sort de Sacrieur"),
    cBS(912, "Param\u00e8tre de sort de Pandawa"),
    cBT(913, "Param\u00e8tre de budget faible"),
    cBU(914, "Param\u00e8tre d'\u00e9quipement \u00e9p\u00e9e"),
    cBV(915, "Param\u00e8tre d'\u00e9quipement dague"),
    cBW(916, "Param\u00e8tre d'\u00e9quipement baguette"),
    cBX(917, "Param\u00e8tre d'\u00e9quipement arc"),
    cBY(918, "Param\u00e8tre d'\u00e9quipement marteau"),
    cBZ(919, "Param\u00e8tre d'\u00e9quipement pelle"),
    cCa(920, "Param\u00e8tre d'\u00e9quipement chapeau"),
    cCb(921, "Param\u00e8tre d'\u00e9quipement cape"),
    cCc(922, "Param\u00e8tre d'\u00e9quipement familier"),
    cCd(923, "Param\u00e8tre d'\u00e9quipement dofus"),
    cCe(924, "Param\u00e8tre de budget \u00e9lev\u00e9"),
    cCf(925, "Param\u00e8tre de tour de jeu"),
    cCg(926, "Param\u00e8tre de temps (en millisecondes)"),
    cCh(927, "Param\u00e8tre d'id d'ar\u00e8ne"),
    cCi(928, "Param\u00e8tre de nombre de combattant"),
    cCj(929, "Param\u00e8tre de sort de Roublard"),
    cCk(930, "Param\u00e8tre de sort de Zobal"),
    cCl(1000, "Aucune limite sur ce combat");

    public static final int cCm = 32;
    public static final int cCn = 33;
    public static final int cCo = 900;
    private short fL;
    private String fM;

    /*
     * WARNING - void declaration
     */
    private ajr_2() {
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

