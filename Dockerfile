# Použití oficiálního obrazu Android SDK jako základ
FROM openjdk:8-jdk

FROM busybox
RUN apt-get install python=2.7

FROM busybox
RUN apt-get install -y --no-install-recommends python=2.7

# Instalace potřebných nástrojů
RUN apt-get update && apt-get install -y \
    wget=1.20.3-1ubuntu1 \
    unzip=6.0-23ubuntu1 \
    && rm -rf /var/lib/apt/lists/*

# Instalace Gradle
RUN wget https://services.gradle.org/distributions/gradle-6.8.3-bin.zip -P /tmp \
    && unzip /tmp/gradle-6.8.3-bin.zip -d /opt \
    && ln -s /opt/gradle-6.8.3/bin/gradle /usr/bin/gradle

# Nastavení pracovního adresáře
WORKDIR /app

# Kopírování projektu do kontejneru
COPY . .

# Sestavení projektu
RUN gradle build

# Definování příkazu pro spuštění aplikace
CMD ["./run.sh"]
