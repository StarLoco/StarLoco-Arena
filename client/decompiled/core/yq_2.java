/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yq
 */
public class yq_2
implements atG {
    private static yq_2 aCm = new yq_2();
    private aez_0 aCn;
    private awC aCo;

    public static yq_2 Fa() {
        return aCm;
    }

    public void b(aez_0 aez_02) {
        this.aCn = aez_02;
    }

    public void a(awC awC2) {
        this.aCo = awC2;
    }

    public awC Fb() {
        return this.aCo;
    }

    public aez_0 Fc() {
        return this.aCn;
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 2261: {
                wv_2 wv_22 = (wv_2)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (this.aCo != null && this.aCn != null) {
                    this.aCo.addSeparator();
                    aon_0 aon_02 = aon_0.aYc();
                    apN apN2 = apN.aDK();
                    if (wv_22.Od()) {
                        this.aCo.a(aon_02.getString("enterSpectatorMode"), null, new lg_2(this, apN2), true);
                    } else if (!apN2.aDN()) {
                        this.aCo.a(aon_02.getString("fightInvitation.training"), null, new lf_2(this, apN2), true);
                        this.aCo.a(aon_02.getString("exchange.invitation"), null, new lu_2(this, apN2), true);
                        this.aCo.a(aon_02.getString("whispToCoach"), null, new lt_2(this), true);
                        ca_0 ca_02 = apN.aDK().Ln().aPY();
                        ca_0 ca_03 = this.aCn.aPY();
                        if (ca_03 == null && ca_02 != null && ca_02.Kg().aQY()) {
                            this.aCo.a(aon_0.aYc().getString("guild.addGuildMember"), null, new ls_2(this, ca_02), true);
                        }
                    }
                }
                add_1.aOG().e(this.aCo);
                bl2 = false;
            }
        }
        return bl2;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    static /* synthetic */ aez_0 a(yq_2 yq_22) {
        return yq_22.aCn;
    }

    static /* synthetic */ aez_0 a(yq_2 yq_22, aez_0 aez_02) {
        yq_22.aCn = aez_02;
        return yq_22.aCn;
    }
}

