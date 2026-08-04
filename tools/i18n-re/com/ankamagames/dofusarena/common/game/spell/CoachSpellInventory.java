/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.common.game.spell;

import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpellManager;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CoachSpellInventory<S extends AbstractSpell>
implements Iterable<S> {
    protected static final Logger m_logger = Logger.getLogger(CoachSpellInventory.class);
    protected StackInventory<S> m_spellInventory;

    public CoachSpellInventory(AbstractSpellManager<S> spellManager, short size) {
        this.m_spellInventory = new StackInventory<S>(size, spellManager, null, true, false, false);
    }

    public void unserialize(byte[] serializedCoachSpellInventory) {
        this.m_spellInventory.unserialize(serializedCoachSpellInventory);
    }

    public byte[] serialize() {
        return this.m_spellInventory.serialize();
    }

    public boolean addCoachSpell(S spell) {
        try {
            this.m_spellInventory.add(spell);
            return true;
        }
        catch (InventoryCapacityReachedException e) {
            m_logger.error((Object)"impossible d'ajouter ce sort : inventaire plein");
        }
        catch (ContentAlreadyPresentException e) {
            m_logger.error((Object)"impossible d'ajouter ce sort : on l'a d\u00e9j\u00e0");
        }
        return false;
    }

    public void removeCoachSpell(long spellId) {
        this.m_spellInventory.removeWithUniqueId(spellId);
    }

    public StackInventory<S> getSpellInventory() {
        return this.m_spellInventory;
    }

    @Override
    public Iterator<S> iterator() {
        return this.m_spellInventory.iterator();
    }

    public int size() {
        return this.m_spellInventory.size();
    }
}

