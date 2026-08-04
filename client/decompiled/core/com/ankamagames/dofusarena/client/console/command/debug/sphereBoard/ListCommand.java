/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.sphereBoard;

import java.util.ArrayList;

public class ListCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        lb_0 lb_02 = akp_1.aVO().lB();
        if (lb_02.isEmpty()) {
            apk_02.trace("Aucun SphereBoard enregistr\u00e9");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder().append(lb_02.size()).append(" sphereBoard(s) enregistr\u00e9e(s) :\n");
        boolean bl2 = true;
        ll_0 ll_02 = lb_02.pK();
        while (ll_02.hasNext()) {
            if (bl2) {
                bl2 = false;
            } else {
                stringBuilder.append(", ");
            }
            ll_02.fK();
            stringBuilder.append(ll_02.kR());
        }
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }
}

