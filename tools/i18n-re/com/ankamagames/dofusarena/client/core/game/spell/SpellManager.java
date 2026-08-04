/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.core.game.spell;

import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import com.ankamagames.dofusarena.client.core.game.spell.UsableSpell;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpellManager;
import gnu.trove.TLongObjectIterator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SpellManager
extends AbstractSpellManager<Spell> {
    private static final SpellManager m_instance = new SpellManager();

    public static SpellManager getInstance() {
        return m_instance;
    }

    public ArrayList<Spell> getSpellsFromBreedId(int breedId) {
        ArrayList<Spell> spells = new ArrayList<Spell>();
        TLongObjectIterator it = this.getSpells().iterator();
        while (it.hasNext()) {
            it.advance();
            if (((Spell)it.value()).getBreedId() != breedId) continue;
            spells.add((Spell)it.value());
        }
        Collections.sort(spells);
        return spells;
    }

    @Override
    public Spell unserializeContent(ByteBuffer buf) {
        Spell refSpell = (Spell)super.unserializeContent(buf);
        UsableSpell spell = null;
        if (refSpell != null) {
            spell = new UsableSpell(refSpell);
        }
        return spell;
    }
}

