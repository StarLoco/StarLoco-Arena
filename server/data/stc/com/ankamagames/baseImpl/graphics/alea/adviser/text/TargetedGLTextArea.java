/*     */ package com.ankamagames.baseImpl.graphics.alea.adviser.text;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText;
/*     */ import java.awt.Font;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetedGLTextArea
/*     */   extends BackgroundedText
/*     */ {
/*  26 */   private IsoWorldTarget m_target = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_xOffset;
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_yOffset;
/*     */   
/*     */ 
/*     */ 
/*     */   public TargetedGLTextArea(Font font, String text)
/*     */   {
/*  40 */     super(font);
/*  41 */     setText(text);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IsoWorldTarget getTarget()
/*     */   {
/*  48 */     return this.m_target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTarget(IsoWorldTarget target)
/*     */   {
/*  56 */     this.m_target = target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getXOffset()
/*     */   {
/*  63 */     return this.m_xOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXOffset(int offset)
/*     */   {
/*  71 */     this.m_xOffset = offset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getYOffset()
/*     */   {
/*  78 */     return this.m_yOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYOffset(int offset)
/*     */   {
/*  86 */     this.m_yOffset = offset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getWorldX()
/*     */   {
/*  93 */     if (this.m_target != null) {
/*  94 */       return this.m_target.getWorldX();
/*     */     }
/*  96 */     return 0.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getWorldY()
/*     */   {
/* 103 */     if (this.m_target != null) {
/* 104 */       return this.m_target.getWorldY();
/*     */     }
/* 106 */     return 0.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getAltitude()
/*     */   {
/* 113 */     if (this.m_target != null) {
/* 114 */       return this.m_target.getAltitude();
/*     */     }
/* 116 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\TargetedGLTextArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */