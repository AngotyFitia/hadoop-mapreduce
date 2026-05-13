# Hadoop MapReduce Exercises – WordCount, Anagrams, Sales, Graph BFS, Graph BFS Writable

## Réalisateur
- Nom et prénoms : **RABARIJAONA Angoty Fitia**
- Étudiante en Master II

---

##  Technologies utilisées
- **Java 11** (compilation et exécution des jobs MapReduce)
- **Apache Hadoop 3.3.6** (HDFS, YARN, MapReduce)
- **Maven** (gestion du projet et des dépendances)
- **Git/GitHub** (gestion de versions et dépôt du projet)
- **Git Bash (Windows)** pour exécuter les commandes et transférer les fichiers
- **HDFS** pour le stockage distribué

---

##  Exercices réalisés

### 1. WordCount
- Objectif : Compter les occurrences de mots dans un fichier texte.
- Input : fichier texte sur HDFS.
- Output : liste des mots avec leur fréquence.
- Commandes :
  ```bash
  hadoop jar wordcount-1.0-SNAPSHOT.jar org.mbds.WordCountDriver input.txt output_wc
  hadoop fs -cat output_wc/*
  ```

---

### 2. Anagrams
- Objectif : Regrouper les mots qui sont des anagrammes.
- Input : fichier texte.
- Output : mots regroupés par clé (tri des lettres).
- Commandes :
  ```bash
  hadoop jar anagrams-1.0-SNAPSHOT.jar org.mbds.AnagramDriver input.txt output_anagrams
  hadoop fs -cat output_anagrams/*
  ```

---

### 3. Sales
- Objectif : Calculer le profit total par région, pays ou type d’article.
- Input : `sales_world_10k.csv` (copié depuis `/vagrant/tp/1/`).
- Output : profits agrégés selon le paramètre choisi.
- Commandes :
  ```bash
  hadoop jar sales-1.0-SNAPSHOT.jar org.mbds.SalesDriver region sales_world_10k.csv results_region
  hadoop jar sales-1.0-SNAPSHOT.jar org.mbds.SalesDriver country sales_world_10k.csv results_country
  hadoop jar sales-1.0-SNAPSHOT.jar org.mbds.SalesDriver item sales_world_10k.csv results_item
  hadoop fs -cat results_region/*
  ```

---

### 4. Graph BFS
- Objectif : Implémenter un parcours en largeur (Breadth-First Search) sur un graphe distribué.
- Input : `graph_input.txt` (copié depuis `/vagrant/tp/2/`).
- Output : état des nœuds (BLANC → GRIS → NOIR) et distances.
- Commandes :
  ```bash
  hadoop jar graph-bfs-1.0-SNAPSHOT.jar org.mbds.GraphDriver graph_input.txt output1
  hadoop jar graph-bfs-1.0-SNAPSHOT.jar org.mbds.GraphDriver output1 output2
  hadoop jar graph-bfs-1.0-SNAPSHOT.jar org.mbds.GraphDriver output2 output3
  hadoop fs -cat output3/*
  ```
- Résultat final : tous les nœuds atteignables deviennent **NOIR** → BFS terminé.

---

### 5. Graph BFS Writable
- Objectif : Reprendre BFS mais en utilisant :
  - Un **Writable personnalisé** (`GraphNodeWritable`)
  - Des **InputFormat/OutputFormat customisés**
  - Un **RecordReader/RecordWriter** adaptés
- Input : `graph_input.txt` (copié depuis `/vagrant/tp/2/`).
- Output : état des nœuds avec sérialisation personnalisée.
- Commandes :
  ```bash
  hadoop jar graph-bfs-writable-1.0-SNAPSHOT.jar org.mbds.GraphDriver graph_input.txt output1_writable
  hadoop jar graph-bfs-writable-1.0-SNAPSHOT.jar org.mbds.GraphDriver output1_writable output2_writable
  hadoop jar graph-bfs-writable-1.0-SNAPSHOT.jar org.mbds.GraphDriver output2_writable output3_writable
  hadoop fs -cat output3_writable/*
  ```
- Résultat final :
  ```
  1    2,5|NOIR|0
  2    2|NOIR|1
  3    6|BLANC|-1
  4    |BLANC|-1
  5    5|NOIR|1
  6    |BLANC|-1
  ```
-> Les nœuds atteignables deviennent **NOIR**, les nœuds inaccessibles restent **BLANC** avec distance `-1`.

---

## Conclusion
Ce dépôt regroupe les 5 exercices Hadoop MapReduce :
- WordCount
- Anagrams
- Sales
- Graph BFS
- Graph BFS Writable

Ils démontrent la maîtrise de :
- La lecture et le traitement de fichiers texte et CSV.  
- L’utilisation de différents **InputFormat/OutputFormat** (TextInputFormat, KeyValueTextInputFormat, formats custom).  
- L’agrégation et la propagation de données dans un environnement distribué.  
- L’exécution itérative d’un algorithme distribué (BFS).  
- La création et l’utilisation d’un **Writable personnalisé** pour gérer la sérialisation des nœuds de graphe.

