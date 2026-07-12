/*     */ package org.fenggui.table;
/*     */ 
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.DecoratorLayer;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Color;
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
/*     */ public class TableAppearance
/*     */   extends DecoratorAppearance
/*     */ {
/*  33 */   private Table table = null;
/*  34 */   private int cellHeight = 20;
/*     */ 
/*     */   
/*     */   private static final int OFFSET = 5;
/*     */ 
/*     */   
/*  40 */   private DecoratorLayer cellUnderlay = new DecoratorLayer(new org.fenggui.IDecorator[0]);
/*  41 */   private DecoratorLayer cellOverlay = new DecoratorLayer(new org.fenggui.IDecorator[0]);
/*     */   
/*  43 */   private int cellSpacing = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean gridVisible = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tableHeaderVisible = true;
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Color headerBackgroundColor = Color.GRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private Color gridColor = Color.GRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private Color textColor = Color.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private Color selectionColor = Color.RED;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private Color headTextColor = Color.GREEN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private Font font = Font.getDefaultFont();
/*     */ 
/*     */   
/*     */   public TableAppearance(Table w) {
/*  85 */     super((IWidget)w);
/*  86 */     this.table = w;
/*     */     
/*  88 */     this.cellHeight = this.font.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  94 */     int rowHeight = this.font.getHeight();
/*     */ 
/*     */     
/*  97 */     ITableModel model = this.table.getModel();
/*     */     
/*  99 */     if (model == null) return new Dimension(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     int numberOfRows = model.getRowCount();
/*     */     
/* 109 */     if (this.tableHeaderVisible) {
/* 110 */       numberOfRows++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     return new Dimension(100, numberOfRows * rowHeight + numberOfRows * this.cellSpacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/* 123 */     ITableModel model = this.table.getModel();
/* 124 */     Table.HeaderControl header = this.table.getHeader();
/*     */     
/* 126 */     if (model == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 132 */     int x = 0;
/* 133 */     int y = 0;
/*     */ 
/*     */     
/* 136 */     y = getContentHeight() - this.cellHeight;
/*     */ 
/*     */ 
/*     */     
/* 140 */     float freeSpace = this.table.getWidth();
/*     */     
/* 142 */     for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++)
/*     */     {
/* 144 */       freeSpace -= this.table.getColumnWidth(columnIndex);
/*     */     }
/*     */     
/* 147 */     g.setFont(this.font);
/*     */     
/* 149 */     if (isTableHeadVisible()) {
/* 150 */       y -= this.cellHeight + this.cellSpacing;
/*     */     }
/* 152 */     int lowerClipBound = g.getClipSpace().getY() - this.cellHeight + this.cellSpacing;
/* 153 */     int upperClipBound = g.getClipSpace().getY() + g.getClipSpace().getHeight();
/*     */     
/* 155 */     int lowerContentBound = getWidget().getDisplayY();
/* 156 */     int upperContentBound = lowerContentBound + (this.cellHeight + this.cellSpacing) * model.getRowCount();
/*     */     
/* 158 */     int row = (upperContentBound - upperClipBound) / (this.cellHeight + this.cellSpacing);
/*     */ 
/*     */     
/* 161 */     if (row < 0)
/*     */     {
/* 163 */       row = 0;
/*     */     }
/*     */     
/* 166 */     if (row > model.getRowCount()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 172 */     y = getContentHeight() - (row + (this.tableHeaderVisible ? 2 : 1)) * (this.cellHeight + this.cellSpacing);
/*     */     
/* 174 */     while (y + lowerContentBound > lowerClipBound && row < model.getRowCount()) {
/*     */       
/* 176 */       x = 0;
/*     */ 
/*     */       
/* 179 */       if (this.table.isSelected(row)) {
/*     */         
/* 181 */         g.setColor(this.selectionColor);
/* 182 */         g.drawFilledRectangle(0, y, getContentWidth(), this.cellHeight);
/*     */       } 
/*     */ 
/*     */       
/* 186 */       for (int i = 0; i < model.getColumnCount(); i++) {
/*     */ 
/*     */         
/* 189 */         this.cellUnderlay.paint(g, x, y, this.table.getColumnWidth(i), this.cellHeight);
/*     */         
/* 191 */         g.setColor(this.textColor);
/*     */         
/* 193 */         Object value = model.getValue(row, i);
/*     */ 
/*     */         
/* 196 */         if (value != null) {
/* 197 */           ICellRenderer cellRenderer = this.table.getColumn(i).getCellRenderer();
/* 198 */           Dimension contentDimension = cellRenderer.getCellContentSize(value);
/* 199 */           if (contentDimension == null) {
/* 200 */             contentDimension = new Dimension(this.table.getColumnWidth(i), this.cellHeight);
/*     */           }
/* 202 */           int alignedX = x + this.table.getColumn(i).getEntryAlignment().alignX(this.table.getColumnWidth(i), contentDimension.getWidth());
/* 203 */           int alignedY = y + this.table.getColumn(i).getEntryAlignment().alignY(this.cellHeight, contentDimension.getHeight());
/* 204 */           cellRenderer.paint(g, value, alignedX, alignedY, this.table.getColumnWidth(i), this.cellHeight);
/*     */         } 
/*     */ 
/*     */         
/* 208 */         this.cellOverlay.paint(g, x, y, this.table.getColumnWidth(i), this.cellHeight);
/*     */ 
/*     */         
/* 211 */         if (this.gridVisible) {
/* 212 */           g.setColor(this.gridColor);
/*     */           
/* 214 */           g.drawLine(x, y, x + this.table.getColumnWidth(i), y);
/* 215 */           g.drawLine(x + this.table.getColumnWidth(i), y, x + this.table.getColumnWidth(i), y + this.cellHeight);
/* 216 */           g.drawLine(x + this.table.getColumnWidth(i), y + this.cellHeight, x, y + this.cellHeight);
/* 217 */           g.drawLine(x, y + this.cellHeight, x, y);
/*     */         } 
/*     */         
/* 220 */         x += this.table.getColumnWidth(i) + this.cellSpacing;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       row++;
/* 231 */       y -= this.cellHeight + this.cellSpacing;
/*     */     } 
/*     */ 
/*     */     
/* 235 */     if (this.tableHeaderVisible) {
/*     */ 
/*     */       
/* 238 */       header.headerY = g.getClipSpace().getHeight() - this.cellHeight - this.table.getY();
/*     */       
/* 240 */       g.setColor(this.headerBackgroundColor);
/* 241 */       g.drawFilledRectangle(0, header.headerY, getContentWidth(), this.font.getHeight());
/*     */       
/* 243 */       x = 0;
/* 244 */       g.setColor(this.headTextColor);
/*     */       
/* 246 */       for (int i = 0; i < model.getColumnCount(); i++) {
/*     */         
/* 248 */         String s = model.getColumnName(i);
/* 249 */         int columnWidth = this.table.getColumnWidth(i);
/*     */         
/* 251 */         s = this.font.confineLength(s, columnWidth - 5);
/*     */         
/* 253 */         int entryOffset = 5 + this.table.getColumn(i).getHeaderAlignment().alignX(columnWidth - 5, this.font.getWidth(s));
/*     */         
/* 255 */         g.setFont(this.font);
/* 256 */         g.drawString(s, x + entryOffset, header.headerY);
/* 257 */         x += columnWidth + this.cellSpacing;
/*     */       } 
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
/*     */   public void setGridColor(Color gridColor) {
/* 288 */     this.gridColor = gridColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 297 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 307 */     this.font = font;
/* 308 */     this.cellHeight = font.getHeight();
/* 309 */     getWidget().updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 314 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getHeadTextColor() {
/* 319 */     return this.headTextColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeadTextColor(Color headTextColor) {
/* 324 */     this.headTextColor = headTextColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getSelectionColor() {
/* 329 */     return this.selectionColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectionColor(Color selectionColor) {
/* 334 */     this.selectionColor = selectionColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getGridColor() {
/* 339 */     return this.gridColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/* 344 */     return this.textColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCellHeight() {
/* 349 */     return this.cellHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCellHeight(int cellHeight) {
/* 354 */     this.cellHeight = cellHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGridVisible(boolean gridVisible) {
/* 363 */     this.gridVisible = gridVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTableHeadVisible() {
/* 369 */     return this.tableHeaderVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeaderVisible(boolean drawTableHead) {
/* 380 */     this.tableHeaderVisible = drawTableHead;
/* 381 */     getWidget().updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCellSpacing() {
/* 389 */     return this.cellSpacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCellSpacing(int cellSpacing) {
/* 397 */     this.cellSpacing = cellSpacing;
/* 398 */     getWidget().updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecoratorLayer getCellOverlay() {
/* 406 */     return this.cellOverlay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecoratorLayer getCellUnderlay() {
/* 414 */     return this.cellUnderlay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getHeaderBackgroundColor() {
/* 422 */     return this.headerBackgroundColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeaderBackgroundColor(Color headerBackgroundColor) {
/* 430 */     this.headerBackgroundColor = headerBackgroundColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnMinWidth() {
/* 435 */     return 15;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\table\TableAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */