/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records.tags;

import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;

public final class EndTag
extends Tag {
    private static final EndTag m_instance = new EndTag();

    public static EndTag getInstance() {
        return m_instance;
    }

    private EndTag() {
    }

    public void setData(byte[] data, short version) throws IOException {
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
    }
}

