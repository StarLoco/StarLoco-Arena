/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.render.IOpenGL;
/*    */ import org.fenggui.render.Pixmap;
/*    */ import org.fenggui.util.Color;
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
/*    */ public class ComboBoxAppearance
/*    */   extends DecoratorAppearance
/*    */ {
/* 30 */   private ComboBox box = null;
/*    */ 
/*    */   
/*    */   public ComboBoxAppearance(ComboBox w) {
/* 34 */     super(w);
/* 35 */     this.box = w;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension getContentMinSizeHint() {
/* 41 */     int pixmapWidth = 0, pixmapHeight = 0;
/*    */     
/* 43 */     if (this.box.getPixmap() != null) {
/*    */       
/* 45 */       pixmapHeight = this.box.getPixmap().getHeight();
/* 46 */       pixmapWidth = this.box.getPixmap().getWidth();
/*    */     } 
/*    */     
/* 49 */     Dimension d = new Dimension(
/* 50 */         this.box.getLabel().getMinWidth() + pixmapWidth, 
/* 51 */         Math.max(this.box.getLabel().getMinHeight(), pixmapHeight));
/*    */     
/* 53 */     return d;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintContent(Graphics g, IOpenGL gl) {
/* 59 */     Label label = this.box.getLabel();
/* 60 */     Pixmap pixmap = this.box.getPixmap();
/*    */     
/* 62 */     g.translate(label.getX(), label.getY());
/* 63 */     label.paint(g);
/* 64 */     g.translate(-label.getX(), -label.getY());
/*    */     
/* 66 */     if (pixmap != null) {
/*    */       
/* 68 */       g.setColor(Color.WHITE);
/* 69 */       g.drawImage(
/* 70 */           pixmap, getContentWidth() - pixmap.getWidth(), 
/* 71 */           getContentHeight() / 2 - pixmap.getHeight() / 2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ComboBoxAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */