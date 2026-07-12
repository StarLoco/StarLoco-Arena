/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSliderAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.event.ISliderMovedListener;
/*     */ import com.ankamagames.xulor.event.SliderMovedEvent;
/*     */ import com.ankamagames.xulor.event.listener.SliderMovedListener;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ISlider;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeSliderAppearance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.Slider;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.ISliderMovedListener;
/*     */ import org.fenggui.event.SliderMovedEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSlider
/*     */   extends XComponent
/*     */   implements ISlider
/*     */ {
/*     */   public static final String TAG = "Slider";
/*  32 */   private Slider m_slider = null;
/*     */   
/*  34 */   private double m_value = 0.0D;
/*     */   private boolean m_horizontal = true;
/*  36 */   private double m_size = 0.15D;
/*     */   
/*     */   private boolean m_valueInit = false;
/*     */   private boolean m_sizeInit = false;
/*  40 */   private ISliderMovedListener m_sliderMovedListener = null;
/*  41 */   private Vector<ISliderMovedListener> m_sml = new Vector<ISliderMovedListener>();
/*     */   
/*     */   private ISlider THIS;
/*     */   
/*     */   public XSlider() {
/*  46 */     this.THIS = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  55 */     System.out.println("<slider value=\"" + this.m_value + "\" horizontal=\"" + this.m_horizontal + "\">"); byte b; int i; IElement[] arrayOfIElement;
/*  56 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  57 */       c.buildXML(); b++; }
/*     */     
/*  59 */     System.out.println("</slider>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  70 */     if (this.m_slider == null) {
/*  71 */       this.m_slider = new Slider(this.m_horizontal);
/*     */       
/*  73 */       applyAllAttributes();
/*     */       
/*  75 */       this.m_sliderMovedListener = new ISliderMovedListener() {
/*     */           public void sliderMoved(SliderMovedEvent sliderMovedEvent) {
/*  77 */             SliderMovedEvent event = new SliderMovedEvent((IComponent)XSlider.this.THIS, sliderMovedEvent.getPosition());
/*  78 */             for (ISliderMovedListener l : XSlider.this.m_sml)
/*  79 */               ((SliderMovedListener)l).run(event); 
/*     */           }
/*     */         };
/*  82 */       this.m_slider.addSliderMovedListener(this.m_sliderMovedListener);
/*     */       
/*  84 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this);
/*     */       
/*  86 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_slider, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  89 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  90 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  93 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/* 103 */     if (this.m_slider == null)
/*     */       return; 
/* 105 */     if (this.m_sizeInit)
/* 106 */       this.m_slider.setSize(this.m_size); 
/* 107 */     if (this.m_valueInit)
/* 108 */       this.m_slider.setValue(this.m_value); 
/* 109 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 113 */     if (this.m_themeNeedToBeApplied) {
/* 114 */       this.m_themeNeedToBeApplied = false;
/* 115 */       applySliderTheme(this.m_slider, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeSelfFromParent() {
/* 120 */     if (this.m_slider != null) {
/* 121 */       this.m_slider.removeSliderMovedListener(this.m_sliderMovedListener);
/*     */     }
/* 123 */     super.removeSelfFromParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/* 132 */     return this.m_value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/* 141 */     return this.m_horizontal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSliderSize() {
/* 150 */     return this.m_size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHorizontal(boolean horizontal) {
/* 159 */     this.m_horizontal = horizontal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(double value) {
/* 169 */     this.m_value = value;
/* 170 */     this.m_valueInit = true;
/* 171 */     if (this.m_slider != null) {
/* 172 */       this.m_slider.setValue(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSliderSize(double size) {
/* 181 */     this.m_size = size;
/* 182 */     this.m_sizeInit = true;
/* 183 */     if (this.m_slider != null) {
/* 184 */       this.m_slider.setSize(size);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnSliderMove(ISliderMovedListener l) {
/* 193 */     this.m_sml.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 201 */     return (Widget)this.m_slider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 209 */     return "Slider";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 218 */     XSlider elem = (XSlider)element;
/* 219 */     elem.m_horizontal = this.m_horizontal;
/* 220 */     elem.m_size = this.m_size;
/* 221 */     elem.m_sizeInit = this.m_sizeInit;
/* 222 */     for (ISliderMovedListener listener : this.m_sml) elem.setOnSliderMove(listener); 
/* 223 */     elem.m_value = this.m_value;
/* 224 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 231 */     XSlider elem = new XSlider();
/* 232 */     copyElementData((IElement)elem);
/* 233 */     return (IElement)elem;
/*     */   }
/*     */   
/*     */   public static void applySliderTheme(Slider slider, ThemeElement element) {
/* 237 */     if (slider == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 241 */     slider.getAppearance().removeAll();
/* 242 */     XComponent.applyThemeAttributes((Widget)slider, element.getAttributes());
/* 243 */     XSpacingAppearance.setAppearance((StandardWidget)slider, element);
/* 244 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 245 */     for (ThemeAppearance app : appearances) {
/* 246 */       if (app != null) {
/* 247 */         XDecoratorAppearance.setAppearance((StandardWidget)slider, app);
/* 248 */         if (app instanceof ThemeSliderAppearance)
/* 249 */           XSliderAppearance.setAppearance(slider, (ThemeSliderAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XSlider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */