## 2. Technické specifikace

### 2.1. Základní komponenty

| Komponenta            | Technologie                    | Účel                                    |
|-----------------------|--------------------------------|-----------------------------------------|
| Modul akvizice        | OpenCV + Android Camera2 API   | Snímání otisků v 1200 DPI               |
| CNN engine            | TensorFlow Lite s custom modelem | Detekce minucií s 94% přesností        |
| Databázové propojení  | REST API k ÚDS/Eurodac         | Online verifikace                       |
| Offline režim         | SQLite + předtrénovaná data    | Práce bez internetu                     |