/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from HZ
 */
public abstract class hz_2
extends atD {
    protected xb_2 bge;
    private static final Logger a = Logger.getLogger(hz_2.class);
    private long bgf;
    private long Bf;

    protected hz_2() {
    }

    public xb_2 TG() {
        return this.bge;
    }

    public boolean isValid() {
        return this.bge != null && this.bge.je() == this.bgf;
    }

    protected void c(xb_2 xb_22) {
        this.bge = xb_22;
        this.bgf = xb_22.je();
    }

    protected void bC(long l2) {
        this.Bf = l2;
    }

    protected void b(xb_2 xb_22, long l2) {
        this.c(xb_22);
        this.Bf = l2;
    }

    public long TH() {
        return this.Bf;
    }

    protected int TI() {
        return 16;
    }

    protected void A(ByteBuffer byteBuffer) {
        byteBuffer.putLong(this.bge.je());
        byteBuffer.putLong(this.Bf);
    }

    protected void c(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        long l2 = byteBuffer.getLong();
        this.Bf = byteBuffer.getLong();
        xb_2 xb_22 = ahh_02.ez(l2);
        if (xb_22 == null) {
            a.warn((Object)("D\u00e9s\u00e9rialisation de timeline : on ne trouve pas le RunningEffect d'UID " + l2));
            return;
        }
        this.c(xb_22);
    }

    public boolean isPersistent() {
        return this.bge != null && this.bge.isPersistent();
    }
}

