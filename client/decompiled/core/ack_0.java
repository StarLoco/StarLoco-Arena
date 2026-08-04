/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from acK
 */
class ack_0 {
    private final String name;
    private final Class[] parameterTypes;
    private final rp_0 ckM;

    ack_0(rp_0 rp_02, String string, Class[] classArray) {
        this.ckM = rp_02;
        this.name = string;
        this.parameterTypes = classArray;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ack_0)) {
            return false;
        }
        ack_0 ack_02 = (ack_0)object;
        if (!this.name.equals(ack_02.name)) {
            return false;
        }
        int n2 = this.parameterTypes.length;
        if (n2 != ack_02.parameterTypes.length) {
            return false;
        }
        for (int j = 0; j < n2; ++j) {
            if (this.parameterTypes[j].equals(ack_02.parameterTypes[j])) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n2 = this.name.hashCode();
        for (int j = 0; j < this.parameterTypes.length; ++j) {
            n2 ^= this.parameterTypes[j].hashCode();
        }
        return n2;
    }
}

