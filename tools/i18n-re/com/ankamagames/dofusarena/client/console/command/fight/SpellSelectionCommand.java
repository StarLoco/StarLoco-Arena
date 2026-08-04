/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
import com.ankamagames.dofusarena.client.core.game.fight.Fight;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
import com.ankamagames.framework.kernel.core.common.message.Worker;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SpellSelectionCommand
implements Command {
    protected static final Logger m_logger = Logger.getLogger(SpellSelectionCommand.class);

    @Override
    public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
        int selected;
        if (args.size() < 3 || args.get(2) == null) {
            return;
        }
        try {
            selected = Integer.valueOf(args.get(2));
        }
        catch (NumberFormatException e) {
            return;
        }
        Fight fight = DofusArenaGameEntity.getInstance().getFight();
        if (fight != null && fight.getTimeline() != null) {
            Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
            if (fighter != null) {
                StackInventory<Spell> spellInventory = fighter.getSpellInventory();
                Spell selectedSpell = null;
                int i = 0;
                for (Spell spell : spellInventory) {
                    if (i == selected) {
                        selectedSpell = spell;
                        break;
                    }
                    ++i;
                }
                if (selectedSpell != null) {
                    UIFighterSpellMessage message = new UIFighterSpellMessage();
                    message.setFighter(fighter);
                    message.setSpell(selectedSpell);
                    message.setId(18006);
                    Worker.getInstance().pushMessage(message);
                }
            } else {
                m_logger.error((Object)"Pas de fighter");
            }
        } else if (fight == null) {
            m_logger.error((Object)"Pas de fight");
        } else {
            m_logger.error((Object)"Pas de timeLine");
        }
    }

    @Override
    public boolean isPassThrough() {
        return false;
    }
}

