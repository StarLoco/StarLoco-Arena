/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;

public class RemoveObject
extends Tag {
    private int m_depth;

    public RemoveObject(int depth) {
        this.m_code = (short)6;
        this.m_depth = depth;
    }

    public RemoveObject() {
    }

    public int getDepth() {
        return this.m_depth;
    }

    public void setDepth(int depth) {
        this.m_depth = depth;
    }

    public void setData(byte[] data, short version) throws IOException {
        InputBitStream inStream = new InputBitStream(data);
        this.m_depth = inStream.readUI16();
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        outStream.writeUI16(this.m_depth);
    }
}

