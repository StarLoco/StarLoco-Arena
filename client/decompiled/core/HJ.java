/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class HJ
extends ael_2 {
    private mm_0 bfp = new mm_0();
    private mm_0 bfq = new mm_0();
    private mm_0 bfr = new mm_0();
    private jg_0 bfs = new jg_0();
    private jg_0 bft = new jg_0();
    private jg_0 bfu = new jg_0();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        for (int j = n2 - 1; 0 <= j; --j) {
            this.bfp.add(byteBuffer.getShort());
            this.bfq.add(byteBuffer.getShort());
            this.bfr.add(byteBuffer.getShort());
            this.bfs.add(byteBuffer.getInt());
            this.bft.add(byteBuffer.getInt());
            this.bfu.add(byteBuffer.getInt());
        }
        return true;
    }

    public int getId() {
        return 2411;
    }

    public mm_0 Tt() {
        return this.bfp;
    }

    public mm_0 Tu() {
        return this.bfq;
    }

    public mm_0 Tv() {
        return this.bfr;
    }

    public jg_0 Tw() {
        return this.bfs;
    }

    public jg_0 Tx() {
        return this.bft;
    }

    public jg_0 Ty() {
        return this.bfu;
    }
}

