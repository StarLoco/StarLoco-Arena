/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
import java.io.IOException;

public abstract class DefineSequence
extends DefinitionTag {
    private short m_loopCount;

    public short getLoopCount() {
        return this.m_loopCount;
    }

    public void setLoopCount(short loopCount) {
        this.m_loopCount = loopCount;
    }

    public abstract int getFrameCount();

    protected InputBitStream readDefinitionSequenceTagHeader(byte[] data) throws IOException {
        InputBitStream inStream = this.readDefinitionTagHeader(data);
        this.m_loopCount = inStream.readUI8();
        return inStream;
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        super.writeData(outStream);
        outStream.writeUI8(this.m_loopCount);
    }
}

