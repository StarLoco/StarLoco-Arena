package org.postgresql.core.v3;

import org.postgresql.core.Query;

interface V3Query extends Query {
  SimpleQuery[] getSubqueries();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\V3Query.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */