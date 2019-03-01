# PR-labs Druta Mihai FI-161
Analizind varianta(5) propusa spre rezolvare am hotarit sa utilizez semafoarele.

Pentru nodurile 5 si 6 din graf am pornit thread-urile simplu .start(), astfel incit ele nu au nicio dependenta.

Pentru nodul 2 am utilizat semaforul pentru al face sa astepte executia thread-urilor 5 si 6.

Analind ultimele 2 noduri am utilizat semaforul pentru a arata dependetele nodurilor 3 si 1 (3 depends 2, 1 depends 3).

In interiorul codului sunt si comentarii la unele rinduri.
