/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.common;

import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;

public class ZoomCommand
implements MC {
    public static final float bgc = 0.1f;

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (arrayList == null || arrayList.size() < 3 || arrayList.get(2) == null) {
            return;
        }
        qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
        if (qs_22 == null) {
            return;
        }
        double d = qs_22.Ft();
        if (((String)arrayList.get(2)).equals("+")) {
            d += (double)0.1f;
        } else if (((String)arrayList.get(2)).equals("-")) {
            d -= (double)0.1f;
        } else {
            return;
        }
        qs_22.k(d);
    }

    public boolean J() {
        return false;
    }
}

