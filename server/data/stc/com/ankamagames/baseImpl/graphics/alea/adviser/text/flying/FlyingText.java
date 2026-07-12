/*     */ package com.ankamagames.baseImpl.graphics.alea.adviser.text.flying;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.Adviser;
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.text.TimeTargetedGLTextArea;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
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
/*     */ public class FlyingText
/*     */   extends TimeTargetedGLTextArea
/*     */   implements Adviser
/*     */ {
/*     */   public class DefaultFlyingTextDeformer
/*     */     implements FlyingTextDeformer
/*     */   {
/*     */     public DefaultFlyingTextDeformer() {}
/*     */     
/*     */     public void process(FlyingText flyingText, long realTime, int frameCount)
/*     */     {
/*  32 */       long elapsedTime = realTime - flyingText.getStartTime();
/*  33 */       flyingText.setYOffset((int)easeOut(elapsedTime, 80.0D, 100.0D, flyingText.getDuration()));
/*  34 */       flyingText.setXOffset(-10);
/*     */       
/*  36 */       float[] color = flyingText.getColor();
/*  37 */       flyingText.setColor(color[0], color[1], color[2], (float)easeOut(elapsedTime, 1.5D, -1.600000023841858D, flyingText.getDuration()));
/*     */     }
/*     */     
/*     */     private double easeOut(double t, double b, double c, double d) {
/*  41 */       return -c * ((t = t / d - 1.0D) * t * t * t - 1.0D) + b;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  48 */   private FlyingTextDeformer m_deformer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FlyingText(Font font, String text)
/*     */   {
/*  57 */     this(font, text, -1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FlyingText(Font font, String text, int duration)
/*     */   {
/*  67 */     super(font, text, duration);
/*  68 */     setAntialised(true);
/*  69 */     this.m_deformer = new DefaultFlyingTextDeformer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public FlyingTextDeformer getDeformer()
/*     */   {
/*  76 */     return this.m_deformer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDeformer(FlyingTextDeformer deformer)
/*     */   {
/*  84 */     this.m_deformer = deformer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getSortPosition()
/*     */   {
/*  93 */     return 2.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(AleaWorldScene scene, long realTime, int frameCount)
/*     */   {
/* 103 */     getDeformer().process(this, realTime, frameCount);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\flying\FlyingText.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */