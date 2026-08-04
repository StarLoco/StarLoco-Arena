/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from XH
 */
public class xh_0
extends aks {
    private int[] bZl = new int[]{0};

    public boolean GO() {
        try {
            this.cY.alGenFilters(1, this.bZl, 0);
            this.bAT.check();
            this.cY.alFilteri(this.bZl[0], 32769, 1);
            this.bAT.check();
        }
        catch (Exception exception) {
            return false;
        }
        return true;
    }

    public void cleanUp() {
        if (this.bZl[0] != 0) {
            this.bAT.check();
            this.cY.alDeleteFilters(1, this.bZl, 0);
            this.bAT.check();
            this.bZl[0] = 0;
        }
        this.bZl = null;
        super.cleanUp();
    }

    public amA GP() {
        return amA.cHH;
    }
}

