/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;

import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionMessage;
import com.ankamagames.dofusarena.common.game.fight.FightActionType;
import java.nio.ByteBuffer;

public class SpellCastMessage
extends FightActionMessage {
    private long m_casterId;
    private int m_spellId;
    private int m_castPositionX;
    private int m_castPositionY;
    private short m_castPositionZ;
    private boolean m_criticalHit;
    private boolean m_criticalMiss;

    public boolean decode(byte[] rawDatas) {
        if (!this.checkMessageSize(rawDatas.length, 21, false)) {
            return false;
        }
        ByteBuffer bb = ByteBuffer.wrap(rawDatas);
        this.decodeFightActionHeader(bb);
        this.m_casterId = bb.getLong();
        this.m_spellId = bb.getInt();
        boolean bl = this.m_criticalMiss = bb.get() == 1;
        if (!this.m_criticalMiss) {
            if (!this.checkMessageSize(rawDatas.length, 32, false)) {
                return false;
            }
            this.m_criticalHit = bb.get() == 1;
            this.m_castPositionX = bb.getInt();
            this.m_castPositionY = bb.getInt();
            this.m_castPositionZ = bb.getShort();
        } else {
            this.m_criticalHit = false;
        }
        return true;
    }

    public int getId() {
        return 8110;
    }

    public long getCasterId() {
        return this.m_casterId;
    }

    public int getSpellId() {
        return this.m_spellId;
    }

    public int getCastPositionX() {
        return this.m_castPositionX;
    }

    public int getCastPositionY() {
        return this.m_castPositionY;
    }

    public short getCastPositionZ() {
        return this.m_castPositionZ;
    }

    public boolean isCriticalHit() {
        return this.m_criticalHit;
    }

    public boolean isCriticalMiss() {
        return this.m_criticalMiss;
    }

    public int getActionId() {
        return this.m_spellId;
    }

    public FightActionType getFightActionType() {
        return FightActionType.SPELL_CAST;
    }
}

