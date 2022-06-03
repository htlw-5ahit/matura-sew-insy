# 9.3. Prozedurale Erweiterung von SQL-Datenbanken

## Was versteht man unter „Prozedurale Erweiterung“ einer SQL-Datenbank?

* PL/SQL verbindet die Abfragesprache SQL mit einer prozeduralen Programmiersprache.
* Erweiterung von SQL um prozedurale Elemente innerhalb der SQL-Umgebung, PL/SQL (Procedural language extensions to SQL).
* “Stored Procedures/Functions” innerhalb der Datenbank
* Direkter Zugriff auf Datenbankinhalt

Quelle:
* [https://de.wikipedia.org/wiki/PL/SQL](https://de.wikipedia.org/wiki/PL/SQL)
* [https://www.dbis.informatik.uni-goettingen.de/Teaching/DBP-SS18/fol-plsql-2on1.pdf](https://www.dbis.informatik.uni-goettingen.de/Teaching/DBP-SS18/fol-plsql-2on1.pdf)

## Wer hat PL/SQL eingeführt?

* Oracle

## Charakterisieren Sie die Syntax von PL/SQL

* PL/SQL-Programme bestehen aus Blöcken:

```sql
declare
    -- Deklarationsblock
begin
    -- Ausführungsteil
exception
    -- Ausnahmeverarbeitung
end;
```

## Wo werden diese gespeichert und welche Vor- bzw. Nachteile hat PL/SQL?

* Speicherort
    * Stored Procedures, Functions und Packages sind Prozeduren und Funktionen, die in der Datenbank gespeichert und verwaltet werden. Sie werden in übersetzter Form abgespeichert.
* Vorteile
    * Hochperformant, da die eigentliche Arbeit innerhalb der Datenbank geschieht
    * Verringerung des Netzwerkverkehrs durch Block-Statements (DECLARE, BEGIN,EXCEPTION and END)
    * Keine Zeitverluste durch (suboptimale) Treiber
    * Möglichkeit der Massenverarbeitung durch Bulk Binds
    * Möglichkeit der nativen Kompilierung zur Beschleunigung von rechenintensiven Arbeiten
    * Universell und plattformübergreifend auf jedem Betriebssystem
* Nachteile
    * Wartung ist aufwendiger
    * Datensätze müssen sich an die vorgesetzte Struktur halten
    * Datenschema muss vorhanden sein

Quelle: [https://www.datenbank-plsql.de/packages.htm](https://www.datenbank-plsql.de/packages.htm)

## Inwieweit ist es sinnvoll, die sogenannte „Business Logik“ – also Funktionalität von Geschäftsobjekten – in den PL/SQL-Raum zu verlagern?

* Es ist sinvoll, wenn es einen standardisierten Ablauf gibt, wie die Geschäftsobjekte verwaltet werden.
* Parameter, Rückgabewert, Wertebereiche, ...

## Welche Konsequenzen ergeben sich damit auf den Software-Lifecycle?

* Zu Beginn müssen alle Prozedueren geplant bzw. erstellt werden.
* Diese müssen auch von der Software unterstützt und verwendet werden.

## Kann eine JDBC-Anwendung eine PL/pgSQL-Funktion nutzen; wenn ja: wie wird eine Nutzung ausschauen?

* Ja

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM your_func()");
```

## Was versteht man unter einer View?

* Gespeicherte SQL-Select-Abfrage
* [siehe Thema 09 / Kapitel 02](./kap02.md)

## Welche Operationen können darauf ausgeführt werden?

* `INSERT`, `UPDATE`, `DELETE`
* [siehe Thema 09 / Kapitel 02](./kap02.md)

## Welche Befehle stehen für das Einfügen/Updaten von Daten unter SQL zur Verfügung?

* `INSERT`
* `UPDATE`

## Grenzen Sie Trigger, Stored Procedures und Functions voneinander ab.

#### Stored Procedure

* Eine Stored Procedure ist ein Satz vorkompilierter SQL (Structured Query Languages), sodass sie von mehreren Programmen wiederverwendet und gemeinsam genutzt werden kann.
* Es kann auf Daten in einer Datenbank zugreifen oder diese ändern.

```sql
Create proc Proc_name  
@permater  
as begin  
----Query here  
end
```

#### Functions

* Grundsätzlich handelt es sich auch um eine Reihe von SQL-Anweisungen, die nur Eingabeparameter akzeptieren und eine Ausgabe in Form eines einzelnen Werts oder in Tabellenform erzeugen.

```sql
create function funname(@parmeter datatype)  
returns Returntype   
as    
begin Returntype  
end
```

#### Trigger

* Ein Trigger ist auch eine Reihe von SQL-Anweisungen in der Datenbank, die automatisch ausgeführt werden, wenn ein besonderes Ereignis in der Datenbank auftritt, wie Einfügen, Löschen, Aktualisieren usw.

```sql
create trigger trigger_name   
before | after    
{insert | update | delete}    
on table_name    
for each row    
---Query here
```

Quelle: [https://www.c-sharpcorner.com/blogs/about-store-proc-function-trigger-in-brif](https://www.c-sharpcorner.com/blogs/about-store-proc-function-trigger-in-brif)

## Welche unterschiedlichen Parameter gibt es und wie können diese verwendet werden.

* Ein Parameter hat einen von drei Modi:
    * `IN`: Call-by-Value (mit Initialwert)
    * `OUT`: Call-by-Reference (ohne Initialwert)
    * `INOUT`: Kombination aus `IN` und `OUT`

```sql
DELIMITER $$

CREATE PROCEDURE GetOrderCountByStatus (
	IN  orderStatus VARCHAR(25),
	OUT total INT
)
BEGIN
	SELECT COUNT(orderNumber)
	INTO total
	FROM orders
	WHERE status = orderStatus;
END$$

DELIMITER ;
```

```sql
CALL GetOrderCountByStatus('Shipped', @total);
SELECT @total;
```

Quelle:
* [StoredProcedures_MySQL (S. 12-14)](../archiv/insy-game/jahrgang4/StoredProcedures_MySQL.pdf)
* [https://www.mysqltutorial.org/stored-procedures-parameters.aspx](https://www.mysqltutorial.org/stored-procedures-parameters.aspx)

## Was ist der Unterschied zwischen PreparedStatement, Stored Procedures und Stored Function?

* Stored Procedure werden vorkompiliert
* Functions werden immer vor dem Ausführen erneut kompiliert

Der Unterschied besteht darin, dass Sie Prepared Statements nicht speichern können. Sie müssen sie jedes Mal "vorbereiten", wenn Sie eine ausführen müssen. Stored Procedures hingegen können gespeichert und einem Schema zugeordnet werden, aber Sie müssen PL/SQL beherrschen, um sie zu schreiben.

Quelle: [https://stackoverflow.com/questions/7296417/difference-between-stored-procedures-and-prepared-statements](https://stackoverflow.com/questions/7296417/difference-between-stored-procedures-and-prepared-statements)

## Wie kann aus einer objektorientierten Sprache wie Java PL/SQL genutzt werden (Beispiel)?

* Ja.

```java
// connect to a local XE database as user HR
OracleDataSource ods = new OracleDataSource();
ods.setURL("jdbc:oracle:thin:hr/hr@localhost:1521/XE");
Connection conn = ods.getConnection();

// call the PL/SQL procedures with the three parameters
// the first two string parameters (1 and 2) are passed to the procedure
// as command-line arguments
// the REF CURSOR parameter (3) is returned from the procedure
String jobquery = "begin get_emp_info(?, ?, ?); end;";
CallableStatement callStmt = conn.prepareCall(jobquery);
callStmt.registerOutParameter(3, OracleTypes.CURSOR);
callStmt.setString(1, args[0]);
callStmt.setString(2, args[1]);
callStmt.execute();

// return the result set
ResultSet rset = (ResultSet)callStmt.getObject(3);

// determine the number of columns in each row of the result set
ResultSetMetaData rsetMeta = rset.getMetaData();
int count = rsetMeta.getColumnCount();

// print the results, all the columns in each row
while (rset.next()) {
    String rsetRow = "";
    for (int i=1; i<=count; i++){
            rsetRow = rsetRow + " " + rset.getString(i);
    }
    System.out.println(rsetRow);
}
```

Quelle: [https://docs.oracle.com/cd/B25329_01/doc/appdev.102/b25108/xedev_jdbc.htm](https://docs.oracle.com/cd/B25329_01/doc/appdev.102/b25108/xedev_jdbc.htm)

## Was ist ein Cursor (wofür wird dieser eingesetzt, wie schaut die Struktur aus)?

* Um eine Ergebnismenge innerhalb einer gespeicherten Prozedur zu verarbeiten, verwendet man einen Cursor.
* Mit einem Cursor kann man eine Reihe von Zeilen durchlaufen, die von einer Abfrage zurückgegeben werden, und jede Zeile einzeln verarbeiten.

* Typen von Cursors
    * Read-only (Schreibgeschützt)
        * Sie können Daten in der zugrunde liegenden Tabelle nicht über den Cursor aktualisieren.
    * Non-scrollable (Nicht scrollbar)
        * Man kann Zeilen nur in der durch die SELECT-Anweisung festgelegten Reihenfolge abrufen.
    * Asensitive (Unempfindlich)
        * Ein asensitiver Cursor zeigt auf die aktuellen Daten (schneller), wohingegen ein insensitiver Cursor eine temporäre Kopie der Daten verwendet.
        * Jede Änderung, die an den Daten von anderen Verbindungen vorgenommen wird, wirkt sich jedoch auf die Daten aus, die von einem asensitiven Cursor verwendet werden

```sql
-- First declare a Cursor
DECLARE cursor_name CURSOR FOR SELECT_statement;

-- Declare Handler
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

-- Open the Cursor
OPEN cursor_name;

-- Fetch the Data
FETCH cursor_name INTO varables list;

-- Close Cursor
CLOSE cursor_name;
```

![](https://www.mysqltutorial.org/wp-content/uploads/2009/12/mysql-cursor.png)

```sql
DELIMITER $$
CREATE PROCEDURE createEmailList (
	INOUT emailList varchar(4000)
)
BEGIN
	DECLARE finished INTEGER DEFAULT 0;
	DECLARE emailAddress varchar(100) DEFAULT "";

	-- declare cursor for employee email
	DEClARE curEmail 
		CURSOR FOR 
			SELECT email FROM employees;

	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

	OPEN curEmail;

	getEmail: LOOP
		FETCH curEmail INTO emailAddress;
		IF finished = 1 THEN 
			LEAVE getEmail;
		END IF;
		-- build email list
		SET emailList = CONCAT(emailAddress,";",emailList);
	END LOOP getEmail;
	CLOSE curEmail;

END$$
DELIMITER ;
```

Quelle: [StoredProcedures_MySQL (S. 24-25)](../archiv/insy-game/jahrgang4/StoredProcedures_MySQL.pdf)

## Welche einsetzbaren Tools gibt es?

* Oracle SQL Developer

## Gibt es Debugger für PL/SQL-Systeme?

* Ja.

## Kompatibilität von PL/SQL (Oracle/MySQL/Postgres/MariaDB/..).

* Oracle: Unterstützt PL/SQL
* MySQL: Kein PL/SQL
* Postgres: Hat PL/pgSQL – ähnlich wie PL/SQL
* MariaDB: sql_mode muss auf Oracle gestellt sein
* Microsoft SQL-Server: hat TSQL – ähnlich wie PL/SQL

## Was versteht man unter SQL-Injection und wie kann man sich davor schützen? 

* Ist eine Code-Injection-Technik, die Ihre Datenbank zerstören kann.
* Ist eine der häufigsten Web-Hacking-Techniken.
* Ist das Platzieren von bösartigem Code in SQL-Anweisungen über Webseiteneingaben.
* Schutz bietet ein Prepared Statement

Quelle: [https://www.w3schools.com/sql/sql_injection.asp](https://www.w3schools.com/sql/sql_injection.asp)