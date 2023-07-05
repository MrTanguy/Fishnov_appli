# Fishnov

Fishnov est une application conçue pour aider les pêcheurs à enregistrer leurs captures quotidiennes. Elle offre un moyen simple et pratique d'enregistrer les détails importants de chaque sortie de pêche, y compris les espèces capturées, le lieu et les notes supplémentaires. Fishnov est développée en Kotlin, en utilisant l'architecture MVVM (Modèle-Vue-Modèle de vue).

## Fonctionnalités

- Création de compte
- Enregistrement des pêches quotidiennes : Enregistrez les espèces, la quantité.
- Visualisation des pêches réalisées

## Dépendances

Fishnov utilise les dépendances suivantes :

- AndroidX : Bibliothèques Android Jetpack pour le développement Android moderne.
- Kotlin Coroutines : Pour gérer la programmation asynchrone et les tâches en arrière-plan.
- Data binding : Gestion des données entre l'Activity, le ViewModel et le Model.
- ViewModel : Fait partie des composants d'architecture Android, il gère les données liées à l'interface utilisateur de manière consciente du cycle de vie.
- LiveData : Également partie des composants d'architecture Android, il permet d'observer les données pour des mises à jour automatiques de l'interface utilisateur.

Pour installer ces dépendances, assurez-vous d'avoir les configurations nécessaires dans le fichier build.gradle de votre projet.

## Développement

Fishnov est développé en utilisant le modèle d'architecture MVVM (Modèle-Vue-Modèle de vue), qui sépare l'interface utilisateur de la logique métier. L'application est composée des éléments suivants :

- Modèle : Responsable de la gestion des données et de la logique métier de l'application. Cela comprend les opérations de base de données, la récupération des données et leur manipulation.
- Vue : Représente l'interface utilisateur, y compris les activités, les fragments et les mises en page. Elle observe le ViewModel pour les changements et met à jour l'interface utilisateur en conséquence.
- ViewModel : Fait le lien entre le Modèle et la Vue, fournissant les données à la Vue et gérant les interactions utilisateur. Il conserve également les données lors des changements de configuration.


## Contact

Si vous avez des questions ou des suggestions concernant Fishnov, n'hésitez pas à nous contacter à l'adresse tanguy.meignier@ynov.com.

Bonne pêche ! 🎣
