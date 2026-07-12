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
/*     */ public class BorderLayout
/*     */   extends LayoutManager
/*     */ {
/*     */   public void doLayout(Container container, List<IWidget> content) {
/*  44 */     int widthWest = 0;
/*  45 */     int widthEast = 0;
/*     */     
/*  47 */     int heightNorth = 0;
/*  48 */     int heightSouth = 0;
/*     */     
/*  50 */     for (IWidget c : content) {
/*  51 */       BorderLayoutData bld = (BorderLayoutData)c.getLayoutData();
/*     */       
/*  53 */       if (bld == null) {
/*     */         continue;
/*     */       }
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
/*  67 */       if (bld.getValue() == 0) {
/*     */         
/*  69 */         heightNorth = getValidMinHeight(c); continue;
/*     */       } 
/*  71 */       if (bld.getValue() == 3) {
/*     */         
/*  73 */         heightSouth = getValidMinHeight(c); continue;
/*     */       } 
/*  75 */       if (bld.getValue() == 2) {
/*     */         
/*  77 */         widthEast = getValidMinWidth(c); continue;
/*     */       } 
/*  79 */       if (bld.getValue() == 1)
/*     */       {
/*  81 */         widthWest = getValidMinWidth(c);
/*     */       }
/*     */     } 
/*     */     
/*  85 */     for (IWidget w : content) {
/*     */       
/*  87 */       BorderLayoutData bld = (BorderLayoutData)w.getLayoutData();
/*     */       
/*  89 */       if (bld == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  94 */       if (bld.getValue() == 0) {
/*     */         
/*  96 */         w.setX(widthWest);
/*  97 */         w.setY(container.getAppearance().getContentHeight() - heightNorth);
/*  98 */         setValidSize(w, 
/*  99 */             container.getAppearance().getContentWidth() - widthWest - widthEast, 
/* 100 */             getValidMinHeight(w)); continue;
/*     */       } 
/* 102 */       if (bld.getValue() == 3) {
/*     */         
/* 104 */         w.setX(widthWest);
/* 105 */         w.setY(0);
/* 106 */         setValidSize(w, 
/* 107 */             container.getAppearance().getContentWidth() - widthWest - widthEast, 
/* 108 */             getValidMinHeight(w)); continue;
/*     */       } 
/* 110 */       if (bld.getValue() == 2) {
/* 111 */         w.setX(container.getAppearance().getContentWidth() - getValidMinWidth(w));
/* 112 */         w.setY(0);
/* 113 */         setValidSize(w, 
/* 114 */             getValidMinWidth(w), 
/* 115 */             container.getAppearance().getContentHeight()); continue;
/*     */       } 
/* 117 */       if (bld.getValue() == 1) {
/* 118 */         w.setX(0);
/* 119 */         w.setY(0);
/* 120 */         setValidSize(w, 
/* 121 */             getValidMinWidth(w), 
/* 122 */             container.getAppearance().getContentHeight());
/*     */         
/*     */         continue;
/*     */       } 
/* 126 */       w.setX(widthWest);
/* 127 */       w.setY(heightSouth);
/* 128 */       setValidSize(w, 
/* 129 */           container.getAppearance().getContentWidth() - widthEast - widthWest, 
/* 130 */           container.getAppearance().getContentHeight() - heightSouth - heightNorth);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinHeight(List<IWidget> content) {
/* 143 */     int leftColumn = 0;
/* 144 */     int rightColumn = 0;
/* 145 */     int middleColumn = 0;
/*     */     
/* 147 */     for (IWidget c : content) {
/*     */       
/* 149 */       BorderLayoutData bld = (BorderLayoutData)c.getLayoutData();
/* 150 */       if (bld == null)
/*     */         continue; 
/* 152 */       if (bld.getValue() == 4 || 
/* 153 */         bld.getValue() == 0 || 
/* 154 */         bld.getValue() == 3) {
/*     */         
/* 156 */         middleColumn += getValidMinHeight(c); continue;
/*     */       } 
/* 158 */       if (bld.getValue() == 2) {
/*     */         
/* 160 */         rightColumn = getValidMinHeight(c); continue;
/*     */       } 
/* 162 */       if (bld.getValue() == 1)
/*     */       {
/* 164 */         leftColumn = getValidMinHeight(c);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     return Math.max(leftColumn, Math.max(rightColumn, middleColumn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinWidth(List<IWidget> content) {
/* 176 */     int leftColumn = 0;
/* 177 */     int rightColumn = 0;
/* 178 */     int middleColumn = 0;
/*     */     
/* 180 */     for (IWidget c : content) {
/*     */       
/* 182 */       BorderLayoutData bld = (BorderLayoutData)c.getLayoutData();
/* 183 */       if (bld == null)
/*     */         continue; 
/* 185 */       if (bld.getValue() == 4 || 
/* 186 */         bld.getValue() == 0 || 
/* 187 */         bld.getValue() == 3) {
/*     */         
/* 189 */         if (middleColumn < getValidMinWidth(c)) middleColumn = getValidMinWidth(c);  continue;
/*     */       } 
/* 191 */       if (bld.getValue() == 2) {
/*     */         
/* 193 */         rightColumn = getValidMinWidth(c); continue;
/*     */       } 
/* 195 */       if (bld.getValue() == 1)
/*     */       {
/* 197 */         leftColumn = getValidMinWidth(c);
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return leftColumn + middleColumn + rightColumn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension computeMinSize(Container container, List<IWidget> content) {
/* 206 */     return new Dimension(getMinWidth(content), getMinHeight(content));
/*     */   }
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\BorderLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */