/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.world;

import java.util.ArrayList;

public class CalendarCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (apN.aDK().c(zb_1.GG())) {
            apN.aDK().b(zb_1.GG());
        } else {
            apN.aDK().a(alv_0.aWM());
            if (de_2.Mc().Me().size() > 0) {
                apN.aDK().a(zb_1.GG());
            } else {
                yq_1 yq_12 = new yq_1();
                apN.aDK().vJ().b(yq_12);
                apN.aDK().vJ().b(new wa_2());
            }
        }
    }

    public boolean J() {
        return false;
    }
}

