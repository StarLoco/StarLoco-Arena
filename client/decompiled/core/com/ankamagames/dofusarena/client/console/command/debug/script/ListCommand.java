/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.script;

import java.util.ArrayList;

public class ListCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        StringBuilder stringBuilder = new StringBuilder("Liste des scripts :");
        Iterable iterable = Ky.WG().WH();
        for (JX jX : iterable) {
            stringBuilder.append('\n').append("- ").append(jX.getId()).append(" (").append("").append(')');
        }
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }
}

