/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.layout.Alignment;
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.render.IOpenGL;
/*    */ import org.fenggui.render.Pixmap;
/*    */ import org.fenggui.util.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Separator
/*    */   extends Image
/*    */ {
/*    */   private boolean m_horizontal;
/* 20 */   private SeparatorAppearance m_appearance = null;
/*    */   
/*    */   public Separator() {
/* 23 */     this(true);
/*    */   }
/*    */   
/*    */   public Separator(boolean horizontal) {
/* 27 */     this.m_horizontal = horizontal;
/* 28 */     this.m_appearance = new SeparatorAppearance(this);
/*    */   }
/*    */   
/*    */   public SeparatorAppearance getAppearance() {
/* 32 */     return this.m_appearance;
/*    */   }
/*    */   
/*    */   public void setAppearance(SeparatorAppearance appearance) {
/* 36 */     this.m_appearance = appearance;
/*    */   }
/*    */   
/*    */   public void setHorizontal(boolean horizontal) {
/* 40 */     this.m_horizontal = horizontal;
/*    */   }
/*    */   
/*    */   public boolean isHorizontal() {
/* 44 */     return this.m_horizontal;
/*    */   }
/*    */   
/*    */   public class SeparatorAppearance extends Image.ImageAppearance
/*    */   {
/*    */     public SeparatorAppearance(IWidget w) {
/* 50 */       super(w);
/*    */     }
/*    */     
/*    */     public void paintContent(Graphics g, IOpenGL gl) {
/* 54 */       if (Separator.this.m_pixmap != null) {
/* 55 */         g.setColor(Color.WHITE);
/*    */         int width;
/* 57 */         int height; int width; if (Separator.this.m_horizontal) {
/* 58 */           int height = Separator.this.m_pixmap.getHeight();
/* 59 */           width = getContentWidth();
/*    */         } else {
/* 61 */           height = getContentHeight();
/* 62 */           width = Separator.this.m_pixmap.getWidth();
/*    */         }
/* 64 */         int x = Separator.this.m_alignment.alignX(getContentWidth(), width);
/* 65 */         int y = Separator.this.m_alignment.alignY(getContentHeight(), height);
/* 66 */         g.drawScaledImage(Separator.this.m_pixmap, x, y, width, height);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Separator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */