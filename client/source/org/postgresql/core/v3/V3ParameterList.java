package org.postgresql.core.v3;

import java.sql.SQLException;
import org.postgresql.core.ParameterList;

interface V3ParameterList extends ParameterList {
  void checkAllParametersSet() throws SQLException;
  
  SimpleParameterList[] getSubparams();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\V3ParameterList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */