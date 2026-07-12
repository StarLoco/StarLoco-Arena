/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.converter.PrimitiveConverter;
/*     */ import com.ankamagames.xulor.core.form.Form;
/*     */ import com.ankamagames.xulor.core.renderer.ResultProvider;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.property.PropertyClient;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.util.Propagation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
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
/*     */ public class GenericParser
/*     */ {
/*  33 */   private static Logger m_logger = Logger.getLogger(GenericParser.class);
/*     */   
/*     */   public static final String ATTR_ID = "id";
/*     */   
/*     */   public static final String ATTR_PATH = "path";
/*     */   
/*     */   public static final String ELEM_FORM = "form";
/*     */   
/*     */   public static final String ATTR_INCLUDE = "include";
/*     */   
/*     */   public static final String ATTR_PROPERTY_NAME = "name";
/*     */   public static final String ATTR_PROPERTY_ATTRIBUTE = "attribute";
/*     */   public static final String ATTR_PROPERTY_LOCAL = "local";
/*     */   public static final String ATTR_THEME_STYLE = "style";
/*     */   public static final String ATTR_PROPERTY_FIELD = "field";
/*     */   public static final String ATTR_PROPERTY_LAYOUT_ON_CHANGE = "layoutOnChange";
/*     */   public static final String ATTR_STYLE_PROPAGATION = "stylepropagation";
/*     */   public static final String ELEM_PROPERTY = "property";
/*     */   public static final String ELEM_FIELDED_PROPERTY = "fieldedproperty";
/*     */   public static final String ELEM_LIST_PROPERTY = "listproperty";
/*     */   public static final String ELEM_ITEM_RENDERER = "itemRenderer";
/*     */   public static final String DEFAULT_THEME = "";
/*  55 */   private Environment m_environment = null;
/*     */ 
/*     */   
/*     */   private boolean m_isBuildingRenderer = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericParser(Environment env) {
/*  63 */     if (env != null) {
/*  64 */       this.m_environment = env;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement parse(Document jdoc) {
/*  75 */     return getElement(jdoc.getRootElement(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IElement getElement(Element tagElement, IElement parent) {
/*     */     String elementMapId;
/*     */     IElement newElement;
/*  88 */     String id = (tagElement.getAttribute("id") != null) ? tagElement.getAttribute("id").getValue().trim() : null;
/*     */     
/*  90 */     ElementMap elementMap = this.m_environment.getCurrentElementMap();
/*     */     
/*  92 */     if (elementMap == null) {
/*  93 */       m_logger.error("Aucune ElementMap n'est définie !");
/*  94 */       elementMapId = "";
/*     */     } else {
/*  96 */       elementMapId = elementMap.getId();
/*     */     } 
/*     */     
/*  99 */     Factory<IElement> factory = Xulor.getInstance().getBinding().getTagLibrary().getFactory(tagElement.getName());
/* 100 */     if (factory == null) {
/* 101 */       m_logger.error("Tag Inconnu : " + tagElement.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 107 */       newElement = factory.newInstance();
/* 108 */     } catch (Exception e) {
/* 109 */       m_logger.error("Erreur lors de l'instanciation du tag " + tagElement.getName() + ". ", e);
/* 110 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 114 */     if (parent != null) {
/* 115 */       parent.add(newElement);
/*     */     }
/*     */     
/* 118 */     if (elementMap != null && id != null) {
/* 119 */       elementMap.add(id, newElement);
/*     */     }
/*     */ 
/*     */     
/* 123 */     newElement.setElementMap(elementMap);
/*     */     
/* 125 */     List<Attribute> attributes = tagElement.getAttributes();
/*     */ 
/*     */     
/* 128 */     if (tagElement.getAttribute("Text") == null && tagElement.getTextTrim().length() > 0) {
/* 129 */       attributes.add(new Attribute("Text", tagElement.getTextTrim()));
/*     */     }
/*     */     
/* 132 */     if (tagElement.getName().equals("form")) {
/* 133 */       this.m_environment.openForm(String.valueOf(elementMapId) + "." + tagElement.getAttributeValue("id"), (Form)newElement);
/*     */     }
/*     */ 
/*     */     
/* 137 */     if (tagElement.getName().equalsIgnoreCase("itemRenderer")) {
/* 138 */       this.m_isBuildingRenderer = true;
/*     */     }
/*     */ 
/*     */     
/* 142 */     String previousStyle = Xulor.getInstance().getThemeParser().getStyle();
/* 143 */     if (newElement instanceof IComponent) {
/* 144 */       IComponent component = (IComponent)newElement;
/*     */       
/* 146 */       ThemeElement themeElement = null;
/* 147 */       String propagation = tagElement.getAttributeValue("stylepropagation");
/* 148 */       if (propagation != null) {
/*     */         
/* 150 */         tagElement.removeAttribute("stylepropagation");
/*     */         try {
/* 152 */           component.setStylePropagation(Propagation.valueOf(propagation.toUpperCase()), false);
/* 153 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*     */       } 
/* 155 */       if (tagElement.getAttributeValue("style") != null) {
/* 156 */         String style = tagElement.getAttributeValue("style");
/*     */         
/* 158 */         tagElement.removeAttribute("style");
/*     */         
/* 160 */         if (Propagation.PROPAGATE.toString().equalsIgnoreCase(propagation)) {
/* 161 */           Xulor.getInstance().getThemeParser().setStyle(style);
/*     */         }
/* 163 */         String themeClass = String.valueOf(tagElement.getName()) + style;
/* 164 */         themeElement = Xulor.getInstance().getThemeParser().getThemeElement(themeClass);
/* 165 */         component.setStyle(style, false);
/*     */       } else {
/* 167 */         String style = Xulor.getInstance().getThemeParser().getStyle();
/* 168 */         if (style == null) {
/* 169 */           style = "";
/*     */         }
/* 171 */         String themeClass = String.valueOf(tagElement.getName()) + style;
/* 172 */         themeElement = Xulor.getInstance().getThemeParser().getThemeElement(themeClass);
/*     */         
/* 174 */         if (themeElement == null) {
/* 175 */           themeClass = tagElement.getName();
/* 176 */           themeElement = Xulor.getInstance().getThemeParser().getThemeElement(themeClass);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (Propagation.STOP.toString().equalsIgnoreCase(propagation)) {
/* 183 */         Xulor.getInstance().getThemeParser().setStyle(null);
/*     */       }
/* 185 */       if (themeElement != null) {
/* 186 */         component.setThemeElement(themeElement);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 192 */     applyAttributes(newElement, factory, attributes);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     Iterator<Element> it = tagElement.getChildren().iterator();
/*     */     
/* 199 */     while (it != null && it.hasNext()) {
/* 200 */       Element child = it.next();
/* 201 */       if (!this.m_isBuildingRenderer && child.getName().toLowerCase().equals("property")) {
/*     */ 
/*     */ 
/*     */         
/* 205 */         String name = child.getAttributeValue("name").trim();
/* 206 */         String attribute = child.getAttributeValue("attribute").trim();
/*     */         
/* 208 */         if (name != null && attribute != null) {
/*     */           boolean layoutOnChange;
/*     */           
/* 211 */           Property property = this.m_environment.getPropertiesProvider().getProperty(name);
/*     */           
/* 213 */           if (property == null) {
/*     */ 
/*     */             
/* 216 */             property = new Property(name);
/* 217 */             this.m_environment.getPropertiesProvider().addProperty(property);
/*     */           } 
/*     */           
/* 220 */           Element result = null;
/* 221 */           for (Object val : child.getChildren()) {
/* 222 */             Element grandChild = (Element)val;
/* 223 */             if (grandChild.getName().equalsIgnoreCase("Condition") || grandChild.getName().equalsIgnoreCase("ValueReplacer")) {
/* 224 */               result = grandChild;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 229 */           ResultProvider condition = null;
/* 230 */           if (result != null) {
/* 231 */             condition = (ResultProvider)getElement(result, null);
/*     */           }
/*     */           
/* 234 */           Attribute fieldAttribute = child.getAttribute("field");
/*     */ 
/*     */           
/*     */           try {
/* 238 */             layoutOnChange = (child.getAttribute("layoutOnChange") == null) ? false : child.getAttribute("layoutOnChange").getBooleanValue();
/* 239 */           } catch (DataConversionException e) {
/* 240 */             layoutOnChange = false;
/*     */           } 
/*     */ 
/*     */           
/* 244 */           if (fieldAttribute != null) {
/* 245 */             property.addPropertyClient(new PropertyClient(newElement, factory, attribute, fieldAttribute.getValue(), condition, layoutOnChange), false);
/*     */           } else {
/* 247 */             property.addPropertyClient(new PropertyClient(newElement, factory, attribute, condition, layoutOnChange), false);
/*     */           } 
/*     */ 
/*     */           
/* 251 */           Form[] forms = this.m_environment.getCurrentForms();
/* 252 */           if (forms != null) {
/* 253 */             byte b; int i; Form[] arrayOfForm; for (i = (arrayOfForm = forms).length, b = 0; b < i; ) { Form f = arrayOfForm[b];
/* 254 */               f.addProperty(property); b++; }
/*     */           
/*     */           } 
/*     */           continue;
/*     */         } 
/* 259 */         m_logger.error("Les attributs name et/ou attribute manquent au tag propriété");
/*     */         continue;
/*     */       } 
/* 262 */       if (child.getName().equals("include")) {
/*     */ 
/*     */         
/* 265 */         String childId = child.getAttributeValue("id");
/* 266 */         if (childId == null) {
/* 267 */           m_logger.error("Pas d'id pour le tag Include, impossible de l'ajouter");
/*     */           continue;
/*     */         } 
/* 270 */         ElementMap includeElementMap = this.m_environment.createElementMap(String.valueOf(elementMapId) + "." + childId);
/*     */ 
/*     */         
/* 273 */         Xulor.getInstance().loadInto(child.getAttributeValue("path"), newElement, includeElementMap, 8L, (short)-1);
/*     */         
/*     */         continue;
/*     */       } 
/* 277 */       getElement(child, newElement);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     Xulor.getInstance().getThemeParser().setStyle(previousStyle);
/*     */     
/* 285 */     if (tagElement.getName().equals("form")) {
/* 286 */       this.m_environment.closeForm(String.valueOf(elementMapId) + "." + tagElement.getAttributeValue("formId"));
/*     */     }
/*     */     
/* 289 */     if (tagElement.getName().equals("itemRenderer")) {
/* 290 */       this.m_isBuildingRenderer = false;
/*     */     }
/*     */     
/* 293 */     return newElement;
/*     */   }
/*     */   
/*     */   private void applyAttributes(Object obj, Factory factory, List attributes) {
/* 297 */     Iterator<Attribute> it = attributes.iterator();
/*     */     
/* 299 */     while (it != null && it.hasNext()) {
/* 300 */       Attribute attr = it.next();
/*     */       
/* 302 */       if ("id".equals(attr.getName())) {
/*     */         continue;
/*     */       }
/* 305 */       Method method = factory.guessSetter(attr.getName());
/* 306 */       if (method != null) {
/*     */ 
/*     */         
/* 309 */         Class<?> paraType = method.getParameterTypes()[0];
/* 310 */         Converter converter = ConverterLibrary.getInstance().getConverter(paraType);
/*     */         
/* 312 */         if (converter != null) {
/*     */           
/* 314 */           Object para = null;
/*     */           try {
/* 316 */             para = converter.convert(paraType, attr.getValue());
/* 317 */             method.invoke(obj, new Object[] { para });
/* 318 */           } catch (Exception e) {
/* 319 */             m_logger.error("Problème à l'invoke :" + method.getName() + ":" + para, e);
/*     */           } 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */         
/* 327 */         if (paraType.isPrimitive()) {
/*     */           try {
/* 329 */             method.invoke(obj, new Object[] { PrimitiveConverter.convertJDOMAttribute(paraType, attr) });
/* 330 */           } catch (Exception e) {
/* 331 */             e.printStackTrace();
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         try {
/* 337 */           method.invoke(obj, new Object[] { attr.getValue() });
/* 338 */         } catch (Exception e) {
/* 339 */           m_logger.error("Problème à l'invoke :" + method.getName() + ":" + attr.getValue(), e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\GenericParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */