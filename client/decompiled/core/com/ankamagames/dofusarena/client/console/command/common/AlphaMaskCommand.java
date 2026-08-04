/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.common;

import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;
import java.util.Iterator;

public class AlphaMaskCommand
implements MC {
    private static boolean mJ = false;

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        mJ = !mJ;
        sj_1 sj_12 = DofusArenaClientInstance.yl().aoc().Ln();
        amg_1 amg_12 = sj_12.Ov();
        amg_12.eT(mJ);
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null) {
            Iterator iterator = adu_02.aKq();
            while (iterator.hasNext()) {
                vD vD2;
                Gx gx = (Gx)iterator.next();
                long l2 = gx.getId();
                ee_2 ee_22 = (ee_2)adu_02.eg(l2);
                if ((ee_22 == null || ee_22.b(avx_0.deu)) && !adu_02.p(ee_22) || amg_12 == (vD2 = ee_22.NW())) continue;
                vD2.eT(mJ);
            }
        }
    }

    public static void y(boolean bl2) {
        sj_1 sj_12 = DofusArenaClientInstance.yl().aoc().Ln();
        if (sj_12 == null) {
            return;
        }
        amg_1 amg_12 = sj_12.Ov();
        if (amg_12 == null) {
            return;
        }
        amg_12.eT(bl2);
    }

    public static void z(boolean bl2) {
        sj_1 sj_12 = DofusArenaClientInstance.yl().aoc().Ln();
        if (sj_12 == null) {
            return;
        }
        amg_1 amg_12 = sj_12.Ov();
        if (amg_12 == null) {
            return;
        }
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 == null) {
            return;
        }
        Iterator iterator = adu_02.aKq();
        while (iterator.hasNext()) {
            vD vD2;
            Gx gx = (Gx)iterator.next();
            long l2 = gx.getId();
            ee_2 ee_22 = (ee_2)adu_02.eg(l2);
            if ((ee_22 == null || ee_22.b(avx_0.deu)) && !adu_02.p(ee_22) || (vD2 = ee_22.NW()) == null || amg_12 == vD2) continue;
            vD2.eT(bl2);
        }
    }

    public boolean J() {
        return false;
    }
}

