## Fingerprint Recognition App

### Spuštění aplikace pomocí Dockeru

1. Ujistěte se, že máte nainstalovaný Docker.
2. Sestavte Docker obraz:

```sh
docker build -t fingerprint-app .
```

3. Spusťte aplikaci v Docker kontejneru:

```sh
docker run -it --rm --privileged -v /dev/bus/usb:/dev/bus/usb fingerprint-app
```

Tímto příkazem sestavíte a spustíte aplikaci v Dockeru, což zajistí konzistentní prostředí pro všechny uživatele.