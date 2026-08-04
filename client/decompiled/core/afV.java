/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class afV
extends ael_2 {
    private long[] csU;
    private long[] csV;

    public boolean a(byte[] byArray) {
        int n2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.csU = new long[byteBuffer.getShort()];
        for (n2 = 0; n2 < this.csU.length; ++n2) {
            this.csU[n2] = byteBuffer.getLong();
        }
        this.csV = new long[byteBuffer.getShort()];
        for (n2 = 0; n2 < this.csV.length; ++n2) {
            this.csV[n2] = byteBuffer.getLong();
        }
        return true;
    }

    public int getId() {
        return 4601;
    }

    public long[] avM() {
        return this.csU;
    }

    public long[] avN() {
        return this.csV;
    }
}

