/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZY
 */
class zy_1 {
    private final JX nr;
    private final String cex;
    private final String cey;
    private final String cez;
    private final String ceA;
    private final String fZ;
    final /* synthetic */ aMi ceB;

    private zy_1(aMi aMi2, JX jX, String string, String string2, String string3, String string4) {
        this.ceB = aMi2;
        this.cey = string;
        this.cex = string2;
        this.cez = string3;
        this.ceA = string4;
        this.nr = jX;
        this.fZ = new StringBuilder(jX.getId()).append("|").append(string2).append("|").append(string).append("|").append(string3).append("|").append(string4).toString();
    }

    public String aoC() {
        return this.cey;
    }

    public String aoD() {
        return this.cex;
    }

    public String aoE() {
        return this.cez;
    }

    public String getFunctionName() {
        return this.ceA;
    }

    public JX aoF() {
        return this.nr;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        zy_1 zy_12 = (zy_1)object;
        return this.fZ.equals(zy_12.fZ);
    }

    public int hashCode() {
        return this.fZ.hashCode();
    }

    /* synthetic */ zy_1(aMi aMi2, JX jX, String string, String string2, String string3, String string4, zs_2 zs_22) {
        this(aMi2, jX, string, string2, string3, string4);
    }
}

