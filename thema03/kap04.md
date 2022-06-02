# 3.4. Exceptions

## Verschachteln 
* Es kann von einer Exception die obere Vererbungsstruktur gefangen werden, um möglichs wenige `catch{...}`-Blöcke zu haben.
* z.B. Kann eine `IOExcpetion` und eine `NFException` als `Exception` gefangen werden, und in einem einzigen catch-Block gefangen und behandelt werden.

## Throwen von Exceptions
* Exceptions können mit dem Schlüsselwort `throw` im Anhang einer Methode, einer Klasse oder als neue Exception an die Klasse/Methode geworfen werden, welche die Methode aufruft.
* Diese Klasse kann die Exception entweder weiterwerfen oder behandeln.
* Beispiele:

```java
throw new EmptyStackException();
```

```java
void testMethod() throws ArithmeticException
```

## CustomExceptions
* Durch Ableiten von Exception kann eine eigene Exception erstellt werden.
* Bsp.:

```java
public class HandledException extends Exception {
    private String code;

    public HandledException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public HandledException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
```

## Wo sollen sie behandelt werden?
* Exceptions sollten entweder
	* In der Main-Klasse behandelt werden, falls keine GUI vorhanden ist
	* Im Controller behandelt werden und dem User grafisch Bescheid gegeben werden