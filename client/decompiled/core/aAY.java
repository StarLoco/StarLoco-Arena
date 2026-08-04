/*
 * Decompiled with CFR 0.152.
 */
public final class aAY {
    private long uN;
    private long dqS;

    public aAY(long l2, long l3) {
        this.uN = l2;
        this.dqS = l3;
    }

    public long wD() {
        return this.uN;
    }

    public long aNa() {
        return this.dqS;
    }

    public aAY n(long l2, long l3) {
        this.uN = l2;
        this.dqS = l3;
        return this;
    }

    public boolean equals(Object object) {
        if (object instanceof aAY) {
            aAY aAY2 = (aAY)object;
            if (this.uN == aAY2.uN && this.dqS == aAY2.dqS) {
                return true;
            }
        }
        return false;
    }
}

