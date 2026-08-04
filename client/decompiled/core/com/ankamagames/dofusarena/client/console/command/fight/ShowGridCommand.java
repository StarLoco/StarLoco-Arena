/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import java.util.ArrayList;

public class ShowGridCommand
implements MC {
    private static boolean aqm = false;

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        aqm = !aqm;
        ShowGridCommand.aI(aqm);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clU, aqm);
    }

    public static void aI(boolean bl2) {
        aqm = bl2;
        if (apN.aDK().aDL() != null) {
            ArrayList arrayList = aga_0.aSG().aSK();
            int n2 = arrayList.size();
            air air2 = aqm ? air.cyd : air.cya;
            for (int j = 0; j < n2; ++j) {
                abb_0 abb_02 = (abb_0)arrayList.get(j);
                DisplayedScreenElement[] displayedScreenElementArray = abb_02.apJ();
                if (displayedScreenElementArray == null) continue;
                for (int i2 = 0; i2 < displayedScreenElementArray.length; ++i2) {
                    EntitySprite entitySprite = displayedScreenElementArray[i2].atW();
                    entitySprite.Hu().a(air2, air.cye);
                }
            }
        }
    }

    public boolean J() {
        return false;
    }
}

