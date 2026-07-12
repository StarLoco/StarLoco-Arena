/*     */ package com.ankamagames.baseImpl.graphics.alea.element.properties;
/*     */ 
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GraphicalElementProperties
/*     */   extends SpatialDataElementProperties
/*     */ {
/*     */   protected int m_gfxId;
/*     */   protected int m_originX;
/*     */   protected int m_originY;
/*     */   protected boolean m_flip;
/*     */   
/*     */   public GraphicalElementProperties(ByteBuffer buffer) {
/*  27 */     read(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicalElementProperties() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlip() {
/*  39 */     return this.m_flip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlip(boolean flip) {
/*  46 */     this.m_flip = flip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGfxId() {
/*  53 */     return this.m_gfxId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGfxId(int gfxId) {
/*  60 */     this.m_gfxId = gfxId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/*  67 */     return this.m_originX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOriginX(int originX) {
/*  74 */     this.m_originX = originX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/*  81 */     return this.m_originY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOriginY(int originY) {
/*  88 */     this.m_originY = originY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(ByteBuffer buffer) {
/*  97 */     super.read(buffer);
/*     */     
/*  99 */     this.m_gfxId = buffer.getInt();
/* 100 */     this.m_originX = buffer.getShort();
/* 101 */     this.m_originY = buffer.getShort();
/*     */     
/* 103 */     this.m_flip = (buffer.get() == 1);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\properties\GraphicalElementProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */