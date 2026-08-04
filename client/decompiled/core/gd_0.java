/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from gD
 */
public class gd_0
extends ael_2 {
    private vy_1 uk = new vy_1();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            this.uk.b(byteBuffer.getShort(), byteBuffer.get());
        }
        return true;
    }

    public int getId() {
        return 6032;
    }

    public vy_1 kh() {
        return this.uk;
    }
}

