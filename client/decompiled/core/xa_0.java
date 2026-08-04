/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xA
 */
class xa_0
extends aEZ {
    private int[] aza;

    xa_0(int n2, int n3) {
        this.aza = new int[]{n2, n3};
    }

    public int[] b(int ... nArray) {
        return new int[]{nArray[0] + this.aza[0], nArray[1] + this.aza[1]};
    }

    public int[] c(int ... nArray) {
        return new int[]{nArray[0] - this.aza[0], nArray[1] - this.aza[1]};
    }
}

