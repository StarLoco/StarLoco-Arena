/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.chat.console.command.TeammateContentCommand;

/*
 * Renamed from SA
 */
class sa_0
extends apc {
    sa_0() {
    }

    public boolean a(ke ke2) {
        azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.teammate"));
        for (abl_2 abl_22 : ahv_0.aUv().aUy()) {
            abl_22.acY().a(TeammateContentCommand.MH());
        }
        return false;
    }
}

