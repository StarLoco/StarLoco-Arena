package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IMouseClickListener;
import com.ankamagames.xulor.util.Alignment;
import com.ankamagames.xulor.util.Pixmap;

public interface IPopupMenu extends IComponent {
  void addLabel(String paramString, Pixmap paramPixmap);
  
  void addButton(String paramString, Pixmap paramPixmap, IMouseClickListener paramIMouseClickListener, boolean paramBoolean);
  
  void addSeparator();
  
  void show(int paramInt1, int paramInt2);
  
  void show();
  
  void setHotSpotPosition(Alignment paramAlignment);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IPopupMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */