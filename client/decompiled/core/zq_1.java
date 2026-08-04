/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ZQ
 */
public class zq_1
extends ael_2 {
    private long bgf;
    private long aj;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bgf = byteBuffer.getLong();
        this.aj = byteBuffer.getLong();
        return true;
    }

    public void j() {
        super.j();
    }

    public void b() {
        super.b();
    }

    public int getId() {
        return 8122;
    }

    public long aow() {
        return this.bgf;
    }

    public long K() {
        return this.aj;
    }
}

