/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from In
 */
public class in_0 {
    private static final int[] bgz = new int[]{0, 0};
    private static final int[][] bgA = new int[][]{{1, 0}, {0, 1}};
    private static final int[][] bgB = new int[][]{{-1, 0}, {0, -1}};
    private int bgC = 0;
    private int m_index = 0;
    private int bgD;

    public int[] Ug() {
        if (this.bgC == 0) {
            this.bgC = 1;
            this.bgD = 0;
            return bgz;
        }
        ++this.m_index;
        int[][] nArray = this.bgC % 2 == 0 ? bgB : bgA;
        int[] nArray2 = nArray[this.bgD];
        if (this.m_index == this.bgC) {
            if (this.bgD == 0) {
                this.bgD = 1;
                this.m_index = 0;
            } else {
                this.bgD = 0;
                this.m_index = 0;
                ++this.bgC;
            }
        }
        return nArray2;
    }
}

