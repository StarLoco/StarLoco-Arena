/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IGridLayout;
/*     */ import java.io.PrintStream;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.layout.GridLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XGridLayout
/*     */   extends XLayoutManager
/*     */   implements IGridLayout
/*     */ {
/*     */   public static final String TAG = "GridLayout";
/*     */   public static final String SHORT_TAG = "GL";
/*  22 */   private GridLayout m_gridLayout = null;
/*     */   
/*  24 */   private int m_columns = 1;
/*  25 */   private int m_rows = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  31 */     System.out.println("<gridlayout rows=\"" + this.m_rows + "\" columns=\"" + this.m_columns + "\">");
/*  32 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  33 */       c.buildXML();
/*     */     }
/*  35 */     System.out.println("</gridlayout>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  43 */     if (this.m_gridLayout == null) {
/*  44 */       this.m_gridLayout = new GridLayout(this.m_rows, this.m_columns);
/*     */       
/*     */ 
/*  47 */       if ((this.m_parent instanceof XContainer)) {
/*  48 */         ((XContainer)this.m_parent).setLayoutManager(this.m_gridLayout);
/*     */       }
/*  50 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_gridLayout, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  53 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  54 */       c.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getColumns()
/*     */   {
/*  63 */     return this.m_columns;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setColumns(int columns)
/*     */   {
/*  70 */     this.m_columns = columns;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getRows()
/*     */   {
/*  77 */     return this.m_rows;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRows(int rows)
/*     */   {
/*  84 */     this.m_rows = rows;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LayoutManager getLayoutManager()
/*     */   {
/*  92 */     return this.m_gridLayout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 100 */     return "GridLayout";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 107 */     XGridLayout elem = new XGridLayout();
/* 108 */     elem.setColumns(this.m_columns);
/* 109 */     elem.setRows(this.m_rows);
/* 110 */     copyElementData(elem);
/* 111 */     return elem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XGridLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */