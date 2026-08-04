/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aLq
 */
public class alq_0
extends so_0 {
    private sj_1 aMT;

    public byte[] encode() {
        byte[] byArray = this.aMT.Ld().getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + byArray.length);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.put(this.aMT.aQe());
        byteBuffer.put(this.aMT.aQd());
        byteBuffer.put(this.aMT.lZ());
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 2049;
    }

    public void b(sj_1 sj_12) {
        this.aMT = sj_12;
    }
}

