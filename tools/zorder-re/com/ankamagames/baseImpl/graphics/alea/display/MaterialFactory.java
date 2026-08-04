/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.framework.graphics.opengl.base.material.Material;
import java.io.File;
import java.text.DecimalFormat;

class MaterialFactory {
    public static final String DEFAULT_FILE_EXTENSION = ".png";
    private static final MaterialFactory m_instance = new MaterialFactory();
    private DecimalFormat frameParser = new DecimalFormat("0000");
    private String m_fileExtension = ".png";
    private String m_gfxPath = "";

    MaterialFactory() {
    }

    public static MaterialFactory getInstance() {
        return m_instance;
    }

    public void setFileExtension(String fileExtention) {
        this.m_fileExtension = fileExtention;
    }

    public void setGfxPath(String gfxPath) {
        this.m_gfxPath = gfxPath;
    }

    public Material getCharacterMaterial(String characterPath, String animationDirectory, int directionIndex, int frameCount) {
        String filePath = String.valueOf(this.m_gfxPath) + System.getProperties().getProperty("file.separator") + characterPath + System.getProperties().getProperty("file.separator") + directionIndex + "-" + animationDirectory + System.getProperties().getProperty("file.separator") + directionIndex + "_" + animationDirectory + this.frameParser.format(frameCount) + this.m_fileExtension;
        if (!new File(filePath).exists()) {
            return null;
        }
        Material material = new Material();
        return material;
    }
}

