/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.chat.console.command.TeammateContentCommand;
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from aAt
 */
public class aat_2
extends ael_2 {
    private byte aV;
    private byte[] dpl;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        this.dpl = new byte[byteBuffer.remaining()];
        byteBuffer.get(this.dpl);
        return true;
    }

    public int getId() {
        return 8000;
    }

    public byte an() {
        return this.aV;
    }

    public byte[] aMK() {
        return this.dpl;
    }

    public static adu_0 ac(byte[] byArray) {
        Object object;
        int n2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray2);
        int n3 = byteBuffer.getInt();
        long l2 = byteBuffer.getLong();
        byte by = byteBuffer.get();
        adu_0 adu_02 = new adu_0(by, n3);
        adu_02.dy(l2);
        adu_02.ca(byteBuffer.getLong());
        adu_02.f(byteBuffer.getInt());
        cp_2 cp_22 = new cp_2();
        int n4 = byteBuffer.get();
        for (n2 = 0; n2 < n4; ++n2) {
            aat_2.a(byteBuffer, cp_22);
        }
        n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            aat_2.a(byteBuffer, adu_02, cp_22, byArray2);
        }
        azg_0 azg_02 = new azg_0(adu_02);
        adu_02.a(azg_02);
        azs_0.aLV().g("fight.timeline", adu_02.ass());
        int n5 = byteBuffer.get();
        ee_2 ee_22 = null;
        for (int j = 0; j < n5; ++j) {
            long l3 = byteBuffer.getLong();
            adu_02.a(l3, false);
            object = (ee_2)adu_02.eg(l3);
            if (object instanceof ta_0 || object instanceof wo_1) {
                if (ee_22 == null) continue;
                ((ee_2)object).c(ee_22);
                continue;
            }
            ee_22 = object;
        }
        adu_02.ass().dl();
        for (yg_0 yg_02 : adu_02.aKn()) {
            cp_2 cp_23 = ((axD)yg_02).aKB();
            object = cp_23.eJ();
            for (int j = 0; j < ((Object)object).length; ++j) {
                akv_0 akv_02 = (akv_0)cp_23.t((long)object[j]);
                aoo_0 aoo_02 = aoo_0.a(akv_02.K(), yg_02.lV(), (long)object[j]);
                akv_02 = adu_02.ass().a(aoo_02, arm_0.lQ(akv_02.aVC() - 1).dS(false));
                akv_02.setPosition(adu_02.ass().bj(akv_02.K()));
            }
        }
        ArrayList arrayList = new ArrayList();
        int n6 = byteBuffer.get();
        for (int j = 0; j < n6; ++j) {
            int n7 = byteBuffer.getInt();
            arrayList.add(cw_1.eO().w(n7));
        }
        adu_02.m(arrayList);
        aat_2.a(byteBuffer, adu_02);
        adu_02.aI(byteBuffer.getShort());
        aat_2.a(byteBuffer, n4, adu_02);
        adu_02.gV().f(byteBuffer);
        adu_02.aKz();
        return adu_02;
    }

    private static void a(ByteBuffer byteBuffer, adu_0 adu_02, cp_2 cp_22, byte[] byArray) {
        Te te = adu_02.ast();
        te.f(byteBuffer);
        te.L(byArray);
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            byte by = byteBuffer.get();
            ee_2 ee_22 = by == 0 ? new ee_2() : (by == 1 ? new ta_0() : new wo_1());
            if (ee_22.b(byteBuffer)) {
                te.j(ee_22);
                long l2 = byteBuffer.getLong();
                cl_1 cl_12 = (cl_1)cp_22.t(l2);
                if (cl_12 != null) {
                    cl_12.a(ee_22);
                    continue;
                }
                a.error((Object)"coach inexistant : probl\u00e8me de s\u00e9rialisation");
                continue;
            }
            a.error((Object)"La d\u00e9s\u00e9rialisation d'un fighter a \u00e9chou\u00e9e !");
        }
        adu_02.a(te, te.lV());
        cp_22.a(new aoe_2(te));
    }

    private static void a(ByteBuffer byteBuffer, cp_2 cp_22) {
        aez_0 aez_02 = new aez_0();
        if (aez_02.b(byteBuffer, 34)) {
            int n2 = byteBuffer.getShort() & 0xFFFF;
            if (n2 > 0) {
                byte[] byArray = new byte[n2];
                byteBuffer.get(byArray);
                PlayerStatisticsReport playerStatisticsReport = (PlayerStatisticsReport)arq_0.aEv().aa(byArray);
                if (playerStatisticsReport != null) {
                    aez_02.a(playerStatisticsReport);
                }
            }
            cp_22.a(aez_02.Lb(), aez_02);
        }
    }

    private static void a(ByteBuffer byteBuffer, adu_0 adu_02) {
        int n2;
        int n3 = byteBuffer.get();
        ArrayList<ack_1> arrayList = new ArrayList<ack_1>();
        for (n2 = 0; n2 < n3; ++n2) {
            ack_1 ack_12;
            long l2 = byteBuffer.getLong();
            long l3 = byteBuffer.getLong();
            int n4 = byteBuffer.getInt();
            int n5 = byteBuffer.getInt();
            short s = byteBuffer.getShort();
            yl_1 yl_12 = ame_1.aWP().eO(l2);
            if (yl_12 != null) {
                ack_12 = yl_12.a(new akh_0(l3, n4, n5, s, adu_02.Np(), null));
                adu_02.gX().f(ack_12);
                adu_02.a(ack_12);
                continue;
            }
            yl_12 = ame_1.aWP().eN(l2);
            if (yl_12 != null) {
                ack_12 = yl_12.a(new akh_0(l3, n4, n5, s, adu_02.Np(), null));
                arrayList.add(ack_12);
                continue;
            }
            a.error((Object)("Impossible de trouver la cellule sp\u00e9ciale " + l2));
        }
        if (!arrayList.isEmpty()) {
            vt_0.aiU().activate();
            for (n2 = 0; n2 < arrayList.size(); ++n2) {
                ack_1 ack_13 = (ack_1)arrayList.get(n2);
                adu_02.gX().f(ack_13);
            }
        }
    }

    public static void a(ByteBuffer byteBuffer, int n2, adu_0 adu_02) {
        sj_1 sj_12 = apN.aDK().Ln();
        int n3 = byteBuffer.getInt();
        Te[] teArray = new Te[]{(Te)azs_0.aLV().getProperty("fight.team0").getValue(), (Te)azs_0.aLV().getProperty("fight.team1").getValue()};
        if (n3 != 0) {
            for (int j = 0; j < n2; ++j) {
                long l2 = byteBuffer.getLong();
                byte by = adu_02.ef(l2).Le();
                if (sj_12 != null && by == sj_12.Le() && l2 != sj_12.getId()) {
                    TeammateContentCommand.MH().bA(l2);
                }
                int n4 = byteBuffer.getInt();
                int n5 = byteBuffer.getInt();
                int n6 = byteBuffer.getInt();
                int n7 = byteBuffer.getInt();
                int n8 = byteBuffer.getInt();
                if (adu_02.aKl() == 6) {
                    teArray[by].hR(n8);
                } else {
                    teArray[by].setStrength(n4);
                }
                teArray[by].hM(n5);
                teArray[by].hP(n6);
                teArray[by].hQ(n7);
            }
        } else {
            Iterator iterator = adu_02.aKj();
            while (iterator.hasNext()) {
                Object object;
                cl_1 cl_12 = (cl_1)iterator.next();
                if (!(cl_12 instanceof aez_0)) continue;
                JG jG = (aez_0)cl_12;
                byte by = adu_02.ef(((ahh_1)((Object)jG)).getId()).Le();
                if (sj_12 != null && by == sj_12.Le() && ((ahh_1)((Object)jG)).getId() != sj_12.getId()) {
                    TeammateContentCommand.MH().bA(((ahh_1)((Object)jG)).getId());
                }
                if (adu_02.aKl() == 6) {
                    teArray[by].hR(((aez_0)jG).afQ());
                    object = xz_0.amc();
                    teArray[by].hM(((sw_1)object).afB());
                    teArray[by].hP(((sw_1)object).afC());
                    teArray[by].hQ(((sw_1)object).afD());
                    continue;
                }
                teArray[by].setStrength(((aez_0)jG).bi((byte)1));
                object = ((aez_0)jG).aQs();
                if (object == null) continue;
                teArray[by].hM(((PlayerStatisticsReport)object).dJ());
                teArray[by].hP(((PlayerStatisticsReport)object).dK());
                teArray[by].hQ(((PlayerStatisticsReport)object).dP());
            }
            for (JG jG : adu_02.aKn()) {
                if (!(jG instanceof axD)) continue;
                teArray[((yg_0)jG).lV()].setName(((axD)jG).getName());
            }
        }
        adu_02.dd(byteBuffer.get() != 0);
    }
}

