/*     */ package com.ankamagames.graphics.isometric.lines;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightedElement;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinesLayer
/*     */ {
/*  18 */   private TIntObjectHashMap<Line> m_lines = new TIntObjectHashMap();
/*  19 */   private TIntArrayList m_lineToRemove = new TIntArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/*  28 */     this.m_lines.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeLine(int id)
/*     */   {
/*  37 */     this.m_lineToRemove.add(id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int getNextFreeId()
/*     */   {
/*  44 */     int id = 0;
/*  45 */     while (this.m_lines.contains(id))
/*  46 */       id++;
/*  47 */     return id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Line createNewLine()
/*     */   {
/*  56 */     int id = getNextFreeId();
/*  57 */     return createNewLine(id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Line createNewLine(int id)
/*     */   {
/*  67 */     Line line = new Line(id);
/*  68 */     this.m_lines.put(id, line);
/*  69 */     return line;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void putLine(Line line)
/*     */   {
/*  77 */     this.m_lines.put(line.getId(), line);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Line getLine(int id)
/*     */   {
/*  86 */     return (Line)this.m_lines.get(id);
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
/*     */   public void prepareElementBeforeRendering(IsoWorldScene scene, HighLightedElement displayedElement) {}
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
/*     */   public void resetMeshIterator()
/*     */   {
/* 123 */     TIntObjectIterator<Line> iterator = this.m_lines.iterator();
/* 124 */     for (int i = this.m_lines.size(); i-- > 0;) {
/* 125 */       iterator.advance();
/* 126 */       Line line = (Line)iterator.value();
/* 127 */       line.resetMeshIterator();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lines\LinesLayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */