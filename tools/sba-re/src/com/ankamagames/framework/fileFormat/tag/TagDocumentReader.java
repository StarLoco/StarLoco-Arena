/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.tag.TagDocument;
import com.ankamagames.framework.fileFormat.tag.TagDocumentFactory;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagHeader;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
import java.io.InputStream;

public class TagDocumentReader {
    private InputBitStream m_bitStream;
    private TagDecoder m_decoder;
    private TagDocument m_document;

    public TagDocumentReader(InputStream stream, TagDecoder decoder, TagDocumentFactory documentFactory) {
        this.create(stream, decoder, documentFactory);
    }

    public void create(InputStream stream, TagDecoder decoder, TagDocumentFactory documentFactory) {
        this.m_bitStream = new InputBitStream(stream);
        this.m_decoder = decoder;
        this.m_document = documentFactory.createDocument();
    }

    public void read() throws Exception {
        this.m_document.getHeader().read(this.m_bitStream);
        short version = this.m_document.getVersion();
        if (!this.m_document.isReadable(version)) {
            throw new Exception("La version lu est inconnue par le document : " + version);
        }
        if (this.m_document.isCompressed()) {
            this.m_bitStream.enableCompression();
        }
        while (true) {
            TagHeader tagHeader = null;
            tagHeader = TagReader.readTagHeader(this.m_bitStream);
            if (tagHeader.getLength() < 0) {
                throw new Exception("Longueur de Tag invalide : " + tagHeader.getLength());
            }
            Tag tag = null;
            byte[] tagData = null;
            try {
                tagData = TagReader.readTagData(this.m_bitStream, tagHeader);
                tag = TagReader.readTag(this.m_decoder, tagHeader, tagData, version);
                if (tag.getCode() == 0) {
                }
            }
            catch (Exception e) {}
            break;
            this.m_document.addTag(tag);
        }
        this.m_bitStream.close();
    }

    public TagDocument getDocument() {
        return this.m_document;
    }
}

