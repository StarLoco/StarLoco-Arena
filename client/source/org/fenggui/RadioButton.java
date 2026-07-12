/*     */ package org.fenggui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ import org.fenggui.event.SelectionChangedEvent;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
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
/*     */ public class RadioButton<E>
/*     */   extends ObservableLabelWidget
/*     */   implements IToggable<E>
/*     */ {
/*  38 */   private ToggableGroup<E> radioButtonGroup = null;
/*  39 */   private E value = null;
/*     */   private boolean selected = false;
/*  41 */   private ArrayList<ISelectionChangedListener> selectionChangedHook = new ArrayList<ISelectionChangedListener>();
/*     */   
/*     */   public static final String LABEL_SELECTED = "selected";
/*     */   
/*     */   public static final String LABEL_DEFAULT = "default";
/*     */   public static final String LABEL_DISABLED = "disabled";
/*     */   
/*     */   public RadioButton(String text, ToggableGroup<E> group, E data) {
/*  49 */     setRadioButtonGroup(group);
/*     */     
/*  51 */     setValue(data);
/*     */     
/*  53 */     buildLogic();
/*     */     
/*  55 */     setupTheme(RadioButton.class);
/*  56 */     getAppearance().setEnabled("default", true);
/*  57 */     getAppearance().setEnabled("disabled", false);
/*  58 */     getAppearance().setEnabled("selected", false);
/*  59 */     setText(text);
/*     */   }
/*     */ 
/*     */   
/*     */   void buildLogic() {
/*  64 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mousePressedEvent)
/*     */           {
/*  68 */             RadioButton.this.setSelected(true);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public RadioButton(String text, ToggableGroup<E> group) {
/*  74 */     this(text, group, (E)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public RadioButton(ToggableGroup<E> group) {
/*  79 */     this((String)null, group, (E)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public RadioButton() {
/*  84 */     this("", (ToggableGroup<E>)null, (E)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public RadioButton(String text) {
/*  89 */     this(text, (ToggableGroup<E>)null, (E)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/*  94 */     return this.selected;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireSelectionChangedEvent(boolean b) {
/*  99 */     SelectionChangedEvent e = new SelectionChangedEvent(this, this, b);
/* 100 */     for (ISelectionChangedListener l : this.selectionChangedHook)
/*     */     {
/* 102 */       l.selectionChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public RadioButton setSelected(boolean s) {
/* 108 */     if (s) {
/*     */       
/* 110 */       this.radioButtonGroup.setSelected(this, true);
/* 111 */       fireSelectionChangedEvent(s);
/* 112 */       getAppearance().setEnabled("default", false);
/* 113 */       getAppearance().setEnabled("selected", true);
/*     */     }
/*     */     else {
/*     */       
/* 117 */       getAppearance().setEnabled("selected", false);
/* 118 */       getAppearance().setEnabled("default", true);
/* 119 */       fireSelectionChangedEvent(s);
/*     */     } 
/*     */     
/* 122 */     this.selected = s;
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ToggableGroup<E> getRadioButtonGroup() {
/* 128 */     return this.radioButtonGroup;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadioButtonGroup(ToggableGroup<E> radioButtonGroup) {
/* 133 */     this.radioButtonGroup = radioButtonGroup;
/* 134 */     if (isSelected()) radioButtonGroup.setSelected(this, isSelected());
/*     */   
/*     */   }
/*     */   
/*     */   public E getValue() {
/* 139 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(E value) {
/* 144 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSelectionChangedListener(ISelectionChangedListener l) {
/* 149 */     this.selectionChangedHook.add(l);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\RadioButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */