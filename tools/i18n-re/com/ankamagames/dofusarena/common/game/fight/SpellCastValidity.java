/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.common.game.fight;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum SpellCastValidity {
    OK,
    OK_BUT_NO_EFFECT_ON_TARGET,
    INVALID_SPELL,
    INVALID_LINE_OF_SIGHT,
    INVALID_TARGET_CELL,
    INVALID_RANGE,
    NOT_ENOUGH_PA,
    TOO_MUCH_CASTS_ON_THIS_TARGET,
    TOO_MUCH_CASTS_THIS_TURN,
    LAST_CAST_TOO_RECENT,
    SPELL_UNKNOWN,
    CELL_NOT_FREE,
    CELLS_NOT_ALIGNED,
    CAST_CRITERIONS_NOT_VALID;


    public boolean isValid() {
        return this == OK || this == OK_BUT_NO_EFFECT_ON_TARGET;
    }
}

