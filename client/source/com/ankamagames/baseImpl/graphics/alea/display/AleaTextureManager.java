/*     */ package com.ankamagames.baseImpl.graphics.alea.display;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.ManagedTexture;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceListener;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TObjectIntHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AleaTextureManager
/*     */   implements ResourceListener
/*     */ {
/*  22 */   private static final AleaTextureManager m_instance = new AleaTextureManager();
/*     */   
/*     */   public static final String DEFAULT_FILE_EXTENSION = ".tga";
/*     */   
/*     */   public static final String DEFAULT_GFX_PATH = "";
/*  27 */   private String m_fileExtension = ".tga";
/*  28 */   private String m_gfxPath = "";
/*     */ 
/*     */   
/*     */   private TIntObjectHashMap<ManagedTexture> m_cachedTextures;
/*     */   
/*     */   private TObjectIntHashMap<ManagedTexture> m_textureIds;
/*     */ 
/*     */   
/*     */   protected AleaTextureManager() {
/*  37 */     this.m_cachedTextures = new TIntObjectHashMap();
/*  38 */     this.m_textureIds = new TObjectIntHashMap();
/*  39 */     TextureManager.getInstance().addListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AleaTextureManager getInstance() {
/*  46 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFileExtension(String fileExtension) {
/*  53 */     this.m_fileExtension = fileExtension;
/*  54 */     if (fileExtension.startsWith(".")) {
/*  55 */       this.m_fileExtension = "." + this.m_fileExtension;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGfxPath(String gfxPath) {
/*  63 */     this.m_gfxPath = gfxPath;
/*  64 */     if (!gfxPath.endsWith("/")) {
/*  65 */       this.m_gfxPath = String.valueOf(this.m_gfxPath) + "/";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManagedTexture getTextureForCell(int gfxId) {
/*  77 */     ManagedTexture texture = (ManagedTexture)this.m_cachedTextures.get(gfxId);
/*     */     
/*  79 */     if (texture == null) {
/*  80 */       StringBuilder builder = new StringBuilder(this.m_gfxPath);
/*  81 */       String filePath = builder.append(gfxId).append(this.m_fileExtension).toString();
/*     */       
/*  83 */       texture = TextureManager.createTextureFromFile(filePath);
/*     */       
/*  85 */       this.m_textureIds.put(texture, gfxId);
/*  86 */       this.m_cachedTextures.put(gfxId, texture);
/*     */     } else {
/*  88 */       TextureManager.getInstance().tagResourceInUse((ManageableResource)texture);
/*     */     } 
/*     */     
/*  91 */     return texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCachedTextures() {
/*  96 */     this.m_cachedTextures.clear();
/*  97 */     TextureManager.getInstance().releaseAllResources();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceContextReloaded(ResourceContext resourceContexts) {}
/*     */ 
/*     */   
/*     */   public void onUnloadResourceContext(ResourceContext resourceContexts) {
/* 105 */     ManagedTexture texture = (ManagedTexture)resourceContexts.getResource();
/* 106 */     int id = this.m_textureIds.get(texture);
/* 107 */     this.m_textureIds.remove(texture);
/* 108 */     this.m_cachedTextures.remove(id);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\AleaTextureManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */