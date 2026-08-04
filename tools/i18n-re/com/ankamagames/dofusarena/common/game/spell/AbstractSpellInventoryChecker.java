/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.common.game.spell;

import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentChecker;
import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
import com.ankamagames.dofusarena.common.game.fighter.Breed;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AbstractSpellInventoryChecker<C extends AbstractSpell>
implements InventoryContentChecker<C> {
    public static final int OK = 0;
    public static final int INVALID_BREED = 1;
    private AbstractFighter m_fighter;

    protected AbstractSpellInventoryChecker(AbstractFighter fighter) {
        this.m_fighter = fighter;
    }

    @Override
    public int canAddItem(Inventory inventory, C item) {
        Breed breed = this.m_fighter.getBreed();
        if (breed == null || ((AbstractSpell)item).getBreedId() != breed.getId()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int canAddItem(Inventory inventory, C item, short position) {
        Breed breed = this.m_fighter.getBreed();
        if (breed == null || ((AbstractSpell)item).getBreedId() != breed.getId()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int canReplaceItem(Inventory inventory, C oldItem, C newItem) {
        if (((AbstractSpell)oldItem).getBreedId() != ((AbstractSpell)newItem).getBreedId()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int canRemoveItem(Inventory inventory, C item) {
        return 0;
    }
}

