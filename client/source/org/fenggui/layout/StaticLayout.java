/*    */ package org.fenggui.layout;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.fenggui.Container;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.LayoutManager;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.InputOutputStream;
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
/*    */ public class StaticLayout
/*    */   extends LayoutManager
/*    */ {
/*    */   public void doLayout(Container container, List<IWidget> content) {}
/*    */   
/*    */   public static void center(IWidget widget, Container container) {
/* 64 */     widget.setX(container.getAppearance().getContentWidth() / 2 - widget.getSize().getWidth() / 2);
/* 65 */     widget.setY(container.getAppearance().getContentHeight() / 2 - widget.getSize().getHeight() / 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension computeMinSize(Container container, List<IWidget> content) {
/* 70 */     return new Dimension(0, 0);
/*    */   }
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\StaticLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */