/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class HelpCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        sj_1 sj_12 = apN.aDK().Ln();
        String string = aon_0.aYc().getString("chat.help");
        zc_0 zc_02 = new zc_0(sj_12.Ld(), sj_12.getId(), string);
        zc_02.eD(5);
        ql_1.acX().a(zc_02);
    }

    public boolean J() {
        return false;
    }
}

