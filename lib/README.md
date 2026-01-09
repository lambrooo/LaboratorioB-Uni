# Librerie Esterne

Questa cartella è destinata a contenere eventuali librerie esterne (.jar) necessarie 
per la compilazione e l'esecuzione.

## Situazione Attuale

Le librerie esterne utilizzate dal progetto sono gestite tramite **Maven** e sono 
automaticamente incluse nei file JAR eseguibili grazie al plugin `maven-shade-plugin`.

I JAR in `/bin` sono "fat JARs" (uber JARs) che contengono già tutte le dipendenze:
- PostgreSQL JDBC Driver
- HikariCP (connection pool)
- SLF4J + Logback (logging)
- FlatLaf (look and feel)
- JavaFX

Non è quindi necessario aggiungere librerie esterne manualmente.

## Come Aggiungere Dipendenze

Per aggiungere nuove dipendenze al progetto:
1. Modificare i file `pom.xml` nei moduli `serverBR` e/o `clientBR`
2. Eseguire `mvn clean package` per ricompilare
