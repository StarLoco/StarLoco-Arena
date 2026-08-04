/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class DofusArenaClientInstance
extends zh_1 {
    private static Logger a = Logger.getLogger(DofusArenaClientInstance.class);
    private static DofusArenaClientInstance aiU = new DofusArenaClientInstance();

    public DofusArenaClientInstance() {
        this.c(new amT(this));
        this.a(new gz_1());
        this.c(new rt_2());
        this.Pm();
    }

    public static DofusArenaClientInstance yl() {
        return aiU;
    }

    protected void ym() {
        super.ym();
    }

    public void start() {
        apN.aDK().a(yy_0.amI());
        azs_0 azs_02 = azs_0.aLV();
        azs_02.g("account.name", this.aod().f(adc_0.clL));
        ArrayList arrayList = NM.a(mu_1.rM(), "proxyGroup", "proxyAddresses");
        int n2 = this.aod().d(adc_0.clM);
        if (n2 < arrayList.size()) {
            azs_02.g("proxy.selected", arrayList.get(n2));
        }
        azs_02.g("account.remember", this.aod().a(adc_0.clK));
        azs_02.g("proxy.list", arrayList.toArray());
        add_1.aOG().a("logonDialog", oh_2.bq("logonDialog"), 0L, (short)10000);
    }

    public void cleanUp() {
        super.cleanUp();
        bx_2 bx_22 = this.kW();
        for (int j = 0; j < this.bxK.length; ++j) {
            this.bxK[j].c(bx_22);
        }
    }

    protected void yn() {
        super.yn();
        azs_0.aLV().g("tutorialMode", false);
        add_1.aOG().f(new vP(0.0f, 0.0f, 0.0f, 0.5f));
    }

    protected void yo() {
        super.yo();
        add_1.aOG().l("dofusarena", avv_0.class);
        add_1.aOG().l("console", aaq_0.class);
        add_1.aOG().l("dofusarena.chat", jd_0.class);
    }
}

