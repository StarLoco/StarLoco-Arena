/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.sphereBoard;

import java.util.ArrayList;

public class ListSpheresCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (arrayList.size() < 1) {
            return;
        }
        int n2 = Gr.R(arrayList.get(2));
        Ei ei = (Ei)akp_1.aVO().aW(n2);
        if (ei == null) {
            apk_02.err("Il n'existe pas de board d'id " + n2);
            return;
        }
        ei.MQ();
        ArrayList arrayList2 = ei.Xn();
        apk_02.trace("Nombre de Sph\u00e8res : " + arrayList2.size());
        int n3 = arrayList2.size();
        for (int j = 0; j < n3; ++j) {
            ayr_0 ayr_02 = (ayr_0)arrayList2.get(j);
            apk_02.trace("id = " + ayr_02.getId() + " --- x = " + ayr_02.aut() + ", y = " + ayr_02.auu() + ", iconId = " + ayr_02.aLl() + ", selectedIconId = " + ayr_02.aLm() + ", pathId = " + ayr_02.aLn() + ", rotation = " + ayr_02.aLo());
        }
    }

    public boolean J() {
        return false;
    }
}

