/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.converter.PrimitiveConverter;
/*     */ import com.ankamagames.xulor.core.Converter;
/*     */ import com.ankamagames.xulor.core.ConverterLibrary;
/*     */ import com.ankamagames.xulor.core.Factory;
/*     */ import com.ankamagames.xulor.util.Cursor;
/*     */ import com.ankamagames.xulor.util.Font;
/*     */ import com.ankamagames.xulor.util.FontManager;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
/*     */ import java.awt.Font;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThemeParser
/*     */ {
/*     */   public static final String ATTR_ID = "id";
/*     */   public static final String ATTR_PIXMAP_TEXTURE = "texture";
/*     */   public static final String ATTR_REFERENCE = "ref";
/*     */   public static final String ATTR_PATH = "path";
/*     */   public static final String ELEM_INIT = "init";
/*     */   public static final String ELEM_TEXTURE = "texture";
/*     */   public static final String ELEM_CURSOR = "cursor";
/*     */   public static final String ATTR_CURSOR_X = "x";
/*     */   public static final String ATTR_CURSOR_Y = "y";
/*     */   public static final String ATTR_CURSOR_TYPE = "type";
/*     */   public static final String ELEM_FONT = "font";
/*     */   public static final String ATTR_FONT = "font";
/*     */   public static final String ATTR_FONT_ANTIALIASED = "antialiased";
/*     */   public static final String XULOR_THEME_NAME = "xulor";
/*     */   public static final String ATTR_STATE = "state";
/*  50 */   private static Logger m_logger = Logger.getLogger(ThemeParser.class);
/*     */   
/*  52 */   private static ThemeParser m_themeParser = new ThemeParser();
/*     */   
/*     */   private String m_themeDirectory;
/*  55 */   private String m_style = null;
/*     */   
/*     */   private ThemeData m_themeData;
/*     */   
/*     */   private HashMap<String, ThemeTexture> m_textures;
/*     */   
/*     */   private HashMap<String, Cursor> m_cursors;
/*     */   private HashMap<String, IThemeElement> m_decorators;
/*     */   private HashMap<String, Font> m_fonts;
/*     */   private ThemeTagLibrary m_taglib;
/*     */   private ConverterLibrary m_cvtlib;
/*     */   
/*     */   private ThemeParser() {
/*  68 */     this.m_textures = new HashMap<String, ThemeTexture>();
/*  69 */     this.m_cursors = new HashMap<String, Cursor>();
/*  70 */     this.m_decorators = new HashMap<String, IThemeElement>();
/*  71 */     this.m_fonts = new HashMap<String, Font>();
/*  72 */     this.m_taglib = ThemeTagLibrary.getInstance();
/*  73 */     this.m_cvtlib = ThemeConverterLibrary.getInstance();
/*     */   }
/*     */   
/*     */   public static ThemeParser getInstance() {
/*  77 */     return m_themeParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTheme(InputStream stream, String directory) throws Exception {
/*  87 */     this.m_themeData = new ThemeData(stream);
/*  88 */     this.m_themeDirectory = directory;
/*  89 */     loadInit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTheme(URL url, String directory) throws Exception {
/* 101 */     loadTheme(url.openStream(), directory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTheme(URI uri, String directory) throws Exception {
/* 113 */     loadTheme(uri.toURL(), directory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTheme(String path, String directory) throws Exception {
/* 125 */     loadTheme(new URL(path), directory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStyle() {
/* 132 */     return this.m_style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStyle(String style) {
/* 140 */     this.m_style = style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Cursor> getCursors() {
/* 147 */     return this.m_cursors.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getThemeDirectory() {
/* 154 */     return this.m_themeDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThemeElement getThemeElement(String appClass) {
/* 165 */     if (this.m_themeData == null) {
/* 166 */       m_logger.error("Impossible de charger une apparence, aucun thème n'est chargé");
/* 167 */       return null;
/*     */     } 
/*     */     
/* 170 */     ThemeElement themeElement = this.m_themeData.getThemeElement(appClass.toUpperCase());
/* 171 */     if (themeElement != null) {
/* 172 */       return themeElement;
/*     */     }
/* 174 */     Element elem = null;
/* 175 */     List<Element> elems = this.m_themeData.getDocument().getRootElement().getChildren();
/* 176 */     for (Element element : elems) {
/* 177 */       if (element.getName().equalsIgnoreCase(appClass)) {
/* 178 */         elem = element;
/*     */         break;
/*     */       } 
/*     */     } 
/* 182 */     if (elem == null) {
/* 183 */       return null;
/*     */     }
/*     */     
/* 186 */     Iterator<Element> it = elem.getChildren().iterator();
/* 187 */     if (it != null && it.hasNext()) {
/* 188 */       this.m_themeData.setThemeElement(appClass.toUpperCase(), (ThemeElement)getElement(it.next()));
/*     */     }
/*     */     
/* 191 */     return this.m_themeData.getThemeElement(appClass.toUpperCase());
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadInit() {
/* 196 */     if (this.m_themeData == null || this.m_themeData.getDocument() == null || this.m_themeData.getDocument().getRootElement().getChild("init") == null) {
/*     */       return;
/*     */     }
/*     */     
/* 200 */     Iterator<Element> it = this.m_themeData.getDocument().getRootElement().getChild("init").getChildren().iterator();
/* 201 */     while (it != null && it.hasNext()) {
/* 202 */       Element child = it.next();
/* 203 */       if (child.getName().equalsIgnoreCase("texture")) {
/* 204 */         loadTexture(child); continue;
/* 205 */       }  if (child.getName().equalsIgnoreCase("font")) {
/* 206 */         loadFont(child); continue;
/* 207 */       }  if (child.getName().equalsIgnoreCase("cursor")) {
/* 208 */         loadCursor(child); continue;
/*     */       } 
/* 210 */       this.m_decorators.put(child.getAttributeValue("id"), getElement(child));
/*     */     } 
/*     */ 
/*     */     
/* 214 */     Xulor.getInstance().getBinding().loadCursors(this.m_cursors.values());
/*     */   }
/*     */   
/*     */   private void loadCursor(Element element) {
/* 218 */     if (!element.getName().equalsIgnoreCase("cursor") || element.getAttribute("path") == null || element.getAttribute("id") == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 222 */       Converter textureConverter = this.m_cvtlib.getConverter(ThemeTexture.class);
/* 223 */       if (element.getAttribute("path") != null) {
/* 224 */         String path = String.valueOf(this.m_themeDirectory) + element.getAttributeValue("path");
/* 225 */         int x = (element.getAttributeValue("x") == null) ? 0 : ((Integer)PrimitiveConverter.convertJDOMAttribute(int.class, element.getAttribute("x"))).intValue();
/* 226 */         int y = (element.getAttributeValue("y") == null) ? 0 : ((Integer)PrimitiveConverter.convertJDOMAttribute(int.class, element.getAttribute("y"))).intValue();
/* 227 */         Cursor.CursorType type = (element.getAttributeValue("type") == null) ? Cursor.CursorType.DEFAULT : Cursor.CursorType.valueOf(element.getAttributeValue("type").toUpperCase());
/* 228 */         ThemeTexture texture = (ThemeTexture)textureConverter.convert(ThemeTexture.class, path);
/* 229 */         Cursor cursor = new Cursor(texture, type, x, y);
/* 230 */         this.m_cursors.put(element.getAttributeValue("id"), cursor);
/*     */       } 
/* 232 */     } catch (Exception e) {
/* 233 */       m_logger.error("Impossible de créer l'instance de curseur", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadTexture(Element element) {
/* 244 */     if (!element.getName().equalsIgnoreCase("texture") || element.getAttribute("path") == null || element.getAttribute("id") == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 248 */       Converter textureConverter = this.m_cvtlib.getConverter(ThemeTexture.class);
/* 249 */       if (element.getAttribute("path") != null) {
/* 250 */         String path = String.valueOf(this.m_themeDirectory) + element.getAttributeValue("path");
/* 251 */         ThemeTexture texture = (ThemeTexture)textureConverter.convert(ThemeTexture.class, path);
/* 252 */         this.m_textures.put(element.getAttributeValue("id"), texture);
/*     */       } 
/* 254 */     } catch (Exception e) {
/* 255 */       m_logger.error("Impossible de créer l'instance de texture", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getTextureIds() {
/* 265 */     return this.m_textures.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThemeTexture getTexture(String textureId) {
/* 276 */     return this.m_textures.get(textureId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadFont(Element element) {
/*     */     Font font;
/* 285 */     if (!element.getName().equalsIgnoreCase("font") || element.getAttribute("font") == null || element.getAttribute("id") == null) {
/*     */       return;
/*     */     }
/*     */     
/* 289 */     if (element.getAttribute("path") != null) {
/*     */       try {
/* 291 */         InputStream stream = (new URL(String.valueOf(this.m_themeDirectory) + element.getAttributeValue("path"))).openStream();
/* 292 */         font = Font.createFont(0, stream);
/* 293 */         stream.close();
/*     */       }
/* 295 */       catch (Exception e) {
/* 296 */         m_logger.warn("Impossible de charger la font", e);
/* 297 */         font = Font.decode(null);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 330 */       Font tmpFont = Font.decode(element.getAttributeValue("font"));
/* 331 */       font = font.deriveFont(tmpFont.getStyle(), tmpFont.getSize());
/*     */     } else {
/* 333 */       font = Font.decode(element.getAttributeValue("font"));
/*     */     } 
/*     */     
/* 336 */     if (element.getAttribute("antialiased") != null) {
/* 337 */       boolean isAntialiased = ((Boolean)PrimitiveConverter.convertJDOMAttribute(boolean.class, element.getAttribute("antialiased"))).booleanValue();
/* 338 */       Font finalFont = new Font(font, isAntialiased);
/* 339 */       this.m_fonts.put(element.getAttributeValue("id"), finalFont);
/* 340 */       FontManager.getInstance().addFont(finalFont);
/*     */     } else {
/* 342 */       Font finalFont = new Font(font);
/* 343 */       this.m_fonts.put(element.getAttributeValue("id"), finalFont);
/* 344 */       FontManager.getInstance().addFont(finalFont);
/*     */     } 
/*     */   }
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
/*     */   private IThemeElement getElement(Element element) {
/*     */     IThemeElement te;
/* 359 */     if (element == null) {
/* 360 */       return null;
/*     */     }
/*     */     
/* 363 */     Factory factory = this.m_taglib.getFactory(element.getName());
/*     */     
/* 365 */     if (factory == null) {
/* 366 */       m_logger.error("Tag non enregistré : " + element.getName());
/* 367 */       return null;
/*     */     } 
/*     */     
/* 370 */     if (element.getAttributeValue("ref") != null) {
/* 371 */       IThemeElement tmpElem = this.m_decorators.get(element.getAttributeValue("ref"));
/* 372 */       if (tmpElem != null) {
/* 373 */         te = tmpElem.cloneAppearance();
/*     */       } else {
/* 375 */         m_logger.error("Impossible de trouver un objet d'id " + element.getAttributeValue("ref"));
/* 376 */         return null;
/*     */       } 
/*     */     } else {
/*     */       try {
/* 380 */         te = (IThemeElement)factory.newInstance();
/* 381 */       } catch (Exception e) {
/* 382 */         m_logger.error("Impossible de créer une nouvelle instance de l'objet de type " + element.getName(), e);
/* 383 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     if (te instanceof ThemePixmap) {
/* 388 */       ThemeTexture texture = this.m_textures.get(element.getAttributeValue("texture"));
/* 389 */       if (texture != null) {
/* 390 */         ((ThemePixmap)te).setTexture(texture);
/*     */       }
/* 392 */       element.removeAttribute("texture");
/*     */     } 
/*     */     
/* 395 */     applyAttributes(te, factory, element.getAttributes());
/*     */     
/* 397 */     Iterator<Element> it = element.getChildren().iterator();
/* 398 */     while (it != null && it.hasNext()) {
/* 399 */       Element child = it.next();
/* 400 */       if (child.getAttributeValue("ref") != null && child.getName().equalsIgnoreCase("font") && te instanceof IFontable) {
/* 401 */         ((IFontable)te).setFont(this.m_fonts.get(child.getAttributeValue("ref")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       te.add(getElement(child));
/*     */     } 
/*     */ 
/*     */     
/* 419 */     return te;
/*     */   }
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
/*     */   private void applyAttributes(Object elem, Factory factory, List attributes) {
/* 433 */     if (attributes.isEmpty()) {
/*     */       return;
/*     */     }
/* 436 */     Iterator<Attribute> it = attributes.iterator();
/*     */     
/* 438 */     while (it != null && it.hasNext()) {
/* 439 */       Attribute attr = it.next();
/*     */       
/* 441 */       Method method = factory.guessSetter(attr.getName());
/* 442 */       if (method != null) {
/* 443 */         Class<?> paraType = method.getParameterTypes()[0];
/* 444 */         Converter converter = this.m_cvtlib.getConverter(paraType);
/*     */         
/* 446 */         if (converter != null) {
/* 447 */           Object para = null;
/*     */           try {
/* 449 */             para = converter.convert(paraType, attr.getValue());
/* 450 */             method.invoke(elem, new Object[] { para });
/* 451 */           } catch (Exception e) {
/* 452 */             m_logger.error(e + ":" + method.getName() + ":" + para, e);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 457 */         if (paraType.isPrimitive())
/*     */           try {
/* 459 */             method.invoke(elem, new Object[] { PrimitiveConverter.convertJDOMAttribute(paraType, attr) });
/* 460 */           } catch (Exception e) {
/* 461 */             e.printStackTrace();
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */