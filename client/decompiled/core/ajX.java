/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class ajX {
    protected static final Logger a = Logger.getLogger(ajX.class);
    protected final cp_2 aiM = new cp_2();
    private static final ajX cCG = new ajX();

    public static ajX azB() {
        return cCG;
    }

    public void b(apn_0 apn_02) {
        this.aiM.u(apn_02.getId());
        for (axu_0 axu_02 : apn_02.aYW()) {
            if (!(axu_02 instanceof tp_1)) continue;
            GY.Ss().c((tp_1)axu_02);
        }
        apn_02.aZa();
    }

    public boolean c(apn_0 apn_02) {
        return this.aiM.m(apn_02.getId());
    }

    public void e(do_1 do_12) {
        if (!this.aiM.v(do_12.getId())) {
            sj_1 sj_12;
            dc_0 dc_02;
            this.aiM.a(do_12.getId(), do_12);
            if (do_12.asN() && (dc_02 = auU.x((short)(sj_12 = apN.aDK().Ln()).getWorldX(), (short)sj_12.getWorldY())) != null) {
                assert (dc_02.Ls().F(do_12.gn(), do_12.go())) : "Coordonn\u00e9es incorrectes " + do_12.gn() + " " + do_12.go();
                dc_02.b(do_12.gn(), do_12.go(), true);
            }
        } else {
            a.error((Object)("Impossible d'ajouter un \u00e9l\u00e9ments interactif d'ID=" + do_12.getId() + " au manager " + this + " qui le contient d\u00e9j\u00e0."));
        }
    }

    public void d(apn_0 apn_02) {
        if (this.c(apn_02)) {
            this.aiM.u(apn_02.getId());
        } else {
            a.warn((Object)"on essaye d'enlever un interactiveElement qui n'est pas dans la liste");
        }
    }

    public do_1 dJ(long l2) {
        do_1 do_12 = (do_1)this.aiM.t(l2);
        if (do_12 != null) {
            return do_12;
        }
        return null;
    }

    public void azC() {
        this.aiM.a(new akj_1(this));
        this.aiM.clear();
    }

    public cp_2 xY() {
        return this.aiM;
    }
}

