# Jeux d'echec en local avec un serveur sur Docker

## 📝 Présentation du projet

Notre projet est de créer un jeu d'échec en réseau où 2 personnes peuvent s'affronter.  
Les joueurs peuvent discuter sur un tchat avec les personnes qui regarde la partie.  
1. Pour se faire, nous avons commencer par créer les différentes pièces et leur contraintes de déplacement.
2. Ensuite, nous avons créer un serveur sur un conteneur docker, pour que les joueurs se connectent et jouent en simultané sur le même plateau.
3. Il ne nous restait plus qu'a détecter les situations d'échec ou d'échec et math.
4. Pour finir, nous avons créer un tchat sur le serveur pour que les joueurs puissent discuter.

## ⚙️ Instalation
### Serveur

- Cloner le dépôt (si cela na pas déjà était fait) : 
```shell
git clone git@github.com:VicenteHugo/docker-sae203.git
```

- Se déplacer dans le dossier 
```shell
cd docker-sae203
```

- Construire le conteneur :
```shell
docker build -t echec .
```

- Lancer le service :
```shell
docker run -d -p 6666:6666 echec
```

La console devrait prévenir que le serveur est démarré.

### Client
1. Cloner le dépôt (si cela na pas déjà était fait) : 
```shell
git clone git@github.com:VicenteHugo/docker-sae203.git
```

2. Se déplacer dans le dossier 
```shell
cd docker-sae203/
```

3. Compilation / Execution
- Sous *Linux*  
Commencer par faire :
```shell
chmod +x run.sh
```
suivi de :
```shell
./run.sh
```
- Sous *Windows*  
Lancer :
```shell
run.bat
```

## 💻 Utilisation

pour l'utilisation faite comme ceci :

- Sous *Linux*  
Commencer par faire :
```shell
chmod +x run.sh
```
suivi de :
```shell
./run.sh
```
si c'est sur votre ordinateur connecter vous en utilisant local host comme ceci :  
 ![Texte alternatif](/imageREADME/connectPanel.png "Titre de l'image")
  
1. si vous ête sur un autre ordinateur pour commencer vérifier que vous soyer sous la même connection  
2. suite a la personne que vas host le serveur doit aller chercher sont ordinateur pour ce fair il doit faire ouvrir un terminal, puis faire ifconfig et trouver l'ip du pc
3. en suite a la place de mettre localhost metter l'ip de l'ordianteur host


- Sous *Windows*  
Lancer :
```shell
run.bat
```
si c'est sur votre ordinateur connecter vous en utilisant local host comme ceci :  
 ![Texte alternatif](/imageREADME/connectPanel.png "Titre de l'image")
  
1. si vous ête sur un autre ordinateur pour commencer vérifier que vous soyer sous la même connection  
2. suite a la personne que vas host le serveur doit aller chercher sont ordinateur pour ce fair il doit faire ouvrir cmd, puis faire ipconfig et trouver l'ip du pc
3. en suite a la place de mettre localhost metter l'ip de l'ordianteur host