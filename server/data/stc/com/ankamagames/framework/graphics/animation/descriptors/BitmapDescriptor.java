/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.instances.Bitmap;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.records.BitmapFrame;
/*     */ import com.ankamagames.framework.graphics.sba.records.Point;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefineBitmap;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BitmapDescriptor
/*     */   extends DisplayObjectDescriptor
/*     */ {
/*  34 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*     */     public Bitmap makeObject() {
/*  36 */       return new Bitmap();
/*     */     }
/*  34 */   });
/*     */   
/*     */ 
/*     */   private float m_hotX;
/*     */   
/*     */ 
/*     */   private float m_hotY;
/*     */   
/*     */   private AlphaBitmapData m_image;
/*     */   
/*     */   private BaseTexture m_texture;
/*     */   
/*     */   private float m_invertScalingValue;
/*     */   
/*     */ 
/*     */   public BitmapDescriptor()
/*     */   {
/*  51 */     super(-1, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapDescriptor(BaseDescriptorLibrary library, DefineBitmap tag)
/*     */   {
/*  61 */     super(tag.getIdentifier(), tag.getLinkage(), false, library);
/*  62 */     initializeHotPointAndBitmapData(tag.getHotPoint(), tag.getBitmapData(), tag.getInvertScalingValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapDescriptor(BitmapFrame bitmapFrame, float invertScalingValue)
/*     */   {
/*  71 */     super(-1, null);
/*  72 */     initializeHotPointAndBitmapData(bitmapFrame.getHotPoint(), bitmapFrame.getBitmapData(), invertScalingValue);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  78 */     if (this.m_image != null) {
/*  79 */       return this.m_image.getWidth();
/*     */     }
/*  81 */     System.err.println("BitmapDescriptor sans image");
/*  82 */     return 0;
/*     */   }
/*     */   
/*     */   public int getHeight()
/*     */   {
/*  87 */     if (this.m_image != null) {
/*  88 */       return this.m_image.getHeight();
/*     */     }
/*  90 */     System.err.println("BitmapDescriptor sans image");
/*  91 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public DisplayObjectDescriptor.DescriptorType getType()
/*     */   {
/*  97 */     return DisplayObjectDescriptor.DescriptorType.BITMAP;
/*     */   }
/*     */   
/*     */   public float getHotX() {
/* 101 */     return this.m_hotX;
/*     */   }
/*     */   
/*     */   public float getHotY() {
/* 105 */     return this.m_hotY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseTexture getTexture()
/*     */   {
/* 113 */     if ((this.m_texture == null) && (this.m_image != null)) {
/* 114 */       byte[] datas = this.m_image.getDatas();
/* 115 */       if (datas != null) {
/* 116 */         this.m_texture = TextureManager.createTexture(this.m_image.getWidth(), this.m_image.getHeight(), datas, 6408);
/*     */       }
/*     */     }
/* 119 */     return this.m_texture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getInvertScalingValue()
/*     */   {
/* 126 */     return this.m_invertScalingValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initializeFromTag(DefinitionTag tag)
/*     */   {
/* 138 */     super.initializeFromTag(tag);
/* 139 */     if ((tag instanceof DefineBitmap)) {
/* 140 */       DefineBitmap defBmpTag = (DefineBitmap)tag;
/* 141 */       initializeHotPointAndBitmapData(defBmpTag.getHotPoint(), defBmpTag.getBitmapData(), defBmpTag.getInvertScalingValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initializeHotPointAndBitmapData(Point hotPoint, AlphaBitmapData bitmapData, float invertScalingValue)
/*     */   {
/* 152 */     if (hotPoint != null) {
/* 153 */       this.m_hotX = (-hotPoint.getX() * 0.1F);
/* 154 */       this.m_hotY = (hotPoint.getY() * 0.1F);
/*     */     }
/*     */     
/* 157 */     this.m_image = bitmapData;
/*     */     
/* 159 */     this.m_invertScalingValue = invertScalingValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DisplayObject createInstance(AbstractDescriptorLibrary library)
/*     */   {
/*     */     Bitmap bitmap;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 171 */       Bitmap bitmap = (Bitmap)m_pool.borrowObject();
/* 172 */       bitmap.initialize(m_pool, library, this.m_id, this.m_linkage);
/*     */     } catch (Exception e) {
/* 174 */       bitmap = new Bitmap(library, this.m_id);
/*     */     }
/* 176 */     return bitmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapDescriptor duplicate()
/*     */   {
/* 186 */     DefinitionTag definitionTag = getLibrary().getIndexedBuffer().getDefinitionTag(getId());
/* 187 */     if ((definitionTag instanceof DefineBitmap)) {
/* 188 */       return new BitmapDescriptor(getLibrary(), (DefineBitmap)definitionTag);
/*     */     }
/* 190 */     m_logger.trace("duplicate BitmapDescriptor ne devrait pas arriver " + definitionTag);
/* 191 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unloadResource(ResourceContext resourceContext)
/*     */   {
/* 201 */     super.unloadResource(resourceContext);
/*     */     
/* 203 */     this.m_image = null;
/*     */     
/* 205 */     if (this.m_texture != null) {
/* 206 */       this.m_texture.reset();
/* 207 */       this.m_texture = null;
/*     */     }
/* 209 */     this.m_invertScalingValue = 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 218 */     return String.format("%s %s", new Object[] { "Bitmap", super.toString() });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 229 */     super.onCheckIn();
/* 230 */     this.m_image = null;
/* 231 */     this.m_texture = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\BitmapDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */