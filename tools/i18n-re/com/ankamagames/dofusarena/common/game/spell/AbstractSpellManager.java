/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.common.game.spell;

import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
import com.ankamagames.baseImpl.common.clientAndServer.game.spell.BasicSpell;
import com.ankamagames.dofusarena.common.game.fighter.Breed;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
import gnu.trove.TLongObjectHashMap;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractSpellManager<S extends AbstractSpell>
implements InventoryContentProvider<S> {
    protected static final Logger m_logger = Logger.getLogger(AbstractSpellManager.class);
    private final TLongObjectHashMap<S> m_spells = new TLongObjectHashMap();
    private final ArrayList<S> m_coachSpells = new ArrayList();
    private final ArrayList<S> m_godSpells = new ArrayList();

    protected AbstractSpellManager() {
    }

    public void addSpell(S spell) {
        this.m_spells.put(((BasicSpell)spell).getId(), spell);
    }

    public void addCoachSpell(S spell) {
        if (((AbstractSpell)spell).getBreedId() == Breed.COACH.getId()) {
            this.m_coachSpells.add(spell);
        }
    }

    public void addGodSpell(S spell) {
        if (((AbstractSpell)spell).getBreedId() == Breed.GOD.getId()) {
            this.m_godSpells.add(spell);
        }
    }

    public ArrayList<S> getCoachSpells() {
        return this.m_coachSpells;
    }

    public ArrayList<S> getGodSpells() {
        return this.m_godSpells;
    }

    public TLongObjectHashMap<S> getSpells() {
        return this.m_spells;
    }

    public S getSpell(long spellId) {
        return (S)((AbstractSpell)this.m_spells.get(spellId));
    }

    @Override
    public S unserializeContent(ByteBuffer buf) {
        return (S)((AbstractSpell)this.m_spells.get(buf.getInt()));
    }
}

