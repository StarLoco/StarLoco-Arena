/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XLabelAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IObservableLabel;
/*     */ import com.ankamagames.xulor.template.IPixmapable;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.ILabel;
/*     */ import org.fenggui.ObservableLabelWidget;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XObservableLabelComponent
/*     */   extends XObservableComponent
/*     */   implements IObservableLabel, IPixmapable
/*     */ {
/*  30 */   protected String m_text = null;
/*  31 */   protected Pixmap m_pixmap = null;
/*     */   
/*  33 */   private Property m_textProperty = null;
/*  34 */   private Property m_pixmapProperty = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPixmap() {
/*  42 */     return this.m_pixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/*  51 */     return this.m_text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  60 */     this.m_text = text;
/*  61 */     ILabel widget = (ILabel)getWidget();
/*     */     
/*  63 */     if (widget != null) {
/*  64 */       widget.setText(text);
/*  65 */       widget.getParent().layout();
/*     */     } 
/*     */     
/*  68 */     if (this.m_textProperty != null) {
/*  69 */       this.m_textProperty.setValue(text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap image) {
/*  79 */     if (getWidget() != null && image != null) {
/*  80 */       ((ILabel)getWidget()).setPixmap(FengguiConstant.toFengguiPixmap(image));
/*     */     }
/*  82 */     if (this.m_pixmapProperty != null) {
/*  83 */       this.m_pixmapProperty.setValue(image);
/*     */     }
/*  85 */     this.m_pixmap = image;
/*     */   }
/*     */ 
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
/*     */   
/*     */   public Widget getWidget() {
/* 104 */     return null;
/*     */   }
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
/*     */   public IElement cloneElementStructure() {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmapProperty(Property pixmapProperty) {
/* 136 */     this.m_pixmapProperty = pixmapProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextProperty(Property textProperty) {
/* 144 */     this.m_textProperty = textProperty;
/*     */   }
/*     */   
/*     */   public void applyObservableLabelComponentAttributes() {
/* 148 */     ILabel widget = (ILabel)getWidget();
/* 149 */     if (widget != null && widget instanceof ObservableLabelWidget) {
/* 150 */       ILabel w = widget;
/* 151 */       if (this.m_text != null) {
/* 152 */         w.setText(this.m_text);
/*     */       }
/* 154 */       if (this.m_pixmap != null) {
/* 155 */         w.setPixmap(FengguiConstant.toFengguiPixmap(this.m_pixmap));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 165 */     XObservableLabelComponent elem = (XObservableLabelComponent)element;
/* 166 */     elem.m_text = this.m_text;
/* 167 */     if (this.m_pixmap != null) elem.m_pixmap = this.m_pixmap.clone(); 
/* 168 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */   public static void applyObservableLabelTheme(ObservableLabelWidget widget, ThemeElement element) {
/* 172 */     if (widget == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     widget.getAppearance().removeAll();
/* 177 */     XComponent.applyThemeAttributes((Widget)widget, element.getAttributes());
/* 178 */     XSpacingAppearance.setAppearance((StandardWidget)widget, element);
/* 179 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 180 */     for (ThemeAppearance app : appearances) {
/* 181 */       if (app != null) {
/* 182 */         XDecoratorAppearance.setAppearance((StandardWidget)widget, app);
/* 183 */         if (app instanceof ThemeLabelAppearance)
/* 184 */           XLabelAppearance.setAppearance((StandardWidget)widget, (ThemeLabelAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XObservableLabelComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */