/*
 * Decompiled with CFR 0.152.
 */
public class azX {
    private String btW;
    private int[] doS;

    public azX(String string, int[] nArray) {
        this.btW = string;
        this.doS = nArray;
    }

    public String getHost() {
        return this.btW;
    }

    public int[] aMz() {
        return this.doS;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("ProxyAddress host=").append(this.btW).append(", ports=").append("{");
        for (int j = 0; j < this.doS.length; ++j) {
            stringBuffer.append(j > 0 ? "," : "").append(this.doS[j]);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}

