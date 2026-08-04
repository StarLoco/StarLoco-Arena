/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class AddCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("add") && arrayList.size() == 6) {
            try {
                ajp_0.a(apk_02);
                ago_0 ago_02 = new ago_0();
                long l2 = Long.parseLong((String)arrayList.get(2));
                ago_02.ad(l2);
                ago_02.aj(Long.parseLong((String)arrayList.get(3)));
                ago_02.C(Short.parseShort((String)arrayList.get(4)));
                ago_02.kV(Integer.parseInt((String)arrayList.get(5)));
                apN.aDK().vJ().b(ago_02);
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

