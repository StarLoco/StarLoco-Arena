/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class SetIsInRegistrationTimePeriodCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("setIsInRegistrationTimePeriod") && arrayList.size() == 4) {
            try {
                ajp_0.a(apk_02);
                kx_2 kx_22 = new kx_2();
                long l2 = Long.parseLong((String)arrayList.get(2));
                byte by = Byte.parseByte((String)arrayList.get(3));
                kx_22.ad(l2);
                kx_22.p(by);
                apN.aDK().vJ().b(kx_22);
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

