/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.IActivationListener;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ import org.fenggui.event.SelectionChangedEvent;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
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
/*     */ public class CheckBox<E>
/*     */   extends ObservableLabelWidget
/*     */   implements IToggable<E>
/*     */ {
/*  47 */   private ArrayList<ISelectionChangedListener> selectionChangedHook = new ArrayList<ISelectionChangedListener>();
/*     */   
/*  49 */   private E value = null;
/*     */   
/*     */   private boolean selected = false;
/*     */   
/*     */   public static final String LABEL_SELECTED = "selected";
/*     */   
/*     */   public static final String LABEL_DISABLED = "disabled";
/*     */   
/*     */   public static final String LABEL_DEFAULT = "default";
/*     */ 
/*     */   
/*     */   public CheckBox() {
/*  61 */     this("");
/*     */   }
/*     */ 
/*     */   
/*     */   void buildLogic() {
/*  66 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mousePressedEvent)
/*     */           {
/*  70 */             CheckBox.this.setSelected(!CheckBox.this.isSelected());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  75 */     addActivationListener(new IActivationListener()
/*     */         {
/*     */           public void widgetActivationChanged(ActivationEvent e)
/*     */           {
/*  79 */             if (e.isEnabled()) {
/*     */               
/*  81 */               if (CheckBox.this.selected) { CheckBox.this.getAppearance().setEnabled("selected", true); }
/*  82 */               else { CheckBox.this.getAppearance().setEnabled("selected", false); }
/*     */             
/*     */             } else {
/*     */               
/*  86 */               CheckBox.this.getAppearance().setEnabled("disabled", true);
/*  87 */               CheckBox.this.getAppearance().setEnabled("default", false);
/*     */             } 
/*     */           }
/*     */         });
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
/*     */   public CheckBox(String text) {
/* 107 */     buildLogic();
/*     */     
/* 109 */     setupTheme(CheckBox.class);
/* 110 */     getAppearance().setEnabled("default", true);
/* 111 */     getAppearance().setEnabled("disabled", false);
/* 112 */     getAppearance().setEnabled("selected", false);
/*     */     
/* 114 */     setText(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/* 124 */     return this.selected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IToggable setSelected(boolean b) {
/* 132 */     getAppearance().setEnabled("default", true);
/*     */     
/* 134 */     fireSelectionChangedEvent(this, this, b);
/*     */     
/* 136 */     if (b) { getAppearance().setEnabled("selected", true); }
/* 137 */     else { getAppearance().setEnabled("selected", false); }
/*     */     
/* 139 */     this.selected = b;
/*     */     
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E getValue() {
/* 150 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(E value) {
/* 159 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSelectionChangedListener(ISelectionChangedListener l) {
/* 168 */     if (!this.selectionChangedHook.contains(l))
/*     */     {
/* 170 */       this.selectionChangedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelectionChangedListener(ISelectionChangedListener l) {
/* 180 */     this.selectionChangedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireSelectionChangedEvent(IWidget source, IToggable t, boolean s) {
/* 191 */     SelectionChangedEvent e = new SelectionChangedEvent(source, t, s);
/*     */     
/* 193 */     for (ISelectionChangedListener l : this.selectionChangedHook)
/*     */     {
/* 195 */       l.selectionChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 202 */     super.process(stream);
/*     */     
/* 204 */     this.selected = stream.processAttribute("selected", this.selected, false);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\CheckBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */