/*
 * Decompiled with CFR 0.152.
 */
public class aeA {
    public static final String PACKAGE = "dofusarena.firework";

    public static final void closeFireworkDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21020);
        acu_1.ara().c(sb_02);
    }

    public static void goToSet(aGJ aGJ2, aht_1 aht_12, aht_1 aht_13, String string) {
        int n2 = Integer.parseInt(string);
        aht_12.setVisible(false);
        aht_13.setVisible(true);
        Object object = aGJ2.getItemValue();
        if (object instanceof fe_1) {
            fe_1 fe_12 = (fe_1)object;
            aij_0.aUF().a(fe_12, n2);
        }
    }

    public static void goToSetList(ke ke2, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(true);
        aht_13.setVisible(false);
    }

    public static void dropFirework(aiU aiU2, String string) {
        wy_2 wy_22 = (wy_2)aiU2.getValue();
        ia_2 ia_22 = new ia_2();
        ia_22.f(21021);
        ia_22.bF(Short.parseShort(string));
        ia_22.b(wy_22);
        acu_1.ara().c(ia_22);
    }

    public static boolean validateFireworkDrop(kn_1 kn_12, Object object, kn_1 kn_13, Object object2, Object object3) {
        return object3 != null && object3 instanceof wy_2 && ((wy_2)object3).tj() != aMK.dYu;
    }

    public static void removeFirework(aly_2 aly_22, String string) {
        wy_2 wy_22 = (wy_2)aly_22.getValue();
        ia_2 ia_22 = new ia_2();
        ia_22.f(21023);
        ia_22.bF(Short.parseShort(string));
        ia_22.b(wy_22);
        acu_1.ara().c(ia_22);
    }

    public static void removeFirework(aGJ aGJ2, String string) {
        wy_2 wy_22 = (wy_2)aGJ2.getItemValue();
        ia_2 ia_22 = new ia_2();
        ia_22.f(21023);
        ia_22.bF(Short.parseShort(string));
        ia_22.b(wy_22);
        acu_1.ara().c(ia_22);
    }

    public static void launchFirework(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21022);
        acu_1.ara().c(sb_02);
    }

    public static void setDelay(ke ke2, String string, UV uV) {
        String string2 = uV.getText();
        if (!string2.equals("")) {
            ((akl_2)iu_0.Ut().Uu().an(Short.parseShort(string))).setDelay(Long.parseLong(string2));
            azs_0.aLV().a((aho_0)iu_0.Ut(), iu_0.ce);
        }
    }
}

