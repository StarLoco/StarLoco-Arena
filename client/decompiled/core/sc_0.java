/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.chat.console.command.GuildContentCommand;

/*
 * Renamed from SC
 */
class sc_0
extends apc {
    sc_0() {
    }

    public boolean a(ke ke2) {
        azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.guild"));
        for (abl_2 abl_22 : ahv_0.aUv().aUy()) {
            abl_22.acY().a(GuildContentCommand.aMf());
        }
        return false;
    }
}

