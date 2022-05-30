# 4.1. Streams und Reader

## Allgemein
* Streams immer schließen, daher am Besten mit TryWithRessources verwenden
* Bei Sockets immer zuerst die OutputStreams öffnen oder kreuzen, niemals auf beiden Seiten zuerst die InputStreams öffnen
* Falls man ein Objekt über einen Stream schreiben/lesen möchte muss man das Interface 'Serializable' implementieren und das Attribut 'SerialVersionUID' mit einer eindeutigen Nummer initialisieren.
*  Streams können "verschachtelt" werden: `new DataInputStream(new BufferedInputStream(new FileInputStream(file)))`

## Streams/Reader
* BinaryStreams (Input-/OutputStream)
  - DataInputStream/DataOutputStream
  - ObjectInputStream/ObjectOutputStream

![](./Streams.png)

* CharacterStreams (Reader/Writer)
  - BufferedReader/BufferedWriter
  - PrintWriter

## Dateien
* Bei Dateien Streams immer buffern
* Falls man einen Stream erneut auf ein File öffnen würde, so würde man den Inhalt überschreiben
  - Falls man etwas anhängen möchte, so kann man im FileWriter/FileOutputStream-Konstruktor als 2. Argument true übergeben und dadurch append aktivieren
* Binär: FileInputStream/FileOutputStream
* Text: FileReader/FileWriter


## Beispiel
* [https://github.com/htlw-5ahit/matura-sew-insy/tree/main/thema04/streams](https://github.com/htlw-5ahit/matura-sew-insy/tree/main/thema04/streams)
