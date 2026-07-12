/*    */ package org.fenggui.util;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ 
/*    */ public class FontSAXHandler
/*    */   extends DefaultHandler
/*    */ {
/* 11 */   private StringBuilder builder = null;
/* 12 */   private Hashtable<Character, CharacterPixmap> texHashMap = null;
/* 13 */   private char character = 'c';
/* 14 */   private int x = 0, y = 0, width = 0, height = 0, charWidth = 0;
/*    */ 
/*    */   
/*    */   public FontSAXHandler(Hashtable<Character, CharacterPixmap> texHashMap) {
/* 18 */     this.texHashMap = texHashMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 24 */     if (this.builder != null) this.builder.append(ch, start, length);
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 30 */     if (qName.equals("CharacterPixmap"))
/*    */     
/* 32 */     { this.x = 0;
/* 33 */       this.y = 0;
/* 34 */       this.width = 0;
/* 35 */       this.height = 0;
/* 36 */       this.charWidth = 0;
/*    */       
/* 38 */       this.x = Integer.parseInt(attributes.getValue("x"));
/* 39 */       this.y = Integer.parseInt(attributes.getValue("y"));
/* 40 */       this.width = Integer.parseInt(attributes.getValue("width"));
/* 41 */       this.height = Integer.parseInt(attributes.getValue("height"));
/* 42 */       this.charWidth = Integer.parseInt(attributes.getValue("charWidth"));
/*    */        }
/*    */     
/* 45 */     else if (qName.equals("character")) { this.builder = new StringBuilder(); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 51 */     if (qName.equals("character")) {
/*    */       
/* 53 */       String s = this.builder.toString();
/* 54 */       if (s.length() > 1) throw new SAXException("The <character> element holds a string that contains more than one character!"); 
/* 55 */       this.character = s.charAt(0);
/* 56 */       this.builder = null;
/*    */     }
/* 58 */     else if (qName.equals("CharacterPixmap")) {
/*    */       
/* 60 */       CharacterPixmap cp = new CharacterPixmap(null, this.x, this.y, this.width, this.height, this.character, this.charWidth);
/* 61 */       this.texHashMap.put(Character.valueOf(this.character), cp);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\FontSAXHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */