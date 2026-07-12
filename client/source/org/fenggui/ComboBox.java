/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.event.ISelectionChangedListener;
/*     */ import org.fenggui.event.SelectionChangedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Pixmap;
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
/*     */ public class ComboBox<E>
/*     */   extends StandardWidget
/*     */   implements IBasicContainer
/*     */ {
/*  38 */   private Pixmap pixmap = null;
/*     */   private Label label;
/*  40 */   private ComboBoxAppearance appearance = null;
/*  41 */   private List<E> list = null;
/*  42 */   private ScrollContainer popupContainer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap pixmap) {
/*  49 */     this.pixmap = pixmap;
/*  50 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public ScrollContainer getPopupContainer() {
/*  55 */     return this.popupContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ComboBoxAppearance getAppearance() {
/*  60 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPixmap() {
/*  68 */     return this.pixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label getLabel() {
/*  77 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<E> getList() {
/*  86 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComboBox() {
/*  95 */     this.appearance = new ComboBoxAppearance(this);
/*     */     
/*  97 */     this.label = new Label();
/*  98 */     this.label.setParent(this);
/*  99 */     this.label.setText("Space holder....");
/*     */     
/* 101 */     this.popupContainer = new ScrollContainer();
/*     */     
/* 103 */     this.list = new List<E>(ToggableGroup.SINGLE_SELECTION);
/* 104 */     this.popupContainer.setInnerWidget(this.list);
/*     */     
/* 106 */     addSelectionChangedListener(getPopupHandler());
/* 107 */     this.appearance = new ComboBoxAppearance(this);
/* 108 */     setupTheme(ComboBox.class);
/* 109 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private ISelectionChangedListener getPopupHandler() {
/* 114 */     return new ISelectionChangedListener()
/*     */       {
/*     */         
/*     */         public void selectionChanged(SelectionChangedEvent e)
/*     */         {
/* 119 */           if (!e.isSelected())
/*     */             return; 
/* 121 */           if (ComboBox.this.getDisplay() != null)
/*     */           {
/* 123 */             ComboBox.this.getDisplay().removePopup();
/*     */           }
/*     */           
/* 126 */           ComboBox.this.getLabel().setText(ComboBox.this.list.getToggableWidgetGroup().getSelectedItem().getText());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSelectionChangedListener(ISelectionChangedListener l) {
/* 133 */     this.list.getToggableWidgetGroup().addSelectionChangedListener(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeSelectionChangedListener(ISelectionChangedListener l) {
/* 138 */     this.list.getToggableWidgetGroup().removeSelectionChangedListener(l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 144 */     int height = getAppearance().getContentHeight();
/* 145 */     int width = getAppearance().getContentWidth();
/*     */     
/* 147 */     int pixmapWidth = 0;
/*     */     
/* 149 */     if (this.pixmap != null) pixmapWidth = this.pixmap.getWidth();
/*     */     
/* 151 */     this.label.setSize(width - pixmapWidth, height);
/*     */     
/* 153 */     this.label.setXY(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 160 */     setMinSize(getAppearance().getMinSizeHint());
/*     */     
/* 162 */     if (getParent() != null) getParent().updateMinSize();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(ListItem<E> item) {
/* 172 */     if (this.list.isEmpty()) {
/* 173 */       this.list.addItem(item);
/*     */       
/* 175 */       this.list.setSelectedIndex(0, true);
/*     */     } else {
/* 177 */       this.list.addItem(item);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(String s) {
/* 188 */     for (ListItem<E> item : this.list.getItems()) {
/*     */       
/* 190 */       if (s.equals(item.getText())) {
/*     */         
/* 192 */         setSelected(item);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(IToggable<E> item) {
/* 204 */     if (!this.list.getItems().contains(item)) {
/*     */       return;
/*     */     }
/* 207 */     this.list.getToggableWidgetGroup().setSelected(item, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedIndex(int index, boolean selected) {
/* 217 */     this.list.setSelectedIndex(index, selected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(String s) {
/* 226 */     addItem(new ListItem<E>(s));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void openPopup() {
/* 234 */     this.list.updateMinSize();
/* 235 */     this.list.setSizeToMinSize();
/*     */     
/* 237 */     int displayY = getDisplayY();
/* 238 */     int displayX = getDisplayX();
/*     */     
/* 240 */     int horMargins = this.popupContainer.getAppearance().getLeftMargins() + 
/* 241 */       this.popupContainer.getAppearance().getRightMargins();
/*     */     
/* 243 */     int verMargins = this.popupContainer.getAppearance().getTopMargins() + 
/* 244 */       this.popupContainer.getAppearance().getBottomMargins();
/*     */     
/* 246 */     if (displayY - this.list.getHeight() < 0) {
/* 247 */       this.popupContainer.setHeight(displayY + verMargins);
/*     */     } else {
/* 249 */       this.popupContainer.setHeight(this.list.getHeight() + verMargins);
/*     */     } 
/* 251 */     this.popupContainer.setWidth(Math.max(this.list.getWidth(), getWidth()) + horMargins);
/*     */     
/* 253 */     this.popupContainer.layout();
/*     */     
/* 255 */     this.popupContainer.setX(displayX);
/* 256 */     this.popupContainer.setY(displayY - this.popupContainer.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     Thread t = new Thread() { public void run() {
/*     */           
/* 265 */           try { sleep(50L); } catch (InterruptedException interruptedException) {}
/* 266 */           ComboBox.this.getDisplay().displayPopUp(ComboBox.this.popupContainer);
/*     */         } }
/*     */       ;
/*     */     
/* 270 */     t.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 276 */     if (this.list == null || !this.list.isInWidgetTree()) {
/* 277 */       openPopup();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 283 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 288 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 293 */     return getParent().getNextWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 298 */     return getParent().getPreviousWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 307 */     super.process(stream);
/*     */     
/* 309 */     stream.processInherentChild("List", this.list);
/* 310 */     stream.processInherentChild("Label", this.label);
/* 311 */     stream.processInherentChild("PopupContainer", this.popupContainer);
/*     */     
/* 313 */     addSelectionChangedListener(getPopupHandler());
/*     */     
/* 315 */     setPixmap((Pixmap)stream.processChild("Pixmap", (IOStreamSaveable)getPixmap(), null, Pixmap.class));
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */