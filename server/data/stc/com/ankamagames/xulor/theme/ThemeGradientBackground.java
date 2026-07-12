/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThemeGradientBackground
/*     */   extends ThemeBackground
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "GradientBackground";
/*  19 */   private Color m_topLeft = Color.LIGHT_GRAY;
/*  20 */   private Color m_topRight = Color.DARK_GRAY;
/*  21 */   private Color m_bottomLeft = Color.RED;
/*  22 */   private Color m_bottomRight = Color.GRAY;
/*     */   
/*     */ 
/*     */ 
/*     */   public void add(IThemeElement elem)
/*     */   {
/*  28 */     if ((elem instanceof ThemePositionableColor)) {
/*  29 */       ThemePositionableColor tpc = (ThemePositionableColor)elem;
/*  30 */       if (tpc.getPosition().equals(Alignment.NORTH_WEST)) {
/*  31 */         this.m_topLeft = tpc.getColor();
/*  32 */       } else if (tpc.getPosition().equals(Alignment.NORTH_EAST)) {
/*  33 */         this.m_topRight = tpc.getColor();
/*  34 */       } else if (tpc.getPosition().equals(Alignment.SOUTH_WEST)) {
/*  35 */         this.m_bottomLeft = tpc.getColor();
/*  36 */       } else if (tpc.getPosition().equals(Alignment.SOUTH_EAST)) {
/*  37 */         this.m_bottomRight = tpc.getColor();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getBottomLeft()
/*     */   {
/*  46 */     return this.m_bottomLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getBottomRight()
/*     */   {
/*  53 */     return this.m_bottomRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTopLeft()
/*     */   {
/*  60 */     return this.m_topLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTopRight()
/*     */   {
/*  67 */     return this.m_topRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBottomLeft(Color bottomLeft)
/*     */   {
/*  74 */     this.m_bottomLeft = bottomLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBottomRight(Color bottomRight)
/*     */   {
/*  81 */     this.m_bottomRight = bottomRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTopLeft(Color topLeft)
/*     */   {
/*  88 */     this.m_topLeft = topLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTopRight(Color topRight)
/*     */   {
/*  95 */     this.m_topRight = topRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IThemeElement cloneAppearance()
/*     */   {
/* 102 */     ThemeGradientBackground bg = new ThemeGradientBackground();
/*     */     
/* 104 */     bg.setBottomLeft(this.m_bottomLeft);
/* 105 */     bg.setBottomRight(this.m_bottomRight);
/* 106 */     bg.setTopLeft(this.m_topLeft);
/* 107 */     bg.setTopRight(this.m_topRight);
/* 108 */     bg.setEnabled(this.m_enabled);
/*     */     
/* 110 */     return bg;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeGradientBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */