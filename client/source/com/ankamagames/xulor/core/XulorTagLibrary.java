/*    */ package com.ankamagames.xulor.core;
/*    */ import com.ankamagames.xulor.binding.fenggui.component.ItemRenderer;
/*    */ import com.ankamagames.xulor.core.form.Form;
/*    */ import com.ankamagames.xulor.core.impl.XPixmap;
/*    */ import com.ankamagames.xulor.core.impl.XShortcut;
/*    */ import com.ankamagames.xulor.core.impl.XToolTip;
/*    */ import com.ankamagames.xulor.core.renderer.XAndCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XBitwiseOperation;
/*    */ import com.ankamagames.xulor.core.renderer.XCollectionCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XConditionResult;
/*    */ import com.ankamagames.xulor.core.renderer.XEqualCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XFalseCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XGreaterCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XItem;
/*    */ import com.ankamagames.xulor.core.renderer.XItemCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XLessCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XListCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XNotCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XNotNullCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XOrCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XTrueCondition;
/*    */ import com.ankamagames.xulor.core.renderer.XValueReplacer;
/*    */ 
/*    */ public abstract class XulorTagLibrary extends TagLibrary {
/*    */   public XulorTagLibrary() {
/* 26 */     registerDefaultTags();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void registerDefaultTags() {
/* 33 */     registerTag("Form", Form.class);
/* 34 */     registerTag("Shortcut", XShortcut.class);
/* 35 */     registerTag("Pixmap", XPixmap.class);
/* 36 */     registerTag("ItemRenderer", ItemRenderer.class);
/* 37 */     registerTag("Data", XData.class);
/* 38 */     registerTag("Item", XItem.class);
/* 39 */     registerTag("ItemCondition", XItemCondition.class);
/* 40 */     registerTag("ListCondition", XListCondition.class);
/* 41 */     registerTag("And", XAndCondition.class);
/* 42 */     registerTag("Or", XOrCondition.class);
/* 43 */     registerTag("Not", XNotCondition.class);
/* 44 */     registerTag("Condition", XConditionResult.class);
/* 45 */     registerTag("isEqual", XEqualCondition.class);
/* 46 */     registerTag("isGreater", XGreaterCondition.class);
/* 47 */     registerTag("isLess", XLessCondition.class);
/* 48 */     registerTag("isNull", XNullCondition.class);
/* 49 */     registerTag("isNotNull", XNotNullCondition.class);
/* 50 */     registerTag("isTrue", XTrueCondition.class);
/* 51 */     registerTag("isFalse", XFalseCondition.class);
/* 52 */     registerTag("StringCondition", XStringCondition.class);
/* 53 */     registerTag("CollectionCondition", XCollectionCondition.class);
/* 54 */     registerTag("ToolTip", XToolTip.class);
/* 55 */     registerTag("ValueReplacer", XValueReplacer.class);
/* 56 */     registerTag("BitwiseOperation", XBitwiseOperation.class);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\XulorTagLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */