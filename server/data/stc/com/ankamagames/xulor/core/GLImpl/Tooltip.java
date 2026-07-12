/*     */ package com.ankamagames.xulor.core.GLImpl;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.text.AbstractTesselBackground;
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText;
/*     */ import java.awt.Font;
/*     */ import javax.media.opengl.GL;
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
/*     */ public class Tooltip
/*     */   extends BackgroundedText
/*     */ {
/*     */   public static final int INFINITE_DURATION = Integer.MAX_VALUE;
/*     */   public static final int DEFAULT_DURATION = 3000;
/*     */   private long m_startTime;
/*  24 */   private int m_duration = 3000;
/*  25 */   private boolean m_shown = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  30 */   private int m_xOffset = 0;
/*  31 */   private int m_yOffset = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public Tooltip(Font font)
/*     */   {
/*  37 */     super(font);
/*  38 */     setBackground(new TooltipBackground());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBackgroundColor(float r, float g, float b, float a)
/*     */   {
/*  50 */     ((AbstractTesselBackground)getBackground()).setBackgoundColor(r, g, b, a);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] getBackgroundColor()
/*     */   {
/*  59 */     return ((AbstractTesselBackground)getBackground()).getBackgroundColor();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBorderColor(float r, float g, float b, float a)
/*     */   {
/*  71 */     ((AbstractTesselBackground)getBackground()).setBorderColor(r, g, b, a);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] getBorderColor()
/*     */   {
/*  80 */     return ((AbstractTesselBackground)getBackground()).getBorderColor();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getDuration()
/*     */   {
/*  87 */     return this.m_duration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDuration(int duration)
/*     */   {
/*  94 */     this.m_duration = duration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOffset(int xOffset, int yOffset)
/*     */   {
/* 104 */     this.m_xOffset = xOffset;
/* 105 */     this.m_yOffset = yOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getXOffset()
/*     */   {
/* 112 */     return this.m_xOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getYOffset()
/*     */   {
/* 119 */     return this.m_yOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float getOriginalX()
/*     */   {
/* 129 */     return super.getOriginalX() + this.m_xOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float getOriginalY()
/*     */   {
/* 139 */     return super.getOriginalY() + this.m_yOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVisible(boolean visible)
/*     */   {
/* 149 */     if ((!visible) && (getText() != null) && (!getText().equals(""))) {
/* 150 */       this.m_shown = false;
/* 151 */       this.m_startTime = 0L;
/*     */     }
/* 153 */     super.setVisible(visible);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/* 164 */     if (getText() == null) {
/* 165 */       return;
/*     */     }
/* 167 */     if (isVisible()) {
/* 168 */       if ((this.m_duration != Integer.MAX_VALUE) && (!this.m_shown)) {
/* 169 */         this.m_startTime = realTime;
/* 170 */         this.m_shown = true;
/*     */       }
/*     */       
/* 173 */       if ((this.m_duration != Integer.MAX_VALUE) && (this.m_startTime + this.m_duration < realTime)) {
/* 174 */         setVisible(false);
/* 175 */         this.m_shown = false;
/* 176 */         return;
/*     */       }
/* 178 */       super.process(realTime, frameCount);
/*     */     } else {
/* 180 */       this.m_shown = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void processGeometry(GL gl)
/*     */   {
/* 191 */     if (getText() == null) {
/* 192 */       return;
/*     */     }
/* 194 */     if (this.m_visible) {
/* 195 */       super.processGeometry(gl);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void display(GL gl)
/*     */   {
/* 206 */     if (getText() == null) {
/* 207 */       return;
/*     */     }
/* 209 */     if (this.m_visible) {
/* 210 */       super.display(gl);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\GLImpl\Tooltip.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */