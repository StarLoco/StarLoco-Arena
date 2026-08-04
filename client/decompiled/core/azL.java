/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class azL
implements atG {
    private static Logger a = Logger.getLogger(azL.class);
    private static azL doJ = new azL();
    private ee_2 doK = null;

    public static azL aMm() {
        return doJ;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 18001: 
            case 18010: {
                auq_0 auq_02 = new auq_0();
                apN.aDK().vJ().b(auq_02);
                return false;
            }
            case 30000: {
                ado ado2 = (ado)pr_02;
                if (ado2.aqY() == 1) {
                    mT mT2;
                    ArrayList arrayList;
                    qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
                    if (this.doK == null && (arrayList = qs_22.c(ado2.au(), (double)ado2.av())).size() != 0 && (mT2 = (mT)arrayList.get(0)) != null && mT2 instanceof vD) {
                        vD vD2 = (vD)mT2;
                        this.doK = vD2.tG();
                        vD2.BT();
                        vD2.BX();
                        azs_0.aLV().g("fight.timeline.currentFighter", vD2.tG());
                        add_1.aOG().a("fighterControlsDialog", oh_2.bq("fighterControlsDialog"), (short)10100);
                        return false;
                    }
                    if (this.doK != null) {
                        Object object;
                        arrayList = qs_22.a(ado2.au(), ado2.av(), 1.0f, ma_0.buh);
                        boolean bl2 = false;
                        ry ry2 = null;
                        if (arrayList == null) {
                            if (qs_22.vn() == null) {
                                a.error((Object)"La sc\u00e8ne n'a pas de camera");
                            } else {
                                a.error((Object)"La sc\u00e8ne n'a pas de cellules");
                            }
                        } else {
                            for (int j = 0; j < arrayList.size() && !bl2; ++j) {
                                object = (DisplayedScreenElement)arrayList.get(j);
                                cl_1 cl_12 = this.doK.LQ();
                                if (cl_12 == null || this.doK.PH() == null) {
                                    if (cl_12 == null) {
                                        a.error((Object)("pas de controlleur pour le fighter " + this.doK));
                                        continue;
                                    }
                                    a.error((Object)("pas d'\u00e9quipe pour le fighter " + this.doK));
                                    continue;
                                }
                                byte by = this.doK.PH().lV();
                                ry2 = ((DisplayedScreenElement)object).atV().avX();
                                bl2 = azh.aLL().a(by, ry2);
                            }
                        }
                        if (!bl2 && ((ArrayList)(object = qs_22.c(ado2.au(), (double)ado2.av()))).size() != 0) {
                            ry2 = ((mT)((ArrayList)object).get(0)).aTI();
                            bl2 = true;
                        }
                        if (bl2) {
                            object = new adn_0();
                            ((adn_0)object).j(this.doK.getId());
                            ((adn_0)object).jW(ry2.getX());
                            ((adn_0)object).jX(ry2.getY());
                            ((adn_0)object).by(ry2.wk());
                            apN.aDK().vJ().b((pr_0)object);
                        }
                        this.doK.NW().BU();
                        this.doK.NW().BY();
                        this.doK = null;
                        azs_0.aLV().g("fight.timeline.currentFighter", (Object)null);
                        add_1.aOG().kO("fighterControlsDialog");
                    }
                }
                return false;
            }
            case 18003: {
                ayd_0 ayd_02 = (ayd_0)pr_02;
                ee_2 ee_22 = ayd_02.tG();
                if (ee_22 != null) {
                    lr_2 lr_22 = new lr_2();
                    lr_22.j(ee_22.getId());
                    lr_22.a(qc_0.bEM);
                    apN.aDK().vJ().b(lr_22);
                }
                return false;
            }
            case 18002: {
                ayd_0 ayd_03 = (ayd_0)pr_02;
                ee_2 ee_23 = ayd_03.tG();
                if (ee_23 != null) {
                    lr_2 lr_23 = new lr_2();
                    lr_23.j(ee_23.getId());
                    lr_23.a(qc_0.bEK);
                    apN.aDK().vJ().b(lr_23);
                }
                return false;
            }
            case 18004: {
                ayd_0 ayd_04 = (ayd_0)pr_02;
                ee_2 ee_24 = ayd_04.tG();
                if (ee_24 != null) {
                    lr_2 lr_24 = new lr_2();
                    lr_24.j(ee_24.getId());
                    lr_24.a(qc_0.bEO);
                    apN.aDK().vJ().b(lr_24);
                }
                return false;
            }
            case 18005: {
                ayd_0 ayd_05 = (ayd_0)pr_02;
                ee_2 ee_25 = ayd_05.tG();
                if (ee_25 != null) {
                    lr_2 lr_25 = new lr_2();
                    lr_25.j(ee_25.getId());
                    lr_25.a(qc_0.bEQ);
                    apN.aDK().vJ().b(lr_25);
                }
                return false;
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
        if (!bl2) {
            add_1.aOG().a("fightPlacementDialog", oh_2.bq("fightPlacementDialog"), 1L, (short)10000);
            add_1.aOG().a("timelineDialog", oh_2.bq("timelineDialog"), 1L, (short)10000);
            azs_0.aLV().g("fight.status", "placement");
            long l2 = apN.aDK().Ln().getId();
            boolean bl3 = apN.aDK().aDL().ef(l2) != null;
            azs_0.aLV().g("fightButtonReadyVisible", bl3);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            if (this.doK != null) {
                this.doK.NW().BU();
                this.doK.NW().BY();
                this.doK = null;
            }
            azs_0.aLV().kb("fight.timeline.currentFighter");
            add_1.aOG().kO("fightPlacementDialog");
            add_1.aOG().kO("fighterControlsDialog");
        }
    }

    public ee_2 aMn() {
        return this.doK;
    }
}

