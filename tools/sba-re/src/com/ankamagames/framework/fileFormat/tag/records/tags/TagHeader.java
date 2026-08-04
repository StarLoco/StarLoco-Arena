/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import java.io.IOException;

public final class TagHeader {
    private short m_code;
    private int m_length;

    TagHeader() {
    }

    TagHeader(InputBitStream stream) throws IOException {
        this.read(stream);
    }

    public short getCode() {
        return this.m_code;
    }

    public int getLength() {
        return this.m_length;
    }

    private void read(InputBitStream stream) throws IOException {
        int codeAndLength = stream.readUI16();
        this.m_code = (short)(codeAndLength >> 6);
        this.m_length = codeAndLength & 0x3F;
        if (this.m_length == 63) {
            this.m_length = (int)stream.readUI32();
        }
    }
}

