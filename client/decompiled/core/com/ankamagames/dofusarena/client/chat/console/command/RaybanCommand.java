/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class RaybanCommand
implements MC {
    private static boolean ai = false;

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (!ai && (apN.aDK().c(ajo_1.azb()) || apN.aDK().c(aks_2.aAh()) || apN.aDK().c(en_2.Na()) && en_2.Nb() == aql_0.cOG)) {
            azs_0.aLV().g("tipsRayban", true);
            nq nq2 = new nq();
            nq2.K(or_0.Vy.tI());
            nq2.ab(true);
            nq2.L((short)1);
            apN.aDK().vJ().b(nq2);
        }
        ai = true;
    }

    public static void uninitialize() {
        ai = false;
        azs_0.aLV().g("tipsRayban", false);
    }

    public boolean J() {
        return false;
    }
}

