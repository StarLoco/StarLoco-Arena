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
/*     */ public class GridLayout
/*     */   extends LayoutManager
/*     */ {
/*     */   private int rows;
/*     */   private int columns;
/*     */   
/*     */   public GridLayout(int rows, int columns) {
/*  44 */     this.rows = rows;
/*  45 */     this.columns = columns;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension computeMinSize(Container container, List<IWidget> content) {
/*  51 */     if (content.isEmpty()) return new Dimension(0, 0);
/*     */     
/*  53 */     IWidget[][] grid = buildWidgetGrid(content);
/*     */     
/*  55 */     int minHeight = 0;
/*  56 */     int minWidth = 0;
/*     */     
/*  58 */     for (int row = 0; row < this.rows; row++)
/*     */     {
/*  60 */       minHeight += getRowMinHeight(grid, row);
/*     */     }
/*     */     
/*  63 */     for (int column = 0; column < this.columns; column++)
/*     */     {
/*  65 */       minWidth += getColumnMinWidth(grid, column);
/*     */     }
/*     */     
/*  68 */     return new Dimension(minWidth, minHeight);
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
/*     */   private IWidget[][] buildWidgetGrid(List<IWidget> content) {
/*  80 */     IWidget[][] array = new IWidget[this.rows][this.columns];
/*  81 */     int i = 0;
/*     */     
/*  83 */     for (IWidget w : content) {
/*     */ 
/*     */       
/*  86 */       array[i / this.columns][i % this.columns] = w;
/*  87 */       i++;
/*     */       
/*  89 */       if (i > this.columns * this.rows)
/*     */       {
/*  91 */         throw new IllegalArgumentException("A " + this.rows + "x" + this.columns + " Gridlayout too small for " + 
/*  92 */             content.size() + " widgets. Increase number of rows/columns.");
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColumnMinWidth(IWidget[][] array, int column) {
/* 101 */     int width = 0;
/*     */     
/* 103 */     for (int i = 0; i < this.rows; i++) {
/*     */       
/* 105 */       if (array[i][column] != null) {
/* 106 */         width = Math.max(width, getValidMinWidth(array[i][column]));
/*     */       }
/*     */     } 
/* 109 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getRowMinHeight(IWidget[][] array, int row) {
/* 114 */     int height = 0;
/*     */     
/* 116 */     for (int i = 0; i < this.columns; i++) {
/*     */       
/* 118 */       if (array[row][i] != null) {
/* 119 */         height = Math.max(height, getValidMinHeight(array[row][i]));
/*     */       }
/*     */     } 
/* 122 */     return height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLayout(Container container, List<IWidget> content) {
/* 129 */     IWidget[][] grid = buildWidgetGrid(content);
/*     */     
/* 131 */     Dimension minSize = container.getAppearance().getContentMinSizeHint();
/*     */     
/* 133 */     int additionalHorizontalSpace = (container.getAppearance().getContentWidth() - minSize.getWidth()) / this.columns;
/* 134 */     int additionalVerticalSpace = (container.getAppearance().getContentHeight() - minSize.getHeight()) / this.rows;
/*     */ 
/*     */     
/* 137 */     if (additionalHorizontalSpace < 0) additionalHorizontalSpace = 0; 
/* 138 */     if (additionalVerticalSpace < 0) additionalVerticalSpace = 0;
/*     */     
/* 140 */     int[] columnMinWidths = new int[this.columns];
/*     */     
/* 142 */     for (int column = 0; column < this.columns; column++)
/*     */     {
/* 144 */       columnMinWidths[column] = getColumnMinWidth(grid, column);
/*     */     }
/*     */     
/* 147 */     int y = container.getAppearance().getContentHeight();
/*     */     
/* 149 */     for (int i = 0; i < this.rows; i++) {
/*     */       
/* 151 */       int x = 0;
/* 152 */       int height = getRowMinHeight(grid, i) + additionalVerticalSpace;
/* 153 */       y -= height;
/*     */       
/* 155 */       for (int j = 0; j < this.columns; j++) {
/*     */         
/* 157 */         IWidget w = grid[i][j];
/* 158 */         if (w != null) {
/* 159 */           w.setX(x);
/* 160 */           w.setY(y);
/* 161 */           w.setSize(new Dimension(columnMinWidths[j] + additionalHorizontalSpace, height));
/*     */ 
/*     */           
/* 164 */           x += columnMinWidths[j] + additionalHorizontalSpace;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 173 */     this.rows = stream.processAttribute("rows", this.rows);
/* 174 */     this.columns = stream.processAttribute("columns", this.columns);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\GridLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */