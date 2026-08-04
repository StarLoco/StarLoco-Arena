/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.chat.console.command.TradeContentCommand;

/*
 * Renamed from SD
 */
class sd_0
extends apc {
    sd_0() {
    }

    public boolean a(ke ke2) {
        azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.trade"));
        for (abl_2 abl_22 : ahv_0.aUv().aUy()) {
            abl_22.acY().a(TradeContentCommand.ayQ());
        }
        return false;
    }
}

