/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from lg
 */
public class lg_0
implements atG {
    protected static final Logger a = Logger.getLogger(lg_0.class);
    private static lg_0 Gl = new lg_0();
    private short Gm;

    public static lg_0 pU() {
        return Gl;
    }

    public void C(short s) {
        this.Gm = s;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 26300: {
                wu_2 wu_22 = (wu_2)pr_02;
                mz_0 mz_02 = new mz_0();
                mz_02.d(wu_22.Y());
                apN.aDK().vJ().b(mz_02);
                return false;
            }
            case 5102: {
                uo_1 uo_12 = (uo_1)pr_02;
                tw_0 tw_02 = new tw_0();
                tw_02.cm(uo_12.fX());
                tw_02.am((byte)1);
                apN.aDK().vJ().b(tw_02);
                return false;
            }
            case 6025: {
                dy_2 dy_22 = (dy_2)pr_02;
                abB abB2 = new abB();
                abB2.U(dy_22.MF());
                abB2.T(dy_22.ME());
                abB2.setName(dy_22.getName());
                abB2.M((short)2);
                abB2.cV(false);
                apN.aDK().vJ().b(abB2);
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        azs_0.aLV().g("menuBar.tutorialButton", false);
        apN.aDK().a(agq_1.awr());
        apN.aDK().b(ug_1.AL());
        apN.aDK().b(ft_1.jr());
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            apN.aDK().b(agq_1.awr());
            apN.aDK().a(ug_1.AL());
            apN.aDK().a(ft_1.jr());
            azs_0.aLV().g("menuBar.tutorialButton", true);
        }
        apN.aDK().dR(false);
    }
}

