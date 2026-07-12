/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.MappedByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.DisplayResizedEvent;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IDisplayResizedListener;
/*     */ import org.fenggui.event.IDragAndDropListener;
/*     */ import org.fenggui.event.IEventListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.KeyTypedEvent;
/*     */ import org.fenggui.event.mouse.MouseButton;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
/*     */ import org.fenggui.event.mouse.MouseWheelEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.layout.StaticLayout;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
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
/*     */ public class Display
/*     */   extends Container
/*     */ {
/*  65 */   private ArrayList<IDragAndDropListener> dndListeners = new ArrayList<IDragAndDropListener>();
/*  66 */   private ArrayList<IEventListener> globalEventListener = new ArrayList<IEventListener>();
/*     */   
/*  68 */   private IWidget mouseOverWidget = this;
/*  69 */   private Binding binding = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean depthTestEnabled = false;
/*     */ 
/*     */   
/*  76 */   private IDragAndDropListener draggingListener = null;
/*     */   
/*  78 */   private IWidget focusedWidget = null;
/*     */   
/*  80 */   private Widget popupWidget = null;
/*     */   
/*  82 */   private File screenshotFile = null;
/*     */   private static final int TARGA_HEADER_SIZE = 18;
/*     */   
/*     */   public Display() {
/*  86 */     this(Binding.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Display(Binding binding) {
/*  96 */     assert binding != null;
/*  97 */     this.binding = binding;
/*  98 */     setY(0);
/*  99 */     setX(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     setSize(binding.getCanvasWidth(), binding.getCanvasHeight());
/* 105 */     setLayoutManager((LayoutManager)new StaticLayout());
/* 106 */     binding.addDisplayResizedListener(new IDisplayResizedListener()
/*     */         {
/*     */           
/*     */           public void displayResized(DisplayResizedEvent displayResizedEvent)
/*     */           {
/* 111 */             Display.this.setSize(displayResizedEvent.getWidth(), displayResizedEvent.getHeight());
/* 112 */             Display.this.layout();
/*     */           }
/*     */         });
/*     */     
/* 116 */     setupTheme(Display.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 122 */     setLayoutManager((LayoutManager)stream
/* 123 */         .processChild(getLayoutManager(), FengGUI.TYPE_REGISTRY));
/* 124 */     stream.processChildren(this.notifyList, FengGUI.TYPE_REGISTRY);
/* 125 */     for (IWidget w : this.notifyList)
/*     */     {
/* 127 */       w.setParent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isInWidgetTree() {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayPopUp(Widget pus) {
/* 147 */     addWidget(pus);
/* 148 */     this.popupWidget = pus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePopup() {
/* 157 */     removeWidget(this.popupWidget);
/* 158 */     this.popupWidget = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Container getParent() {
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Display getDisplay() {
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display() {
/* 185 */     IOpenGL opengl = this.binding.getOpenGL();
/* 186 */     opengl.pushAllAttribs();
/*     */     
/* 188 */     opengl.activateTexture(0);
/*     */     
/* 190 */     opengl.setViewPort(0, 0, this.binding.getCanvasWidth(), this.binding.getCanvasHeight());
/*     */     
/* 192 */     opengl.setModelMatrixMode();
/* 193 */     opengl.pushMatrix();
/*     */     
/* 195 */     opengl.loadIdentity();
/*     */     
/* 197 */     opengl.setProjectionMatrixMode();
/* 198 */     opengl.pushMatrix();
/* 199 */     opengl.loadIdentity();
/*     */     
/* 201 */     opengl.setOrtho2D(0, this.binding.getCanvasWidth(), 0, this.binding.getCanvasHeight());
/*     */     
/* 203 */     opengl.setModelMatrixMode();
/*     */     
/* 205 */     opengl.setupStateVariables(this.depthTestEnabled);
/*     */ 
/*     */ 
/*     */     
/* 209 */     Graphics g = this.binding.getGraphics();
/* 210 */     g.resetTransformations();
/*     */     
/* 212 */     for (int i = 0; i < getContent().size(); i++) {
/*     */       
/* 214 */       IWidget c = getContent().get(i);
/*     */       
/* 216 */       if (c == null) {
/*     */ 
/*     */         
/* 219 */         System.err.println("NullPointerEx. prevention :( It is known a bug caused by multi threading!");
/*     */       }
/*     */       else {
/*     */         
/* 223 */         opengl.pushMatrix();
/*     */         
/* 225 */         clipWidget(g, c);
/*     */         
/* 227 */         g.translate(c.getX(), c.getY());
/*     */         
/* 229 */         c.paint(g);
/*     */         
/* 231 */         g.translate(-c.getX(), -c.getY());
/* 232 */         opengl.popMatrix();
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     opengl.setProjectionMatrixMode();
/*     */     
/* 238 */     opengl.popMatrix();
/*     */     
/* 240 */     opengl.setModelMatrixMode();
/* 241 */     opengl.popMatrix();
/*     */     
/* 243 */     opengl.popAllAttribs();
/*     */     
/* 245 */     if (this.screenshotFile != null) {
/*     */       
/* 247 */       screenshot(opengl, getWidth(), getHeight(), this.screenshotFile);
/* 248 */       this.screenshotFile = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getFocusedWidget() {
/* 257 */     return this.focusedWidget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocusedWidget(IWidget widget) {
/* 267 */     if (this.focusedWidget != null && !this.focusedWidget.equals(widget)) {
/*     */       
/* 269 */       FocusEvent e = new FocusEvent(this.focusedWidget, true);
/* 270 */       this.focusedWidget.focusChanged(e);
/* 271 */       fireGlobalEventListener((Event)e);
/*     */     } 
/*     */     
/* 274 */     this.focusedWidget = widget;
/*     */     
/* 276 */     if (widget != null) {
/*     */       
/* 278 */       FocusEvent e = new FocusEvent(widget, false);
/* 279 */       widget.focusChanged(e);
/* 280 */       fireGlobalEventListener((Event)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean grandParentIsPopupWidget(IWidget w) {
/* 286 */     if (w.getParent() == null) return false;
/*     */     
/* 288 */     if (w.getParent().equals(this.popupWidget)) return true;
/*     */     
/* 290 */     return grandParentIsPopupWidget(w.getParent());
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
/*     */   public boolean fireMousePressedEvent(int mouseX, int mouseY, MouseButton mouseButton, int clickCount) {
/* 304 */     IWidget w = getWidget(mouseX, mouseY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     IWidget toDeletePopupWidget = null;
/* 314 */     if (this.popupWidget != null && 
/* 315 */       !w.equals(this.popupWidget) && 
/* 316 */       !grandParentIsPopupWidget(w))
/*     */     {
/* 318 */       toDeletePopupWidget = this.popupWidget;
/*     */     }
/*     */ 
/*     */     
/* 322 */     boolean returnsValue = false;
/*     */     
/* 324 */     if (!w.equals(this)) {
/*     */       
/* 326 */       IWidget targetWidget = w;
/*     */       
/* 328 */       if (targetWidget.isTraversable() && !(targetWidget instanceof Container))
/*     */       {
/* 330 */         setFocusedWidget(targetWidget);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 335 */       MousePressedEvent e = new MousePressedEvent(w, mouseX, mouseY, mouseButton, clickCount);
/* 336 */       w.mousePressed(e);
/* 337 */       fireGlobalEventListener((Event)e);
/*     */       
/* 339 */       for (int i = 0; i < this.dndListeners.size(); i++) {
/*     */         
/* 341 */         IDragAndDropListener dndListener = this.dndListeners.get(i);
/* 342 */         if (dndListener.isDndWidget(w, mouseX, mouseY)) {
/*     */           
/* 344 */           dndListener.select(mouseX, mouseY);
/* 345 */           this.draggingListener = dndListener;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 350 */       while (w.getParent() != null && !w.getParent().equals(this)) {
/* 351 */         w = w.getParent();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 359 */       returnsValue = true;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 364 */       setFocusedWidget((IWidget)null);
/*     */     } 
/*     */     
/* 367 */     if (toDeletePopupWidget != null)
/*     */     {
/* 369 */       if (this.popupWidget.equals(toDeletePopupWidget)) {
/*     */         
/* 371 */         removePopup();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 376 */         removeWidget(toDeletePopupWidget);
/*     */       } 
/*     */     }
/*     */     
/* 380 */     return returnsValue;
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
/*     */   public boolean fireMouseReleasedEvent(int mouseX, int mouseY, MouseButton mouseButton, int clickCount) {
/* 394 */     IWidget w = getWidget(mouseX, mouseY);
/* 395 */     boolean ret = false;
/* 396 */     if (this.draggingListener != null) {
/*     */       
/* 398 */       this.draggingListener.drop(mouseX, mouseY, w);
/* 399 */       this.draggingListener = null;
/* 400 */       ret = true;
/*     */     } 
/*     */     
/* 403 */     if (w.equals(this)) return ret;
/*     */     
/* 405 */     MouseReleasedEvent e = new MouseReleasedEvent(w, mouseX, mouseY, mouseButton, clickCount);
/* 406 */     w.mouseReleased(e);
/* 407 */     fireGlobalEventListener((Event)e);
/*     */     
/* 409 */     return true;
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
/*     */   public boolean fireMouseDraggedEvent(int mouseX, int mouseY, MouseButton mouseButton) {
/* 422 */     IWidget w = getWidget(mouseX, mouseY);
/*     */     
/* 424 */     if (this.draggingListener != null)
/*     */     {
/* 426 */       this.draggingListener.drag(mouseX, mouseY);
/*     */     }
/*     */     
/* 429 */     if (!this.mouseOverWidget.equals(w)) {
/*     */       
/* 431 */       MouseExitedEvent exited = new MouseExitedEvent(w, this.mouseOverWidget);
/* 432 */       this.mouseOverWidget.mouseExited(exited);
/* 433 */       fireGlobalEventListener((Event)exited);
/*     */       
/* 435 */       MouseEnteredEvent entered = new MouseEnteredEvent(w, this.mouseOverWidget);
/* 436 */       w.mouseEntered(entered);
/* 437 */       fireGlobalEventListener((Event)entered);
/*     */     } 
/* 439 */     this.mouseOverWidget = w;
/*     */     
/* 441 */     if (w.equals(this)) return false;
/*     */     
/* 443 */     MouseDraggedEvent e = new MouseDraggedEvent(w, mouseX, mouseY, mouseButton);
/* 444 */     w.mouseDragged(e);
/* 445 */     fireGlobalEventListener((Event)e);
/*     */     
/* 447 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireMouseWheel(int mouseX, int mouseY, boolean up, int rotation) {
/* 452 */     IWidget w = getFocusedWidget();
/*     */     
/* 454 */     MouseWheelEvent e = new MouseWheelEvent(w, mouseX, mouseY, up, rotation);
/* 455 */     fireGlobalEventListener((Event)e);
/*     */     
/* 457 */     if (w != null) w.mouseWheel(e);
/*     */ 
/*     */     
/* 460 */     if (getWidget(mouseX, mouseY) == this) return false;
/*     */     
/* 462 */     return true;
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
/*     */ 
/*     */   
/*     */   public int getDisplayX() {
/* 483 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayY() {
/* 492 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void takeScreenshot(File screenshotFile) {
/* 501 */     this.screenshotFile = screenshotFile;
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
/*     */   private void screenshot(IOpenGL gl, int width, int height, File file) {
/*     */     try {
/* 518 */       RandomAccessFile out = new RandomAccessFile(file, "rw");
/* 519 */       FileChannel ch = out.getChannel();
/* 520 */       int fileLength = 18 + width * height * 3;
/* 521 */       out.setLength(fileLength);
/* 522 */       MappedByteBuffer image = ch.map(FileChannel.MapMode.READ_WRITE, 0L, fileLength);
/*     */ 
/*     */       
/* 525 */       image.put(0, (byte)0).put(1, (byte)0);
/* 526 */       image.put(2, (byte)2);
/* 527 */       image.put(12, (byte)(width & 0xFF));
/* 528 */       image.put(13, (byte)(width >> 8));
/* 529 */       image.put(14, (byte)(height & 0xFF));
/* 530 */       image.put(15, (byte)(height >> 8));
/* 531 */       image.put(16, (byte)24);
/*     */ 
/*     */       
/* 534 */       image.position(18);
/*     */       
/* 536 */       ByteBuffer bgr = image.slice();
/*     */ 
/*     */       
/* 539 */       gl.readPixels(0, 0, width, height, bgr);
/*     */ 
/*     */       
/* 542 */       ch.close();
/*     */     }
/* 544 */     catch (Exception e) {
/*     */       
/* 546 */       e.printStackTrace();
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
/*     */ 
/*     */   
/*     */   public boolean fireMouseMovedEvent(int displayX, int displayY) {
/* 562 */     IWidget w = getWidget(displayX, displayY);
/*     */     
/* 564 */     w.mouseMoved(displayX, displayY);
/*     */ 
/*     */     
/* 567 */     if (!this.mouseOverWidget.equals(w)) {
/*     */       
/* 569 */       MouseExitedEvent exited = new MouseExitedEvent(w, this.mouseOverWidget);
/* 570 */       this.mouseOverWidget.mouseExited(exited);
/* 571 */       fireGlobalEventListener((Event)exited);
/*     */       
/* 573 */       MouseEnteredEvent entered = new MouseEnteredEvent(w, this.mouseOverWidget);
/* 574 */       w.mouseEntered(entered);
/* 575 */       fireGlobalEventListener((Event)entered);
/*     */     } 
/* 577 */     this.mouseOverWidget = w;
/* 578 */     return !w.equals(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 583 */     IWidget w = super.getWidget(x, y);
/* 584 */     if (w != null) return w; 
/* 585 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireKeyPressedEvent(char keyValue, Key keyClass) {
/* 590 */     return fireKeyPressedEvent(keyValue, keyClass, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireKeyPressedEvent(char keyValue, Key keyClass, int modifiers) {
/* 595 */     if (this.focusedWidget != null) {
/*     */       
/* 597 */       KeyPressedEvent e = new KeyPressedEvent(this.focusedWidget, keyValue, keyClass);
/* 598 */       if ((modifiers & 0x40) == 64 || (
/* 599 */         modifiers & 0x280) == 640 || 
/* 600 */         modifiers == 0) {
/* 601 */         this.focusedWidget.keyPressed(e);
/* 602 */         fireGlobalEventListener((Event)e);
/*     */         
/* 604 */         return true;
/*     */       } 
/* 606 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 610 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean fireKeyReleasedEvent(char keyValue, Key keyClass) {
/* 616 */     return fireKeyReleasedEvent(keyValue, keyClass, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireKeyReleasedEvent(char keyValue, Key keyClass, int modifiers) {
/* 621 */     if (this.focusedWidget != null) {
/*     */       
/* 623 */       KeyReleasedEvent e = new KeyReleasedEvent(this.focusedWidget, keyValue, keyClass);
/* 624 */       if ((modifiers & 0x40) == 64 || (
/* 625 */         modifiers & 0x280) == 640 || 
/* 626 */         modifiers == 0) {
/* 627 */         this.focusedWidget.keyReleased(e);
/* 628 */         fireGlobalEventListener((Event)e);
/*     */         
/* 630 */         return true;
/*     */       } 
/* 632 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 636 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireKeyTypedEvent(char keyValue) {
/* 641 */     return fireKeyTypedEvent(keyValue, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireKeyTypedEvent(char keyValue, int modifiers) {
/* 646 */     if (this.focusedWidget != null) {
/*     */       
/* 648 */       KeyTypedEvent e = new KeyTypedEvent(this.focusedWidget, keyValue);
/* 649 */       if ((modifiers & 0x40) == 64 || (
/* 650 */         modifiers & 0x2000) == 8192 || 
/* 651 */         modifiers == 0) {
/* 652 */         this.focusedWidget.keyTyped(e);
/* 653 */         fireGlobalEventListener((Event)e);
/*     */         
/* 655 */         return true;
/*     */       } 
/* 657 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 661 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPopupWidget() {
/* 671 */     return this.popupWidget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDndListener(IDragAndDropListener dndl) {
/* 680 */     if (!this.dndListeners.contains(dndl)) {
/* 681 */       this.dndListeners.add(dndl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDndListener(IDragAndDropListener dndl) {
/* 690 */     this.dndListeners.remove(dndl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireGlobalEventListener(Event event) {
/* 699 */     if (this.globalEventListener.isEmpty())
/*     */       return; 
/* 701 */     for (int i = 0; i < this.globalEventListener.size(); i++)
/*     */     {
/* 703 */       ((IEventListener)this.globalEventListener.get(i)).processEvent(event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGlobalEventListener(IEventListener listener) {
/* 714 */     this.globalEventListener.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDepthTestEnabled() {
/* 719 */     return this.depthTestEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDepthTestEnabled(boolean depthTestDisabled) {
/* 724 */     this.depthTestEnabled = depthTestDisabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeGlobalEventListener(IEventListener listener) {
/* 733 */     this.globalEventListener.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void focusedWidgetValityCheck() {
/* 742 */     if (getFocusedWidget() != null && getFocusedWidget().getDisplay() == null)
/*     */     {
/* 744 */       setFocusedWidget((IWidget)null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Display.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */