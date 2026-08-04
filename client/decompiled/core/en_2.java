/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.chat.console.command.RaybanCommand;
import org.apache.log4j.Logger;

/*
 * Renamed from En
 */
public class en_2
extends nb_0 {
    private static final Logger a = Logger.getLogger(en_2.class);
    private static en_2 aQq = new en_2();
    private static int aQr = aql_0.cOD;

    public static en_2 Na() {
        return aQq;
    }

    public static int Nb() {
        return aQr;
    }

    public static void fk(int n2) {
        aQr = n2;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 23101: {
                xz_0 xz_02 = xz_0.amc();
                if (aQr == aql_0.cOF) {
                    if (en_2.lj()) {
                        long l2 = apN.aDK().Ln().getId();
                        short s = xz_02.tI();
                        this.d(s, l2);
                    }
                } else if (aQr == aql_0.cOG && en_2.lk()) {
                    long l3 = apN.aDK().Ln().getId();
                    short s = 10000;
                    this.d(s, l3);
                }
                return false;
            }
        }
        return super.a(pr_02);
    }

    protected void W() {
        add_1.aOG().kO("tournamentEvolutionDialog");
        add_1.aOG().kO("tournamentGraveyardDialog");
    }

    public void a(fh_2 fh_22, boolean bl2) {
        super.a(fh_22, bl2);
        apN.aDK().a(ds_2.LP());
    }

    public void b(fh_2 fh_22, boolean bl2) {
        RaybanCommand.uninitialize();
        super.b(fh_22, bl2);
    }

    private void d(short s, long l2) {
        vk_1.aj(l2);
        vk_1.C(s);
        apN.aDK().a(do_2.Mm());
        ly_1 ly_12 = new ly_1();
        ly_12.ad(vk_1.fx());
        ly_12.aj(vk_1.qX());
        ly_12.C(vk_1.qY());
        apN.aDK().vJ().b(ly_12);
        apN.aDK().b(this);
    }
}

