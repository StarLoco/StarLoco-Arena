/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from EB
 */
public abstract class eb_1
implements JG,
akU {
    protected static final Logger a = Logger.getLogger(eb_1.class);
    protected oj_0 aRL;
    protected final alb_1 aRM;
    private short oX;
    protected long aFL;

    protected eb_1(alb_1 alb_12) {
        this.aRM = alb_12;
    }

    public short hG() {
        return this.oX;
    }

    public void q(short s) {
        this.oX = (short)Math.max(0, s);
    }

    public void w(short s) {
        this.oX = (short)Math.max(0, this.oX + s);
        if (this.aRL.isUnique()) {
            this.oX = (short)Math.min(1, this.oX);
        }
    }

    public final short jg() {
        return Short.MAX_VALUE;
    }

    public boolean e(uh_1 uh_12) {
        return this.jf() == uh_12.jf();
    }

    public long je() {
        return this.aFL;
    }

    public int jf() {
        try {
            return this.aRL.jf();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public oj_0 NR() {
        return this.aRL;
    }

    public void NS() {
    }

    public byte[] cd() {
        byte[] byArray = new byte[4];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byteBuffer.putInt(this.aRL.getId());
        return byArray;
    }

    public boolean b(ByteBuffer byteBuffer) {
        boolean bl2 = true;
        int n2 = byteBuffer.getInt();
        this.aRL = this.aRM.pj(n2);
        if (this.aRL == null) {
            a.error((Object)("Unable to unserialize AbstractCoachCard : referenceCard not found : " + n2));
            bl2 = false;
        }
        this.aFL = uq_1.ahR();
        return bl2;
    }

    public void b() {
        this.aRL = null;
        this.oX = 0;
        this.aFL = 0L;
    }

    public void j() {
        this.aRL = null;
        this.oX = 0;
        this.aFL = 0L;
    }

    public static int NT() {
        return 4;
    }

    public boolean ji() {
        return true;
    }

    public String toString() {
        return "referenceCardId = " + (this.aRL != null ? Integer.valueOf(this.aRL.getId()) : "null") + ", quantity = " + this.oX;
    }
}

