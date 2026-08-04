/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from Ar
 */
public class ar_0
extends ael_2 {
    private final ArrayList aGX = new ArrayList();
    private cp_2 aGY = new cp_2();

    public boolean a(byte[] byArray) {
        int n2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aGX.clear();
        int n3 = byteBuffer.get();
        for (n2 = 0; n2 < n3; ++n2) {
            sw_1 sw_12 = sw_1.afp();
            if (sw_12.b(byteBuffer)) {
                this.aGX.add(zK.a(sw_12));
                continue;
            }
            sw_12.release();
        }
        n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            long l2 = byteBuffer.getLong();
            byte[] byArray2 = new byte[byteBuffer.get()];
            byteBuffer.get(byArray2);
            this.aGY.a(l2, aey_0.V(byArray2));
        }
        return true;
    }

    public int getId() {
        return 6030;
    }

    public ArrayList Hh() {
        return this.aGX;
    }

    public int Hi() {
        return this.aGX.size();
    }

    public cp_2 Hj() {
        return this.aGY;
    }
}

