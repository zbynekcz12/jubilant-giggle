```mermaid
graph TD
    A[Live Scan] --> B[Okamžitá analýza CNN]
    B --> C{Shoda?}
    C -->|Ano| D[Zobrazení profilu podezřelého]
    C -->|Ne| E[Uložení do Eurodac/ÚDS]
    D --> F[Export protokolu pro PČR]
```