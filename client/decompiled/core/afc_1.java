/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afC
 */
public enum afc_1 {
    crp("Winter", new acx_1[]{new rd_1(0, 0, 0, 21, 12, 0), new rd_1(0, 0, 0, 31, 12, 0), new rd_1(0, 0, 0, 1, 1, 0), new rd_1(0, 0, 0, 20, 3, 0)}, new float[]{-0.5f, -0.4f, -0.3f, -0.2f, -0.1f, -0.05f, 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.1f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0.05f, 0.0f, -0.1f, -0.2f, -0.3f, -0.4f, -0.5f}),
    crq("Spring", new acx_1[]{new rd_1(0, 0, 0, 21, 3, 0), new rd_1(0, 0, 0, 20, 6, 0)}, new float[]{-0.5f, -0.4f, -0.3f, -0.2f, -0.1f, -0.05f, 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.1f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0.05f, 0.0f, -0.1f, -0.2f, -0.3f, -0.4f, -0.5f}),
    crr("Summer", new acx_1[]{new rd_1(0, 0, 0, 21, 6, 0), new rd_1(0, 0, 0, 20, 9, 0)}, new float[]{-0.5f, -0.4f, -0.3f, -0.2f, -0.1f, -0.05f, 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.1f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0.05f, 0.0f, -0.1f, -0.2f, -0.3f, -0.4f, -0.5f}),
    crs("Fall", new acx_1[]{new rd_1(0, 0, 0, 21, 9, 0), new rd_1(0, 0, 0, 20, 12, 0)}, new float[]{-0.5f, -0.4f, -0.3f, -0.2f, -0.1f, -0.05f, 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.1f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0.05f, 0.0f, -0.1f, -0.2f, -0.3f, -0.4f, -0.5f});

    private static final afc_1[] crt;
    private static final rd_1 cru;
    private final String m_name;
    private final acx_1[] crv;
    private final float[] crw;
    private afc_1 crx;
    private afc_1 cry;

    public static afc_1 n(acx_1 acx_12) {
        cru.a(acx_12);
        cru.setYear(0);
        cru.setSeconds(0);
        cru.setMinutes(0);
        cru.setHours(0);
        for (afc_1 afc_12 : crt) {
            int n2 = afc_12.crv.length / 2;
            for (int j = 0; j < n2; ++j) {
                acx_1 acx_13 = afc_12.crv[j * 2];
                acx_1 acx_14 = afc_12.crv[j * 2 + 1];
                if (!acx_13.equals(cru) && !acx_13.b(cru) || !acx_14.d(cru) && !acx_14.equals(cru)) continue;
                return afc_12;
            }
        }
        return null;
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private afc_1(float[] fArray) {
        void var5_3;
        void var4_2;
        this.m_name = fArray;
        this.crv = var4_2;
        this.crw = var5_3;
    }

    public String getName() {
        return this.m_name;
    }

    public float[] avh() {
        return this.crw;
    }

    public static afc_1[] avi() {
        return crt;
    }

    public byte aiK() {
        return (byte)(this.ordinal() + 1);
    }

    public acx_1 avj() {
        return this.crv[0];
    }

    public int o(acx_1 acx_12) {
        cru.a(acx_12);
        cru.setYear(0);
        int n2 = this.crv.length / 2;
        int n3 = 0;
        for (int j = 0; j < n2; ++j) {
            acx_1 acx_13 = this.crv[j * 2];
            acx_1 acx_14 = this.crv[j * 2 + 1];
            if ((acx_13.equals(cru) || acx_13.b(cru)) && (acx_14.d(cru) || acx_14.equals(cru))) {
                n3 += acx_13.g(cru).getDays();
                break;
            }
            n3 += acx_13.g(acx_14).getDays();
        }
        return n3;
    }

    private void a(afc_1 afc_12, afc_1 afc_13) {
        this.crx = afc_12;
        this.cry = afc_13;
    }

    public afc_1 avk() {
        return this.crx;
    }

    public afc_1 avl() {
        return this.cry;
    }

    public String toString() {
        switch (this) {
            case crq: {
                return "Printemps";
            }
            case crr: {
                return "Et\u00e9";
            }
            case crs: {
                return "Automne";
            }
            case crp: {
                return "Hiver";
            }
        }
        return super.toString();
    }

    static {
        crt = new afc_1[]{crp, crq, crr, crs};
        cru = new rd_1(0, 0, 0, 0, 0, 0);
        crp.a(crs, crq);
        crq.a(crp, crr);
        crr.a(crq, crs);
        crs.a(crr, crp);
    }
}

