/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from mB
 */
public class mb_2
extends iz_0 {
    private String wV;
    private String fM;

    public mb_2() {
    }

    public mb_2(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
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

    public int getType() {
        return 2;
    }

    public iz_0 h(ByteBuffer byteBuffer) {
        mb_2 mb_22 = new mb_2();
        mb_22.C(byteBuffer);
        return mb_22;
    }

    public iz_0 nk() {
        mb_2 mb_22 = new mb_2();
        this.c(mb_22);
        return mb_22;
    }

    public String getTitle() {
        return this.wV;
    }

    public void setTitle(String string) {
        this.wV = string;
    }

    public String getDescription() {
        return this.fM;
    }

    public void setDescription(String string) {
        this.fM = string;
    }
}

