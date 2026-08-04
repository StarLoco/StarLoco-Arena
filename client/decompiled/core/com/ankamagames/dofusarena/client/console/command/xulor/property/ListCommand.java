/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.xulor.property;

import java.util.ArrayList;

public class ListCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        StringBuilder stringBuilder = new StringBuilder("# Liste des propri\u00e9t\u00e9s #");
        Iterable iterable = azs_0.aLV().oa();
        for (afl_0 afl_02 : iterable) {
            stringBuilder.append('\n').append(" - ").append(afl_02.getName());
            if (!(afl_02.getValue() instanceof aho_0)) continue;
            stringBuilder.append(" (F)");
        }
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }
}

