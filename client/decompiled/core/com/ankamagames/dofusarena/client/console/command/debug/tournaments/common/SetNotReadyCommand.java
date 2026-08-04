/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class SetNotReadyCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setNotReady") && arrayList.size() == 5) {
            try {
                ds_2.a(apk_02);
                bt_0 bt_02 = new bt_0();
                long l2 = Long.parseLong((String)arrayList.get(2));
                long l3 = Long.parseLong((String)arrayList.get(3));
                short s = Short.parseShort((String)arrayList.get(4));
                if (!add_1.aOG().kR("tournamentsSearchStatusDialog")) {
                    vk_1.ad(l2);
                    vk_1.aj(l3);
                    vk_1.C(s);
                }
                bt_02.ad(l2);
                bt_02.aj(l3);
                bt_02.C(s);
                apN.aDK().vJ().b(bt_02);
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

