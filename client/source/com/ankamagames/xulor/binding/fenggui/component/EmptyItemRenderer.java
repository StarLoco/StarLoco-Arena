/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.IWidget;
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
/*     */ public class EmptyItemRenderer
/*     */   extends ItemRenderer
/*     */ {
/*     */   public EmptyItemRenderer() {
/*  23 */     setName("empty");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IElement element) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyItemValue(XElement[] elements, Item item) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRenderer cloneElementStructure() {
/*  66 */     return super.cloneElementStructure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/*  74 */     return super.getEncapsulatedObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Item item) {
/*  82 */     return super.isCompatible(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Item item) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(RenderableContainer renderable) {
/*  97 */     renderable.addChild((IWidget)new Container());
/*  98 */     renderable.setRenderableChildren(new XElement[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRenderableCompatible(IItemRenderable renderable) {
/* 113 */     if (renderable == null) {
/* 114 */       return false;
/*     */     }
/*     */     
/* 117 */     if (renderable.getItemValue() == null) {
/* 118 */       return true;
/*     */     }
/*     */     
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\EmptyItemRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */