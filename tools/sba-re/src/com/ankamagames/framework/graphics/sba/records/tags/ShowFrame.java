/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;

public class ShowFrame
extends Tag {
    private int m_duration;

    public ShowFrame(int duration) {
        this.m_code = 1;
        this.m_duration = duration;
    }

    public ShowFrame() {
    }

    public int getDuration() {
        return this.m_duration;
    }

    public void setDuration(int duration) {
        this.m_duration = duration;
    }

    public void setData(byte[] data, short version) throws IOException {
        InputBitStream inStream = new InputBitStream(data);
        this.m_duration = inStream.readUI16() & 0xFFFF;
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        outStream.writeUI16(this.m_duration);
    }
}

