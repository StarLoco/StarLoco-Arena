/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from Ua
 */
public abstract class ua_1 {
    public static int ERROR = -1;
    public static int bPk = -2;
    public static int bPl = 0;
    public static int bPm = 20;
    protected final cp_2 bPn = new cp_2();
    protected final kl_1 bPo = new kl_1();
    protected final cp_2 bPp = new cp_2();
    protected long bPq = 0L;
    protected static final Logger a = Logger.getLogger(ua_1.class);

    public final boolean cM(long l2) {
        if (this.cY(l2)) {
            this.bPo.h(l2, (short)(this.bPo.bU(l2) + 1));
            return false;
        }
        this.cT(l2);
        this.bPo.h(l2, (short)1);
        this.bPp.a(l2, new qa_2());
        this.cV(l2);
        return true;
    }

    public final void a(long l2, ArrayList arrayList) {
        this.cM(l2);
        qa_2 qa_22 = (qa_2)this.bPp.t(l2);
        String string = "Demande de mailbox par " + l2 + ": ";
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            aLb aLb2 = (aLb)arrayList.get(j);
            string = string + aLb2.getId() + " ";
            long l3 = aLb2.getId();
            if (!this.bPn.m(l3)) {
                this.bPn.a(l3, aLb2);
            } else {
                aLb2.release();
            }
            qa_22.ct(l3);
        }
        a.info((Object)string);
        this.cW(l2);
        arrayList.clear();
    }

    public final void cN(long l2) {
        if (!this.cY(l2)) {
            a.warn((Object)("On tente de d\u00e9connecter un client qui n'est pas connect\u00e9 " + l2));
            return;
        }
        if (this.bPo.bU(l2) > 1) {
            this.bPo.h(l2, (short)(this.bPo.bU(l2) - 1));
        } else {
            this.cU(l2);
            this.bPo.bV(l2);
            this.bPp.u(l2);
            this.cX(l2);
        }
    }

    public final ArrayList cO(long l2) {
        ArrayList<aLb> arrayList = new ArrayList<aLb>();
        if (this.cY(l2)) {
            int n2 = ((qa_2)this.bPp.t(l2)).size();
            for (int j = 0; j < n2; ++j) {
                aLb aLb2 = (aLb)this.bPn.t(((qa_2)this.bPp.t(l2)).hn(j));
                if (aLb2 != null) {
                    arrayList.add(aLb2);
                    continue;
                }
                a.error((Object)("Mail non existant : clientId" + l2 + " mailId " + ((qa_2)this.bPp.t(l2)).hn(j)));
            }
        } else {
            a.warn((Object)("On demande la mailBox d'un client non connect\u00e9 " + l2));
        }
        return arrayList;
    }

    public final qa_2 cP(long l2) {
        if (this.cY(l2)) {
            return (qa_2)this.bPp.t(l2);
        }
        a.warn((Object)("On demande la mailBox optimis\u00e9e d'un client non connect\u00e9 " + l2));
        return null;
    }

    public final long a(aLb aLb2) {
        if (aLb2.aWa() < 0L || this.cY(aLb2.aWa())) {
            if (this.cS(aLb2.aWa())) {
                return bPk;
            }
            this.e(aLb2);
            if (aLb2.aWa() > 0L) {
                ((qa_2)this.bPp.t(aLb2.aWa())).ct(aLb2.getId());
                this.bPn.a(aLb2.getId(), aLb2);
            }
            if (aLb2.aWa() != aLb2.aWb() && this.cY(aLb2.aWb())) {
                ((qa_2)this.bPp.t(aLb2.aWb())).ct(aLb2.getId());
                this.bPn.a(aLb2.getId(), aLb2);
            }
            this.g(aLb2);
            return this.bPq;
        }
        a.warn((Object)("Un client d\u00e9connect\u00e9 du MailManager tente d'envoyer un mail. " + aLb2.aWa()));
        return ERROR;
    }

    public final int k(long l2, long l3) {
        aLb aLb2 = (aLb)this.bPn.t(l2);
        if (aLb2 != null) {
            a.info((Object)("Demande de suppression de mail " + aLb2.toString()));
            if (!aLb2.aWh() || aLb2.aWa() < 0L || aLb2.aWj() && aLb2.aWa() == l3 || aLb2.aWi() && aLb2.aWb() == l3 || aLb2.aWb() == aLb2.aWa()) {
                return this.b(aLb2);
            }
            this.a(aLb2, aLb2);
            if (aLb2.aWb() == l3) {
                aLb2.fm(true);
            }
            if (aLb2.aWa() == l3) {
                aLb2.fl(true);
            }
            this.b(aLb2, aLb2);
            return bPl;
        }
        return ERROR;
    }

    public final int cQ(long l2) {
        aLb aLb2 = (aLb)this.bPn.t(l2);
        if (aLb2 != null) {
            return this.b(aLb2);
        }
        return ERROR;
    }

    public final int b(aLb aLb2) {
        int n2;
        this.f(aLb2);
        a.info((Object)("Supression effective de mail " + aLb2.toString()));
        this.bPn.u(aLb2.getId());
        if (this.cY(aLb2.aWa())) {
            ((qa_2)this.bPp.t(aLb2.aWa())).remove(((qa_2)this.bPp.t(aLb2.aWa())).cw(aLb2.getId()));
        }
        if (this.cY(aLb2.aWb()) && aLb2.aWa() != aLb2.aWb() && 0 <= (n2 = ((qa_2)this.bPp.t(aLb2.aWb())).cw(aLb2.getId()))) {
            ((qa_2)this.bPp.t(aLb2.aWb())).remove(n2);
        }
        this.h(aLb2);
        return bPl;
    }

    public final int c(aLb aLb2) {
        aLb aLb3 = (aLb)this.bPn.t(aLb2.getId());
        if (aLb3 != null) {
            this.a(aLb2, aLb3);
            this.bPn.a(aLb2.getId(), aLb2);
            this.b(aLb2, aLb3);
            return bPl;
        }
        return ERROR;
    }

    public final aLb cR(long l2) {
        return (aLb)this.bPn.t(l2);
    }

    public void d(aLb aLb2) {
        this.a(aLb2, aLb2);
        aLb2.fk(true);
        this.b(aLb2, aLb2);
    }

    public boolean cS(long l2) {
        if (l2 < 0L || ((qa_2)this.bPp.t(l2)).size() < bPm) {
            return false;
        }
        int n2 = 0;
        qa_2 qa_22 = (qa_2)this.bPp.t(l2);
        for (int j = 0; j < qa_22.size(); ++j) {
            aLb aLb2 = (aLb)this.bPn.t(qa_22.get(j));
            if (aLb2 == null || (aLb2.aWa() != l2 || aLb2.aWi()) && (aLb2.aWb() != l2 || aLb2.aWj())) continue;
            ++n2;
        }
        return n2 >= bPm;
    }

    protected void cT(long l2) {
    }

    protected void cU(long l2) {
        qa_2 qa_22 = (qa_2)this.bPp.t(l2);
        int n2 = qa_22.size();
        for (int j = 0; j < n2; ++j) {
            aLb aLb2 = (aLb)this.bPn.t(qa_22.hn(j));
            if (aLb2 == null || aLb2.aWa() != l2 && this.bPo.m(aLb2.aWa()) || aLb2.aWb() != l2 && this.bPo.m(aLb2.aWb())) continue;
            this.bPn.u(aLb2.getId());
            aLb2.release();
        }
    }

    protected void e(aLb aLb2) {
        ++this.bPq;
        aLb2.c(this.bPq);
    }

    protected void f(aLb aLb2) {
    }

    protected void a(aLb aLb2, aLb aLb3) {
    }

    protected void cV(long l2) {
    }

    protected void cW(long l2) {
    }

    protected void cX(long l2) {
    }

    protected void g(aLb aLb2) {
    }

    protected void h(aLb aLb2) {
        aLb2.release();
    }

    protected void b(aLb aLb2, aLb aLb3) {
        if (aLb2 != aLb3) {
            aLb3.release();
        }
    }

    public boolean cY(long l2) {
        return this.bPo.m(l2);
    }

    public void cZ(long l2) {
        this.bPq = l2;
        a.info((Object)("currentMailId set\u00e9e \u00e0 " + l2));
    }
}

