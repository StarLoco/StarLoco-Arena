/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import com.ankamagames.dofusarena.client.chat.console.command.GuildContentCommand;
import com.ankamagames.dofusarena.client.chat.console.command.TeammateContentCommand;
import com.ankamagames.dofusarena.client.chat.console.command.TradeContentCommand;
import com.ankamagames.dofusarena.client.chat.console.command.VicinityContentCommand;
import java.util.ArrayList;

public class SelectPipeCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        MC mC = null;
        if (string.equals("/s")) {
            azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.vicinity"));
            mC = new VicinityContentCommand();
        } else if (string.equals("/c")) {
            azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.guild"));
            mC = GuildContentCommand.aMf();
        } else if (string.equals("/t")) {
            azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.trade"));
            mC = TradeContentCommand.ayQ();
        } else if (string.equals("/p")) {
            azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.teammate"));
            mC = TeammateContentCommand.MH();
        }
        for (Object object : ahv_0.aUv().aUy()) {
            ((abl_2)object).acY().a(mC);
        }
        String string2 = (String)arrayList.get(2);
        if (string2 != null && !string2.equals("") && mC != null) {
            Object object;
            object = new ArrayList();
            ((ArrayList)object).add(string2);
            mC.a(apk_02, adb_22, (ArrayList)object);
        }
    }

    public boolean J() {
        return false;
    }
}

