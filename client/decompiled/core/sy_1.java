/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.chat.console.command.VicinityContentCommand;

/*
 * Renamed from Sy
 */
class sy_1
extends apc {
    sy_1() {
    }

    public boolean a(ke ke2) {
        azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.vicinity"));
        for (abl_2 abl_22 : ahv_0.aUv().aUy()) {
            abl_22.acY().a(new VicinityContentCommand());
        }
        return false;
    }
}

