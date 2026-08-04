/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class ListCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("list") && arrayList.size() == 2) {
            ajp_0.a(apk_02);
            wa_2 wa_22 = new wa_2();
            apN.aDK().vJ().b(wa_22);
        }
    }

    public boolean J() {
        return false;
    }
}

