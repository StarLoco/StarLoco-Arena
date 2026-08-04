/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug;

import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;

public class LocateCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Du du = DofusArenaClientInstance.yl().YP().aNC();
        StringBuilder stringBuilder = new StringBuilder(du.toString());
        stringBuilder.append(" (").append(du.gn()).append(", ").append(du.go()).append(")");
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }
}

