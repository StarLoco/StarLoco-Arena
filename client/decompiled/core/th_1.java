/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Th
 */
public class th_1
extends ael_2 {
    private final qa_2 bMV = new qa_2();

    public boolean a(byte[] byArray) {
        boolean bl2 = this.a(byArray.length, 1, false);
        if (bl2) {
            this.bMV.clear();
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            int n2 = byteBuffer.getInt();
            for (int j = n2 - 1; 0 <= j; --j) {
                this.bMV.ct(byteBuffer.getLong());
            }
        }
        return bl2;
    }

    public qa_2 afR() {
        return this.bMV;
    }

    public int getId() {
        return 4098;
    }
}

