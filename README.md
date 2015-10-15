#Micro projet IHM IS3

## IMPORTANT
Le mot de passe par defaut est "**root**", pour le modifier allez dans ControleurPaiement.java.

## Organisation
Ce répertoire contient les dossiers qui concernent le projet d'ACDA du 3ème semèstre.
*	**doc** : contient la documentation
*	**src** : contient les sources Java
*	**lib** : contient le driver pour MariaDB

## Compilation
*	Compiler et installer la partie Paiement :

``javac Paiement.java`` 
``java "../lib/maria-java-client.jar:." Paiement`` 

*	Compiler et installer la partie Nettoyage :

``javac Nettoyage.java`` 
``java "../lib/maria-java-client.jar:." Nettoyage`` 

*	Compiler et installer la partie Réservation :

``javac Reservation.java`` 
``java "../lib/maria-java-client.jar:." Reservation`` 
