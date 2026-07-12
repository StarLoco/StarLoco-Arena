/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;

public class SBAHeader
extends TagDocumentHeader {
    public static final boolean DEFAULT_COMPRESS = true;
    public static final String SBA_SIGNATURE = "sba";

    public SBAHeader() {
        this.setSignature(SBA_SIGNATURE);
    }

    public void reset() {
        super.reset();
        this.setVersion((short)3);
        this.setCompressed(true);
    }
}

