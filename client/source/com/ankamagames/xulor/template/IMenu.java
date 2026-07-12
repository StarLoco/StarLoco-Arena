package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IMenuClosedListener;

public interface IMenu extends IComponent {
  int getItemCount();
  
  IComponent getItem(int paramInt);
  
  void setText(String paramString);
  
  void setOnClose(IMenuClosedListener paramIMenuClosedListener);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */