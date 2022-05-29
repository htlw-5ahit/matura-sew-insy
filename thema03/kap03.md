# 3.3. Threads und Synchronisierung

## Synchronized
* Java verwendet die Methode synchronized(), um einen Monitor zu erstellen, und jedes Objekt kann als Monitor dienen. 
* Ein Monitor verwendet eine Funktion, die mit einem bestimmten Datenelement/Variable als Sperre verbunden ist. 
* Wenn ein Thread versucht, auf diese Daten zuzugreifen und sie zu ändern, schränkt der Monitor diesen Prozess ein und hält ihn fest, bis der aktuelle Thread seine Ausführung beendet hat.
* Bsp.:

```java
synchronized void showMsg(String  msg){ //synchronized method  
		for(int i=1;i<=5;i++){  
			System.out.println(msg);  
			try{  
				Thread.sleep(500);  
			}catch(Exception e){
				System.out.println(e);
			}  
		}  
	}  
```

https://www.delftstack.com/howto/java/monitor-in-java/

## Locks
* flexiblerer und ausgefeilterer Thread-Synchronisierungsmechanismus als der synchronisierte Block
* Die Lock-API bietet eine Methode lockInterruptibly(), mit der der Thread unterbrochen werden kann, wenn er auf die Sperre wartet
* Eine gesperrte Instanz sollte immer entsperrt werden, um Deadlocks zu vermeiden. 
* Locks sollten einen try/catch- und einen finally-Block enthalten.
* Bsp.:

```java
Lock lock = ...; 
lock.lock();
try {
    // access to the shared resource
} finally {
    lock.unlock();
}
```

### ReentrantLock
* Standardklasse für Verwendung von Locks
* Bsp.:

```java
public class SharedObject {
    //...
    ReentrantLock lock = new ReentrantLock();
    int counter = 0;

    public void perform() {
        lock.lock();
        try {
            // Critical section here
            count++;
        } finally {
            lock.unlock();
        }
    }
    //...
```

## Synchronized Collections
* Thread-Safe Collections
* synchronizedCollection()
* synchronizedList()
* synchronizedMap()
* synchronizedSortedMap()
* synchronizedSet()
* synchronizedSortedSet()
* Bsp.:

```java
Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
```

## ConcurrentCollections
* BlockingQueue 
* ConcurrentMap
	* Standardimplementierung: ConcurrentHashMap
* ConcurrentNavigableMap
	* Standardimplementierung: ConcurrentSkipListMap

## Unterschied Concurrent/Synchornized Collections
* Collections.synchronizedCollection() sollte verwendet werden, wenn Konsistenz am Wichtigsten ist
	* Das gesamte Objekt wird für lese- und Schreiboperationen gelockt
* ConcurrentCollections sollten verwendet werden, wenn die performance am Wichtigsten ist
	* Die Collection wird in Segmente geteilt und sie können einzeln gelockt werden, um mehrere Veränderungen auf einmal durchzuführen

# Semaphoren
* Verwendet um die Anzahl der gleichzeitigen Threads, die auf eine bestimmte Ressource zugreifen, zu begrenzen
* Bsp.:

HIER BEISPIEL EINFÜGEN

HIER BEISPIEL EINFÜGEN

HIER BEISPIEL EINFÜGEN

HIER BEISPIEL EINFÜGEN

HIER BEISPIEL EINFÜGEN

HIER BEISPIEL EINFÜGEN

