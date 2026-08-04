/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xV
 */
public class xv_2 {
    private long azN;
    private long azO;
    private byte azP;
    private byte azQ;
    private int azR;

    public xv_2(long l2) {
        this.azN = l2;
        this.azR = (int)(this.azN % 1000L);
        long l3 = this.azN / 1000L;
        this.azQ = (byte)(l3 % 60L);
        long l4 = this.azN / 1000L / 60L;
        this.azP = (byte)(l4 % 60L);
        this.azO = this.azN / 1000L / 60L / 60L;
    }

    public long getTime() {
        return this.azN;
    }

    public long Ex() {
        return this.azO;
    }

    public byte Ey() {
        return this.azP;
    }

    public byte Ez() {
        return this.azQ;
    }

    public int EA() {
        return this.azR;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.azO != 0L) {
            stringBuilder.append(this.azO).append("h ");
        }
        if (this.azP != 0) {
            stringBuilder.append(this.azP).append("min ");
        }
        if (this.azQ != 0) {
            stringBuilder.append(this.azQ).append("sec ");
        }
        stringBuilder.append(this.azR).append("ms");
        return stringBuilder.toString();
    }
}

