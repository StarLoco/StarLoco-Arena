/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNF
 */
class anf_1 {
    anf_1 dZD;
    anf_1 dZE;
    String key;
    adr_0 dZF;
    long timestamp;
    final /* synthetic */ pp_2 dZG;

    anf_1(pp_2 pp_22, String string, adr_0 adr_02, long l2) {
        this.dZG = pp_22;
        this.key = string;
        this.dZF = adr_02;
        this.timestamp = l2;
    }

    public long aXz() {
        return this.timestamp;
    }

    public void eR(long l2) {
        this.timestamp = l2;
    }

    public int hashCode() {
        int n2 = 31;
        int n3 = 1;
        n3 = 31 * n3 + (this.key == null ? 0 : this.key.hashCode());
        return n3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        anf_1 anf_12 = (anf_1)object;
        if (this.key == null ? anf_12.key != null : !this.key.equals(anf_12.key)) {
            return false;
        }
        return !(this.dZF == null ? anf_12.dZF != null : !this.dZF.equals(anf_12.dZF));
    }

    public String toString() {
        return "(" + this.key + ", " + this.dZF + ")";
    }
}

