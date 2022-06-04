# 5.2. JavaFX Properties & Bindings, Multithreading

## Properties
* Bsp.:
```java
public class MyBean {
	private StringProperty sample = new SimpleStringProperty();
	
	public String getSample() {
		return sample.get();
	}

	public void setSample(String value) {
		sample.set(value);
	}
	
	public StringProperty sampleProperty() {
		return sample;
	}
}
```

### Einfache Properties (abstrakte Klassen):
* BooleanProperty
* DoubleProperty
* FloatProperty
* IntegerProperty
* LongProperty
* StringProperty

### Erzeugen von Properties über konkrete Klassen (Konstruktor mit maximalen Parametern)

```java
BooleanProperty booleanProperty = new
SimpleBooleanProperty(true, „b″, this);

DoubleProperty doubleProperty = new
SimpleDoubleProperty(1.5, „d″, this);

FloatProperty floatProperty = new
SimpleFloatProperty(1.5f, „f″, this);

IntegerProperty integerProperty = new
SimpleIntegerProperty(123, „i″, this);

LongProperty longProperty = new
SimpleLongProperty(1234567899l, „l″, this);

StringProperty stringProperty = new
SimpleStringProperty("hallo", „s″, this);
```

### Object Properties
* speichern beliebige Objekte

```java
ObjectProperty<Image> objectProperty =
new SimpleObjectProperty<>();
```

## Bindings
* Bindings „verknüpfen“ Properties mit Werten

```java
label.textProperty().bind(myBean.sampleProperty());
````

* Binding lösen

```java
label.textProperty().unbind();
```
* Im Controller erstellt
* Entweder durch
	* Implements Initializable (Interface) oder
	* @FXML initialize

### Numerische Bindings
* .add / .substract
* .multiply / .divide
* .negate
* .min / .max
* Bsp.:

```java
DoubleProperty number1 = new SimpleDoubleProperty(1);
DoubleProperty number2 = new SimpleDoubleProperty(2);
DoubleProperty number3 = new SimpleDoubleProperty(3);

NumberBinding calculated = Bindings.add(
number1, Bindings.multiply(number2,number3));
```

### Fluent-API

```java
DoubleProperty number1 = new SimpleDoubleProperty(1);
DoubleProperty number2 = new SimpleDoubleProperty(2);
DoubleProperty number3 = new SimpleDoubleProperty(3);

NumberBinding calculated =
number1.add(number2.multiply(number3));
```

### Low-Level-API

```java
DoubleProperty number1 = new SimpleDoubleProperty(1);
DoubleProperty number2 = new SimpleDoubleProperty(2);
DoubleProperty number3 = new SimpleDoubleProperty(3);
NumberBinding calculated = new DoubleBinding() {
   {
     super.bind(number1, number2, number3);
   }
   @Override
   protected double computeValue() {
     return number1.get() + (number2.get() * number3.get());
   }
};
```

### Bidirektionale Bindings
* 2 Properties können gegenseitig aneinander gebunden werden

```java
DoubleProperty number1 = new SimpleDoubleProperty(1);
DoubleProperty number2 = new SimpleDoubleProperty(2);
number2.bindBidirectional(number1);
```

* Dann können Sie auch gebundene Properties gesetzt werden:
```java
number2.setValue(3);
number1.setValue(4);
System.out.println(„number2 hat Wert: „ + number2.getValue());
```

### Object Bindings
* mit ObjectBindings können beliebige Objekte an Properties gebunden werden
* Vorgehensweise:
	* eigene Klasse ableiten von ObjectBinding<T>
	* im Konstruktor ein passendes Property annehmen (muss nicht vom Typ T sein!)
	* Property als Attribut speichern
	* `T computeValue()` Methode implementieren, die ein
	* Objekt retourniert, welches über das Property gebindet wurde


### Boolean Bindings
* Boolean Bindings: Logische Verknüpfung von BooleanProperty
	* `.greaterThan` / `.greaterThanOrEqualTo`
	* `.isEqualTo` / `.isNotEqualTo`
	* `.lessThan` / `.lessThanOrEqualTo`
	* `.and` / `.or`
	* `isNotEmpty`
* Bsp:
* in einem Textfield überprüfen, ob Eingabe mit mindestens 3 Zeichen:

```java
BooleanBinding textFieldEntered =
textField.textProperty()
.isNotEmpty()
.and(textField.textProperty().length().greaterThan(3));
```

* Button soll deaktiviert werden, wenn im Textfield nichts steht

```java
button.disableProperty().bind(textFieldEntered.not());
```

## Serialisierung von Properties
* Properties können NICHT serialisiert werden
	* daher müssen sie in dem Fall mit transient gekennzeichnet werden
	* sie werden dann aber auch NICHT gespeichert!

```java
public class MyBean implements Serializable {
	private transient StringProperty sample = new SimpleStringProperty();
	public String getSample() {
		return sample.get();	
	}
	
	public void setSample(String value) {
		sample.set(value);
	}
	
	public StringProperty sampleProperty() {
		return sample;
	}
}
```

* Möchte man Datenobjekte mit Properties serialisieren, dannmuss man muss man die Serialisierung selbst in die Handnehmen (z.B. StringProperty):

```java
public class xyz implements Externalizable {
    private SimpleStringProperty x = new SimpleStringProperty("");
 
@Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setX ((String) in.readObject());
    }
 
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getX());
    }

```

* Klasse mit Properties Serialisieren:

```java
public class Packet implements Externalizable {
    private static final long serialVersionUID = -8256294089416034037L;
    private SimpleStringProperty varName = new SimpleStringProperty("");
    private SimpleStringProperty varValue = new SimpleStringProperty("");
 
    public Packet() {
        this("", "");
    }
    public Packet(String varName, String varValue) {
        setVarName(varName);
        setVarValue(varValue);
    }
    public String getVarName() {
        return varName.get();
    }
    public void setVarName(String var) {
        varName.set(var);
    }
   
    public String getVarValue() {
        return varValue.get();
    }
    public void setVarValue(String value) {
        varValue.set(value);
    }
    public SimpleStringProperty getVarNameProperty() {
        return varName;
    }
    public SimpleStringProperty getVarValueProperty() {
        return varValue;
    }
 
    @Override
    public String toString() {
        return getVarName() + ": " + getVarValue();
    }
 
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setVarName((String) in.readObject());
        setVarValue((String) in.readObject());
}
 
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getVarName());
        out.writeObject(getVarValue());
    }

```

## ListView

```java
// Definiere ListProperty -> es sollen Strings gespeichert werden
ListProperty<String> listProperty = new SimpleListProperty<>();

// Erstelle Collection mit Inhalt
List<String> iosList = new ArrayList<>();

iosList.add("iPhone 4");
iosList.add("iPhone 5");
iosList.add("iPhone 6");
iosList.add("iPhone SE");
iosList.add("iPhone 8");

// setzen der Werte durch umwandeln einer ArrayList in eine observableList
listProperty.set(FXCollections.observableArrayList(iosList));

// binde die ListView Items an die ListProperty
myListView.itemsProperty().bind(listProperty);

// ändere Elemente in der listProperty
listProperty.add("iPhone11");
listProperty.remove(0);
```

* ... `myListView.getSelectionModel().getSelectedItem()` liefert das ausgewählte Item
* ... `myListView.getSelectionModel().selectedItemProperty()` liefert das Property des ausgewählten Elements für Bindings

## Task 
* implementiert das Worker Interface
* Verwendung für einmalige Hintergrundberechnungen (abgeleitet von FutureTask)
* Task implementiert Runnable, somit auch Start über Executor möglich
* Ergebnis des Tasks mit Methode `get()`, wenn Berechnung zu Ende ist
	* Berechnung noch nicht am Ende: `get()` blockiert
* in `call()` Methode wird Arbeit verrichtet und gegebenenfalls Properties aktualisiert

* Fortschritt aktualisieren:
	* in `call()` wird Methode updateProgress aufrufen
	* über `task.progressProperty()` kann ein Binding auf z.B. eine Progressbar realisiert werden

* Tasks unterbrechen
	* vgl. „interrupt“ in Threads
	* im Controller wird mit myTask.cancel() versucht den Task zu beenden
	* bei Tasks: in Methode „call“ prüfen auf isCancelled()

* Aktionen nach Beendigung des Tasks:

```java
task.setOnSucceeded((WorkerStateEvent event) -> {
   Object value = task.getValue();
   // do anything with the result
   updateTheUI(value);
});

// setOnFailed
// setOnScheduled
// setOnCanceled
// setOnRunning
```

## Service
* Klasse Service
	* verwaltet einen Task
	* Tasks können über Service mehrfach ausgeführt werden
	* Task ohne Service kann nur 1x ausgeführt werden!
	
* Klasse ScheduledService
	* führt Tasks in vorbestimmten Intervallen

* Klasse Service - wichtige Methoden:
	* `start()` - startet den Service
	* `reset()` - resettet den Service, funktioniert aber nur, wenn Thread in finished Status ist (SUCCEEDED, FAILED, CANCELLED, READY)
	* `restart()` - laufender Thread wird gecancelt und dann neu gestartet
	* `cancel()` - canceled laufenden Thread

* Task definieren:

```java
public CounterTask extends Task<Integer>{
   public CounterTask(int max) {
     this.max = max;
     updateMessage("Ready to count...");
   }
   @Override protected Integer call() throws Exception {
     updateMessage("Counting...");
     for (int i = 0; i < max; i++) {
       Thread.sleep(10);
       updateProgress(i, max);
     }
     updateMessage("READY");
     return max;
   }
}
```

* Service definieren:

```java
public class CounterService extends Service<Integer>{
   private final int max;
   public CounterService(int max) {
     this.max = max;
   }
   @Override
   protected Task<Integer> createTask() {
     return new CounterTask(max);
   }
}
```

### ScheduledService
* führt Tasks in vorbestimmten Intervallen wieder aus
* ScheduledService definieren

```java
public class CounterService extends ScheduledService<Integer>{
   private final int max;
   public CounterService(int max) {
	 super();
	 setPeriod(Duration.seconds(2));
     this.max = max;
   }
   @Override
   protected Task<Integer> createTask() {
     return new CounterTask(max);
   }
}
```

* Bei Task/Service Wert (z.B. in GUI) an valueProperty binden - somit ist Wert immer aktuell

```java
Label label = new Label();
label.textProperty().bind(Bindings.concat("Value: ",
    counterService.valueProperty()));
```

* bei ScheduledService wird valueProperty regelmäßig null sein, da der Service immer wieder neu gestartet wird (und somit der Wert zurückgesetzt wird)
	* daher gibt es die Property lastValue

```java
label.textProperty().bind(Bindings.concat("Value: ",
counterService.lastValueProperty()));
```

* Abbruch bei Misserfolg => Service muss manuell wieder gestartet werden!

```java
counterService.setRestartOnFailure(false);
counterService.start();
```

* Festlegen, wie oft es der Service im Fehlerfall versuchen soll:

```java
counterService.setRestartOnFailure(true);
counterService.setMaximumFailureCount(3);
counterService.start();
```

* Nach Fehler ist es meist nicht sinnvoll es sofort neu zu versuchen
* Daher unterschiedliche Strategien:
	* LOGARITHMIC_BACKOFF_STRATEGY
	* EXPONENTIAL_BACKOFF_STRATEGY
	* LINEAR_BACKOFF_STRATEGY

```java
counterService.setRestartOnFailure(true);
counterService.setMaximumFailureCount(3);
counterService.setBackoffStrategy(
	ScheduledService.EXPONENTIAL_BACKOFF_STRATEGY );
counterService.start();
```

## Progress anzeigen (unwahrscheinlich zur Matura)

```java
Task task = new Task<Void>() {
	@Override public Void call() {
		static final int max = 1000000;
		for (int i=1; i<=max; i++) {
		if (isCancelled()) {
			break;
		}
		updateProgress(i, max);
	}
	return null;
	}
};

ProgressBar bar = new ProgressBar();
bar.progressProperty().bind(task.progressProperty());
new Thread(task).start();
```

## Platform.runLater (unwahrscheinlich zur Matura)
* Soll eine GUI Komponente von einem Nicht-GUI-Thread heraus modifiziert werden, so kann Platform.runLater verwendet werden
* public static void runLater(Runnable runnable)
	* die Aufgabe wird in den GUI Thread eingereiht und frühest möglich abgearbeitet
	* kleinere Aufgaben können ebenso mit Platform.runlater() realisiert werden
	* größere/rechenintensivere Aufgaben mittels Threads!
	
* Annahme: Eine ListView wird über ein Property an einenService „gebindet“
	* listView.itemsProperty().bind(myListService.resultProperty());	
* in dem Fall muss eine Änderung der ListView über runlater realisiert werden:
	* Platform.runLater(() -> result.add("Element " + finalI));
