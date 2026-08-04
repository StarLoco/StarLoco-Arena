/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.world;

import java.util.ArrayList;

public class MuteCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        sj_1 sj_12;
        boolean bl2 = (sj_12 = apN.aDK().Ln()).yP();
        sj_12.aw(!bl2);
        if (!bl2) {
            add_1.aOG().a(aon_0.aYc().getString("isIgnoringInvitation"), 1090L, 102, 1);
        } else {
            add_1.aOG().a(aon_0.aYc().getString("isNotIgnoringInvitation"), 1090L, 102, 1);
        }
    }

    public boolean J() {
        return false;
    }
}

