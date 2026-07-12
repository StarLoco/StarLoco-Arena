/*     */ package org.fenggui.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IToggable;
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
/*     */ public class Record<E>
/*     */   implements IToggable<E>
/*     */ {
/*  29 */   private E node = null;
/*  30 */   private ArrayList<Record<E>> children = new ArrayList<Record<E>>();
/*  31 */   public int row = 0;
/*     */   private boolean isExpandable = false;
/*     */   private boolean isSelected = false;
/*  34 */   private ITreeModel<E> model = null;
/*  35 */   private int offset = 0;
/*     */ 
/*     */   
/*     */   public Record(ITreeModel<E> model, E node) {
/*  39 */     this.node = node;
/*  40 */     this.model = model;
/*  41 */     if (node == null) throw new IllegalArgumentException("node == null");
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffset() {
/*  47 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public Record<E> getChild(int index) {
/*  52 */     return this.children.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllChildren() {
/*  57 */     this.children.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChild(Record<E> r) {
/*  62 */     this.children.add(r);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOffset(int offset) {
/*  67 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpandable(boolean isExpandable) {
/*  73 */     this.isExpandable = isExpandable;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberOfChildren() {
/*  78 */     return this.children.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Record<E>> getChildren() {
/*  83 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExpandable() {
/*  89 */     return this.isExpandable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E getNode() {
/*  95 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/* 101 */     return this.isSelected;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IToggable setSelected(boolean b) {
/* 107 */     this.isSelected = b;
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E getValue() {
/* 114 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 120 */     return this.model.getText(this.node);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\tree\Record.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */