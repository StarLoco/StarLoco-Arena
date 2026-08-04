/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug;

import java.util.ArrayList;

public class EntityStatusCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        StringBuilder stringBuilder = new StringBuilder("Status de DofusArenaGameEntity :");
        try {
            stringBuilder.append('\n').append("Connect\u00e9 : ").append(apN.aDK().vJ().isConnected());
        }
        catch (Exception exception) {
            // empty catch block
        }
        stringBuilder.append('\n').append("Frames : ");
        for (atG atG2 : apN.aDK().ii()) {
            stringBuilder.append('\n').append(" - ").append(atG2.getClass().getSimpleName());
        }
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }
}

