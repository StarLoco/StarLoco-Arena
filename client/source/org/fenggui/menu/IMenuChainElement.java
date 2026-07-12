package org.fenggui.menu;

public interface IMenuChainElement {
  void closeForward();
  
  void closeBackward();
  
  IMenuChainElement getNextMenu();
  
  IMenuChainElement getPreviousMenu();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\IMenuChainElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */