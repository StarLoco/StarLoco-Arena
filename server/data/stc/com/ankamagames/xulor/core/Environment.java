/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.Binding;
/*     */ import com.ankamagames.xulor.core.form.Form;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import com.ankamagames.xulor.template.IRadioGroup;
/*     */ import com.ankamagames.xulor.theme.ThemeParser;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ public class Environment
/*     */ {
/*  27 */   private HashMap<String, IRadioGroup> m_radiogroups = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  32 */   private HashMap<String, Form> m_forms = null;
/*     */   
/*  34 */   private HashMap<IItemRenderable, ArrayList<IElement>> m_renderableAssociations = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  39 */   private HashMap<Object, IElement> m_elementsByWidget = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  49 */   private HashMap<String, ElementMap> m_elementMaps = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  54 */   private HashMap<String, ThemeParser> m_themes = null;
/*     */   
/*  56 */   private String m_currentElementMapId = null;
/*  57 */   private String m_currentThemeId = null;
/*     */   
/*  59 */   private ArrayList<Form> m_currentForms = null;
/*     */   private PropertiesProvider m_pm;
/*     */   
/*     */   public Environment()
/*     */   {
/*  64 */     this.m_pm = new PropertiesProvider();
/*     */   }
/*     */   
/*     */   public PropertiesProvider getPropertiesProvider() {
/*  68 */     return this.m_pm;
/*     */   }
/*     */   
/*     */   public void putRadioGroup(String id, IRadioGroup rb) {
/*  72 */     if (this.m_radiogroups == null) {
/*  73 */       this.m_radiogroups = new HashMap();
/*     */     }
/*     */     
/*  76 */     this.m_radiogroups.put(id, rb);
/*     */   }
/*     */   
/*     */   public IRadioGroup getRadioGroup(String id) {
/*  80 */     if (this.m_radiogroups != null) {
/*  81 */       return (IRadioGroup)this.m_radiogroups.get(id);
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public boolean radioGroupExists(String id) {
/*  87 */     if (this.m_radiogroups != null) {
/*  88 */       return this.m_radiogroups.containsKey(id);
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public void addElementToRenderable(IItemRenderable renderable, IElement element) {
/*  94 */     if (renderable == null) {
/*  95 */       return;
/*     */     }
/*  97 */     ArrayList<IElement> elemList = (ArrayList)this.m_renderableAssociations.get(renderable);
/*  98 */     if (elemList == null) {
/*  99 */       elemList = new ArrayList();
/* 100 */       this.m_renderableAssociations.put(renderable, elemList);
/*     */     }
/*     */     
/* 103 */     elemList.add(element);
/*     */   }
/*     */   
/*     */   public void cleanElementFromRenderableParent(IItemRenderable renderable) {
/* 107 */     ArrayList<IElement> elemList = (ArrayList)this.m_renderableAssociations.get(renderable);
/* 108 */     if (elemList == null) {
/* 109 */       return;
/*     */     }
/*     */     
/* 112 */     for (IElement elem : elemList) {
/* 113 */       elem.setRenderableParent(null);
/*     */     }
/* 115 */     this.m_renderableAssociations.remove(renderable);
/*     */   }
/*     */   
/*     */   public void putElementByWidget(Object id, IElement c) {
/* 119 */     if (this.m_elementsByWidget == null) {
/* 120 */       this.m_elementsByWidget = new HashMap();
/* 121 */       Xulor.getInstance().getBinding().getEnvironmentWidgetCleaner(this.m_elementsByWidget).start();
/*     */     }
/* 123 */     synchronized (this.m_elementsByWidget) {
/* 124 */       this.m_elementsByWidget.put(id, c);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public IElement getElementByWidget(Object id)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 191	com/ankamagames/xulor/core/Environment:m_elementsByWidget	Ljava/util/HashMap;
/*     */     //   4: ifnull +27 -> 31
/*     */     //   7: aload_0
/*     */     //   8: getfield 191	com/ankamagames/xulor/core/Environment:m_elementsByWidget	Ljava/util/HashMap;
/*     */     //   11: dup
/*     */     //   12: astore_2
/*     */     //   13: monitorenter
/*     */     //   14: aload_0
/*     */     //   15: getfield 191	com/ankamagames/xulor/core/Environment:m_elementsByWidget	Ljava/util/HashMap;
/*     */     //   18: aload_1
/*     */     //   19: invokevirtual 213	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   22: checkcast 95	com/ankamagames/xulor/template/IElement
/*     */     //   25: aload_2
/*     */     //   26: monitorexit
/*     */     //   27: areturn
/*     */     //   28: aload_2
/*     */     //   29: monitorexit
/*     */     //   30: athrow
/*     */     //   31: aconst_null
/*     */     //   32: areturn
/*     */     // Line number table:
/*     */     //   Java source line #129	-> byte code offset #0
/*     */     //   Java source line #130	-> byte code offset #7
/*     */     //   Java source line #131	-> byte code offset #14
/*     */     //   Java source line #130	-> byte code offset #28
/*     */     //   Java source line #134	-> byte code offset #31
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	33	0	this	Environment
/*     */     //   0	33	1	id	Object
/*     */     //   12	17	2	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   14	27	28	finally
/*     */     //   28	30	28	finally
/*     */   }
/*     */   
/*     */   public void removeElementByWidget(Object id)
/*     */   {
/* 138 */     if (this.m_elementsByWidget != null) {
/* 139 */       synchronized (this.m_elementsByWidget) {
/* 140 */         this.m_elementsByWidget.remove(id);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean elementExistsByWidget(Object id)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 191	com/ankamagames/xulor/core/Environment:m_elementsByWidget	Ljava/util/HashMap;
/*     */     //   4: ifnull +24 -> 28
/*     */     //   7: aload_0
/*     */     //   8: getfield 191	com/ankamagames/xulor/core/Environment:m_elementsByWidget	Ljava/util/HashMap;
/*     */     //   11: dup
/*     */     //   12: astore_2
/*     */     //   13: monitorenter
/*     */     //   14: aload_0
/*     */     //   15: getfield 191	com/ankamagames/xulor/core/Environment:m_elementsByWidget	Ljava/util/HashMap;
/*     */     //   18: aload_1
/*     */     //   19: invokevirtual 211	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
/*     */     //   22: aload_2
/*     */     //   23: monitorexit
/*     */     //   24: ireturn
/*     */     //   25: aload_2
/*     */     //   26: monitorexit
/*     */     //   27: athrow
/*     */     //   28: iconst_0
/*     */     //   29: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #146	-> byte code offset #0
/*     */     //   Java source line #147	-> byte code offset #7
/*     */     //   Java source line #148	-> byte code offset #14
/*     */     //   Java source line #147	-> byte code offset #25
/*     */     //   Java source line #151	-> byte code offset #28
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	30	0	this	Environment
/*     */     //   0	30	1	id	Object
/*     */     //   12	14	2	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   14	24	25	finally
/*     */     //   25	27	25	finally
/*     */   }
/*     */   
/*     */   public Form[] getCurrentForms()
/*     */   {
/* 159 */     if (this.m_currentForms != null) {
/* 160 */       return (Form[])this.m_currentForms.toArray(new Form[0]);
/*     */     }
/*     */     
/* 163 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Form getCurrentForm()
/*     */   {
/* 173 */     if (this.m_currentForms != null) {
/* 174 */       return (Form)this.m_currentForms.get(this.m_currentForms.size() - 1);
/*     */     }
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   public Form getForm(String formId) {
/* 180 */     if (this.m_forms == null)
/* 181 */       return null;
/* 182 */     return (Form)this.m_forms.get(formId);
/*     */   }
/*     */   
/*     */   public Collection<Form> getForms() {
/* 186 */     if (this.m_forms == null) {
/* 187 */       return null;
/*     */     }
/* 189 */     return this.m_forms.values();
/*     */   }
/*     */   
/*     */   public void openForm(String formId, Form form) {
/* 193 */     if (this.m_forms == null) {
/* 194 */       this.m_forms = new HashMap();
/* 195 */       this.m_currentForms = new ArrayList();
/*     */     }
/*     */     
/* 198 */     this.m_forms.put(formId, form);
/* 199 */     this.m_currentForms.add(form);
/*     */   }
/*     */   
/*     */   public void closeForm(String formId) {
/* 203 */     if (this.m_forms == null) {
/* 204 */       return;
/*     */     }
/* 206 */     Form form = (Form)this.m_forms.get(formId);
/* 207 */     this.m_currentForms.remove(form);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeForm(String formId)
/*     */   {
/* 215 */     Form form = (Form)this.m_forms.remove(formId);
/* 216 */     this.m_currentForms.remove(form);
/*     */   }
/*     */   
/*     */   public class FormOperationException extends Exception
/*     */   {
/*     */     public FormOperationException(String message)
/*     */     {
/* 223 */       super();
/*     */     }
/*     */   }
/*     */   
/*     */   public ElementMap createElementMap(String elementMapId)
/*     */   {
/* 229 */     if (this.m_elementMaps == null) {
/* 230 */       this.m_elementMaps = new HashMap();
/*     */     }
/*     */     
/*     */ 
/* 234 */     ElementMap elementMap = new ElementMap(elementMapId);
/*     */     
/*     */ 
/* 237 */     this.m_elementMaps.put(elementMapId, elementMap);
/*     */     
/* 239 */     return elementMap;
/*     */   }
/*     */   
/*     */   public void removeElementMap(String elementMapId) {
/* 243 */     if (this.m_elementMaps != null) {
/* 244 */       this.m_elementMaps.remove(elementMapId);
/*     */     }
/*     */   }
/*     */   
/*     */   public ElementMap getElementMap(String id) {
/* 249 */     return (ElementMap)this.m_elementMaps.get(id);
/*     */   }
/*     */   
/*     */   public ElementMap getCurrentElementMap() {
/* 253 */     if (this.m_currentElementMapId != null)
/* 254 */       return (ElementMap)this.m_elementMaps.get(this.m_currentElementMapId);
/* 255 */     return null;
/*     */   }
/*     */   
/*     */   public void setCurrentElementMap(ElementMap elementMap) {
/* 259 */     this.m_currentElementMapId = (elementMap != null ? elementMap.getId() : null);
/*     */   }
/*     */   
/*     */   public void putThemeParser(String id, ThemeParser theme) {
/* 263 */     if (this.m_themes == null) {
/* 264 */       this.m_themes = new HashMap();
/*     */     }
/* 266 */     this.m_currentThemeId = id;
/* 267 */     this.m_themes.put(id, theme);
/*     */   }
/*     */   
/*     */   public ThemeParser getThemeParser(String id) {
/* 271 */     if (this.m_themes == null) {
/* 272 */       return null;
/*     */     }
/*     */     
/* 275 */     return (ThemeParser)this.m_themes.get(id);
/*     */   }
/*     */   
/*     */   public ThemeParser getCurrentThemeParser() {
/* 279 */     if (this.m_themes != null) {
/* 280 */       return (ThemeParser)this.m_themes.get(this.m_currentThemeId);
/*     */     }
/* 282 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\Environment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */