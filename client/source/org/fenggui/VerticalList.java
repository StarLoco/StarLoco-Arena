/*     */ package org.fenggui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.render.Font;
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
/*     */ public class VerticalList<E>
/*     */   extends StandardWidget
/*     */   implements INotLayoutableWidget
/*     */ {
/*  34 */   private ArrayList<ListItem<E>> items = new ArrayList<ListItem<E>>();
/*  35 */   private ToggableGroup<E> toggableWidgetGroup = null;
/*     */   
/*  37 */   private ArrayList<Integer> columnWidth = new ArrayList<Integer>();
/*  38 */   private VerticalListAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalList() {
/*  45 */     this(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<ListItem<E>> getItems() {
/*  50 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalList(int selectionType) {
/*  59 */     this.toggableWidgetGroup = new ToggableGroup<E>(selectionType);
/*  60 */     this.appearance = new VerticalListAppearance<E>(this);
/*  61 */     setupTheme(VerticalList.class);
/*  62 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnWidth(int i) {
/*  68 */     return ((Integer)this.columnWidth.get(i)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public VerticalListAppearance getAppearance() {
/*  73 */     return this.appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItem(ListItem<E> li) {
/*  78 */     this.items.add(li);
/*  79 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItem(String text) {
/*  84 */     addItem(new ListItem<E>(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth(int proposedHeight) {
/*  89 */     Font font = getAppearance().getFont();
/*  90 */     int x = 0;
/*  91 */     int y = getAppearance().getContentHeight() + font.getHeight();
/*     */     
/*  93 */     int currentMax = 0;
/*  94 */     int tmp = 0;
/*     */     
/*  96 */     for (ListItem<E> item : this.items) {
/*     */       
/*  98 */       tmp = font.getWidth(item.getText());
/*     */       
/* 100 */       if (tmp > currentMax) currentMax = tmp;
/*     */       
/* 102 */       y -= font.getHeight();
/*     */       
/* 104 */       if (y <= 0) {
/*     */         
/* 106 */         x += currentMax + 10;
/* 107 */         currentMax = 0;
/* 108 */         y = getAppearance().getContentHeight() - font.getHeight();
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return x + currentMax + 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mp) {
/* 119 */     Font font = getAppearance().getFont();
/*     */     
/* 121 */     int mx = mp.getDisplayX() - getDisplayX();
/* 122 */     int my = mp.getDisplayY() - getDisplayY();
/*     */     
/* 124 */     int x = 0;
/* 125 */     int y = getAppearance().getContentHeight() + font.getHeight();
/*     */     
/* 127 */     int columnCounter = 0;
/*     */     
/* 129 */     for (ListItem<E> item : this.items) {
/*     */       
/* 131 */       if (y <= my && y + font.getHeight() >= my && 
/* 132 */         x <= mx && x + ((Integer)this.columnWidth.get(columnCounter)).intValue() >= mx) {
/*     */         
/* 134 */         this.toggableWidgetGroup.setSelected(item, true);
/* 135 */         item.setSelected(true);
/*     */       } 
/*     */       
/* 138 */       y -= font.getHeight();
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (y <= 0) {
/*     */         
/* 144 */         x += ((Integer)this.columnWidth.get(columnCounter)).intValue();
/* 145 */         columnCounter++;
/* 146 */         y = getAppearance().getContentHeight() - font.getHeight();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 156 */     Font font = getAppearance().getFont();
/* 157 */     int x = 0;
/* 158 */     int y = getAppearance().getContentHeight() + font.getHeight();
/*     */     
/* 160 */     int currentMax = 0;
/* 161 */     int tmp = 0;
/* 162 */     this.columnWidth.clear();
/* 163 */     for (ListItem<E> item : this.items) {
/*     */       
/* 165 */       tmp = font.getWidth(item.getText());
/*     */       
/* 167 */       if (tmp > currentMax) currentMax = tmp;
/*     */       
/* 169 */       y -= font.getHeight();
/*     */       
/* 171 */       if (y <= 0) {
/*     */         
/* 173 */         x += currentMax + 10;
/* 174 */         this.columnWidth.add(Integer.valueOf(currentMax + 10));
/* 175 */         currentMax = 0;
/* 176 */         y = getAppearance().getContentHeight() - font.getHeight();
/*     */       } 
/*     */     } 
/* 179 */     this.columnWidth.add(Integer.valueOf(currentMax + 10));
/*     */   }
/*     */ 
/*     */   
/*     */   public void heightHint(int height) {
/* 184 */     Font font = getAppearance().getFont();
/* 185 */     int x = 0;
/* 186 */     int y = getAppearance().getContentHeight() + font.getHeight();
/*     */     
/* 188 */     int currentMax = 0;
/* 189 */     int tmp = 0;
/*     */     
/* 191 */     for (ListItem<E> item : this.items) {
/*     */       
/* 193 */       tmp = font.getWidth(item.getText());
/*     */       
/* 195 */       if (tmp > currentMax) currentMax = tmp;
/*     */       
/* 197 */       y -= font.getHeight();
/*     */       
/* 199 */       if (y <= 0) {
/*     */         
/* 201 */         x += currentMax + 10;
/* 202 */         currentMax = 0;
/* 203 */         y = getAppearance().getContentHeight() - font.getHeight();
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     setMinSize(x + currentMax, height);
/*     */   }
/*     */   
/*     */   public void widthHint(int width) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\VerticalList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */