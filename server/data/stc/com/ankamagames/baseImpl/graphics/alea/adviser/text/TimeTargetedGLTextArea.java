/*     */ package com.ankamagames.baseImpl.graphics.alea.adviser.text;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimeTargetedGLTextArea
/*     */   extends TargetedGLTextArea
/*     */ {
/*     */   public static final int INFINITE_DURATION = -1;
/*  29 */   private long m_startTime = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  34 */   private long m_elapsedLifeTime = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  39 */   private int m_duration = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeTargetedGLTextArea(Font font, String text)
/*     */   {
/*  50 */     super(font, text);
/*  51 */     setDuration(-1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeTargetedGLTextArea(Font font, String text, int duration)
/*     */   {
/*  63 */     super(font, text);
/*  64 */     setDuration(duration);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getStartTime()
/*     */   {
/*  71 */     return this.m_startTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getDuration()
/*     */   {
/*  78 */     return this.m_duration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDuration(int duration)
/*     */   {
/*  86 */     this.m_duration = duration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void incElapsedLifeTime(int time)
/*     */   {
/*  95 */     this.m_elapsedLifeTime += time;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAlive()
/*     */   {
/* 105 */     if (this.m_duration == -1) {
/* 106 */       return true;
/*     */     }
/* 108 */     return this.m_elapsedLifeTime <= this.m_duration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetElapsedLifeTime()
/*     */   {
/* 115 */     this.m_startTime = 0L;
/* 116 */     this.m_elapsedLifeTime = 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/* 126 */     super.process(realTime, frameCount);
/*     */     
/* 128 */     if (this.m_startTime == 0L)
/*     */     {
/* 130 */       this.m_startTime = realTime;
/*     */     }
/*     */     else {
/* 133 */       this.m_elapsedLifeTime = (realTime - this.m_startTime);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\TimeTargetedGLTextArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */