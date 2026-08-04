/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.hardcore;

import java.util.ArrayList;

public class SetNotReadyCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setNotReady") && arrayList.size() == 4) {
            try {
                vu_1.a(apk_02);
                bm_1 bm_12 = new bm_1();
                long l2 = Long.parseLong((String)arrayList.get(2));
                short s = Short.parseShort((String)arrayList.get(3));
                if (!add_1.aOG().kR("classicSearchStatusDialog")) {
                    mh_1.aj(l2);
                    mh_1.C(s);
                }
                bm_12.aj(l2);
                bm_12.C(s);
                apN.aDK().vJ().b(bm_12);
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

