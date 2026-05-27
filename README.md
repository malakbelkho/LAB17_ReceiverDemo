# BroadcastPulse – LAB 17

Application Android permettant de comprendre et tester les **BroadcastReceiver** à travers trois scénarios principaux :  
la détection du **mode avion**, la réception du **démarrage du téléphone**, et l’envoi d’un **broadcast personnalisé** depuis l’application.

## Objectif:

Le but de ce laboratoire est de :

- Comprendre le rôle des `BroadcastReceiver` dans Android
- Différencier un receiver **dynamique** et un receiver **statique**
- Détecter un événement système : `ACTION_AIRPLANE_MODE_CHANGED`
- Recevoir l’événement système `BOOT_COMPLETED` après le redémarrage de l’émulateur
- Créer et envoyer un broadcast personnalisé depuis `MainActivity`
- Comprendre le cycle de vie de la méthode `onReceive()`
- Appliquer les bonnes pratiques liées à `registerReceiver()` et `unregisterReceiver()`
- Respecter les règles récentes d’Android concernant `exported`, les permissions et les restrictions en arrière-plan
- Créer une interface personnalisée, moderne et visuellement agréable avec des fichiers XML et des drawables

## Description de l’application:

L’application **BroadcastPulse** contient :

- Un écran principal moderne avec :
  - Titre de l’application
  - Carte de statut
  - Bouton d’activation du receiver dynamique
  - Bouton d’envoi d’un broadcast custom
  - Journal visuel des événements reçus

- Un receiver dynamique :
  - Détecte les changements du mode avion
  - S’active uniquement lorsque l’utilisateur clique sur le bouton
  - Se désactive proprement avec `unregisterReceiver()`

- Un receiver statique :
  - Déclaré dans `AndroidManifest.xml`
  - Réagit à l’événement `BOOT_COMPLETED`
  - Permet de vérifier que l’application peut recevoir un événement système même si l’Activity n’est pas ouverte

- Un receiver personnalisé :
  - Reçoit un broadcast interne envoyé depuis `MainActivity`
  - Affiche un message à travers un `Toast`
  - Permet de comprendre la communication par Intent entre composants

## Fonctionnalités:

- Activation / désactivation d’un receiver dynamique
- Détection du changement du mode avion
- Réception du broadcast système `BOOT_COMPLETED`
- Envoi d’un broadcast personnalisé depuis l’application
- Affichage de messages avec `Toast`
- Vérification des événements via Logcat
- Mise à jour dynamique de l’interface
- Journal des événements récents
- Interface moderne avec :
  - Dégradé de fond
  - Carte arrondie
  - Boutons personnalisés
  - Badge de statut
  - Couleurs contrastées et design nuancé

## Technologies utilisées:

- Android Studio
- Java
- XML
- BroadcastReceiver
- Intent
- IntentFilter
- AndroidManifest
- Logcat
- ADB
- API minimum : 24 Android 7.0

## Aperçu de l’application:

▶️ Une démonstration vidéo complète est disponible dans le dossier **Demo** du repository.

⚠️ En cas de problème de lecture :

👉 [▶️ Voir la démo sur Google Drive](https://drive.google.com/file/d/1xY2ZmKgQDoIfh2eJ_GZ-7ULxKEZDRd3I/view?usp=sharing)

## Structure du projet:

### Code Java:

#### MainActivity.java

- Représente l’écran principal de l’application
- Initialise les vues de l’interface
- Gère le bouton d’activation du receiver dynamique
- Enregistre le receiver du mode avion avec `registerReceiver()`
- Désenregistre le receiver avec `unregisterReceiver()`
- Envoie un broadcast personnalisé vers `PulseEventReceiver`
- Met à jour la carte de statut
- Ajoute les événements dans le journal visuel

#### SkySignalReceiver.java

- Receiver dynamique responsable de la détection du mode avion
- Écoute l’action système :

```java
Intent.ACTION_AIRPLANE_MODE_CHANGED
```
- Récupère l’état du mode avion avec :

```java
intent.getBooleanExtra("state", false)
```

- Affiche un message indiquant si le mode avion est activé ou désactivé
- Utilise une interface pour renvoyer l’information vers `MainActivity`

#### StartupPulseReceiver.java

- Receiver statique responsable de la détection du démarrage du téléphone
- Déclaré dans `AndroidManifest.xml`
- Écoute l’action système :

```java
Intent.ACTION_BOOT_COMPLETED
```

- Affiche un message dans Logcat lorsque l’émulateur redémarre
- Permet de comprendre le fonctionnement des receivers statiques

#### PulseEventReceiver.java

- Receiver chargé de recevoir un broadcast personnalisé
- Utilise une action interne personnalisée :

```java
com.example.receiverdemo.action.PULSE_EVENT
```

- Récupère un message envoyé depuis `MainActivity`
- Affiche le message reçu avec un `Toast`

## Layouts:

### activity_main.xml

L’écran principal contient :

- Un titre central : **BroadcastPulse**
- Un sous-titre expliquant les notions du lab
- Une carte principale affichant :
  - L’état du receiver
  - Une description de l’événement courant
- Un bouton pour activer ou désactiver l’écoute du mode avion
- Un bouton pour envoyer un broadcast personnalisé
- Une carte contenant le journal des événements

## Design XML:

### bg_broadcast_screen.xml

- Fond principal de l’application
- Utilise un dégradé sombre et moderne
- Donne un style plus professionnel à l’écran

### bg_glass_card.xml

- Carte principale de statut
- Coins arrondis
- Bordure douce
- Style proche d’une carte moderne

### bg_primary_button.xml

- Bouton principal
- Utilisé pour activer ou désactiver le receiver dynamique
- Dégradé coloré

### bg_secondary_button.xml

- Bouton secondaire
- Utilisé pour envoyer un broadcast personnalisé
- Fond plus discret avec bordure colorée

### bg_status_pill.xml

- Badge affiché dans la carte principale
- Met en évidence le type de lab

### bg_timeline_item.xml

- Carte contenant le journal des événements
- Permet d’afficher les actions récentes de manière claire

## Manifest:

### AndroidManifest.xml

Le Manifest contient :

- La permission nécessaire pour recevoir le démarrage du téléphone :

```xml
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

- La déclaration de l’Activity principale
- La déclaration du receiver statique `StartupPulseReceiver`
- La déclaration du receiver custom `PulseEventReceiver`

Exemple :

```xml
<receiver
    android:name=".StartupPulseReceiver"
    android:enabled="true"
    android:exported="true">

    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>

</receiver>
```

Le receiver custom est déclaré avec :

```xml
android:exported="false"
```

Cela permet d’éviter que d’autres applications puissent l’appeler directement.

## Tests réalisés:

### Test du receiver dynamique:

1. Lancer l’application
2. Cliquer sur **Activer l’écoute avion**
3. Activer ou désactiver le mode avion depuis les paramètres de l’émulateur
4. Observer le Toast et la mise à jour de l’interface

Résultat attendu :

```text
Mode avion activé : connexions suspendues
```

ou :

```text
Mode avion désactivé : connexions restaurées
```

### Test du broadcast personnalisé:

1. Lancer l’application
2. Cliquer sur **Envoyer un broadcast custom**
3. Observer le Toast affiché

Résultat attendu :

```text
Signal interne reçu : Message envoyé depuis MainActivity
```

### Test du BootReceiver:

1. Lancer l’application au moins une fois sur l’émulateur
2. Redémarrer l’émulateur avec ADB :

```bash
adb reboot
```

Ou avec le chemin complet si ADB n’est pas reconnu :

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" -s emulator-5554 reboot
```

3. Après le redémarrage, vérifier Logcat avec le tag :

```text
StartupPulseReceiver
```

Résultat obtenu :

```text
BOOT_COMPLETED reçu : Le téléphone vient de démarrer.
```

Remarque :

Le Toast après redémarrage peut ne pas apparaître sur certaines versions récentes d’Android, car le système peut limiter l’affichage provenant de composants en arrière-plan.  
La vérification la plus fiable se fait donc avec **Logcat**.

## Notions importantes:

### BroadcastReceiver

Un `BroadcastReceiver` est un composant Android qui permet de réagir à un événement envoyé par le système ou par une application.

### onReceive()

La méthode `onReceive()` est appelée automatiquement lorsqu’un broadcast correspondant est reçu.

### Receiver dynamique

Un receiver dynamique est créé et enregistré dans le code Java avec :

```java
registerReceiver()
```

Il est ensuite désactivé avec :

```java
unregisterReceiver()
```

Il est recommandé pour les événements qui doivent être écoutés uniquement lorsque l’application est active.

### Receiver statique

Un receiver statique est déclaré directement dans le fichier `AndroidManifest.xml`.

Il peut recevoir certains événements système, comme :

```java
BOOT_COMPLETED
```

### IntentFilter

Un `IntentFilter` sert à indiquer quelles actions un receiver doit écouter.

Exemple :

```java
IntentFilter filter = new IntentFilter();
filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
```

### Broadcast custom

Un broadcast custom est un événement créé par l’application elle-même.

Dans ce lab, `MainActivity` envoie un broadcast interne vers `PulseEventReceiver`.

### exported

L’attribut `android:exported` indique si un composant peut être appelé par d’autres applications.

- `exported="true"` : le composant peut recevoir certains événements externes ou système
- `exported="false"` : le composant reste limité à l’application

## Difficultés rencontrées:

Pendant le test du `BootReceiver`, le Toast n’apparaissait pas après le redémarrage de l’émulateur.

Après vérification avec Logcat, le receiver fonctionnait correctement et le message suivant apparaissait :

```text
BOOT_COMPLETED reçu : Le téléphone vient de démarrer.
```

Le problème ne venait donc pas du code, mais du comportement récent d’Android qui peut empêcher l’affichage de Toasts depuis certains composants exécutés en arrière-plan.

## Conclusion:

Ce laboratoire m’a permis de comprendre le fonctionnement des `BroadcastReceiver` sous Android.

À travers l’application **BroadcastPulse**, j’ai pu manipuler :

- Un receiver dynamique pour écouter le changement du mode avion
- Un receiver statique pour détecter le démarrage du téléphone
- Un broadcast personnalisé envoyé depuis l’application
- Les permissions nécessaires dans le Manifest
- Les bonnes pratiques liées à `registerReceiver()` et `unregisterReceiver()`
- Les restrictions modernes d’Android concernant les composants en arrière-plan

Ce lab montre comment Android permet aux applications de réagir à des événements internes ou système, tout en respectant les règles de sécurité et de cycle de vie des composants.
