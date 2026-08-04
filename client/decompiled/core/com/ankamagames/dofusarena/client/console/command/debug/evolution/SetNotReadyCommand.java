/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.evolution;

import java.util.ArrayList;

public class SetNotReadyCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setNotReady") && arrayList.size() == 4) {
            try {
                wp_0.a(apk_02);
                abn_0 abn_02 = new abn_0();
                long l2 = Long.parseLong((String)arrayList.get(2));
                short s = Short.parseShort((String)arrayList.get(3));
                abn_02.aj(l2);
                abn_02.C(s);
                apN.aDK().vJ().b(abn_02);
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

