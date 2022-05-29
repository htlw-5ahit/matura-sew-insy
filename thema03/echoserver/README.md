# 4302_EchoServer

## Aufgabe:
Entwickle einen Echo Server plus den dazu passenden Client

### Server
- beim Start gibt der Server die *lokale Socket Adresse* in der Konsole aus (Recherche Internet/JavaAPI)
- Server wartet auf *Port 2007* auf eingehende Verbindungen
- jede eingehende Verbinung wird in einem *EIGENEN Thread* bearbeitet
- der Server kann somit mehrere Clients parallel bedienen
- vom Client wird ein Text geschickt, dieser wird als Echo an den Client zurück gesendet
- sendet der Client als TEXT die Zeichenkette "QUIT", wird die Verbindung getrennt

### Client
- baut Verbindung mit Server auf Port 2007 auf
- die *entfernte Socket Adresse* soll in der Konsole ausgegeben werden
- User kann beliebigen Text eingeben, welcher an den Server geschickt wird
- im Anschluss wartet er auf das empfangene Echo, welches er am Bildschirm ausgegeben


### Erweiterung
- baue den EchoServer um und verwende einen ThreadPool!
