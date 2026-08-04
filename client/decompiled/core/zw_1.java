/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZW
 */
public class zw_1
implements hR {
    private static final zw_1 ceq = new zw_1();
    private static final fw_2 cer = new fw_2();

    public static zw_1 aoB() {
        return ceq;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.calendarEvent");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray = aly_1.aAQ().a(cer);
        tb_0.initialize();
        for (lJ lJ2 : lJArray) {
            iz_0 iz_02;
            fw_2 fw_22 = (fw_2)lJ2;
            switch (fw_22.getType()) {
                case 1: {
                    iz_02 = new wk_1();
                    break;
                }
                case 2: {
                    iz_02 = new mb_2();
                    break;
                }
                case 3: {
                    iz_02 = new jF();
                    break;
                }
                case 4: {
                    iz_02 = new qr_0();
                    break;
                }
                case 5: {
                    iz_02 = new nc_0();
                    break;
                }
                case 6: {
                    iz_02 = new ayo();
                    break;
                }
                case 7: {
                    iz_02 = new aoy_2();
                    break;
                }
                default: {
                    iz_02 = new wk_1();
                }
            }
            tb_0.zl().e(fw_22.getId(), iz_02);
        }
        mk_12.b(this);
    }
}

