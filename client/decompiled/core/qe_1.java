/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Qe
 */
public enum qe_1 {
    bEZ,
    bFa,
    bFb,
    bFc,
    bFd,
    bFe,
    bFf,
    bFg,
    bFh,
    bFi,
    bFj,
    bFk,
    bFl,
    bFm,
    bFn,
    bFo,
    bFp,
    bFq,
    bFr,
    bFs,
    bFt,
    bFu,
    bFv,
    bFw,
    bFx,
    bFy,
    bFz,
    bFA,
    bFB,
    bFC,
    bFD,
    bFE,
    bFF,
    bFG,
    bFH,
    bFI,
    bFJ,
    bFK,
    bFL,
    bFM,
    bFN,
    bFO;

    private static qe_1[] bFP;

    public static qe_1 fG(String string) {
        qe_1[] qe_1Array;
        for (qe_1 qe_12 : qe_1Array = qe_1.values()) {
            if (!qe_12.name().equals(string.toUpperCase())) continue;
            return qe_12;
        }
        return qe_1Array[0];
    }

    public static qe_1 hi(int n2) {
        return bFP[n2];
    }

    static {
        bFP = qe_1.values();
    }
}

