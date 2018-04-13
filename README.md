# RB Car Race

1. Implementieren Sie in JAVA einen Spieleserver mit Multi-Threading, der Client-Anfragen auf Grundlage eines eigenen TCP Protokolls verarbeitet.
2. ZieldesDienstes:CarRace
Der Spieleserver ermöglicht nach dem Start oder nach einem Rennen eine Setup-Time (von z.B. 30 Sekunden), in dem unbestimmt viele Clients sich mit beliebig vielen Fahrzeu- gen anmelden können, um an einem Rennen teilzunehmen.
Der Spieleserver muss Eingaben von Clients in Form einer Registrierung (RegisterCar) von Fahrzeugen über eine zu bestimmende Zeit (wie z.B. 30 Sekunden) annehmen und sam- meln können.
Als Schlüssel für die Registrierung nutzt der Spieleserver das 4-Tuple (Source IP, Destina- tion IP, Source Port, Destination Port) und den Namen je Fahrzeug. Das bedeutet, dass der gleiche Name bei einem Client nicht möglich ist, aber von unterschiedlichen Client- Instanzen. Eine erfolgreiche Registrierung soll dem Client je Fahrzeug mit einem Count- down (in Sekunden) bis zum Rennstart bestätigt werden (Eine Nachricht mit einer relati- ven Zeit, die am Client je Sekunde verringert werden kann). Läuft ein Rennen wird die Registrierung mit einem Fehler zurückgewiesen.
3. Nach der Setupzeit wird ein Rennen gestartet, in dem zufällig verteilt Zeiten für die Run- denzeiten der einzelnen Fahrzeuge festgelegt wird. Abhängig von der Rundenzeit wird der Client direkt nach erreichen des Ziels (Ablauf der Zeit) informiert, welcher Platz von einem seiner Fahrzeuge erstritten wurde. Alle weiteren Client-Instanzen erhalten diese Information nicht. Nach dem alle Fahrzeuge das Ziel erreicht haben, wird allen Client- Instanzen eine Gesamtübersicht übermittelt. Ein Fahrzeug benötigt maximal 30 Sekun- den für die Fahrt.
4. Falls sich in der Setupzeit kein Fahrzeug angemeldet hat, wird die Setup-Time erneut ausgeführt. Sollte sich nur ein Fahrzeug registriert haben, wird das Rennen abgebrochen /beendet und der Teilnehmer wird darüber informiert.
5. Ein Client kann mehr als ein Fahrzeug beim Server anmelden. Es müssen geeignete Me- chanismen gefunden werden diese Fahrzeuge mit Namen zu verbinden. Nach einer Re- gistrierung muss mit dem Befehl „\INFO“ entweder der Countdown, oder die gemelde- ten Fahrzeuge mit Ihrem aktuellen Status, angezeigt werden können. Nach Rennende wird die Gesamtübersicht ohne Interaktion des Nutzers angezeigt.
6. Nach dem Ende der „Rennfahrt“ und der damit verbundenen Gesamtübersicht soll es möglich sein mit dem alten Setup oder einem neuen Setup erneut teilzunehmen.

### Autor: Prof. Martin Becke
