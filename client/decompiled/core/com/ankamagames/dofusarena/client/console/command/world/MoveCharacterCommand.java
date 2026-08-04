/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.world;

import java.util.ArrayList;

public class MoveCharacterCommand
implements MC {
    private static long ciz = 0L;

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (arrayList.size() < 3 || arrayList.get(2) == null) {
            return;
        }
        if (System.nanoTime() - ciz < 300000000L) {
            return;
        }
        qc_0 qc_02 = qc_0.hf(Integer.valueOf((String)arrayList.get(2)));
        sj_1 sj_12 = apN.aDK().Ln();
        ry ry2 = new ry(sj_12.aNU(), sj_12.aNV(), (short)sj_12.getAltitude());
        int n2 = ry2.getX() + qc_02.acJ()[0];
        int n3 = ry2.getY() + qc_02.acJ()[1];
        dc_0 dc_02 = auU.bW(ry2.getX(), ry2.getY());
        dc_0 dc_03 = auU.bW(n2, n3);
        if (dc_02 == null || dc_03 == null) {
            return;
        }
        pc_0 pc_02 = new pc_0(sj_12.ge(), sj_12.ox(), sj_12.BP());
        akd_0[] akd_0Array = jc_0.blY;
        int n4 = dc_02.Ls().a(ry2.getX(), ry2.getY(), akd_0Array, 0);
        short s = mo_1.a(0, n4, akd_0Array, ry2.wk());
        if (s == Short.MIN_VALUE) {
            return;
        }
        akd_0[] akd_0Array2 = jc_0.blZ;
        int n5 = dc_03.Ls().a(n2, n3, akd_0Array2, 0);
        int n6 = pc_02.a(s, 0, n4, akd_0Array, 0, n5, akd_0Array2);
        if (n6 == Short.MIN_VALUE) {
            return;
        }
        arh_0 arh_02 = new arh_0(1);
        arh_02.b(0, n2, n3, akd_0Array2[n6].wp);
        aLY aLY2 = new aLY();
        aLY2.a(arh_02);
        apN.aDK().vJ().b(aLY2);
        ciz = System.nanoTime();
    }

    public boolean J() {
        return false;
    }
}

