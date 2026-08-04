/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aCC
 */
public class acc_2
extends ael_2 {
    private final ArrayList duz = new ArrayList();

    public ArrayList aOr() {
        return this.duz;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            long l2 = byteBuffer.getLong();
            this.duz.add(l2);
        }
        return true;
    }

    public int getId() {
        return 206;
    }
}

