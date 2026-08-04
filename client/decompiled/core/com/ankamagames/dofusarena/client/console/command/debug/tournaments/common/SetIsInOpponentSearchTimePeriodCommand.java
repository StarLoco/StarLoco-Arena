/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class SetIsInOpponentSearchTimePeriodCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setIsInOpponentSearchTimePeriod") && arrayList.size() == 6) {
            try {
                ajp_0.a(apk_02);
                aeC aeC2 = new aeC();
                long l2 = Long.parseLong((String)arrayList.get(2));
                byte by = Byte.parseByte((String)arrayList.get(3));
                byte by2 = Byte.parseByte((String)arrayList.get(4));
                long l3 = Long.parseLong((String)arrayList.get(5));
                aeC2.ad(l2);
                aeC2.ax(by);
                aeC2.ay(by2);
                aeC2.dB(l3);
                apN.aDK().vJ().b(aeC2);
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

