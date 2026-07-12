/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Alignment;
/*    */ import com.ankamagames.xulor.util.Dimension;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.fenggui.Container;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.LayoutManager;
/*    */ import org.fenggui.Widget;
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
/*    */ public class StaticLayoutPlus
/*    */   extends LayoutManager
/*    */ {
/*    */   public Dimension computeMinSize(Container arg0, List<IWidget> arg1) {
/* 33 */     return new Dimension(0, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLayout(Container container, List<IWidget> list) {
/* 41 */     if (container == null || list == null) {
/*    */       return;
/*    */     }
/*    */     
/* 45 */     for (IWidget w : list) {
/* 46 */       Widget widget = (Widget)w;
/* 47 */       if (widget == null || !(widget.getLayoutData() instanceof StaticLayoutPlusData)) {
/*    */         continue;
/*    */       }
/* 50 */       StaticLayoutPlusData sld = (StaticLayoutPlusData)widget.getLayoutData();
/*    */       
/* 52 */       if (sld.getDimension() != null) {
/* 53 */         int width, height; Dimension size = sld.getDimension();
/*    */         
/* 55 */         if (size.getWidthPercentage() != null) {
/* 56 */           width = (int)Math.round(container.getAppearance().getContentWidth() * size.getWidthPercentage().getValue() / 100.0D);
/*    */         } else {
/* 58 */           width = size.getWidth();
/*    */         } 
/* 60 */         if (size.getHeightPercentage() != null) {
/* 61 */           height = (int)Math.round(container.getAppearance().getContentHeight() * size.getHeightPercentage().getValue() / 100.0D);
/*    */         } else {
/* 63 */           height = size.getHeight();
/*    */         } 
/* 65 */         if (height != -1) widget.setHeight(height); 
/* 66 */         if (width != -1) widget.setWidth(width); 
/* 67 */         if (sld.isResizeOnce()) {
/* 68 */           sld.resized();
/*    */         }
/*    */       } else {
/*    */         
/* 72 */         widget.setSizeToMinSize();
/*    */       } 
/*    */       
/* 75 */       if (sld.isXInit()) widget.setX(sld.getX()); 
/* 76 */       if (sld.isYInit()) widget.setY(sld.getY());
/*    */       
/* 78 */       if (sld.getAlignment() != null) {
/* 79 */         Alignment align = sld.getAlignment();
/* 80 */         widget.setX(align.getX(widget.getSize().getWidth(), container.getAppearance().getContentWidth()));
/* 81 */         widget.setY(align.getY(widget.getSize().getHeight(), container.getAppearance().getContentHeight()));
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void process(InputOutputStream arg0) throws IOException, IOStreamException {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\StaticLayoutPlus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */