/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class SetReadyCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setReady") && arrayList.size() == 5) {
            try {
                ds_2.a(apk_02);
                ly_1 ly_12 = new ly_1();
                long l2 = Long.parseLong((String)arrayList.get(2));
                long l3 = Long.parseLong((String)arrayList.get(3));
                short s = Short.parseShort((String)arrayList.get(4));
                if (!add_1.aOG().kR("tournamentsSearchStatusDialog")) {
                    vk_1.ad(l2);
                    vk_1.aj(l3);
                    vk_1.C(s);
                }
                ly_12.ad(l2);
                ly_12.aj(l3);
                ly_12.C(s);
                apN.aDK().vJ().b(ly_12);
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

