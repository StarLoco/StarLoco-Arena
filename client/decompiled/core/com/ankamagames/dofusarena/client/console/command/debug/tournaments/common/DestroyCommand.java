/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class DestroyCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("destroy") && arrayList.size() == 3) {
            try {
                ajp_0.a(apk_02);
                bi_2 bi_22 = new bi_2();
                long l2 = Long.parseLong((String)arrayList.get(2));
                bi_22.ad(l2);
                apN.aDK().vJ().b(bi_22);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
    }

    public boolean J() {
        return false;
    }
}

