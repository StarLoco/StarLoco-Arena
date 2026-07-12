/*     */ package com.ankamagames.dofusarena.common.game.fighter.characteristic;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicUpdateListener;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class FighterCharacteristic
/*     */   extends AbstractCharacteristic
/*     */ {
/*  23 */   protected static final Logger m_logger = Logger.getLogger(FighterCharacteristic.class);
/*     */   
/*     */   private FighterCharacteristicType m_type;
/*     */   
/*  27 */   private CharacteristicUpdateListener<FighterCharacteristic> m_listener = null;
/*     */   public static final byte SIZE_IN_BYTE = 12;
/*     */   protected int m_currentValue;
/*     */   protected int m_maxValue;
/*     */   protected int m_minValue;
/*     */   protected int m_upperBound;
/*     */   protected int m_lowerBound;
/*     */   
/*     */   public FighterCharacteristic(FighterCharacteristicType type, int lowerBound, int upperBound) {
/*  36 */     this.m_type = type;
/*  37 */     setBounds(lowerBound, upperBound);
/*     */   }
/*     */   
/*     */   public FighterCharacteristicType getType() {
/*  41 */     return this.m_type;
/*     */   }
/*     */   
/*     */   public void setListener(CharacteristicUpdateListener<FighterCharacteristic> listener) {
/*  45 */     this.m_listener = listener;
/*     */   }
/*     */   
/*     */   private void dispatchUpdate() {
/*  49 */     if (this.m_listener != null) {
/*  50 */       this.m_listener.onCharacteristicUpdated(this);
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
/*     */   public int value() {
/*  81 */     return this.m_currentValue;
/*     */   }
/*     */   
/*     */   public int max() {
/*  85 */     return this.m_maxValue;
/*     */   }
/*     */   
/*     */   public int min() {
/*  89 */     return this.m_minValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int current) {
/*  96 */     this.m_currentValue = Math.max(this.m_minValue, Math.min(this.m_maxValue, current));
/*  97 */     dispatchUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int add(int updateValue) {
/* 105 */     set(this.m_currentValue + updateValue);
/* 106 */     return this.m_currentValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int substract(int updateValue) {
/* 114 */     set(this.m_currentValue - updateValue);
/* 115 */     return this.m_currentValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(int max) {
/* 121 */     this.m_maxValue = Math.max(this.m_lowerBound, Math.min(this.m_upperBound, max));
/* 122 */     this.m_maxValue = Math.max(this.m_maxValue, this.m_minValue);
/*     */     
/* 124 */     if (this.m_currentValue > this.m_maxValue)
/* 125 */       this.m_currentValue = this.m_maxValue; 
/* 126 */     dispatchUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMin(int min) {
/* 131 */     this.m_minValue = Math.min(this.m_upperBound, Math.max(this.m_lowerBound, min));
/* 132 */     this.m_minValue = Math.min(this.m_maxValue, this.m_minValue);
/*     */     
/* 134 */     if (this.m_currentValue < this.m_minValue)
/* 135 */       this.m_currentValue = this.m_minValue; 
/* 136 */     dispatchUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int updateMaxValue(int modification) {
/* 146 */     if (this.m_maxValue == Integer.MAX_VALUE)
/* 147 */       return this.m_maxValue; 
/* 148 */     setMax(this.m_maxValue + modification);
/* 149 */     return this.m_maxValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int updateMinValue(int modification) {
/* 159 */     if (this.m_minValue == Integer.MIN_VALUE)
/* 160 */       return this.m_minValue; 
/* 161 */     setMin(this.m_minValue + modification);
/* 162 */     return this.m_minValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBounds(int lowerBound, int upperBound) {
/* 172 */     this.m_lowerBound = Math.min(lowerBound, upperBound);
/* 173 */     this.m_upperBound = Math.max(lowerBound, upperBound);
/*     */     
/* 175 */     if (this.m_maxValue > this.m_upperBound)
/* 176 */       setMax(this.m_upperBound); 
/* 177 */     if (this.m_minValue < this.m_lowerBound)
/* 178 */       setMin(this.m_lowerBound); 
/*     */   }
/*     */   
/*     */   public byte[] serialize() {
/* 182 */     ByteBuffer bb = ByteBuffer.allocate(12);
/* 183 */     bb.putInt(this.m_currentValue);
/* 184 */     bb.putInt(this.m_minValue);
/* 185 */     bb.putInt(this.m_maxValue);
/* 186 */     return bb.array();
/*     */   }
/*     */   
/*     */   public void unserialize(byte[] data) {
/* 190 */     ByteBuffer bf = ByteBuffer.wrap(data);
/* 191 */     this.m_currentValue = bf.getInt();
/* 192 */     this.m_minValue = bf.getInt();
/* 193 */     this.m_maxValue = bf.getInt();
/* 194 */     dispatchUpdate();
/*     */   }
/*     */   
/*     */   public void makeDefault() {
/* 198 */     CharacteristicUpdateListener<FighterCharacteristic> listener = this.m_listener;
/* 199 */     this.m_listener = null;
/* 200 */     setMin(this.m_type.getDefaultMin());
/* 201 */     setMax(this.m_type.getDefaultMax());
/* 202 */     set(this.m_type.getDefaultValue());
/* 203 */     this.m_listener = listener;
/* 204 */     dispatchUpdate();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\characteristic\FighterCharacteristic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */