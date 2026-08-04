/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from Km
 */
class km_2
extends ov_2 {
    private final byte[] bnp;
    private final nw_2 bnq;

    km_2(nw_2 nw_22, short s, byte[] byArray) {
        super(s);
        this.bnq = nw_22;
        this.bnp = byArray;
    }

    protected void b(DataOutputStream dataOutputStream) {
        dataOutputStream.write(this.bnp);
    }
}

