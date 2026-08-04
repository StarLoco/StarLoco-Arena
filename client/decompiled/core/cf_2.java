/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from CF
 */
public class cf_2
extends fh_2
implements JG,
adq_1 {
    protected static final boolean cR = false;
    protected static final Logger a = Logger.getLogger(cf_2.class);
    protected ait_0 aMg;
    protected acl_0 uG = null;
    private static int aMh = -1;

    public cf_2() {
        this.F(false);
        this.c(aMh--);
    }

    public void b() {
        this.F(false);
        this.aMg = null;
        this.uG = null;
        this.c(aMh--);
    }

    public void j() {
        this.aMg = null;
        this.uG = null;
        this.c(0L);
    }

    public void Ko() {
    }

    public void Kp() {
        a.info((Object)"FrameworkEntity::onReConnect()");
    }

    public void Kq() {
    }

    public void Kr() {
    }

    public void Ks() {
    }

    public void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    public acl_0 Kt() {
        return this.uG;
    }

    public synchronized void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"Exception lev\u00e9e lors de la lib\u00e9ration d'une FrameworkEntity : ", (Throwable)exception);
            }
        }
    }

    public void a(ait_0 ait_02) {
        this.aMg = ait_02;
    }

    public ait_0 Ku() {
        return this.aMg;
    }

    public synchronized void closeConnection() {
        if (this.aMg != null) {
            this.aMg.ayf();
        }
    }

    public synchronized void a(cf_2 cf_22) {
        ait_0 ait_02;
        if (cf_22 == null) {
            return;
        }
        if (this.aMg != null) {
            a.info((Object)"Fermeture de l'ancienne connection du FrameworkEntity");
            this.aMg.ayf();
            this.aMg = null;
        }
        if ((ait_02 = cf_22.aMg) != null) {
            this.aMg = ait_02;
            this.aMg.a(this);
            cf_22.a((ait_0)null);
        }
    }

    public synchronized boolean isConnected() {
        return this.aMg != null && !this.aMg.ayg() && !this.aMg.ayh();
    }

    public void b(pr_0 pr_02) {
        this.a(pr_02, false);
    }

    public synchronized void a(pr_0 pr_02, boolean bl2) {
        block11: {
            if (this.aMg != null) {
                try {
                    long l2 = System.currentTimeMillis();
                    byte[] byArray = pr_02.encode();
                    int n2 = (int)(System.currentTimeMillis() - l2);
                    adq adq2 = adq.u(pr_02.getClass().getSimpleName(), true);
                    if (byArray != null && byArray.length > 0) {
                        adq2.db(true);
                        adq2.jO(n2);
                        adq2.fe(byArray.length);
                        this.aMg.X(byArray);
                        break block11;
                    }
                    adq2.db(false);
                    adq2.jO(n2);
                    a.error((Object)("Message vide ou erreur d'encodage : " + pr_02.getId() + ", class : " + pr_02.getClass().getName()));
                }
                catch (Exception exception) {
                    a.error((Object)"Exception levee dans l'envoi d'un message", (Throwable)exception);
                }
            } else {
                a.error((Object)("Pas de connexion disponible pour envoyer le message !" + pr_02.getClass().getSimpleName()));
            }
        }
        try {
            if (!pr_02.uA()) {
                pr_02.release();
            }
        }
        catch (Exception exception) {
            if (this.aMg != null) {
                ka_2 ka_22 = this.aMg.rd();
                if (ka_22 != null) {
                    ka_22.d(exception);
                } else {
                    a.error((Object)bl_0.b(exception));
                }
            }
            a.error((Object)bl_0.b(exception));
        }
    }

    public synchronized void Kv() {
        if (this.aMg != null) {
            this.aMg.Kv();
        }
    }

    public synchronized void C(byte[] byArray) {
        if (this.aMg != null) {
            this.aMg.X(byArray);
        }
    }
}

