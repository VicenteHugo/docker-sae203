# Jeux d'echec en local avec un serveur sur Docker



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
docker run -d -p 8000:8000 echec
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
```shell
run.bat
```

## 💻 Utilisation