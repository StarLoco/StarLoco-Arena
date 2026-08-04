/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class EndTurnCommand
implements MC {
    private static Logger a = Logger.getLogger(EndTurnCommand.class);

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null && adu_02.ass() != null) {
            ko_2 ko_22 = adu_02.Zy();
            if (ko_22 == ko_2.bpv) {
                ee_2 ee_22 = (ee_2)adu_02.ass().nP();
                ayd_0 ayd_02 = new ayd_0();
                ayd_02.b(ee_22);
                ayd_02.f(18001);
                acu_1.ara().c(ayd_02);
            } else if (ko_22 == ko_2.bpu) {
                sb_0 sb_02 = new sb_0();
                sb_02.f(18011);
                acu_1.ara().c(sb_02);
            } else if (ko_22 == ko_2.bpt) {
                sb_0 sb_03 = new sb_0();
                sb_03.f(18010);
                acu_1.ara().c(sb_03);
            } else if (ko_22 == ko_2.bps) {
                sb_0 sb_04 = new sb_0();
                sb_04.f(18009);
                acu_1.ara().c(sb_04);
            }
        } else {
            a.trace((Object)((adu_02 == null ? "fight" : "timeline") + " null"));
        }
    }

    public boolean J() {
        return false;
    }
}

