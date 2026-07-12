/*     */ package org.fenggui.render;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.Hashtable;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.fenggui.io.DefaultElementName;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.util.Alphabet;
/*     */ import org.fenggui.util.CharacterPixmap;
/*     */ import org.fenggui.util.FontSAXHandler;
/*     */ import org.fenggui.util.fonttoolkit.FontFactory;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ @DefaultElementName("font")
/*     */ public class Font
/*     */   implements IOStreamSaveable
/*     */ {
/*  57 */   private static Font defaultFont = null;
/*  58 */   private Hashtable<Character, CharacterPixmap> texHashMap = null;
/*  59 */   private BufferedImage image = null;
/*  60 */   private int height = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Font getDefaultFont() {
/*  69 */     if (defaultFont != null) return defaultFont;
/*     */     
/*  71 */     java.awt.Font awtFont = new java.awt.Font("Serif", 0, 12);
/*     */     
/*  73 */     defaultFont = FontFactory.renderStandardFont(awtFont);
/*     */     
/*  75 */     return defaultFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultFont(Font font) {
/*  84 */     defaultFont = font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font(BufferedImage map, Hashtable<Character, CharacterPixmap> texHashMap, int height) {
/*  95 */     this.texHashMap = texHashMap;
/*  96 */     this.image = map;
/*  97 */     this.height = height;
/*     */     
/*  99 */     if (map.getType() != 2)
/*     */     {
/* 101 */       throw new IllegalArgumentException("The image map has to be of type TYPE_INT_ARGB!");
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
/*     */   public Font(String textureFilename, String xmlFilename) throws FileNotFoundException, IOException {
/* 115 */     loadFont(textureFilename, xmlFilename);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font(InputOnlyStream stream) throws IOException, IOStreamException {
/* 126 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public Font(InputStream textureIn, InputStream xmlIn) throws FileNotFoundException, IOException {
/* 131 */     loadFont(textureIn, xmlIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadFont(InputStream textureIn, InputStream xmlIn) throws IOException {
/* 136 */     this.image = ImageIO.read(textureIn);
/* 137 */     this.texHashMap = new Hashtable<Character, CharacterPixmap>();
/* 138 */     FontSAXHandler fontHandler = new FontSAXHandler(this.texHashMap);
/*     */     
/* 140 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */ 
/*     */     
/*     */     try {
/* 144 */       SAXParser saxParser = factory.newSAXParser();
/* 145 */       saxParser.parse(xmlIn, (DefaultHandler)fontHandler);
/*     */       
/* 147 */       this.height = ((CharacterPixmap)this.texHashMap.get(Character.valueOf('a'))).getHeight();
/*     */     }
/* 149 */     catch (SAXException e) {
/*     */       
/* 151 */       e.printStackTrace();
/*     */     }
/* 153 */     catch (ParserConfigurationException e) {
/*     */       
/* 155 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadFont(String textureFilename, String xmlFilename) throws IOException {
/* 163 */     InputStream xmlIn = Binding.getInstance().getResource(xmlFilename);
/* 164 */     InputStream textureIn = Binding.getInstance().getResource(textureFilename);
/*     */     
/* 166 */     loadFont(textureIn, xmlIn);
/*     */     
/* 168 */     xmlIn.close();
/* 169 */     textureIn.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFontData(String textureFilename, String descriptionFilename) throws IOException {
/* 174 */     ImageIO.write(this.image, "png", new File(textureFilename));
/*     */     
/* 176 */     StringBuilder buffer = new StringBuilder();
/*     */     
/* 178 */     buffer.append("<?xml version='1.0' encoding='utf-8'?>\n\n");
/* 179 */     buffer.append("<!--  Keep in mind that pixmaps have their origin in the upper left corner! -->\n\n");
/*     */     
/* 181 */     buffer.append("<Font>\n");
/*     */     
/* 183 */     for (Character c : this.texHashMap.keySet()) {
/*     */       
/* 185 */       CharacterPixmap pixmap = this.texHashMap.get(c);
/*     */       
/* 187 */       pixmap.toXML("  ", buffer);
/*     */     } 
/* 189 */     buffer.append("</Font>");
/* 190 */     FileOutputStream fileOut = new FileOutputStream(descriptionFilename, false);
/* 191 */     OutputStreamWriter out = new OutputStreamWriter(fileOut, "UTF-8");
/* 192 */     System.out.println("Writing description file with encoding " + out.getEncoding());
/* 193 */     out.write(buffer.toString());
/* 194 */     out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uploadToVideoMemory() {
/* 204 */     ITexture tex = Binding.getInstance().getTexture(this.image);
/*     */     
/* 206 */     for (CharacterPixmap cp : this.texHashMap.values())
/*     */     {
/* 208 */       cp.setTexture(tex);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCharacterMapped(char c) {
/* 214 */     return this.texHashMap.containsKey(Character.valueOf(c));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(char s) {
/* 224 */     CharacterPixmap cp = this.texHashMap.get(Character.valueOf(s));
/* 225 */     if (cp == null) return 0; 
/* 226 */     return cp.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String s) {
/* 236 */     if (s == null) return 0; 
/* 237 */     int length = 0;
/* 238 */     for (int i = 0; i < s.length(); ) { length += getWidth(s.charAt(i)); i++; }
/* 239 */      return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage getImage() {
/* 244 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(char[] chars, int start, int end) {
/* 255 */     if (end <= start) return 0; 
/* 256 */     int length = 0;
/* 257 */     for (int i = start; i < end; i++) {
/* 258 */       length += getWidth(chars[i]);
/*     */     }
/* 260 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String confineLength(String s, int width) {
/* 271 */     int length = 0;
/*     */     
/* 273 */     for (int i = 0; i < s.length(); i++) {
/*     */       
/* 275 */       length += getWidth(s.charAt(i));
/*     */       
/* 277 */       if (length >= width) {
/*     */ 
/*     */         
/* 280 */         int pLength = getWidth("...");
/*     */         
/* 282 */         while (length + pLength >= width && i >= 0) {
/*     */           
/* 284 */           length -= getWidth(s.charAt(i));
/* 285 */           i--;
/*     */         } 
/*     */         
/* 288 */         s = String.valueOf(s.substring(0, ++i)) + "...";
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 293 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 302 */     return this.height;
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
/*     */   public CharacterPixmap getCharPixMap(char ch) {
/* 316 */     if (((CharacterPixmap)this.texHashMap.get(Character.valueOf('a'))).getTexture() == null)
/*     */     {
/* 318 */       uploadToVideoMemory();
/*     */     }
/*     */     
/* 321 */     CharacterPixmap p = this.texHashMap.get(Character.valueOf(ch));
/*     */     
/* 323 */     if (p == null)
/*     */     {
/*     */       
/* 326 */       return this.texHashMap.get(Character.valueOf('?'));
/*     */     }
/* 328 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 343 */     if (stream.isInputStream()) {
/*     */       
/* 345 */       if (stream.startSubcontext("load")) {
/*     */         
/* 347 */         String map = stream.processAttribute("map", "not-set");
/* 348 */         String image = stream.processAttribute("image", "not-set");
/* 349 */         map = String.valueOf(((InputOnlyStream)stream).getResourcePath()) + map;
/* 350 */         image = String.valueOf(((InputOnlyStream)stream).getResourcePath()) + image;
/* 351 */         loadFont(image, map);
/* 352 */         stream.endSubcontext();
/*     */         return;
/*     */       } 
/* 355 */       if (stream.startSubcontext("create")) {
/*     */         
/* 357 */         String name = stream.processAttribute("fontName", "not-set");
/* 358 */         String typeStr = stream.processAttribute("type", "not-set", "plain");
/* 359 */         int type = 0;
/*     */         
/* 361 */         if (typeStr.equalsIgnoreCase("plain"))
/* 362 */         { type = 0; }
/* 363 */         else if (typeStr.equalsIgnoreCase("bold"))
/* 364 */         { type = 1; }
/* 365 */         else if (typeStr.equalsIgnoreCase("italic"))
/* 366 */         { type = 2; }
/* 367 */         else { throw new IllegalArgumentException("Unknwown font type '" + typeStr + "'"); }
/*     */         
/* 369 */         int size = stream.processAttribute("size", 16);
/* 370 */         boolean antialiasing = stream.processAttribute("antialiasing", true, false);
/*     */         
/* 372 */         java.awt.Font awtFont = new java.awt.Font(name, type, size);
/* 373 */         Font f = FontFactory.renderStandardFont(awtFont, antialiasing, Alphabet.ENGLISH);
/* 374 */         this.height = f.height;
/* 375 */         this.image = f.image;
/* 376 */         this.texHashMap = f.texHashMap;
/* 377 */         stream.endSubcontext();
/*     */       } else {
/* 379 */         throw new IOStreamException("neither <create> nor <load> found in <Font>");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 387 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\Font.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */