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
/*    */ public class SliderAppearance
/*    */   extends DecoratorAppearance
/*    */ {
/* 28 */   private Slider slider = null;
/*    */ 
/*    */   
/*    */   public SliderAppearance(Slider w) {
/* 32 */     super(w);
/* 33 */     this.slider = w;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension getContentMinSizeHint() {
/* 39 */     return new Dimension(30, 30);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintContent(Graphics g, IOpenGL gl) {
/* 48 */     if (this.slider.isEnabled() || !(this.slider.getParent() instanceof ScrollBar)) {
/* 49 */       Button sliderButton = this.slider.getSliderButton();
/*    */       
/* 51 */       g.translate(sliderButton.getX(), sliderButton.getY());
/* 52 */       sliderButton.paint(g);
/* 53 */       g.translate(-sliderButton.getX(), -sliderButton.getY());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\SliderAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */