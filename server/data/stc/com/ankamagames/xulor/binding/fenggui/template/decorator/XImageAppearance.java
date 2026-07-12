/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image.ImageAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.util.AlignmentSwitch;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeImageAppearance;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.layout.Alignment;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.switches.SetPixmapSwitch;
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
/*     */ public class XImageAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "ImageAppearance";
/*  30 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IAppearance getAppearance()
/*     */   {
/*  37 */     return null;
/*     */   }
/*     */   
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
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/*  70 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  77 */     return "ImageAppearance";
/*     */   }
/*     */   
/*     */   public static void setAppearance(Image image, ThemeImageAppearance theme) {
/*  81 */     if ((image == null) || (theme == null)) {
/*  82 */       return;
/*     */     }
/*  84 */     Image.ImageAppearance app = image.getAppearance();
/*     */     
/*  86 */     Pixmap pixmap = FengguiConstant.toFengguiPixmap(theme.getPixmap());
/*  87 */     if (pixmap != null) {
/*  88 */       app.add(new SetPixmapSwitch(theme.getState(), pixmap));
/*     */     }
/*     */     
/*  91 */     Alignment alignment = FengguiConstant.toFengguiAlignment(theme.getAlignment());
/*  92 */     if (alignment != null) {
/*  93 */       app.add(new AlignmentSwitch(theme.getState(), alignment));
/*     */     }
/*     */     
/*  96 */     image.setKeepAspectRatio(theme.isKeepAspectRatio());
/*  97 */     image.setScaled(theme.isScaled());
/*     */     
/*  99 */     if (theme.getState().equals("default")) {
/* 100 */       app.setEnabled("default", true);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XImageAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */