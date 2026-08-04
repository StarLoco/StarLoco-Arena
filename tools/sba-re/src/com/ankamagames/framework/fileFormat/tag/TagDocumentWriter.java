/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag;

import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.TagDocument;
import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
import java.io.IOException;
import java.io.OutputStream;

public class TagDocumentWriter {
    private OutputBitStream m_bitStream;
    private TagDocument m_document;

    public TagDocumentWriter(TagDocument document, OutputStream stream) {
        this.m_bitStream = new OutputBitStream(stream);
        this.m_document = document;
    }

    public void write() throws IOException {
        try {
            byte[] tagsBuffer = TagWriter.writeTags(this.m_document.getTags());
            long fileLength = 8 + tagsBuffer.length;
            this.writeHeader(fileLength);
            if (this.m_document.isCompressed()) {
                this.m_bitStream.enableCompression();
            }
            this.m_bitStream.writeBytes(tagsBuffer);
        }
        finally {
            try {
                this.m_bitStream.close();
            }
            catch (Exception exception) {}
        }
    }

    private void writeHeader(long fileLength) throws IOException {
        TagDocumentHeader header = this.m_document.getHeader();
        header.setFileLength(fileLength);
        header.write(this.m_bitStream);
    }
}

