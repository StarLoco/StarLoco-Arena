/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class yh
implements atG {
    private static final Logger a = Logger.getLogger(yh.class);
    private static final yh aAL = new yh();

    public static yh EP() {
        return aAL;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 22051: {
                ajk_1 ajk_12 = qy_2.ady().aV(((sb_0)pr_02).ak());
                azs_0.aLV().g("selectedAchievementType", ajk_12);
                ArrayList arrayList = ajk_12.azl();
                if (arrayList.size() > 0) {
                    azs_0.aLV().g("selectedAchievementSubtype", arrayList.get(0));
                }
                azs_0.aLV().a((aho_0)qy_2.ady(), qy_2.ce);
                azs_0.aLV().a((aho_0)((ajk_1)azs_0.aLV().getProperty("selectedAchievementType").getValue()), ajk_1.ce);
                return false;
            }
            case 22052: {
                azs_0.aLV().g("selectedAchievementSubtype", qy_2.ady().aW(((sb_0)pr_02).ak()));
                azs_0.aLV().a((aho_0)qy_2.ady(), qy_2.ce);
                azs_0.aLV().a((aho_0)((ajk_1)azs_0.aLV().getProperty("selectedAchievementType").getValue()), ajk_1.ce);
                return false;
            }
            case 22053: {
                azs_0.aLV().g("selectedAchievement", new aea_1(qy_2.ce(((sb_0)pr_02).ak())));
                azs_0.aLV().a((aho_0)qy_2.ady(), qy_2.ce);
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            apN.aDK().a(A.U());
            add_1.aOG().l("dofusarena.achievement", qJ.class);
            anp_0 anp_02 = new anp_0();
            apN.aDK().vJ().b(anp_02);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        apN.aDK().b(A.U());
        add_1.aOG().kO("achievementDialog");
        add_1.aOG().kG("dofusarena.achievement");
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

