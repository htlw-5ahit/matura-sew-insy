# 2.3. dynamische Bindung, Polymorphie, überschreiben/überladen

## Dynamische Bindung

* Dynamic Binding löst die Bindung zur Laufzeit auf, da der Compiler nicht weiß, welche Implementierung zur Kompilierzeit verwendet werden soll.
* Verwendet das Objekt zum Binden und nicht den Typ/Klasse
* Bsp.: Überschreiben von Methoden

* Anwendungsbeispiel:
```java
class School {
    public void ringBell() {
        System.out.println("Ringing the school bell...");
    }
}
class Classroom extends School {
    @Override
    public void ringBell() {
        System.out.println("Ringing the classroom bell...");
    }
}
public class Main {
    public static void main(String[] args) {
        School s1 = new School(); //Type is School and object is of School
        s1.ringBell();
        Classroom c1 = new Classroom(); //Type is Classroom and object is of Classroom
        c1.ringBell();
        School s2 = new Classroom(); //Type is School and object is of Classroom
        s2.ringBell();
    }
}
```

## Methoden (Signatur, Parameter, Überschreiben (Override), Overload)

### Signatur
* Kombination aus dem Methodennamen und der Parameterliste.

### Überschreiben (@Override)
* `@Override`
* Methodenkörper in der Unterklasse überschreiben.
* Bsp.:

```java
class ParentClass {
	public void displayMethod(String msg) {
		System.out.println(msg);
	}
}
class SubClass extends ParentClass {
	@Override
	public void displayMethod(String msg) {
		System.out.println("Message is: "+ msg);
	}
	public static void main(String args[]) {
		SubClass obj = new SubClass();
		obj.displayMethod("Hey!!");
	}
}
```

### Overload
* Bei Methodenüberladung können mehrere Methoden denselben Namen mit unterschiedlichen Parametern haben.
* Bsp.:

```java
int myMethod(int x)
float myMethod(float x)
double myMethod(double x, double y)
```

## Polymorphie

### Statische Polymorphie
* Overloading von Methoden

### Dynamische Polymorphie
* Methodenkörper wird in der Unterklasse umgeschrieben
* Zur Laufzeit wird entschieden, welche Methode aufgerufen wird
* Bsp.:

```java
Tier[] tiere = {
    new Vogel(),
    new Wurm()
};

for (int i = 0; i < tiere.length; i++) {
    tiere[i].bewegtSich(); //einmal bewegtSich() von Vogel, einmal bewegtSich() von Wurm
}
```

## Generics (<>, z.B Task/Service, Collections)
* Werden mit <> gekennzeichnet
* In <> kann bei der Verwendung eine beliebige Klasse eingegeben werden
* In <> steht Variablenname, z.B. < E >
* Verwendet bei:
	* Task/Service
	* Collections
* Bsp.:

```java
HashSet<Person> personen = new HashSet<>();
```