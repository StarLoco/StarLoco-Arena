/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from wi
 */
public class wi_0
extends np_1 {
    private mp_2 auo;

    public void a(mv_1 mv_12) {
        mv_12.b(this.auo);
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        byteBuffer.putInt(this.getType());
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.Om);
        byteBuffer.put(this.auo.cd());
        return byteBuffer.array();
    }

    public void k(ByteBuffer byteBuffer) {
        this.aW = byteBuffer.getInt();
        this.Om = byteBuffer.getInt();
        this.JI = ug_2.bQd;
        this.auo = mp_2.i(byteBuffer);
    }

    public int nj() {
        return 12 + this.auo.nj();
    }

    public mp_2 CD() {
        return this.auo;
    }

    public void a(mp_2 mp_22) {
        this.auo = mp_22;
    }

    public int T() {
        return 0;
    }

    public int getType() {
        return ajr_2.cBn.tI();
    }
}

