package gnu.trove;

import java.io.Serializable;

public interface TLinkable extends Serializable {
  TLinkable getNext();
  
  TLinkable getPrevious();
  
  void setNext(TLinkable paramTLinkable);
  
  void setPrevious(TLinkable paramTLinkable);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TLinkable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */