/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag;

import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TagDocument {
    protected TagDocumentHeader m_header;
    private ArrayList<Tag> m_tags = new ArrayList();

    public TagDocument() {
        this.createHeader();
        this.resetHeader();
    }

    protected void createHeader() {
        this.m_header = new TagDocumentHeader();
    }

    private void resetHeader() {
        this.m_header.reset();
    }

    public TagDocumentHeader getHeader() {
        return this.m_header;
    }

    public void setVersion(short version) {
        this.m_header.setVersion(version);
    }

    public short getVersion() {
        return this.m_header.getVersion();
    }

    public boolean isReadable(short version) {
        return true;
    }

    public void setFileLength(long fileLength) {
        this.m_header.setFileLength(fileLength);
    }

    public long getFileLength() {
        return this.m_header.getFileLength();
    }

    public void setCompressed(boolean compressed) {
        this.m_header.setCompressed(compressed);
    }

    public boolean isCompressed() {
        return this.m_header.isCompressed();
    }

    public ArrayList<Tag> getTags() {
        return this.m_tags;
    }

    public void addTag(Tag tag) {
        this.m_tags.add(tag);
    }

    public void addTags(ArrayList<Tag> tags) {
        this.m_tags.addAll(tags);
    }

    public boolean removeTag(Tag tag) {
        return this.m_tags.remove(tag);
    }

    public Tag removeTag(int index) {
        return this.m_tags.remove(index);
    }

    public void clear() {
        this.resetHeader();
        this.m_tags.clear();
    }
}

