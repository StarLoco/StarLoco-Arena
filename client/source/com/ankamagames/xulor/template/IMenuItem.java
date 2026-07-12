package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IMenuItemPressedListener;

public interface IMenuItem extends IElement {
  void setEnabled(boolean paramBoolean);
  
  void setText(String paramString);
  
  void setOnClick(IMenuItemPressedListener paramIMenuItemPressedListener);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IMenuItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */