/*     */ package org.fenggui.menu;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Item;
/*     */ import org.fenggui.event.IMenuItemPressedListener;
/*     */ import org.fenggui.event.MenuItemPressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.BufferedTextRenderer;
/*     */ import org.fenggui.render.ITextRenderer;
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
/*     */ public class MenuItem
/*     */   extends Item
/*     */ {
/*  44 */   private ArrayList<IMenuItemPressedListener> menuItemPressedHook = new ArrayList<IMenuItemPressedListener>();
/*     */   
/*  46 */   protected Menu menu = null;
/*     */   private boolean enabled = true;
/*  48 */   private ITextRenderer textRenderer = (ITextRenderer)new BufferedTextRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem(String text) {
/*  56 */     this(text, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuItem(String text, boolean enabled) {
/*  61 */     super(text);
/*  62 */     setText(text);
/*  63 */     setEnabled(enabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem(InputOnlyStream stream) throws IOException, IOStreamException {
/*  71 */     super(null);
/*  72 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  81 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  90 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu getMenu() {
/*  98 */     return this.menu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMenuItemPressedListener(IMenuItemPressedListener l) {
/* 107 */     if (!this.menuItemPressedHook.contains(l))
/*     */     {
/* 109 */       this.menuItemPressedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMenuItemPressedListener(IMenuItemPressedListener l) {
/* 119 */     this.menuItemPressedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireMenuItemPressedEvent() {
/* 127 */     MenuItemPressedEvent e = new MenuItemPressedEvent(this.menu, this);
/*     */     
/* 129 */     for (IMenuItemPressedListener l : this.menuItemPressedHook)
/*     */     {
/* 131 */       l.menuItemPressed(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 138 */     super.process(stream);
/*     */     
/* 140 */     stream.processAttribute("enabled", this.enabled, true);
/* 141 */     stream.processChild("Menu", (IOStreamSaveable)this.menu, null, Menu.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 147 */     return this.textRenderer.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 153 */     this.textRenderer.setText(text);
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextRenderer getTextRenderer() {
/* 158 */     return this.textRenderer;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\MenuItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */