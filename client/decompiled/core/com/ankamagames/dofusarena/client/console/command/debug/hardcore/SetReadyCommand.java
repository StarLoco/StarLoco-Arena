/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.hardcore;

import java.util.ArrayList;

public class SetReadyCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setReady") && arrayList.size() == 4) {
            try {
                vu_1.a(apk_02);
                atj_0 atj_02 = new atj_0();
                long l2 = Long.parseLong((String)arrayList.get(2));
                short s = Short.parseShort((String)arrayList.get(3));
                if (!add_1.aOG().kR("classicSearchStatusDialog")) {
                    mh_1.aj(l2);
                    mh_1.C(s);
                }
                atj_02.aj(l2);
                atj_02.C(s);
                apN.aDK().vJ().b(atj_02);
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

