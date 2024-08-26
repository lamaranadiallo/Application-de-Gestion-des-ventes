# Application de Gestion des Ventes

## Table des matières
1. [Introduction](#introduction)
2. [Fonctionnalités](#fonctionnalités)
3. [Technologies utilisées](#technologies-utilisées)
4. [Architecture du projet](#architecture-du-projet)
5. [Installation et configuration](#installation-et-configuration)
6. [Utilisation](#utilisation)
7. [Captures d'écran](#captures-décran)
8. [Modèle de données](#modèle-de-données)
9. [Défis techniques et solutions](#défis-techniques-et-solutions)
10. [Améliorations futures](#améliorations-futures)
11. [Contribution](#contribution)
12. [Licence](#licence)
13. [Contact](#contact)

## Introduction

Cette application de gestion des ventes est un projet personnel développé pour démontrer mes compétences en développement Java et en conception d'interfaces utilisateur. Elle offre une solution complète pour gérer l'inventaire, les ventes et les utilisateurs d'un commerce de détail.

[Vous pouvez insérer ici une capture d'écran de l'écran principal de l'application]

## Fonctionnalités

- **Authentification des utilisateurs** : Système de connexion sécurisé avec différents niveaux d'accès (directeur, caissier).
- **Gestion de l'inventaire** : Ajout, modification et suppression de produits.
- **Réalisation des ventes** : Interface intuitive pour effectuer des transactions.
- **Tableau de bord** : Visualisation des statistiques de vente et des performances.
- **Gestion des utilisateurs** : Interface d'administration pour gérer les comptes utilisateurs (réservée aux directeurs).
- **Historique des ventes** : Consultation et recherche dans l'historique des transactions.
- **Sauvegarde et chargement des données** : Possibilité d'exporter et d'importer les données de l'inventaire.

## Technologies utilisées

- Java 11
- Swing pour l'interface graphique
- JDBC pour la connexion à la base de données (MySQL/PostgreSQL)
- Maven pour la gestion des dépendances
- JUnit pour les tests unitaires

## Architecture du projet

Le projet suit une architecture MVC (Modèle-Vue-Contrôleur) pour une meilleure séparation des responsabilités :

- **Modèle** : Classes représentant les données (Utilisateur, Produit, Vente, etc.)
- **Vue** : Interfaces utilisateur Swing (FenetreConnexion, MenuPrincipal, GestionUtilisateursInterface, etc.)
- **Contrôleur** : Classes gérant la logique métier (GestionUtilisateurs, Inventaire, etc.)

[Vous pouvez insérer ici un diagramme de l'architecture du projet]

## Installation et configuration

1. Clonez ce dépôt :
   ```
   git clone https://github.com/votre-nom/gestion-ventes-app.git
   ```
2. Importez le projet dans votre IDE préféré.
3. Configurez la base de données en modifiant le fichier `config.properties` :
   ```
   db.type=mysql
   db.host=localhost
   db.port=3306
   db.name=gestion_ventes
   db.user=votre_utilisateur
   db.password=votre_mot_de_passe
   ```
4. Exécutez le script SQL fourni pour créer les tables nécessaires.
5. Compilez et exécutez l'application.

## Utilisation

1. Lancez l'application.
2. Connectez-vous avec les identifiants par défaut :
   - Directeur : username "admin", password "admin123"
   - Caissier : username "caissier", password "caissier123"
3. Explorez les différentes fonctionnalités selon votre niveau d'accès.

## Captures d'écran

[Insérez ici plusieurs captures d'écran montrant les principales interfaces de l'application]

## Modèle de données

Le modèle de données de l'application est représenté par le diagramme entité-relation suivant :

[Insérez ici le diagramme du modèle conceptuel de données que nous avons créé précédemment]

## Défis techniques et solutions

1. **Gestion des droits d'accès** : Implémentation d'un système de rôles (directeur, caissier) pour contrôler l'accès aux fonctionnalités.
2. **Performances de l'interface graphique** : Utilisation de SwingWorker pour les opérations longues afin de maintenir une interface réactive.
3. **Cohérence des données** : Mise en place de transactions pour assurer l'intégrité des données lors des ventes.

## Améliorations futures

- Intégration d'un système de génération de rapports PDF.
- Ajout d'un module de gestion des fournisseurs.
- Développement d'une version mobile de l'application.
- Implémentation d'un système de notifications en temps réel.

## Contribution

Les contributions à ce projet sont les bienvenues. N'hésitez pas à forker le projet, créer une branche, ajouter vos modifications et soumettre une pull request.

## Contact

Si vous êtes intéressé par mon profil ou si vous avez des questions sur ce projet, n'hésitez pas à me contacter :

- [Email](mrdiallo77@gmail.com)
- [LinkedIn](https://www.linkedin.com/in/lamarana-diallo-3312a512b/)
- GitHub(https://github.com/mldiallo001)

Je suis ouvert aux opportunités professionnelles et serais ravi de discuter de projets passionnants !