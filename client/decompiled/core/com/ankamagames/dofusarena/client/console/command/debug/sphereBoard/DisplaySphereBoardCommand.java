/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.sphereBoard;

import java.util.ArrayList;

public class DisplaySphereBoardCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (arrayList.size() < 1) {
            return;
        }
        if (apN.aDK().c(afb_1.auN())) {
            apN.aDK().b(afb_1.auN());
            return;
        }
        int n2 = Gr.R(arrayList.get(2));
        Ei ei = (Ei)akp_1.aVO().aW(n2);
        if (ei == null) {
            apk_02.err("Il n'existe pas de board d'id " + n2);
            return;
        }
        afb_1.auN().setSphereBoard(ei);
        apN.aDK().a(afb_1.auN());
    }

    public boolean J() {
        return false;
    }
}

