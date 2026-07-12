/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.framework.graphics.sba;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagHeader;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
import com.ankamagames.framework.graphics.sba.SBADocument;
import com.ankamagames.framework.graphics.sba.records.SBAHeader;
import com.ankamagames.framework.graphics.sba.records.tags.CommonDefineTag;
import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
import com.ankamagames.framework.graphics.sba.records.tags.SBADefinitionTagDecoder;
import com.ankamagames.framework.graphics.sba.records.tags.SBATagDecoder;
import gnu.trove.TIntArrayList;
import gnu.trove.TIntLongHashMap;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IndexedDefinitionTagBuffer {
    private static Logger m_logger = Logger.getLogger(IndexedDefinitionTagBuffer.class);
    private TIntLongHashMap m_definitionTagIndex;
    private final HashMap<String, Integer> m_linkageDictionary = new HashMap();
    private TIntArrayList m_identifiers;
    private InputBitStream m_bitStream;
    private static final TagDecoder m_lightDecoder = new SBADefinitionTagDecoder();
    private static final TagDecoder m_decoder = new SBATagDecoder();
    private short m_version = (short)3;

    public IndexedDefinitionTagBuffer(byte[] buffer) throws Exception {
        this.checkHeader(buffer);
        this.generateIndex();
    }

    public TIntArrayList getIdentifiers() {
        return this.m_identifiers;
    }

    public Set<String> getLinkages() {
        return this.m_linkageDictionary.keySet();
    }

    public boolean contains(int identifier) {
        return this.m_definitionTagIndex.containsKey(identifier);
    }

    public boolean contains(String linkage) {
        return linkage != null && this.m_linkageDictionary.containsKey(linkage);
    }

    public int getIdFromLinkage(String linkage) {
        Integer value;
        if (linkage != null && (value = this.m_linkageDictionary.get(linkage)) != null) {
            return value;
        }
        return 0;
    }

    public DefinitionTag getDefinitionTag(int identifier) {
        if (this.m_definitionTagIndex.containsKey(identifier)) {
            long offset = this.m_definitionTagIndex.get(identifier);
            return this.readTag(offset);
        }
        return null;
    }

    public DefinitionTag getDefinitionTag(String linkage) {
        if (this.m_linkageDictionary.containsKey(linkage)) {
            return this.getDefinitionTag(this.m_linkageDictionary.get(linkage));
        }
        return null;
    }

    private void checkHeader(byte[] buffer) throws Exception {
        InputBitStream bitStream = new InputBitStream(buffer);
        SBAHeader header = new SBAHeader();
        header.read(bitStream);
        this.m_version = header.getVersion();
        if (this.m_version != 3) {
            m_logger.warn((Object)("Attention!! IndexedDefinitionTagBuffer version obsol\u00e8te:" + this.m_version + "  courante:" + 3));
            if (!new SBADocument().isReadable(this.m_version)) {
                throw new Exception("La version lu est inconnue : " + this.m_version);
            }
        }
        if (header.isCompressed()) {
            bitStream.enableCompression();
        }
        byte[] tagsBuffer = bitStream.readBytes((int)header.getFileLength() - 8);
        this.m_bitStream = new InputBitStream(tagsBuffer);
        bitStream.close();
    }

    private void generateIndex() throws Exception {
        this.m_definitionTagIndex = new TIntLongHashMap();
        this.m_identifiers = new TIntArrayList();
        while (true) {
            long offset = this.m_bitStream.getOffset();
            Tag tag = this.readTag(m_lightDecoder);
            if (tag.getCode() == 0) break;
            if (!(tag instanceof CommonDefineTag)) continue;
            CommonDefineTag commonDefineTag = (CommonDefineTag)tag;
            int identifier = commonDefineTag.getIdentifier();
            this.m_definitionTagIndex.put(identifier, offset);
            if (commonDefineTag.isLinked()) {
                this.m_linkageDictionary.put(commonDefineTag.getLinkage(), identifier);
            }
            this.m_identifiers.add(identifier);
        }
    }

    private Tag readTag(TagDecoder decoder) throws Exception {
        TagHeader tagHeader = TagReader.readTagHeader(this.m_bitStream);
        if (tagHeader.getLength() < 0) {
            throw new Exception("Longueur de Tag invalide : " + tagHeader.getLength());
        }
        byte[] tagData = TagReader.readTagData(this.m_bitStream, tagHeader);
        Tag tag = TagReader.readTag(decoder, tagHeader, tagData, this.m_version);
        return tag;
    }

    private DefinitionTag readTag(long offset) {
        try {
            this.m_bitStream.setOffset(offset);
            return (DefinitionTag)this.readTag(m_decoder);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

