/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.component.RenderableCollection;
/*    */ import com.ankamagames.xulor.template.IItemRenderable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XListCondition
/*    */   extends XUnaryConditionOperator
/*    */ {
/*    */   public static final String TAG = "ListCondition";
/*    */   public static final String SELECTED_KEY = "selected";
/*    */   public static final String EVEN_INDEX_KEY = "evenIndex";
/*    */   public static final String ODD_INDEX_KEY = "oddIndex";
/*    */   public static final String INDEX_KEY = "index";
/*    */   
/*    */   public String getTag() {
/* 29 */     return "ListCondition";
/*    */   }
/*    */   
/*    */   public boolean isValid(Object object) {
/* 33 */     if (this.m_comparedValueInit)
/* 34 */       object = this.m_comparedValue; 
/* 35 */     if (!(object instanceof IItemRenderable)) {
/* 36 */       return false;
/*    */     }
/* 38 */     IItemRenderable renderable = (IItemRenderable)object;
/* 39 */     RenderableCollection collection = renderable.getRenderableCollection();
/*    */     
/* 41 */     if (collection == null) {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     if (this.m_key != null) {
/* 46 */       if (this.m_key.equalsIgnoreCase("selected")) {
/* 47 */         boolean selected = (renderable == collection.getSelected());
/* 48 */         return this.m_condition.isValid(Boolean.valueOf(selected));
/* 49 */       }  if (this.m_key.equalsIgnoreCase("evenIndex")) {
/* 50 */         int index = collection.getItemIndex(renderable.getItemValue());
/* 51 */         return this.m_condition.isValid(Boolean.valueOf((index % 2 == 0)));
/* 52 */       }  if (this.m_key.equalsIgnoreCase("oddIndex")) {
/* 53 */         int index = collection.getItemIndex(renderable.getItemValue());
/* 54 */         return this.m_condition.isValid(Boolean.valueOf((index % 2 != 0)));
/* 55 */       }  if (this.m_key.equalsIgnoreCase("index")) {
/* 56 */         int index = collection.getItemIndex(renderable.getItemValue());
/* 57 */         return this.m_condition.isValid(Integer.valueOf(index));
/*    */       } 
/*    */     } 
/*    */     
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 65 */     XListCondition clone = new XListCondition();
/* 66 */     copyConditionData(clone);
/* 67 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XListCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */