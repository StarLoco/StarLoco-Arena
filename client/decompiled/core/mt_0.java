/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;

/*
 * Renamed from Mt
 */
public abstract class mt_0
implements JG {
    protected static final Logger a = Logger.getLogger(mt_0.class);
    public static final acl_0 btS = new ym_0(new ai_0());
    public static final acl_0 btT = new ym_0(new ah_0());
    protected final pa_0 btU = new pa_0();
    protected final cs_1 btV = new cs_1();
    private acl_0 uG;

    private mt_0() {
    }

    public abstract DataOutputStream a(FileOutputStream var1);

    public abstract DataInputStream a(FileInputStream var1);

    public static synchronized mt_0 b(acl_0 acl_02) {
        mt_0 mt_02 = null;
        try {
            mt_02 = (mt_0)acl_02.adr();
            mt_02.a(acl_02);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        return mt_02;
    }

    public void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    public void release() {
        try {
            this.uG.af(this);
            this.uG = null;
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    /* synthetic */ mt_0(ai_0 ai_02) {
        this();
    }
}

