/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from LC
 */
public class lc_0
implements Serializable {
    public static final lc_0 brK = new lc_0("<internally generated location>", -1, -1);
    private final String bnj;
    private final short brL;
    private final short brM;

    public lc_0(String string, short s, short s2) {
        this.bnj = string;
        this.brL = s;
        this.brM = s2;
    }

    public String getFileName() {
        return this.bnj;
    }

    public short XQ() {
        return this.brL;
    }

    public short XR() {
        return this.brM;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.bnj != null) {
            stringBuffer.append("File ").append(this.bnj).append(", ");
        }
        stringBuffer.append("Line ").append(this.brL).append(", ");
        stringBuffer.append("Column ").append(this.brM);
        return stringBuffer.toString();
    }
}

