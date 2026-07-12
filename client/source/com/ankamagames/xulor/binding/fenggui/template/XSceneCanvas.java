/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.SceneCanvas;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ISceneCanvas;
/*     */ import org.fenggui.Widget;
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
/*     */ public class XSceneCanvas
/*     */   extends XComponent
/*     */   implements ISceneCanvas
/*     */ {
/*     */   public static final String TAG = "SceneCanvas";
/*  24 */   private SceneCanvas m_sceneCanvas = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  32 */     System.out.println("<sceneCanvas>"); byte b; int i; IElement[] arrayOfIElement;
/*  33 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  34 */       c.buildXML(); b++; }
/*     */     
/*  36 */     System.out.println("</sceneCanvas>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  46 */     if (this.m_sceneCanvas == null) {
/*  47 */       this.m_sceneCanvas = new SceneCanvas();
/*     */       
/*  49 */       applyAllAttributes();
/*     */       
/*  51 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this);
/*     */       
/*  53 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_sceneCanvas, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  56 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  57 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  60 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  70 */     applySceneCanvasAttributes();
/*  71 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  75 */     if (this.m_themeNeedToBeApplied) {
/*  76 */       this.m_themeNeedToBeApplied = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applySceneCanvasAttributes() {
/*  83 */     if (!(getWidget() instanceof SceneCanvas)) {
/*     */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayNonBlockingAvailability() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 103 */     return (Widget)this.m_sceneCanvas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 111 */     return "SceneCanvas";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 119 */     XSceneCanvas elem = new XSceneCanvas();
/* 120 */     copyElementData((IElement)elem);
/* 121 */     return (IElement)elem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XSceneCanvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */