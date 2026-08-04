/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagHeader;
import com.ankamagames.framework.fileFormat.tag.records.tags.UnknownTag;
import java.io.IOException;

public final class TagReader {
    public static Tag readTag(TagDecoder decoder, TagHeader header, byte[] tagData, short version) throws IOException {
        Tag tag = decoder.creatTagInstanceFromCode(header.getCode());
        if (tag == null) {
            tag = new UnknownTag();
        }
        tag.setCode(header.getCode());
        tag.setData(tagData, version);
        tag.setLength(tagData.length);
        return tag;
    }

    public static byte[] readTagData(InputBitStream stream, TagHeader header) throws IOException {
        return stream.readBytes(header.getLength());
    }

    public static TagHeader readTagHeader(InputBitStream stream) throws IOException {
        return new TagHeader(stream);
    }

    public static Tag readTag(TagDecoder decoder, InputBitStream stream, short version) throws IOException {
        TagHeader header = TagReader.readTagHeader(stream);
        byte[] tagData = stream.readBytes(header.getLength());
        return TagReader.readTag(decoder, header, tagData, version);
    }
}

