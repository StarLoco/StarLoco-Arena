/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight;

import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
import java.nio.ByteBuffer;

public class SpellCastRequestMessage
extends OutputOnlyProxyMessage {
    private long m_fighterId;
    private int m_spellId;
    private int m_castPositionX;
    private int m_castPositionY;
    private short m_castPositionZ;

    public byte[] encode() {
        ByteBuffer bb = ByteBuffer.allocate(22);
        bb.putLong(this.m_fighterId);
        bb.putInt(this.m_spellId);
        bb.putInt(this.m_castPositionX);
        bb.putInt(this.m_castPositionY);
        bb.putShort(this.m_castPositionZ);
        return this.addClientHeader((byte)3, bb.array());
    }

    public int getId() {
        return 8109;
    }

    public void setFighterId(long fighterId) {
        this.m_fighterId = fighterId;
    }

    public void setSpellId(int spellId) {
        this.m_spellId = spellId;
    }

    public void setCastPosition(int x, int y, short z) {
        this.m_castPositionX = x;
        this.m_castPositionY = y;
        this.m_castPositionZ = z;
    }
}

