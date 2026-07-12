/*     */ package com.ankamagames.framework.kernel.impl.admin.messages.serverToClient;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.SecureMessage;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class PropertyItemMessage
/*     */   extends SecureMessage
/*     */ {
/*     */   private String m_propertyName;
/*     */   private int m_propertyType;
/*     */   private String m_stringIndex;
/*     */   private int m_intIndex;
/*     */   private byte m_byteValue;
/*     */   private short m_shortValue;
/*     */   private int m_intValue;
/*     */   private long m_longValue;
/*     */   private double m_doubleValue;
/*     */   private float m_floatValue;
/*     */   private String m_stringValue;
/*     */   
/*     */   public byte[] encode() {
/*  41 */     byte[] value, name = this.m_propertyName.getBytes();
/*     */     
/*  43 */     int valueSize = 0;
/*  44 */     switch (this.m_propertyType) {
/*     */       case 1:
/*  46 */         valueSize = 1;
/*     */         break;
/*     */       case 2:
/*  49 */         valueSize = 2;
/*     */         break;
/*     */       case 3:
/*  52 */         valueSize = 4;
/*     */         break;
/*     */       case 4:
/*  55 */         valueSize = 8;
/*     */         break;
/*     */       case 5:
/*  58 */         valueSize = 8;
/*     */         break;
/*     */       case 6:
/*  61 */         valueSize = 4;
/*     */         break;
/*     */       case 7:
/*  64 */         valueSize = this.m_stringValue.length() + 1;
/*     */         break;
/*     */     } 
/*     */     
/*  68 */     byte[] stringIndex = new byte[0];
/*  69 */     if (this.m_stringIndex != null) {
/*  70 */       stringIndex = this.m_stringIndex.getBytes();
/*     */     }
/*  72 */     ByteBuffer buffer = ByteBuffer.allocate(1 + name.length + 1 + stringIndex.length + 4 + 4 + valueSize);
/*     */     
/*  74 */     buffer.put((byte)name.length);
/*  75 */     buffer.put(name);
/*  76 */     buffer.putInt(this.m_propertyType);
/*  77 */     buffer.put((byte)stringIndex.length);
/*  78 */     buffer.put(stringIndex);
/*  79 */     buffer.putInt(this.m_intIndex);
/*     */     
/*  81 */     switch (this.m_propertyType) {
/*     */       case 1:
/*  83 */         buffer.put(this.m_byteValue);
/*     */         break;
/*     */       case 2:
/*  86 */         buffer.putShort(this.m_shortValue);
/*     */         break;
/*     */       case 3:
/*  89 */         buffer.putInt(this.m_intValue);
/*     */         break;
/*     */       case 4:
/*  92 */         buffer.putLong(this.m_longValue);
/*     */         break;
/*     */       case 5:
/*  95 */         buffer.putDouble(this.m_doubleValue);
/*     */         break;
/*     */       case 6:
/*  98 */         buffer.putFloat(this.m_floatValue);
/*     */         break;
/*     */       
/*     */       case 7:
/* 102 */         value = this.m_stringValue.getBytes();
/* 103 */         buffer.put((byte)value.length);
/* 104 */         buffer.put(value);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return crypt(buffer.array());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*     */     byte[] value;
/* 119 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*     */     
/* 121 */     byte[] name = new byte[buffer.get() & 0xFF]; buffer.get(name);
/* 122 */     this.m_propertyName = new String(name);
/* 123 */     this.m_propertyType = buffer.getInt();
/*     */     
/* 125 */     byte[] stringIndex = new byte[buffer.get() & 0xFF]; buffer.get(stringIndex);
/* 126 */     this.m_stringIndex = new String(stringIndex);
/* 127 */     this.m_intIndex = buffer.getInt();
/*     */     
/* 129 */     switch (this.m_propertyType) {
/*     */       case 1:
/* 131 */         this.m_byteValue = buffer.get();
/*     */         break;
/*     */       case 2:
/* 134 */         this.m_shortValue = buffer.getShort();
/*     */         break;
/*     */       case 3:
/* 137 */         this.m_intValue = buffer.getInt();
/*     */         break;
/*     */       case 4:
/* 140 */         this.m_longValue = buffer.getLong();
/*     */         break;
/*     */       case 5:
/* 143 */         this.m_doubleValue = buffer.getDouble();
/*     */         break;
/*     */       case 6:
/* 146 */         this.m_floatValue = buffer.getFloat();
/*     */         break;
/*     */       
/*     */       case 7:
/* 150 */         value = new byte[buffer.get() & 0xFF]; buffer.get(value);
/* 151 */         this.m_stringValue = new String(value);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 165 */     return 11;
/*     */   }
/*     */   
/*     */   public String getPropertyName() {
/* 169 */     return this.m_propertyName;
/*     */   }
/*     */   
/*     */   public void setPropertyName(String propertyName) {
/* 173 */     this.m_propertyName = propertyName;
/*     */   }
/*     */   
/*     */   public int getPropertyType() {
/* 177 */     return this.m_propertyType;
/*     */   }
/*     */   
/*     */   public void setPropertyType(int propertyType) {
/* 181 */     this.m_propertyType = propertyType;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByteValue() {
/* 186 */     return this.m_byteValue;
/*     */   }
/*     */   
/*     */   public void setByteValue(byte byteValue) {
/* 190 */     this.m_byteValue = byteValue;
/*     */   }
/*     */   
/*     */   public short getShortValue() {
/* 194 */     return this.m_shortValue;
/*     */   }
/*     */   
/*     */   public void setShortValue(short shortValue) {
/* 198 */     this.m_shortValue = shortValue;
/*     */   }
/*     */   
/*     */   public int getIntValue() {
/* 202 */     return this.m_intValue;
/*     */   }
/*     */   
/*     */   public void setIntValue(int intValue) {
/* 206 */     this.m_intValue = intValue;
/*     */   }
/*     */   
/*     */   public long getLongValue() {
/* 210 */     return this.m_longValue;
/*     */   }
/*     */   
/*     */   public void setLongValue(long longValue) {
/* 214 */     this.m_longValue = longValue;
/*     */   }
/*     */   
/*     */   public double getDoubleValue() {
/* 218 */     return this.m_doubleValue;
/*     */   }
/*     */   
/*     */   public void setDoubleValue(double doubleValue) {
/* 222 */     this.m_doubleValue = doubleValue;
/*     */   }
/*     */   
/*     */   public float getFloatValue() {
/* 226 */     return this.m_floatValue;
/*     */   }
/*     */   
/*     */   public void setFloatValue(float floatValue) {
/* 230 */     this.m_floatValue = floatValue;
/*     */   }
/*     */   
/*     */   public String getStringValue() {
/* 234 */     return this.m_stringValue;
/*     */   }
/*     */   
/*     */   public void setStringValue(String stringValue) {
/* 238 */     this.m_stringValue = stringValue;
/*     */   }
/*     */   
/*     */   public String getStringIndex() {
/* 242 */     return this.m_stringIndex;
/*     */   }
/*     */   
/*     */   public void setStringIndex(String stringIndex) {
/* 246 */     this.m_stringIndex = stringIndex;
/*     */   }
/*     */   
/*     */   public int getIntIndex() {
/* 250 */     return this.m_intIndex;
/*     */   }
/*     */   
/*     */   public void setIntIndex(int intIndex) {
/* 254 */     this.m_intIndex = intIndex;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\serverToClient\PropertyItemMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */