/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records.tags;

import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class TagWriter {
    private TagWriter() {
    }

    public static void writeTag(OutputBitStream stream, Tag tag) throws IOException {
        tag.write(stream);
    }

    public static byte[] writeTag(Tag tag) throws IOException {
        OutputBitStream stream = new OutputBitStream();
        TagWriter.writeTag(stream, tag);
        return stream.getData();
    }

    public static byte[] writeTags(ArrayList<Tag> tags) throws IOException {
        OutputBitStream stream = new OutputBitStream();
        TagWriter.writeTags(stream, tags);
        return stream.getData();
    }

    public static void writeTags(OutputBitStream stream, ArrayList<Tag> tags) throws IOException {
        for (Tag tag : tags) {
            TagWriter.writeTag(stream, tag);
        }
        stream.writeUI16(0);
    }
}

