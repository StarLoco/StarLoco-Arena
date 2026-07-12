/*     */ package com.ankamagames.graphics.isometric.text;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.text.GLTextArea;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BackgroundedText
/*     */   extends GLTextArea
/*     */ {
/*  23 */   public static DefaultBackgroundedTextPreRenderStates m_defaultPreRenderStates = new DefaultBackgroundedTextPreRenderStates();
/*  24 */   public static DefaultBackgroundedTextPostRenderStates m_defaultPostRenderStates = new DefaultBackgroundedTextPostRenderStates();
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum BackgroundedTextHotPointPosition
/*     */   {
/*  30 */     SOUTH_WEST,  SOUTH,  SOUTH_EAST, 
/*  31 */     NORTH_WEST,  NORTH,  NORTH_EAST, 
/*  32 */     WEST,  EAST;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  38 */   private DrawedBackground m_background = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  43 */   private BackgroundedTextHotPointPosition m_hotPointPosition = BackgroundedTextHotPointPosition.SOUTH_WEST;
/*     */   
/*     */ 
/*     */   protected float m_originalX;
/*     */   
/*     */ 
/*     */   protected float m_originalY;
/*     */   
/*     */ 
/*     */   protected float m_deltaX;
/*     */   
/*     */   protected float m_deltaY;
/*     */   
/*  56 */   private float m_drawZoneX = 0.0F;
/*  57 */   private float m_drawZoneY = 0.0F;
/*  58 */   private float m_drawZoneMaxX = 0.0F;
/*  59 */   private float m_drawZoneMaxY = 0.0F;
/*  60 */   private boolean m_drawZoneActivated = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BackgroundedText(Font font)
/*     */   {
/*  68 */     this(font, false, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BackgroundedText(Font font, boolean antialiased)
/*     */   {
/*  78 */     this(font, antialiased, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BackgroundedText(Font font, boolean antialiased, String text)
/*     */   {
/*  88 */     this(font, antialiased, text, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BackgroundedText(Font font, String text)
/*     */   {
/*  98 */     this(font, false, text, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BackgroundedText(Font font, boolean antialiased, String text, DrawedBackground drawedBackground)
/*     */   {
/* 109 */     super(font, antialiased);
/* 110 */     setText(text);
/* 111 */     if (drawedBackground != null) {
/* 112 */       this.m_background = drawedBackground;
/*     */     }
/* 114 */     init();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void init()
/*     */   {
/* 121 */     setAntialised(true);
/* 122 */     setPreRenderStates(m_defaultPreRenderStates);
/* 123 */     setPostRenderStates(m_defaultPostRenderStates);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setBackground(DrawedBackground background)
/*     */   {
/* 132 */     this.m_background = background;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DrawedBackground getBackground()
/*     */   {
/* 139 */     return this.m_background;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BackgroundedTextHotPointPosition getHotPointPosition()
/*     */   {
/* 146 */     return this.m_hotPointPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHotPointPosition(BackgroundedTextHotPointPosition alignment)
/*     */   {
/* 153 */     this.m_hotPointPosition = alignment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected float getOriginalX()
/*     */   {
/* 160 */     return this.m_originalX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected float getOriginalY()
/*     */   {
/* 167 */     return this.m_originalY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDrawZoneBounds(float x, float y, float width, float height)
/*     */   {
/* 179 */     this.m_drawZoneX = x;
/* 180 */     this.m_drawZoneY = y;
/* 181 */     this.m_drawZoneMaxX = (x + width);
/* 182 */     this.m_drawZoneMaxY = (y + height);
/* 183 */     this.m_drawZoneActivated = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawPrimitives(GL gl)
/*     */   {
/* 194 */     float x = 0.0F;
/* 195 */     float y = 0.0F;
/*     */     
/* 197 */     float width = 0.0F;
/* 198 */     float height = 0.0F;
/*     */     
/* 200 */     float leftMargin = this.m_background != null ? this.m_background.getLeftMargin() : 0;
/* 201 */     float rightMargin = this.m_background != null ? this.m_background.getRightMargin() : 0;
/* 202 */     float topMargin = this.m_background != null ? this.m_background.getTopMargin() : 0;
/* 203 */     float bottomMargin = this.m_background != null ? this.m_background.getBottomMargin() : 0;
/*     */     
/* 205 */     float textXOffset = leftMargin;
/* 206 */     float textYOffset = bottomMargin;
/*     */     
/* 208 */     x = getOriginalX();
/* 209 */     if (getScaleFactorX() != 0.0F) {
/* 210 */       width = (getTextWidth() + leftMargin + rightMargin) / getScaleFactorX();
/* 211 */       textXOffset = leftMargin / getScaleFactorX();
/*     */     } else {
/* 213 */       width = leftMargin + rightMargin;
/*     */     }
/*     */     
/* 216 */     y = getOriginalY();
/* 217 */     if (getScaleFactorY() != 0.0F) {
/* 218 */       height = (getTextHeight() + bottomMargin + topMargin) / getScaleFactorY();
/* 219 */       textYOffset = bottomMargin / getScaleFactorY();
/*     */     } else {
/* 221 */       height = bottomMargin + topMargin;
/*     */     }
/*     */     
/*     */ 
/* 225 */     switch (this.m_hotPointPosition)
/*     */     {
/*     */     case NORTH: 
/* 228 */       x -= (int)(width / 2.0F);
/* 229 */       break;
/*     */     
/*     */ 
/*     */     case NORTH_EAST: 
/* 233 */       x -= width;
/* 234 */       break;
/*     */     
/*     */ 
/*     */     case NORTH_WEST: 
/* 238 */       y -= height;
/* 239 */       break;
/*     */     
/*     */ 
/*     */     case SOUTH: 
/* 243 */       x -= (int)(width / 2.0F);
/* 244 */       y -= height;
/* 245 */       break;
/*     */     
/*     */ 
/*     */     case SOUTH_EAST: 
/* 249 */       x -= width;
/* 250 */       y -= height;
/* 251 */       break;
/*     */     
/*     */ 
/*     */     case SOUTH_WEST: 
/* 255 */       y -= (int)(height / 2.0F);
/* 256 */       break;
/*     */     
/*     */ 
/*     */     case WEST: 
/* 260 */       x -= width;
/* 261 */       y -= (int)(height / 2.0F);
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 267 */     if (this.m_drawZoneActivated) {
/* 268 */       x = (int)Math.max(this.m_drawZoneX, Math.min(x, this.m_drawZoneMaxX - width));
/* 269 */       y = (int)Math.max(this.m_drawZoneY, Math.min(y, this.m_drawZoneMaxY - height));
/*     */     }
/*     */     
/* 272 */     setX(x + (int)textXOffset + this.m_deltaX);
/* 273 */     setY(y + (int)textYOffset + this.m_deltaY);
/*     */     
/* 275 */     if (this.m_background != null) {
/* 276 */       this.m_background.drawBubbleBackground(gl, x, y, width, height);
/*     */     }
/*     */     
/* 279 */     super.drawPrimitives(gl);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPosition(float x, float y)
/*     */   {
/* 290 */     throw new UnsupportedOperationException("Cette métode ne peut être utilisé pour la classe " + getClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPosition(float x, float y, float deltaX, float deltaY)
/*     */   {
/* 300 */     super.setPosition(x + deltaX, y + deltaY);
/* 301 */     this.m_originalX = x;
/* 302 */     this.m_originalY = y;
/* 303 */     this.m_deltaX = deltaX;
/* 304 */     this.m_deltaY = deltaY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getSortPosition()
/*     */   {
/* 313 */     return 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\text\BackgroundedText.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */