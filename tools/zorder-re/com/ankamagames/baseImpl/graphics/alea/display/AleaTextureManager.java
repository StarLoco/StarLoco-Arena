/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.framework.graphics.opengl.TextureManager;
import com.ankamagames.framework.graphics.opengl.base.ManagedTexture;
import com.ankamagames.framework.kernel.core.resource.ResourceContext;
import com.ankamagames.framework.kernel.core.resource.ResourceListener;
import gnu.trove.TIntObjectHashMap;
import gnu.trove.TObjectIntHashMap;

public class AleaTextureManager
implements ResourceListener {
    private static final AleaTextureManager m_instance = new AleaTextureManager();
    public static final String DEFAULT_FILE_EXTENSION = ".tga";
    public static final String DEFAULT_GFX_PATH = "";
    private String m_fileExtension = ".tga";
    private String m_gfxPath = "";
    private TIntObjectHashMap<ManagedTexture> m_cachedTextures = new TIntObjectHashMap();
    private TObjectIntHashMap<ManagedTexture> m_textureIds = new TObjectIntHashMap();

    protected AleaTextureManager() {
        TextureManager.getInstance().addListener(this);
    }

    public static AleaTextureManager getInstance() {
        return m_instance;
    }

    public void setFileExtension(String fileExtension) {
        this.m_fileExtension = fileExtension;
        if (fileExtension.startsWith(".")) {
            this.m_fileExtension = "." + this.m_fileExtension;
        }
    }

    public void setGfxPath(String gfxPath) {
        this.m_gfxPath = gfxPath;
        if (!gfxPath.endsWith("/")) {
            this.m_gfxPath = String.valueOf(this.m_gfxPath) + "/";
        }
    }

    public ManagedTexture getTextureForCell(int gfxId) {
        ManagedTexture texture = this.m_cachedTextures.get(gfxId);
        if (texture == null) {
            StringBuilder builder = new StringBuilder(this.m_gfxPath);
            String filePath = builder.append(gfxId).append(this.m_fileExtension).toString();
            texture = TextureManager.createTextureFromFile(filePath);
            this.m_textureIds.put(texture, gfxId);
            this.m_cachedTextures.put(gfxId, texture);
        } else {
            TextureManager.getInstance().tagResourceInUse(texture);
        }
        return texture;
    }

    public void clearCachedTextures() {
        this.m_cachedTextures.clear();
        TextureManager.getInstance().releaseAllResources();
    }

    public void onResourceContextReloaded(ResourceContext resourceContexts) {
    }

    public void onUnloadResourceContext(ResourceContext resourceContexts) {
        ManagedTexture texture = (ManagedTexture)resourceContexts.getResource();
        int id = this.m_textureIds.get(texture);
        this.m_textureIds.remove(texture);
        this.m_cachedTextures.remove(id);
    }
}

