FROM debian:latest

# Installation de Java, X11 et Xvfb
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk coreutils

# Copie des fichiers
COPY ./src/server /echec/server

# Configuration de la variable d'environnement JAVA_HOME et PATH
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

RUN chmod +x /echec/server/startServer.sh

EXPOSE 6666

# Lancement du serveur
CMD bash -c "cd /echec/server/ && javac *.java && java Controleur"