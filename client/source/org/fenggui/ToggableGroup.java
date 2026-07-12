/*     */ package org.fenggui;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ import org.fenggui.event.SelectionChangedEvent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToggableGroup<E>
/*     */ {
/*  46 */   public static int SINGLE_SELECTION = 1;
/*  47 */   public static int MULTIPLE_SELECTION = -1;
/*     */   
/*  49 */   private int numberOfSelectableItems = 1;
/*     */ 
/*     */   
/*  52 */   private ArrayList<IToggable<E>> selected = new ArrayList<IToggable<E>>();
/*  53 */   private ArrayList<ISelectionChangedListener> selectionChangedHook = new ArrayList<ISelectionChangedListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToggableGroup(int numberOfSelectableItems) {
/*  61 */     if (numberOfSelectableItems < -1) numberOfSelectableItems = -1; 
/*  62 */     if (numberOfSelectableItems == 0) numberOfSelectableItems = 1; 
/*  63 */     this.numberOfSelectableItems = numberOfSelectableItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToggableGroup() {
/*  72 */     this(SINGLE_SELECTION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfSelectableItems() {
/*  81 */     return this.numberOfSelectableItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumberOfSelectableItems(int numberOfSelectableItems) {
/*  91 */     this.numberOfSelectableItems = numberOfSelectableItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(IToggable<E> w, boolean b) {
/*  98 */     if (b) {
/*     */       
/* 100 */       for (int i = this.selected.size() - 1; i >= this.numberOfSelectableItems - 1; i--) {
/*     */         
/* 102 */         IToggable<E> s = this.selected.get(i);
/* 103 */         s.setSelected(false);
/* 104 */         this.selected.remove(i);
/* 105 */         fireSelectionChangedEvent(null, s, false);
/*     */       } 
/*     */       
/* 108 */       this.selected.add(0, w);
/*     */       
/* 110 */       fireSelectionChangedEvent(null, w, b);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IToggable<E> getSelectedItem() {
/* 121 */     if (this.selected.isEmpty()) return null; 
/* 122 */     return this.selected.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E getSelectedValue() {
/* 131 */     if (this.selected.isEmpty()) return null; 
/* 132 */     return ((IToggable<E>)this.selected.get(0)).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSelectedValues(List<E> toBeFilled) {
/* 141 */     for (int i = 0; i < this.selected.size(); i++) {
/*     */       
/* 143 */       IToggable<E> s = this.selected.get(i);
/* 144 */       toBeFilled.add(s.getValue());
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
/*     */   public E[] getSelectedValues(Class<?> returnType) {
/* 157 */     Object[] array = (Object[])Array.newInstance(returnType, this.selected.size());
/*     */     
/* 159 */     for (int i = 0; i < this.selected.size(); i++) {
/*     */       
/* 161 */       IToggable<E> s = this.selected.get(i);
/* 162 */       array[i] = s.getValue();
/*     */     } 
/*     */     
/* 165 */     return (E[])array;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireSelectionChangedEvent(IWidget source, IToggable t, boolean s) {
/* 170 */     SelectionChangedEvent e = new SelectionChangedEvent(source, t, s);
/*     */     
/* 172 */     for (ISelectionChangedListener l : this.selectionChangedHook)
/*     */     {
/* 174 */       l.selectionChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSelectionChangedListener(ISelectionChangedListener l) {
/* 180 */     this.selectionChangedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeSelectionChangedListener(ISelectionChangedListener l) {
/* 185 */     this.selectionChangedHook.remove(l);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ToggableGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */