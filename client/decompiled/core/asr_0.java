/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.io.File;
import org.apache.log4j.Logger;

/*
 * Renamed from asR
 */
public abstract class asr_0 {
    protected lb_0 cSy = null;
    protected aaW cSz = null;
    protected aaW cSA;
    protected lb_0 cSB;
    protected static final int cSC = 1973737728;
    private boolean cSD = true;
    private boolean cPM = false;
    private String m_name;
    private String eA;
    private long cSE;
    private Logger a = Logger.getLogger(asr_0.class);

    public asr_0() {
        this.cSB = new lb_0(5);
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void l(String string, String string2) {
        this.cPM = true;
        this.m_name = string;
        this.eA = string2;
        this.cSE = this.aFQ();
    }

    public void reload() {
        this.cSD = true;
        this.l(this.m_name, this.eA);
    }

    public void m(String string, String string2) {
        this.cPM = true;
        this.m_name = string;
    }

    public final String getName() {
        return this.m_name;
    }

    public void a(db_2 db_22, Entity entity) {
        if (!this.cPM) {
            ahA.axi().axk();
        }
        if (this.cSD) {
            this.t(db_22);
            this.cSD = false;
        }
        if (this.cSz == null) {
            this.aFP();
        }
    }

    public final void mg(int n2) {
        if (this.cSy == null) {
            return;
        }
        aaW aaW2 = (aaW)this.cSy.get(n2);
        if (aaW2 == null) {
            return;
        }
        if (!aaW2.apD()) {
            aaW2 = this.cSA;
        }
        if (this.cSz == aaW2) {
            return;
        }
        this.cSz = aaW2;
        this.reset();
    }

    public final boolean kK(int n2) {
        if (this.cSy == null) {
            return false;
        }
        aaW aaW2 = (aaW)this.cSy.get(n2);
        return aaW2 != null && aaW2.apD();
    }

    public void reset() {
        if (this.cSz == null) {
            return;
        }
        this.cSB.clear();
        this.cSz.reset();
    }

    public void a(QI qI) {
    }

    public final boolean aFO() {
        return this.cSz == this.cSA || !ahA.axi().axl();
    }

    public void parse() {
    }

    public void t(db_2 db_22) {
        if (this.cSy == null) {
            return;
        }
        ll_0 ll_02 = this.cSy.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            aaW aaW2 = (aaW)ll_02.value();
            aaW2.o(db_22);
            if (ll_02.kR() != 1771900034) continue;
            this.cSA = aaW2;
        }
    }

    private void aFP() {
        if (this.cSy == null) {
            return;
        }
        ll_0 ll_02 = this.cSy.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            aaW aaW2 = (aaW)ll_02.value();
            if (!aaW2.apD()) continue;
            this.a.info((Object)("Technique " + aaW2.getName() + " selected"));
            this.cSz = aaW2;
            return;
        }
        assert (false) : "No technique selected";
    }

    private long aFQ() {
        if (this.eA == null) {
            return 0L;
        }
        int n2 = this.eA.indexOf("file:");
        if (n2 == -1) {
            return 0L;
        }
        String string = this.eA.substring(n2 + 5);
        return new File(string).lastModified();
    }

    public final void aFR() {
        if (this.aFQ() > this.cSE) {
            this.reload();
        }
    }
}

