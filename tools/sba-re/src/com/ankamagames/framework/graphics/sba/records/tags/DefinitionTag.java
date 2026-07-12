/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;

public abstract class DefinitionTag
extends Tag {
    protected int m_identifier;
    protected String m_linkage;

    public void setIdentifier(int identifier) {
        this.m_identifier = identifier;
    }

    public int getIdentifier() {
        return this.m_identifier;
    }

    public boolean isLinked() {
        return this.m_linkage != null;
    }

    public String getLinkage() {
        return this.m_linkage;
    }

    public void setLinkage(String linkage) {
        this.m_linkage = linkage;
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        outStream.writeUI16(this.m_identifier);
        if (this.m_linkage != null) {
            outStream.writeBooleanBit(true);
            outStream.writeString(this.m_linkage);
        } else {
            outStream.writeBooleanBit(false);
        }
        outStream.align();
    }

    protected InputBitStream readDefinitionTagHeader(byte[] data) throws IOException {
        InputBitStream inStream = new InputBitStream(data);
        this.m_identifier = inStream.readUI16();
        boolean hasLinkage = inStream.readBooleanBit();
        if (hasLinkage) {
            this.m_linkage = inStream.readString();
            inStream.align();
        } else {
            this.m_linkage = null;
        }
        return inStream;
    }
}

