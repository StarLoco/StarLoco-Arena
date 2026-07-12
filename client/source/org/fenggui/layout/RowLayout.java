/*     */ package org.fenggui.layout;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.util.Dimension;
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
/*     */ public class RowLayout
/*     */   extends LayoutManager
/*     */ {
/*     */   private boolean horizontal = true;
/*     */   
/*     */   public RowLayout() {}
/*     */   
/*     */   public RowLayout(boolean horizontal) {
/*  52 */     this.horizontal = horizontal;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/*  57 */     return this.horizontal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLayout(Container container, List<IWidget> content) {
/*  65 */     double freeSpacePerComp = 0.0D;
/*     */     
/*  67 */     int reqSpace = this.horizontal ? 
/*  68 */       getSumOfAllWidths(content) : getSumOfAllHeights(content);
/*     */     
/*  70 */     freeSpacePerComp = (this.horizontal ? 
/*  71 */       container.getAppearance().getContentWidth() : 
/*  72 */       container.getAppearance().getContentHeight());
/*     */     
/*  74 */     double expandableWidgets = 0.0D;
/*     */     
/*  76 */     for (IWidget c : content) {
/*  77 */       if (c.isExpandable()) expandableWidgets++;
/*     */     
/*     */     } 
/*  80 */     if (expandableWidgets == 0.0D) expandableWidgets = 1.0D;
/*     */ 
/*     */     
/*  83 */     freeSpacePerComp = (freeSpacePerComp - reqSpace) / expandableWidgets;
/*     */ 
/*     */     
/*  86 */     if (freeSpacePerComp < 0.0D) freeSpacePerComp = 0.0D;
/*     */     
/*  88 */     int x = 0;
/*  89 */     int y = 0;
/*     */ 
/*     */ 
/*     */     
/*  93 */     for (int i = 0; i < content.size(); i++) {
/*     */ 
/*     */       
/*  96 */       IWidget w = null;
/*     */       
/*  98 */       if (this.horizontal) {
/*     */         
/* 100 */         w = content.get(i);
/*     */         
/* 102 */         setValidSize(w, 
/* 103 */             (int)freeSpacePerComp + getValidMinWidth(w), 
/* 104 */             container.getAppearance().getContentHeight());
/*     */         
/* 106 */         w.setX(x);
/* 107 */         x += w.getSize().getWidth();
/* 108 */         w.setY(container.getAppearance().getContentHeight() / 2 - w.getSize().getHeight() / 2);
/*     */       }
/*     */       else {
/*     */         
/* 112 */         w = content.get(content.size() - i - 1);
/*     */         
/* 114 */         setValidSize(w, 
/* 115 */             container.getAppearance().getContentWidth(), 
/* 116 */             (int)freeSpacePerComp + getValidMinHeight(w));
/*     */         
/* 118 */         w.setY(y);
/* 119 */         y += w.getSize().getHeight();
/* 120 */         w.setX(container.getAppearance().getContentWidth() / 2 - w.getSize().getWidth() / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSumOfAllHeights(List<IWidget> content) {
/* 128 */     int sum = 0;
/* 129 */     for (IWidget c : content)
/*     */     {
/* 131 */       sum += getValidMinHeight(c);
/*     */     }
/* 133 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSumOfAllWidths(List<IWidget> content) {
/* 140 */     int reqW = 0;
/* 141 */     for (IWidget c : content)
/*     */     {
/* 143 */       reqW += getValidMinWidth(c);
/*     */     }
/* 145 */     return reqW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension computeMinSize(Container container, List<IWidget> content) {
/* 153 */     int minW = 0;
/* 154 */     int minH = 0;
/*     */ 
/*     */     
/* 157 */     for (IWidget c : content) {
/*     */       
/* 159 */       if (this.horizontal) {
/*     */         
/* 161 */         minW += getValidMinWidth(c);
/* 162 */         if (minH < getValidMinHeight(c)) minH = getValidMinHeight(c);
/*     */         
/*     */         continue;
/*     */       } 
/* 166 */       if (minW < getValidMinWidth(c)) minW = getValidMinWidth(c); 
/* 167 */       minH += getValidMinHeight(c);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return new Dimension(minW, minH);
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 177 */     this.horizontal = stream.processAttribute("horizontal", this.horizontal);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\RowLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */