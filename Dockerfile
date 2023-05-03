FROM debian:latest

# Installation de Java, X11 et Xvfb
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk x11vnc xvfb

# Copie des fichiers
COPY ./bin /echec

# Configuration de la variable d'environnement JAVA_HOME et PATH
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

RUN echo "java -cp /echec controleur.Controleur" > ~/.xinitrc && chmod +x ~/.xinitrc

# Lancement du serveur X11
CMD ["v11vnc", "-create", "-forever"]
