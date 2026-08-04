/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Yq
extends ael_2 {
    private static String NO_NAME = "-";
    private byte LA;
    private long fz;
    private long[] bOp;
    private String[] bOq;
    private String BF;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.LA = byteBuffer.get();
        if (this.LA == aou_0.cKV) {
            a.error((Object)("Mauvaise utilisation d'un message de type " + this.getClass() + " : Status \u00e9gal \u00e0 " + this.LA + "."));
        } else if (this.LA == aou_0.cKW) {
            int n2;
            this.fz = byteBuffer.getLong();
            this.bOp = new long[byteBuffer.getInt()];
            for (n2 = this.bOp.length - 1; 0 <= n2; --n2) {
                this.bOp[n2] = byteBuffer.getLong();
            }
            this.bOq = new String[byteBuffer.getInt()];
            for (n2 = this.bOq.length - 1; 0 <= n2; --n2) {
                try {
                    byte[] byArray2 = new byte[byteBuffer.getInt()];
                    byteBuffer.get(byArray2);
                    this.bOq[n2] = new String(byArray2, "UTF-8");
                    continue;
                }
                catch (Exception exception) {
                    this.bOq[n2] = NO_NAME;
                }
            }
            try {
                byte[] byArray3 = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray3);
                this.BF = new String(byArray3, "UTF-8");
            }
            catch (Exception exception) {
                this.BF = NO_NAME;
            }
        } else if (this.LA == aou_0.cKX) {
            this.fz = byteBuffer.getLong();
        }
        return true;
    }

    public long cA() {
        return this.fz;
    }

    public long[] aga() {
        return this.bOp;
    }

    public String[] agb() {
        return this.bOq;
    }

    public String BC() {
        return this.BF;
    }

    public boolean amw() {
        return this.LA == aou_0.cKW;
    }

    public boolean amx() {
        return this.LA == aou_0.cKX;
    }

    public int getId() {
        return 28620;
    }
}

