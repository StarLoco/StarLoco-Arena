/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.SceneCanvas;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ISceneCanvas;
/*     */ import java.io.PrintStream;
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
/*     */   public void buildXML()
/*     */   {
/*  32 */     System.out.println("<sceneCanvas>");
/*  33 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  34 */       c.buildXML();
/*     */     }
/*  36 */     System.out.println("</sceneCanvas>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  46 */     if (this.m_sceneCanvas == null) {
/*  47 */       this.m_sceneCanvas = new SceneCanvas();
/*     */       
/*  49 */       applyAllAttributes();
/*     */       
/*  51 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  53 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_sceneCanvas, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  56 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  57 */       c.buildGUI();
/*     */     }
/*     */     
/*  60 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
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
/*     */   public void applySceneCanvasAttributes()
/*     */   {
/*  83 */     if (!(getWidget() instanceof SceneCanvas)) {}
/*     */   }
/*     */   
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
/*     */   public Widget getWidget()
/*     */   {
/* 103 */     return this.m_sceneCanvas;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 111 */     return "SceneCanvas";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 119 */     XSceneCanvas elem = new XSceneCanvas();
/* 120 */     copyElementData(elem);
/* 121 */     return elem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XSceneCanvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */