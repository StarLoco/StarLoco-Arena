/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from fh
 */
public class fh_2
implements alx_0 {
    protected static final Logger a = Logger.getLogger(fh_2.class);
    private final ArrayList qe = new ArrayList();
    private final ArrayList qf = new ArrayList();
    private final ArrayList qg = new ArrayList();
    private final ArrayList qh = new ArrayList();
    private final ArrayList qi = new ArrayList();
    private boolean qj = false;
    private long nD = 0L;

    public long getId() {
        return this.nD;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        this.ie();
        ArrayList arrayList = this.qe;
        synchronized (arrayList) {
            this.qj = true;
            int n2 = this.qe.size();
            if (n2 > 0) {
                for (int j = 0; j < n2; ++j) {
                    atG atG2 = (atG)this.qe.get(j);
                    if (atG2 == null) continue;
                    try {
                        bl2 = atG2.a(pr_02);
                    }
                    catch (Exception exception) {
                        a.error((Object)("Exception lev\u00e9e lors du traitement d'un message : " + pr_02.getClass().getSimpleName() + " " + (exception.getStackTrace() != null && exception.getStackTrace().length > 0 ? exception.getStackTrace()[0] : " ")), (Throwable)exception);
                    }
                    if (bl2) {
                        continue;
                    }
                    break;
                }
            } else {
                a.warn((Object)("L'entit\u00e9 destinataire du message n'a pas de frames, message : " + pr_02.getClass().getSimpleName()));
                bl2 = true;
            }
            this.qj = false;
        }
        if (bl2) {
            a.warn((Object)("[DEFAUT DE CONCEPTION] Message (" + pr_02.getClass().getSimpleName() + ") non trait\u00e9, de type " + pr_02.getId() + ", les frames ont toutes retourn\u00e9 true"));
        }
        this.if();
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ie() {
        ArrayList arrayList = this.qe;
        synchronized (arrayList) {
            atG atG2;
            int n2;
            int n3;
            ArrayList arrayList2 = this.qg;
            synchronized (arrayList2) {
                n3 = this.qg.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    atG2 = (atG)this.qg.get(n2);
                    if (this.qe.contains(atG2)) continue;
                    this.qe.add(0, atG2);
                    atG2.a(this, false);
                }
                this.qg.clear();
            }
            arrayList2 = this.qf;
            synchronized (arrayList2) {
                n3 = this.qf.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    atG2 = (atG)this.qf.get(n2);
                    if (!this.qe.remove(atG2)) continue;
                    atG2.b(this, false);
                }
                this.qf.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void if() {
        ArrayList arrayList = this.qe;
        synchronized (arrayList) {
            atG atG2;
            int n2;
            int n3;
            ArrayList arrayList2 = this.qi;
            synchronized (arrayList2) {
                n3 = this.qi.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    atG2 = (atG)this.qi.get(n2);
                    if (this.qe.contains(atG2)) continue;
                    this.qe.add(0, atG2);
                    atG2.a(this, false);
                }
                this.qi.clear();
            }
            arrayList2 = this.qh;
            synchronized (arrayList2) {
                n3 = this.qh.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    atG2 = (atG)this.qh.get(n2);
                    if (!this.qe.remove(atG2)) continue;
                    atG2.b(this, false);
                }
                this.qh.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(atG atG2) {
        if (this.qj) {
            boolean bl2;
            ArrayList arrayList = this.qh;
            synchronized (arrayList) {
                bl2 = this.qh.contains(atG2);
            }
            if (bl2) {
                arrayList = this.qg;
                synchronized (arrayList) {
                    if (!this.qg.contains(atG2)) {
                        this.qg.add(atG2);
                    }
                    atG2.a(this, true);
                }
                arrayList = this.qf;
                synchronized (arrayList) {
                    this.qf.remove(atG2);
                }
            }
            arrayList = this.qi;
            synchronized (arrayList) {
                this.qi.add(atG2);
                atG2.a(this, true);
            }
        }
        ArrayList arrayList = this.qe;
        synchronized (arrayList) {
            if (!this.qe.contains(atG2)) {
                this.qe.add(0, atG2);
                atG2.a(this, false);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(atG atG2) {
        if (this.qj) {
            boolean bl2;
            ArrayList arrayList = this.qi;
            synchronized (arrayList) {
                bl2 = this.qi.contains(atG2);
            }
            if (bl2) {
                arrayList = this.qf;
                synchronized (arrayList) {
                    if (!this.qf.contains(atG2)) {
                        this.qf.add(atG2);
                    }
                }
                arrayList = this.qg;
                synchronized (arrayList) {
                    if (this.qg.remove(atG2)) {
                        atG2.b(this, false);
                    }
                }
            }
            arrayList = this.qh;
            synchronized (arrayList) {
                if (!this.qh.contains(atG2)) {
                    this.qh.add(atG2);
                    atG2.b(this, true);
                }
            }
        }
        ArrayList arrayList = this.qe;
        synchronized (arrayList) {
            if (this.qe.remove(atG2)) {
                atG2.b(this, false);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void ig() {
        Object[] objectArray = null;
        Object[] objectArray2 = this.qe;
        synchronized (this.qe) {
            objectArray = this.qe.toArray();
            // ** MonitorExit[var2_2] (shouldn't be in output)
            if (this.qj) {
                if (objectArray != null) {
                    objectArray2 = this.qh;
                    synchronized (objectArray2) {
                        for (Object object : objectArray) {
                            atG atG2 = (atG)object;
                            if (this.qh.contains(atG2)) continue;
                            this.qh.add(atG2);
                            atG2.b(this, true);
                        }
                    }
                }
            } else {
                objectArray2 = this.qe;
                synchronized (objectArray2) {
                    this.qe.clear();
                }
                for (Object object : objectArray) {
                    atG atG3 = (atG)object;
                    atG3.b(this, false);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean c(atG atG2) {
        ArrayList arrayList;
        boolean bl2;
        if (this.qj) {
            bl2 = this.qe.contains(atG2);
        } else {
            arrayList = this.qe;
            synchronized (arrayList) {
                bl2 = this.qe.contains(atG2);
            }
        }
        if (!bl2) {
            arrayList = this.qg;
            synchronized (arrayList) {
                bl2 = this.qg.contains(atG2);
            }
        }
        if (!bl2) {
            arrayList = this.qi;
            synchronized (arrayList) {
                bl2 = this.qi.contains(atG2);
            }
        }
        return bl2;
    }

    public boolean ih() {
        return this.qj;
    }

    public void F(boolean bl2) {
        this.qj = bl2;
    }

    public ArrayList ii() {
        return this.qe;
    }

    public ArrayList ij() {
        return this.qf;
    }

    public ArrayList ik() {
        return this.qg;
    }

    public ArrayList il() {
        return this.qh;
    }

    public ArrayList im() {
        return this.qi;
    }

    public final void in() {
        StringBuffer stringBuffer = new StringBuffer("Frames in ");
        stringBuffer.append(this.getClass().getName()).append("\n");
        int n2 = 0;
        for (atG atG2 : this.qe) {
            stringBuffer.append(n2).append("> ").append(atG2.getClass().getName()).append("\n");
            ++n2;
        }
        a.info((Object)stringBuffer.toString());
    }
}

