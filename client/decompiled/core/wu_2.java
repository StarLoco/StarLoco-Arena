/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from wU
 */
public class wu_2
extends ael_2 {
    private long ap;
    private boolean avO;
    private boolean acR;
    private final ArrayList atc = new ArrayList();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.ap = byteBuffer.getLong();
        this.avO = byteBuffer.get() == 1;
        this.acR = byteBuffer.get() == 1;
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.atc.add(new String(byArray2));
        }
        return true;
    }

    public int getId() {
        return 26300;
    }

    public long Y() {
        return this.ap;
    }

    public Iterable Ds() {
        return this.atc;
    }

    public boolean Dt() {
        return this.avO;
    }

    public boolean uK() {
        return this.acR;
    }
}

