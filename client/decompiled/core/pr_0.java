/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from pR
 */
public abstract class pr_0
implements JG,
Wv {
    protected static final Logger a = Logger.getLogger(pr_0.class);
    acl_0 uG;
    protected Wv acG;
    protected Wv acH;
    long acI;
    protected aba acJ;
    protected boolean acK = false;

    public Wv uw() {
        return this.acH;
    }

    public void a(Wv wv) {
        this.acH = wv;
    }

    public Wv ux() {
        return this.acG;
    }

    public void b(Wv wv) {
        this.acG = wv;
    }

    public alx_0 uy() {
        if (this.acJ != null) {
            return (alx_0)this.acJ.asj();
        }
        return null;
    }

    public void a(alx_0 alx_02) {
        this.acJ = new aba();
        this.acJ.a(alx_02);
    }

    public boolean uz() {
        if (this.acJ != null) {
            return this.acJ.asi();
        }
        return false;
    }

    protected void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    public void release() {
        this.acJ = null;
        this.acK = false;
        if (this.uG != null) {
            try {
                acl_0 acl_02 = this.uG;
                this.uG = null;
                acl_02.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        } else {
            this.j();
        }
    }

    public boolean uA() {
        return this.acK;
    }

    public void aj(boolean bl2) {
        this.acK = bl2;
    }

    public final void uB() {
        if (!this.acK) {
            this.release();
        }
    }

    public void execute() {
        if (this.acJ != null) {
            alx_0 alx_02 = (alx_0)this.acJ.asj();
            if (alx_02 != null) {
                alx_02.a(this);
            } else {
                a.warn((Object)("Le message de type " + this.getClass().getSimpleName() + " n'a pas de destinataire."));
            }
        }
    }

    public abstract byte[] encode();

    public abstract boolean a(byte[] var1);

    public abstract int getId();

    public void f(int n2) {
    }

    protected final byte[] m(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() >= 1) {
            int n2 = byteBuffer.get() & 0xFF;
            if (n2 == 0) {
                return null;
            }
            if (byteBuffer.remaining() < n2) {
                return null;
            }
            byte[] byArray = new byte[n2];
            byteBuffer.get(byArray);
            return byArray;
        }
        return null;
    }

    public boolean a(int n2, int n3, boolean bl2) {
        if (bl2) {
            if (n2 != n3) {
                a.error((Object)("****************************** Message de longueur incorrecte : re\u00e7u=" + n2 + " octet(s), attendu=" + n3 + " octet(s), type : " + this.getClass().getName()), (Throwable)new Exception("TRACE"));
                return false;
            }
        } else if (n2 < n3) {
            a.error((Object)("****************************** Message de longueur incorrecte : re\u00e7u=" + n2 + " octet(s), attendu >= " + n3 + " octet(s), type : " + this.getClass().getName()), (Throwable)new Exception("TRACE"));
            return false;
        }
        return true;
    }

    void aw(long l2) {
        this.acI = l2;
    }

    public long uC() {
        return this.acI;
    }

    public String toString() {
        return this.getClass().getName() + '@' + Integer.toHexString(this.hashCode()) + ", listener : " + this.uy();
    }
}

