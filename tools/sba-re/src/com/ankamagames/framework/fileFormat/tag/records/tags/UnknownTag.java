/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records.tags;

import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;

public final class UnknownTag
extends Tag {
    private byte[] inData;

    public UnknownTag() {
    }

    public UnknownTag(short code, byte[] data) {
        this.m_code = code;
        this.inData = data;
    }

    public byte[] getData() {
        return this.inData;
    }

    public void setData(byte[] data, short version) throws IOException {
        this.inData = data;
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        outStream.writeBytes(this.inData);
    }
}

