/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ayo
extends th_2 {
    private boolean OS;
    private boolean OT;

    public ayo() {
    }

    public ayo(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
        super(rd_12, rd_13, jx_02, n2);
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        this.B(byteBuffer);
        byteBuffer.put((byte)(this.OS ? 1 : 0));
        byteBuffer.put((byte)(this.OT ? 1 : 0));
        return byteBuffer.array();
    }

    public int nj() {
        return this.UD() + 1 + 1;
    }

    public iz_0 h(ByteBuffer byteBuffer) {
        ayo ayo2 = new ayo();
        ayo2.C(byteBuffer);
        ayo2.OS = byteBuffer.get() == 1;
        ayo2.OT = byteBuffer.get() == 1;
        return ayo2;
    }

    public iz_0 nk() {
        ayo ayo2 = new ayo();
        this.c(ayo2);
        ayo2.OS = this.OS;
        ayo2.OT = this.OT;
        return ayo2;
    }

    public void ae(boolean bl2) {
        this.OS = bl2;
    }

    public void af(boolean bl2) {
        this.OT = bl2;
    }

    public int getType() {
        return 6;
    }
}

