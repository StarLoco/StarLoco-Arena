/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import org.apache.log4j.Logger;

/*
 * Renamed from Ia
 */
public class ia_1
extends aoq_2 {
    private static final Logger a = Logger.getLogger(ia_1.class);
    private static final ia_1 bgg = new ia_1();
    private String IJ;

    public static ia_1 TJ() {
        return bgg;
    }

    private ia_1() {
    }

    public final void TK() {
        this.aYe();
        this.pJ(0);
        this.fq(false);
    }

    public final void a(int n2, int n3, String string) {
        this.cu(n2, n3);
        this.pJ(0);
        this.IJ = string;
        this.fq(true);
    }

    protected mk_1 TL() {
        return DofusArenaClientInstance.yl();
    }

    protected void TM() {
        pm_0.ur().bD(true).m(this.IJ, 0);
    }

    protected void TN() {
        pm_0.ur().done();
    }
}

