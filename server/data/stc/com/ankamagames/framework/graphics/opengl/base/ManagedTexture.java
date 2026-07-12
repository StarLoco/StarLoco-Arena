/*     */ package com.ankamagames.framework.graphics.opengl.base;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagedTexture
/*     */   extends BaseTexture
/*     */   implements ManageableResource
/*     */ {
/*     */   public static class ManagedTextureContext
/*     */     extends ResourceContext
/*     */   {
/*     */     private String m_fileName;
/*     */     
/*     */     public String getFileName()
/*     */     {
/*  22 */       return this.m_fileName;
/*     */     }
/*     */     
/*     */     public void setFileName(String fileName) {
/*  26 */       this.m_fileName = fileName;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void bind()
/*     */   {
/*  36 */     TextureManager.getInstance().tagResourceInUse(this);
/*  37 */     super.bind();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/*  51 */     reset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reloadResource(ResourceContext resourceContext)
/*     */   {
/*  63 */     ManagedTextureContext context = (ManagedTextureContext)resourceContext;
/*  64 */     ManagedTexture texture = (ManagedTexture)context.getResource();
/*     */     
/*     */ 
/*  67 */     if (texture.getTexture() == null) {
/*  68 */       Texture tex = null;
/*     */       try {
/*  70 */         tex = TextureManager.createRawTextureFromFile(context.getFileName());
/*     */       } catch (Exception e) {
/*  72 */         e.printStackTrace();
/*     */       }
/*     */       
/*  75 */       if (tex != null) {
/*  76 */         texture.setTexture(tex);
/*     */       }
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
/*     */   public void unloadResource(ResourceContext resourceContext)
/*     */   {
/*  90 */     ManagedTextureContext context = (ManagedTextureContext)resourceContext;
/*  91 */     ManagedTexture texture = (ManagedTexture)context.getResource();
/*     */     
/*  93 */     texture.reset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long estimateMemoryUsageInBytes()
/*     */   {
/* 103 */     Texture texture = getTexture();
/* 104 */     if (texture != null) {
/* 105 */       return texture.getImageDataSize();
/*     */     }
/* 107 */     return 0L;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\ManagedTexture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */