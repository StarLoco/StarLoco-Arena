/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
import java.io.IOException;

public class CommonDefineTag
extends DefinitionTag {
    CommonDefineTag() {
    }

    public void setData(byte[] data, short version) throws IOException {
        this.readDefinitionTagHeader(data);
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        super.writeData(outStream);
    }
}

