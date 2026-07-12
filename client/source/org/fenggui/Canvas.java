/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.render.IOpenGL;
/*    */ import org.fenggui.util.Dimension;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Canvas
/*    */   extends StandardWidget
/*    */ {
/* 44 */   private DecoratorAppearance appearance = null;
/*    */ 
/*    */   
/*    */   public Canvas() {
/* 48 */     this.appearance = new DefaultDectoratorAppearance(this);
/* 49 */     setupTheme(Canvas.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public DecoratorAppearance getAppearance() {
/* 54 */     return this.appearance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAppearance(DecoratorAppearance appearance) {
/* 59 */     this.appearance = appearance;
/*    */   }
/*    */ 
/*    */   
/*    */   class DefaultDectoratorAppearance
/*    */     extends DecoratorAppearance
/*    */   {
/*    */     public DefaultDectoratorAppearance(IWidget w) {
/* 67 */       super(w);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public Dimension getContentMinSizeHint() {
/* 73 */       return new Dimension(0, 0);
/*    */     }
/*    */     
/*    */     public void paintContent(Graphics g, IOpenGL gl) {}
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Canvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */