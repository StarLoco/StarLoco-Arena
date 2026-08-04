/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;

public class ActionFlag
extends Tag {
    protected String m_action;

    public ActionFlag(String content) {
        this.m_code = (short)7;
        this.m_action = content;
    }

    public ActionFlag() {
    }

    public String getAction() {
        return this.m_action;
    }

    public void setAction(String action) {
        this.m_action = action;
    }

    public void setData(byte[] data, short version) throws IOException {
        InputBitStream inStream = new InputBitStream(data);
        this.m_action = inStream.readString();
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        outStream.writeString(this.m_action);
    }
}

