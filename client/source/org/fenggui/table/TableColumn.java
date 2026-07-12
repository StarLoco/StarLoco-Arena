/*     */ package org.fenggui.table;
/*     */ 
/*     */ import org.fenggui.layout.Alignment;
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
/*     */ public class TableColumn
/*     */ {
/*  32 */   private ICellRenderer cellRenderer = new TextCellRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private String name = "---";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private int width = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private float relativeWidth = -1.0F;
/*     */   
/*  49 */   private Alignment headingAlignment = Alignment.MIDDLE;
/*  50 */   private Alignment entryAlignment = Alignment.LEFT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableColumn(String name) {
/*  60 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableColumn(String name, int width) {
/*  71 */     this.name = name;
/*  72 */     setWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableColumn(String name, float relativeWidth) {
/*  83 */     this.name = name;
/*  84 */     setRelativeWidth(relativeWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  94 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 104 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 109 */     this.width = width;
/* 110 */     this.relativeWidth = -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRelativeWidth() {
/* 120 */     return this.relativeWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRelativeWidth(float relativeWidth) {
/* 129 */     relativeWidth = Math.max(0.0F, Math.min(1.0F, relativeWidth));
/* 130 */     this.relativeWidth = relativeWidth;
/* 131 */     this.width = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/* 140 */     return (this.relativeWidth != -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Alignment getHeaderAlignment() {
/* 145 */     return this.headingAlignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeaderAlignment(Alignment headingAlignment) {
/* 150 */     if (headingAlignment == null)
/*     */       return; 
/* 152 */     this.headingAlignment = headingAlignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public Alignment getEntryAlignment() {
/* 157 */     return this.entryAlignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntryAlignment(Alignment valueAlignment) {
/* 162 */     if (valueAlignment == null)
/*     */       return; 
/* 164 */     this.entryAlignment = valueAlignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public ICellRenderer getCellRenderer() {
/* 169 */     return this.cellRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCellRenderer(ICellRenderer cellRenderer) {
/* 174 */     if (cellRenderer == null) throw new IllegalArgumentException("cellRenderer == null");
/*     */     
/* 176 */     this.cellRenderer = cellRenderer;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\table\TableColumn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */