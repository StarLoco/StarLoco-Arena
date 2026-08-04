/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from RQ
 */
public class rq_2
extends ael_2 {
    private int bKK;
    byte[] Nw;
    private akv_0 bKL;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bKK = byteBuffer.getInt();
        this.Nw = new byte[byteBuffer.getShort()];
        byteBuffer.get(this.Nw);
        this.bKL = akv_0.ab(byteBuffer);
        return true;
    }

    public void j() {
        super.j();
        this.Nw = null;
    }

    public void b() {
        super.b();
    }

    public int getId() {
        return 8121;
    }

    public byte[] aew() {
        return this.Nw;
    }

    public akv_0 aex() {
        return this.bKL;
    }

    public int aey() {
        return this.bKK;
    }

    public int M() {
        return this.bKK;
    }
}

