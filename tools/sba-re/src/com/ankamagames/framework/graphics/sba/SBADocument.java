/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba;

import com.ankamagames.framework.fileFormat.tag.TagDocument;
import com.ankamagames.framework.graphics.sba.records.SBAHeader;

public class SBADocument
extends TagDocument {
    private int m_currentIdentifier = 0;
    public static final short[] READABLE_VERSION = new short[]{1, 2, 3};

    protected void createHeader() {
        this.m_header = new SBAHeader();
    }

    public int getNextIdentifier() {
        return ++this.m_currentIdentifier;
    }

    public void clear() {
        super.clear();
        this.m_currentIdentifier = 0;
    }

    public boolean isReadable(short version) {
        int i = READABLE_VERSION.length - 1;
        while (i >= 0) {
            if (READABLE_VERSION[i] == version) {
                return true;
            }
            --i;
        }
        return false;
    }
}

