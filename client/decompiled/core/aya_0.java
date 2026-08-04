/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aya
 */
public class aya_0
extends ael_2 {
    private final qa_2 dkw = new qa_2();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            this.dkw.ct(byteBuffer.getLong());
        }
        return true;
    }

    public int getId() {
        return 4104;
    }

    public qa_2 aKI() {
        return this.dkw;
    }

    public int aKJ() {
        return this.dkw.size();
    }
}

