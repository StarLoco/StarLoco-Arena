/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from bS
 */
public abstract class bs_1
implements ut_0 {
    protected static final Logger a = Logger.getLogger(bs_1.class);
    private final lb_0 hE = new lb_0();
    private final lb_0 hF = new lb_0();

    protected bs_1() {
    }

    public void a(jb_2 jb_22) {
        this.hE.c(jb_22.getId(), jb_22);
    }

    public lb_0 dU() {
        return this.hE;
    }

    public jb_2 E(int n2) {
        return (jb_2)this.hE.get(n2);
    }

    public jb_2 d(ByteBuffer byteBuffer) {
        return (jb_2)this.hE.get(byteBuffer.getInt());
    }

    public void a(lb_0 lb_02, int n2) {
        this.hF.c(n2, lb_02);
    }

    public lb_0 F(int n2) {
        return (lb_0)this.hF.get(n2);
    }
}

