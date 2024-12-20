# Java2D Geometry Dash Clone

## Javadoc disponible
- **Pour accéder à la Javadoc, ouvrez localement le fichier situé à : docs/index.html**

## Description
Ce projet est un clone 2D de **Geometry Dash** développé en Java en utilisant la bibliothèque **Swing**. En plus du gameplay classique, le jeu inclut un **créateur de cartes** pour permettre aux joueurs de concevoir leurs propres niveaux. Ce projet a été réalisé dans le cadre de la piscine Java, visant à approfondir la maîtrise de la programmation orientée objet (POO).

## Fonctionnalités
- **Gameplay dynamique** : Déplacez un personnage qui avance automatiquement et évitez des obstacles variés.
- **Créateur de cartes** : Interface intuitive pour concevoir vos propres niveaux et les tester immédiatement.
- **Niveaux personnalisables** : Créez, enregistrez et jouez à des niveaux uniques.
  
## Objectifs pédagogiques
1. Implémenter les principes de la **programmation orientée objet** (encapsulation, héritage, polymorphisme).
2. Utiliser la bibliothèque **Swing** pour concevoir une interface graphique et un créateur de cartes interactifs.
3. Gérer les **événements utilisateur** (clavier, souris) pour un gameplay fluide et une expérience utilisateur agréable.
4. Structurer le projet de manière modulaire avec des **classes bien définies** pour chaque fonctionnalité.

## Technologies utilisées
- **Java SE** (Standard Edition)
- **Swing** pour l'interface graphique
- **Fichiers JSON** pour la sauvegarde et le chargement des cartes personnalisées par sérialisation et désérialisation. Création d'un parser.

## Installation

### Pré-requis
- JDK 11 ou version ultérieure
- Un IDE Java (IntelliJ IDEA, Eclipse, NetBeans, etc.), ou VSCode avec les extensions nécessaires.

### Étapes d'installation
1. Clonez ce dépôt :
   ```bash
   git clone https://github.com/Klemso/2DJava-GeometryDash.git 
   ```
2. Ouvrez le projet dans votre IDE préféré.

3. Assurez-vous que toutes les dépendances nécessaires sont correctement configurées.
    Compilez et exécutez le fichier Main.java pour lancer le jeu.


## Contrôles du jeu
- Mode Joueur
- **Espace** : Sauter

## Mode Créateur
- **Clic gauche** : Ajouter un obstacle
- **Clic and drag molette** : Bouger la caméra
- **S** : Sauvegarder la carte
- **L** : Charger la carte
- **E** : Jouer la carte
