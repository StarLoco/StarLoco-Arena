/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class jF
extends iz_0 {
    private String BF;
    private int BG = 1;

    public jF() {
    }

    public jF(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
        super(rd_12, rd_13, jx_02, n2);
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        this.B(byteBuffer);
        return byteBuffer.array();
    }

    public int nj() {
        return this.UD();
    }

    public iz_0 h(ByteBuffer byteBuffer) {
        jF jF2 = new jF();
        jF2.C(byteBuffer);
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        jF2.BF = aey_0.V(byArray);
        return jF2;
    }

    public iz_0 nk() {
        jF jF2 = new jF();
        this.c(jF2);
        jF2.BF = this.BF;
        return jF2;
    }

    public void bt(int n2) {
        this.BG = n2;
    }

    public int getType() {
        return 3;
    }
}

