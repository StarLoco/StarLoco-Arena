/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from lA
 */
public class la_1
extends ael_2 {
    private int Hp;
    private int Hq;
    private int Hr;
    private int Hs;
    private long Ht;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.Hp = byteBuffer.getInt();
        this.Hq = byteBuffer.getInt();
        this.Hr = byteBuffer.getInt();
        this.Hs = byteBuffer.getInt();
        this.Ht = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 22094;
    }

    public int qo() {
        return this.Hp;
    }

    public int qp() {
        return this.Hq;
    }

    public int qq() {
        return this.Hr;
    }

    public int qr() {
        return this.Hs;
    }

    public long qs() {
        return this.Ht;
    }
}

