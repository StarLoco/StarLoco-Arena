/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aDD
 */
public class add_2
extends so_0 {
    private long bZ;

    public add_2(long l2) {
        this.bZ = l2;
    }

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.bZ);
        return this.a((byte)2, byteBuffer.array());
    }

    public long Kd() {
        return this.bZ;
    }

    public int getId() {
        return 519;
    }
}

