/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.template.IBorderLayoutData;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import java.io.PrintStream;
/*     */ import org.fenggui.layout.BorderLayoutData;
/*     */ import org.fenggui.layout.ILayoutData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XBorderLayoutData
/*     */   extends XLayoutData
/*     */   implements IBorderLayoutData
/*     */ {
/*     */   public static final String TAG = "BorderLayoutData";
/*     */   public static final String SHORT_TAG = "BLD";
/*  20 */   private BorderLayoutData m_borderLayoutData = null;
/*     */   
/*     */   private String m_data;
/*     */   
/*  24 */   public static XBorderLayoutData NORTH = new XBorderLayoutData("NORTH");
/*  25 */   public static XBorderLayoutData SOUTH = new XBorderLayoutData("SOUTH");
/*  26 */   public static XBorderLayoutData WEST = new XBorderLayoutData("WEST");
/*  27 */   public static XBorderLayoutData EAST = new XBorderLayoutData("EAST");
/*  28 */   public static XBorderLayoutData CENTER = new XBorderLayoutData("CENTER");
/*     */   
/*     */   public XBorderLayoutData() {
/*  31 */     this.m_data = "NORTH";
/*     */   }
/*     */   
/*     */   public XBorderLayoutData(String layout) {
/*  35 */     this.m_data = layout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  44 */     System.out.println("<borderlayoutdata data=\"" + this.m_data + "\">");
/*  45 */     for (IElement c : this.m_children) {
/*  46 */       c.buildXML();
/*     */     }
/*  48 */     System.out.println("</borderlayoutdata>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  58 */     if (this.m_borderLayoutData == null)
/*     */     {
/*  60 */       if (this.m_data.equalsIgnoreCase("NORTH")) {
/*  61 */         this.m_borderLayoutData = BorderLayoutData.NORTH;
/*  62 */       } else if (this.m_data.equalsIgnoreCase("SOUTH")) {
/*  63 */         this.m_borderLayoutData = BorderLayoutData.SOUTH;
/*  64 */       } else if (this.m_data.equalsIgnoreCase("WEST")) {
/*  65 */         this.m_borderLayoutData = BorderLayoutData.WEST;
/*  66 */       } else if (this.m_data.equalsIgnoreCase("EAST")) {
/*  67 */         this.m_borderLayoutData = BorderLayoutData.EAST;
/*  68 */       } else if (this.m_data.equalsIgnoreCase("CENTER")) {
/*  69 */         this.m_borderLayoutData = BorderLayoutData.CENTER;
/*     */       }
/*  71 */       if (this.m_parent != null) ((XComponent)this.m_parent).setLayoutData(this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  74 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  75 */       c.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setData(String data) {
/*  80 */     this.m_data = data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ILayoutData getLayoutData()
/*     */   {
/*  88 */     return this.m_borderLayoutData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  96 */     return "BorderLayoutData";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 104 */     XBorderLayoutData elem = (XBorderLayoutData)element;
/* 105 */     elem.m_data = this.m_data;
/* 106 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 113 */     XBorderLayoutData elem = new XBorderLayoutData();
/* 114 */     copyElementData(elem);
/* 115 */     return elem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XBorderLayoutData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */