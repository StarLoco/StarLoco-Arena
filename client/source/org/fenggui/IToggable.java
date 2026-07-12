package org.fenggui;

public interface IToggable<E> {
  boolean isSelected();
  
  IToggable setSelected(boolean paramBoolean);
  
  E getValue();
  
  String getText();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\IToggable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */