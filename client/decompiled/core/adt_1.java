/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aDt
 */
public class adt_1 {
    static void f(ahu_0 ahu_02) {
        String string = dh_2.getSystemProperty("logback.statusListenerClass");
        if (!dh_2.isEmpty(string)) {
            adt_1.b(ahu_02, string);
        }
    }

    static void b(ahu_0 ahu_02, String string) {
        pm_1 pm_12 = null;
        if ("SYSOUT".equalsIgnoreCase(string)) {
            pm_12 = new afk_0();
        } else {
            try {
                pm_12 = (pm_1)dh_2.a(string, pm_1.class, ahu_02);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (pm_12 != null) {
            ahu_02.ea().a(pm_12);
        }
    }
}

