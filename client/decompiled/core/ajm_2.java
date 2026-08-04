/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ajm
 */
public class ajm_2
extends so_0 {
    private int Hp;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(this.Hp);
        return this.a((byte)3, byteBuffer.array());
    }

    public void kV(int n2) {
        this.Hp = n2;
    }

    public int getId() {
        return 5204;
    }
}

