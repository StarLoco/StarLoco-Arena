/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aEr
 */
public class aer_0
extends so_0 {
    private String bak;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.bak);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)4, byteBuffer.array());
    }

    public int getId() {
        return 3135;
    }

    public void fo(String string) {
        this.bak = string;
    }
}

