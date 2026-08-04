/*
 * Decompiled with CFR 0.152.
 */
class fb
extends aEZ {
    private int[][] qb;

    fb(int n2, int n3, qc_0 qc_02) {
        if (qc_02 == qc_0.bET) {
            qc_02 = qc_0.bEK;
        }
        int n4 = qc_02.acJ()[0];
        int n5 = qc_02.acJ()[1];
        this.qb = new int[][]{{n4, -n5, n2}, {n5, n4, n3}};
    }

    public int[] b(int ... nArray) {
        return new int[]{this.qb[0][0] * nArray[0] + this.qb[0][1] * nArray[1] + this.qb[0][2], this.qb[1][0] * nArray[0] + this.qb[1][1] * nArray[1] + this.qb[1][2]};
    }

    public int[] c(int ... nArray) {
        int n2 = nArray[0] - this.qb[0][2];
        int n3 = nArray[1] - this.qb[1][2];
        return new int[]{this.qb[0][0] * n2 + this.qb[1][0] * n3, this.qb[0][1] * n2 + this.qb[1][1] * n3};
    }
}

