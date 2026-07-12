/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.MapNavigator;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IMapNavigator;
/*     */ import com.ankamagames.xulor.util.DisplayableMapPoint;
/*     */ import java.util.Vector;
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
/*     */ public class XMapNavigator
/*     */   extends XSceneCanvas
/*     */   implements IMapNavigator
/*     */ {
/*     */   public static final String TAG = "MapNavigator";
/*  27 */   private MapNavigator m_mapNavigator = null;
/*     */   
/*  29 */   private DisplayableMapPoint[] m_content = null;
/*  30 */   private double m_maxZoom = 1.0D;
/*  31 */   private double m_minZoom = 1.0D;
/*  32 */   private double m_zoomScale = 1.0D;
/*     */   private double m_isoXCenter;
/*     */   private double m_isoYCenter;
/*     */   private boolean m_isoMap = false;
/*  36 */   private BackgroundedText.BackgroundedTextHotPointPosition m_tooltipHotPoint = BackgroundedText.BackgroundedTextHotPointPosition.SOUTH;
/*  37 */   private MapNavigator.MapShape m_mapShape = MapNavigator.MapShape.RECTANGLE;
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector<IMouseClickListener> m_mcl;
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  46 */     System.out.println("<MapNavigator>"); byte b; int i; IElement[] arrayOfIElement;
/*  47 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  48 */       c.buildXML(); b++; }
/*     */     
/*  50 */     System.out.println("</MapNavigator>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  60 */     if (this.m_mapNavigator == null) {
/*  61 */       this.m_mapNavigator = new MapNavigator();
/*     */       
/*  63 */       applyAllAttributes();
/*     */       
/*  65 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  66 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_mapNavigator, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  69 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  70 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  73 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  83 */     if (this.m_mapNavigator == null) {
/*     */       return;
/*     */     }
/*  86 */     this.m_mapNavigator.setMaxZoom(this.m_maxZoom);
/*  87 */     this.m_mapNavigator.setMinZoom(this.m_minZoom);
/*  88 */     this.m_mapNavigator.setZoomScale(this.m_zoomScale);
/*  89 */     this.m_mapNavigator.setIsoXCenter(this.m_isoXCenter);
/*  90 */     this.m_mapNavigator.setIsoYCenter(this.m_isoYCenter);
/*  91 */     this.m_mapNavigator.setItems(this.m_content);
/*  92 */     this.m_mapNavigator.setIsoMap(this.m_isoMap);
/*  93 */     this.m_mapNavigator.setTooltipHotPoint(this.m_tooltipHotPoint);
/*  94 */     this.m_mapNavigator.setMapShape(this.m_mapShape);
/*  95 */     this.m_mapNavigator.setMouseClickListener(this.m_mcl);
/*  96 */     applyComponentAttributes();
/*  97 */     applySceneCanvasAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 102 */     if (this.m_themeNeedToBeApplied) {
/* 103 */       this.m_themeNeedToBeApplied = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 114 */     return (Widget)this.m_mapNavigator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 123 */     XMapNavigator elem = (XMapNavigator)element;
/* 124 */     elem.setIsoXCenter(this.m_isoXCenter);
/* 125 */     elem.setIsoYCenter(this.m_isoYCenter);
/* 126 */     elem.setMaxZoom(this.m_maxZoom);
/* 127 */     super.copyElementData((IElement)elem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 136 */     XMapNavigator elem = new XMapNavigator();
/* 137 */     copyElementData((IElement)elem);
/* 138 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 147 */     return "MapNavigator";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(DisplayableMapPoint[] content) {
/* 156 */     this.m_content = content;
/* 157 */     if (this.m_mapNavigator != null) {
/* 158 */       this.m_mapNavigator.setItems(this.m_content);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setIsoXCenter(double x) {
/* 163 */     this.m_isoXCenter = x;
/* 164 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setIsoXCenter(x); 
/*     */   }
/*     */   
/*     */   public void setIsoYCenter(double y) {
/* 168 */     this.m_isoYCenter = y;
/* 169 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setIsoYCenter(y); 
/*     */   }
/*     */   
/*     */   public void setMaxZoom(double maxZoom) {
/* 173 */     this.m_maxZoom = maxZoom;
/* 174 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setMaxZoom(maxZoom); 
/*     */   }
/*     */   
/*     */   public void setMinZoom(double minZoom) {
/* 178 */     this.m_minZoom = minZoom;
/* 179 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setMinZoom(minZoom); 
/*     */   }
/*     */   
/*     */   public void setZoomScale(double zoomScale) {
/* 183 */     this.m_zoomScale = zoomScale;
/* 184 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setZoomScale(zoomScale); 
/*     */   }
/*     */   
/*     */   public void setTooltipHotPoint(BackgroundedText.BackgroundedTextHotPointPosition tooltipHotPoint) {
/* 188 */     this.m_tooltipHotPoint = tooltipHotPoint;
/* 189 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setTooltipHotPoint(tooltipHotPoint); 
/*     */   }
/*     */   
/*     */   public void setIsoMap(boolean isoMap) {
/* 193 */     this.m_isoMap = isoMap;
/* 194 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setIsoMap(isoMap); 
/*     */   }
/*     */   
/*     */   public void setMapShape(MapNavigator.MapShape mapShape) {
/* 198 */     this.m_mapShape = mapShape;
/* 199 */     if (this.m_mapNavigator != null) this.m_mapNavigator.setMapShape(mapShape); 
/*     */   }
/*     */   
/*     */   public void setOnClick(IMouseClickListener l) {
/* 203 */     if (this.m_mcl == null) this.m_mcl = new Vector<IMouseClickListener>(); 
/* 204 */     this.m_mcl.add(l);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMapNavigator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */