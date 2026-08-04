/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.LinkedList;
import org.apache.log4j.Logger;

/*
 * Renamed from VR
 */
public class vr_0
implements dA {
    protected static final Logger a = Logger.getLogger(vr_0.class);
    private static final vr_0 bTr = new vr_0();
    private akb_2 bTs;
    private final LinkedList bTt = new LinkedList();
    private boolean bTu = false;

    public static vr_0 aiM() {
        return bTr;
    }

    public akb_2 b(Eq eq) {
        if (this.bTs == null) {
            this.bTs = new akb_2();
        }
        this.bTs.c(eq);
        return this.bTs;
    }

    public void aiN() {
        if (this.bTs != null) {
            this.bTs.kill();
        }
    }

    public void clear() {
        this.aiN();
        int n2 = this.bTt.size();
        for (int j = 0; j < n2; ++j) {
            akb_2 akb_22 = (akb_2)this.bTt.get(j);
            akb_22.kill();
        }
    }

    public akb_2 aiO() {
        return this.bTs;
    }

    public LinkedList aiP() {
        return this.bTt;
    }

    public void aiQ() {
        if (this.bTs == null) {
            return;
        }
        this.bTt.add(this.bTs);
        this.bTs = null;
        if (!this.bTu) {
            this.aiR();
        }
    }

    private void aiR() {
        if (!this.bTu && this.bTt.size() > 0) {
            this.bTu = true;
            akb_2 akb_22 = (akb_2)this.bTt.getFirst();
            akb_22.a(this);
            akb_22.aVJ();
        }
    }

    public void a(akb_2 akb_22) {
        this.bTt.remove(akb_22);
        this.bTu = false;
        this.aiR();
    }

    public void aiS() {
        a.info((Object)("Action In Execution : " + this.bTu));
        if (this.bTt.size() > 0) {
            for (Object object : this.bTt) {
                a.info((Object)("Executing Action Group (" + ((akb_2)object).aVH().size() + " actions)"));
                for (Eq eq : ((akb_2)object).aVH()) {
                    a.info((Object)(" * " + eq.getClass().getSimpleName()));
                }
            }
        }
        if (this.bTs != null) {
            a.info((Object)("Pending Action Group (" + this.bTs.aVH().size() + " groupes)"));
            for (Object object : this.bTs.aVH()) {
                a.info((Object)(" - " + object.getClass().getSimpleName()));
            }
        } else {
            a.info((Object)"Pending Action Group is null");
        }
    }

    public void aiT() {
        while (!this.bTt.isEmpty()) {
            akb_2 akb_22 = (akb_2)this.bTt.getFirst();
            akb_22.b(this);
            akb_22.aiT();
            this.bTt.remove(akb_22);
        }
    }
}

