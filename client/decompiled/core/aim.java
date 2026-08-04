/*
 * Decompiled with CFR 0.152.
 */
public class aim {
    private long cxP = -1L;

    private synchronized long axF() {
        return this.cxP;
    }

    private synchronized long dE(long l2) {
        long l3 = this.cxP;
        this.cxP = l2;
        return l3;
    }

    public String toString() {
        return "native:" + this.cxP;
    }
}

