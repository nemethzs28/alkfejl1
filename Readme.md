# Németh Zsombor Gábor (QFV25Y) - Alkalmazásfejlesztés I. Kötelező Program
## SNAKE GAME

A szokványostól egy kicsit eltérő Snake játék.

## Feature lista
* Main menu, melyben a következő lehetőségek érhetőek el:
    * Egyjátékos mód indítása (Singleplayer mode gomb)
    * Többjátékos mód indítása (Multiplayer mode gomb)
    * Toplista az eddigi elért eredményekkel (Top list gomb)
    * Opciók menü (Settings gomb)
    * Kilépés (Quit)
* A többjátékos mód során az egyes játékos a W,A,S,D billenytűkkel tudja irányítani a kígyóját, a kettes játékos a nyilakkal
* Az egyjátékos és a többjátékos mód is rendelkezik egy "éhség" nevezetű funkcióval, aktivitási ideje alatt a játékos képes magába harapni anélkül, hogy ez a játék végét jelentené, a lerahapott rész elvész, aktiválni a világoskék kaja megevésével lehetséges
* A többjátékos mód továbbá rendelkezik egy "kannibál" nevezetű funkcióval is, aktivitási ideje alatt a játékost nem képes a másik játékos "megölni", ha érintkezik vele meghal
* A kannibál mód véget ér ha a játékos felvesz további 2 kaját
* Kannibál csak az lehet, akinek a pontszáma legfeljebb tízzel kevesebb mint a másik játékos pontszáma
* Kannibál csak akkor lehet valaki, ha a másik játékos nem kannibál
* A kaják színét (kívéve a világoskék éhség és a lila kannibál) az Opciók menüben lehet változtatni
* Az Opciók menüben lehetőség van a kígyó/kígyók színének és alapsebességének megadására
* Az Opciók menüben lehetőség van a pályát körülvevő fal be- illetve kikapcsolására (alapértelmezetten ki van kapcsolva)
* Ha ki van kapcsolva a fal, akkor a kígyók a pályán "loopolnak"
* Az Opciók menüben lehetőség van a pálya méretének megadására (alapértelmezetten 20)
* A Toplistában külön táblába vannak szervezve az Egyjátékos és a Kétjátékos játékok
* A Toplistában lehetőség van a lejátszott játékok módosítására (név és szerzett pont) és törlésére
* A Toplista meg van valósítva egy webes felületen is


## Követelmények, működés
* A program Java 11 alatt íródott
* A programban egyetlen beégetett útvonal van, az adatbázis elérési útvonala: ezt a resources/database mappán belül található applicaton.propertiesben kell átírni
* TomCat szükséges, 9.0.45 -ös verzió!
* Az asztali verzió fordítása és futtatása a Mavennek adott *mvn clean compile javafx:run@run* paranccsal működik
* A webszerver indításához a konfigurációban be kell állítani a megfelelő TomCat verziót!





