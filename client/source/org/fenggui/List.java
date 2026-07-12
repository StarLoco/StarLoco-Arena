/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ public class List<E>
/*     */   extends StandardWidget
/*     */ {
/*  42 */   private ToggableGroup<E> toggableWidgetGroup = null;
/*  43 */   private ArrayList<ListItem<E>> items = new ArrayList<ListItem<E>>();
/*  44 */   private int mouseOverRow = -1;
/*  45 */   private ListAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List() {
/*  53 */     this(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List(int selectionType) {
/*  63 */     this.toggableWidgetGroup = new ToggableGroup<E>(selectionType);
/*     */     
/*  65 */     this.appearance = new ListAppearance(this);
/*  66 */     setupTheme(List.class);
/*  67 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ToggableGroup<E> getToggableWidgetGroup() {
/*  73 */     return this.toggableWidgetGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(ListItem<E> li) {
/*  79 */     this.items.add(li);
/*  80 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItem(String text) {
/*  85 */     addItem(new ListItem<E>(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public ListItem<E> getItem(int row) {
/*  90 */     return this.items.get(row);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeItem(int row) {
/*  95 */     removeItem(this.items.get(row));
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeItem(ListItem<E> item) {
/* 100 */     this.items.remove(item);
/* 101 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 106 */     this.items.clear();
/* 107 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mp) {
/* 113 */     int mouseY = mp.getDisplayY() - getDisplayY();
/*     */     
/* 115 */     int row = (getAppearance().getContentHeight() - mouseY) / getAppearance().getRowHeight();
/*     */     
/* 117 */     setSelectedIndex(row, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 124 */     super.process(stream);
/*     */     
/* 126 */     if (stream.startSubcontext("listItems")) {
/*     */       
/* 128 */       stream.processChildren("ListItem", this.items, ListItem.class);
/* 129 */       stream.endSubcontext();
/*     */     } 
/* 131 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 138 */     int mouseY = displayY - getDisplayY();
/*     */     
/* 140 */     this.mouseOverRow = (getAppearance().getContentHeight() - mouseY) / getAppearance().getRowHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 146 */     this.mouseOverRow = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 151 */     return this.items.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 156 */     return this.items.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedIndex(int index, boolean selected) {
/* 162 */     if (index < 0 || index >= this.items.size()) {
/*     */       return;
/*     */     }
/* 165 */     ListItem<E> item = this.items.get(index);
/* 166 */     this.toggableWidgetGroup.setSelected(item, selected);
/* 167 */     item.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<ListItem<E>> getItems() {
/* 172 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseOverRow() {
/* 177 */     return this.mouseOverRow;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListAppearance getAppearance() {
/* 182 */     return this.appearance;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\List.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */