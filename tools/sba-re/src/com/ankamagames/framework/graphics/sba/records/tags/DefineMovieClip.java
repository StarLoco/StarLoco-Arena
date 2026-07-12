/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
import com.ankamagames.framework.graphics.sba.records.tags.DefineSequence;
import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
import com.ankamagames.framework.graphics.sba.records.tags.SBATagDecoder;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefineMovieClip
extends DefineSequence {
    private int m_frameCount;
    private ArrayList<Tag> m_tags;

    public DefineMovieClip(int identifier) {
        this.m_code = (short)4;
        this.m_identifier = identifier;
        this.setLoopCount((short)0);
        this.m_tags = new ArrayList();
    }

    public DefineMovieClip() {
    }

    public ArrayList<Tag> getTags() {
        return this.m_tags;
    }

    @Override
    public int getFrameCount() {
        return this.m_frameCount;
    }

    public void addTag(Tag tag) {
        if (tag instanceof DefinitionTag) {
            return;
        }
        this.m_tags.add(tag);
    }

    public void addTags(ArrayList<Tag> tags) {
        for (Tag tag : tags) {
            this.addTag(tag);
        }
    }

    public boolean removeTag(Tag tag) {
        return this.m_tags.remove(tag);
    }

    public Tag removeTag(int index) {
        return this.m_tags.remove(index);
    }

    @Override
    public void setData(byte[] data, short version) throws IOException {
        Tag tag;
        InputBitStream inStream = this.readDefinitionSequenceTagHeader(data);
        this.m_tags = new ArrayList();
        while ((tag = TagReader.readTag(SBATagDecoder.getInstance(), inStream, version)).getCode() != 0) {
            if (tag.getCode() == 1) {
                ++this.m_frameCount;
            }
            this.m_tags.add(tag);
        }
    }

    @Override
    protected void writeData(OutputBitStream outStream) throws IOException {
        super.writeData(outStream);
        TagWriter.writeTags(outStream, this.m_tags);
    }
}

