/*     */ package org.fenggui.table;
/*     */ 
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.render.Binding;
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
/*     */ public class Table
/*     */   extends StandardWidget
/*     */ {
/*  43 */   private TableAppearance appearance = null;
/*     */ 
/*     */ 
/*     */   
/*  47 */   private ITableModel model = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private TableColumn[] columns = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private boolean[] selected = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private int selectionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean multipleSelection = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readOnly = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private HeaderControl header = new HeaderControl();
/*     */   
/*     */   class HeaderControl
/*     */   {
/*     */     int headerY;
/*  86 */     int mouseX = 0;
/*  87 */     int columnResizeIndex = 0;
/*  88 */     int columnWidthBuffer = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HeaderControl getHeader() {
/*  93 */     return this.header;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Table() {
/* 102 */     this.appearance = new TableAppearance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseDraggedEvent mp) {
/* 110 */     if (getAppearance().isTableHeadVisible())
/*     */     {
/* 112 */       if (this.header.columnResizeIndex > -1) {
/*     */         
/* 114 */         int newWidth = this.header.columnWidthBuffer + mp.getDisplayX() - this.header.mouseX;
/* 115 */         int sum = getColumnWidth(this.header.columnResizeIndex) + getColumnWidth(this.header.columnResizeIndex + 1);
/*     */ 
/*     */         
/* 118 */         if (newWidth < getAppearance().getColumnMinWidth() || sum - newWidth < getAppearance().getColumnMinWidth()) {
/*     */           return;
/*     */         }
/* 121 */         this.columns[this.header.columnResizeIndex].setWidth(newWidth);
/* 122 */         this.columns[this.header.columnResizeIndex + 1].setWidth(sum - newWidth);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 130 */     if (this.header.columnResizeIndex > -1) {
/*     */       
/* 132 */       this.header.columnResizeIndex = -1;
/* 133 */       Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 140 */     if (!getAppearance().isTableHeadVisible()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 145 */     int widgetY = displayY - getDisplayY();
/*     */     
/* 147 */     if (this.header.headerY + getAppearance().getCellHeight() - widgetY < getAppearance().getCellHeight()) {
/*     */       
/* 149 */       int column = isOnColumn(displayX - getDisplayX());
/* 150 */       if (column >= 0) {
/*     */         
/* 152 */         if (this.header.columnResizeIndex <= -1)
/* 153 */           Binding.getInstance().getCursorFactory().getHorizontalResizeCursor().show(); 
/* 154 */         this.header.columnResizeIndex = column;
/*     */         return;
/*     */       } 
/* 157 */       if (this.header.columnResizeIndex > -1)
/*     */       {
/* 159 */         this.header.columnResizeIndex = -1;
/* 160 */         Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/*     */       }
/*     */     
/* 163 */     } else if (this.header.columnResizeIndex > -1) {
/*     */       
/* 165 */       this.header.columnResizeIndex = -1;
/* 166 */       Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mp) {
/* 174 */     if (getAppearance().isTableHeadVisible())
/*     */     {
/* 176 */       if (this.header.columnResizeIndex > -1) {
/*     */         
/* 178 */         this.header.mouseX = mp.getDisplayX();
/* 179 */         this.header.columnWidthBuffer = getColumnWidth(this.header.columnResizeIndex);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 185 */     if (getAppearance().isTableHeadVisible() && mp.getDisplayY() - getDisplayY() >= this.header.headerY) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (this.readOnly) {
/*     */       return;
/*     */     }
/* 194 */     assertSelectionArraySize();
/*     */     
/* 196 */     int mouseY = getDisplayY() + getAppearance().getContentHeight() - mp.getDisplayY();
/* 197 */     mouseY += getAppearance().getCellSpacing();
/*     */     
/* 199 */     int row = mouseY / (getAppearance().getCellHeight() + getAppearance().getCellSpacing());
/*     */ 
/*     */     
/* 202 */     if (getAppearance().isTableHeadVisible()) {
/* 203 */       row--;
/*     */     }
/*     */     
/* 206 */     if (row < 0 || row >= this.selected.length) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 211 */     if (!this.selected[row]) {
/* 212 */       this.selectionCount++;
/*     */     } else {
/* 214 */       this.selectionCount--;
/*     */     } 
/* 216 */     if (this.multipleSelection) {
/*     */       
/* 218 */       this.selected[row] = !this.selected[row];
/*     */     }
/*     */     else {
/*     */       
/* 222 */       clearSelection();
/* 223 */       this.selected[row] = !this.selected[row];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(int index, boolean b) {
/* 229 */     assertModel();
/* 230 */     assertSelectionArraySize();
/*     */ 
/*     */     
/* 233 */     if (index < 0 || index >= this.selected.length) {
/*     */       return;
/*     */     }
/* 236 */     if (this.multipleSelection) {
/*     */ 
/*     */       
/* 239 */       if (this.selected[index] != b) {
/*     */ 
/*     */         
/* 242 */         this.selected[index] = b;
/*     */         
/* 244 */         if (b) {
/* 245 */           this.selectionCount++;
/*     */         } else {
/* 247 */           this.selectionCount--;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 252 */       clearSelection();
/* 253 */       this.selected[index] = b;
/* 254 */       this.selectionCount = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModel(ITableModel m) {
/* 260 */     this.columns = new TableColumn[m.getColumnCount()];
/*     */     
/* 262 */     this.selected = new boolean[m.getRowCount()];
/*     */     
/* 264 */     for (int i = 0; i < this.columns.length; i++)
/*     */     {
/* 266 */       this.columns[i] = new TableColumn(m.getColumnName(i));
/*     */     }
/*     */     
/* 269 */     this.model = m;
/*     */     
/* 271 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public ITableModel getModel() {
/* 276 */     return this.model;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelected(int row) {
/* 281 */     assertModel();
/*     */     
/* 283 */     if (row >= 0 && row < this.selected.length) {
/* 284 */       return this.selected[row];
/*     */     }
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelectionCount() {
/* 291 */     return this.selectionCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void distributeColumnWidthsEqually() {
/* 296 */     if (this.model == null) {
/* 297 */       throw new IllegalStateException("The table has no model!");
/*     */     }
/* 299 */     for (int i = 0; i < this.columns.length; i++)
/*     */     {
/* 301 */       this.columns[i].setWidth(getAppearance().getContentWidth() / this.columns.length);
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
/*     */   public void setColumnWidth(int columnIndex, int widthInPixel) {
/* 315 */     getColumn(columnIndex).setWidth(widthInPixel);
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
/*     */   public void setColumnWidth(int columnIndex, float relativeWidth) {
/* 328 */     getColumn(columnIndex).setRelativeWidth(relativeWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMultipleSelection() {
/* 336 */     return this.multipleSelection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultipleSelection(boolean multipleSelection) {
/* 345 */     this.multipleSelection = multipleSelection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSelection() {
/* 354 */     for (int i = 0; i < this.selected.length; i++)
/*     */     {
/* 356 */       this.selected[i] = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 365 */     if (this.model != null && this.columns.length > 0 && getColumnWidth(0) == -1)
/*     */     {
/* 367 */       distributeColumnWidthsEqually();
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
/*     */   public int getSelection() {
/* 380 */     assertModel();
/*     */     
/* 382 */     for (int i = 0; i < this.selected.length; i++) {
/*     */       
/* 384 */       if (this.selected[i])
/* 385 */         return i; 
/*     */     } 
/* 387 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 397 */     this.readOnly = readOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public TableColumn getColumn(int columnIndex) {
/* 402 */     assertModel();
/*     */     
/* 404 */     return this.columns[columnIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int isOnColumn(int x) {
/* 410 */     double sum = 0.0D;
/* 411 */     for (int col = 0; col < this.columns.length - 1; col++) {
/*     */       
/* 413 */       sum += (getColumnWidth(col) + getAppearance().getCellSpacing());
/* 414 */       if (Math.abs(sum - x) < 5.0D) {
/* 415 */         return col;
/*     */       }
/*     */     } 
/* 418 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assertSelectionArraySize() {
/* 423 */     if (this.selected.length == this.model.getRowCount()) {
/*     */       return;
/*     */     }
/* 426 */     boolean[] newSelected = new boolean[this.model.getRowCount()];
/*     */     
/* 428 */     for (int i = 0; i < this.selected.length && i < newSelected.length; i++)
/*     */     {
/* 430 */       newSelected[i] = this.selected[i];
/*     */     }
/*     */     
/* 433 */     this.selected = newSelected;
/*     */   }
/*     */ 
/*     */   
/*     */   public TableAppearance getAppearance() {
/* 438 */     return this.appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assertModel() {
/* 443 */     if (this.model == null) {
/* 444 */       throw new IllegalStateException("No table model set!");
/*     */     }
/*     */   }
/*     */   
/*     */   public int getColumnWidth(int columnIndex) {
/* 449 */     TableColumn column = this.columns[columnIndex];
/* 450 */     if (column != null) {
/*     */       
/* 452 */       if (column.isRelative())
/*     */       {
/* 454 */         return (int)(getAppearance().getContentWidth() * column.getRelativeWidth());
/*     */       }
/*     */       
/* 457 */       return column.getWidth();
/*     */     } 
/* 459 */     return 0;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\table\Table.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */