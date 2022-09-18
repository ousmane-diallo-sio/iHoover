**iHoover**

[<img src="GIMP/logo.jpg" width="100"/>](GIMP/logo.jpg)

<br>

Pour ce projet (dont le contexte est disponible ci-dessous), j'ai fait le choix du langage `(Java)` en développant une application mobile sous `(Android Studio)`.

L'application répond à toutes les contraintes énoncées dans la description du projet, en voici quelques captures d'écran :

| | | |
| :------: | :------: | :------: |
| ![](screenshots/Screenshot1.png) | ![](screenshots/Screenshot2.png) | ![](screenshots/Screenshot3.png) |


Pour télécharger l'application, [cliquez ici](app/release/iHoover.apk).

<br>
<br>

## Contexte

La société “iHoover” a décidé de développer un aspirateur automatique.

L’aspirateur doit pouvoir parcourir l'intégralité d’une pièce donnée, sa position est représentée par ses coordonnées (x,y) et d'une lettre indiquant l'orientation selon la notation cardinale anglaise (N,E,W,S). Une pièce est modélisée par une grille rectangulaire.

Par exemple, l’aspirateur peut se trouver dans la position « 0, 0, N », ce qui signifie qu’il se situe dans le coin inférieur gauche de la pièce, et orientée vers le Nord.

Pour contrôler l’aspirateur, une séquence de commandes symbolisée par une suite de lettres lui est envoyée. Les commandes possibles sont « D », « G » et « A ». « D » et « G » font pivoter l’aspirateur de 90° à droite ou à gauche respectivement, sans la déplacer. « A » signifie que l'on avance d'une case dans la direction à laquelle elle fait face, et sans modifier son orientation.

On définit que la case directement au Nord de la position (x, y) a pour coordonnées (x, y+1).

Pour programmer l’aspirateur, on lui fournit des données en entrée:

Les dimensions de la grille à savoir le nombre de carrés sur l’axe x puis y.
La position initiale de l’aspirateur, ainsi que son orientation.
Une série d'instructions que l’aspirateur exécutera.

Lorsque l’aspirateur achève une série d'instruction, il communique sa position et son orientation.
Objectif
Concevoir et développer une application permettant de paramétrer la dimension de la grille, de positionner l’aspirateur et d’exécuter une liste d’instructions. On doit alors pouvoir connaître sa position finale.
