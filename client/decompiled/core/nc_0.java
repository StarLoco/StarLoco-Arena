/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from nC
 */
public class nc_0
extends iz_0 {
    private boolean OS;
    private boolean OT;

    public nc_0() {
    }

    public nc_0(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
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
        nc_0 nc_02 = new nc_0();
        nc_02.C(byteBuffer);
        nc_02.OS = byteBuffer.get() == 1;
        nc_02.OT = byteBuffer.get() == 1;
        return nc_02;
    }

    public iz_0 nk() {
        nc_0 nc_02 = new nc_0();
        this.c(nc_02);
        nc_02.OS = this.OS;
        nc_02.OT = this.OT;
        return nc_02;
    }

    public void ae(boolean bl2) {
        this.OS = bl2;
    }

    public void af(boolean bl2) {
        this.OT = bl2;
    }

    public int getType() {
        return 5;
    }
}

