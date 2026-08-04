/*
 * Decompiled with CFR 0.152.
 */
public class eN {
    static final eN pU = new eN();

    private eN() {
    }

    public static void d(ahu_0 ahu_02) {
        Ju ju = ahu_02.ea();
        if (ju != null) {
            ju.c(new jP("Setting up default configuration.", ahu_02));
        }
        zl zl2 = new zl();
        zl2.a(ahu_02);
        zl2.setName("console");
        alr_0 alr_02 = new alr_0();
        alr_02.a(ahu_02);
        alr_02.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        alr_02.start();
        zl2.a(alr_02);
        zl2.start();
        arN arN2 = ahu_02.lw("root");
        arN2.a(zl2);
    }

    public static void hU() {
        ahu_0 ahu_02 = (ahu_0)LD.XV();
        eN.d(ahu_02);
    }
}

