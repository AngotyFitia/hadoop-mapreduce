# Exercice 5 – Graph Breadth-First Search (BFS) avec Writable

## Objectif
Implémenter l’algorithme de **parcours en largeur (BFS)** sur un graphe distribué avec Hadoop MapReduce, en utilisant :
- Un **Writable personnalisé** (`GraphNodeWritable`)
- Des **formats d’entrée/sortie customisés** (`GraphInputFormat`, `GraphOutputFormat`)
- Un **RecordReader/RecordWriter** adaptés (`GraphRecordReader`, `GraphRecordWriter`)

---

## Fichiers créés
- `GraphDriver.java` → Driver principal du job Hadoop
- `GraphMapper.java` → Mapper qui propage les distances aux voisins
- `GraphReducer.java` → Reducer qui fusionne les informations des nœuds
- `GraphNodeWritable.java` → Type Writable personnalisé
- `GraphInputFormat.java` / `GraphRecordReader.java` → Lecture des données
- `GraphOutputFormat.java` / `GraphRecordWriter.java` → Écriture des résultats

---

## Étapes de mise en place

### 1. Compilation du projet
Sur le PC :
```bash
mvn clean package
scp target/graph-bfs-writable-1.0-SNAPSHOT.jar rabarijaona@spark.aiaoma.com:~
```

### 2. Préparation des données
Sur la VM :
```bash
cp /vagrant/tp/2/graph_input.txt .
hadoop fs -put graph_input.txt .
hadoop fs -ls
```

---

## Exécution du BFS Writable

### Première itération
```bash
hadoop jar graph-bfs-writable-1.0-SNAPSHOT.jar org.mbds.GraphDriver graph_input.txt output1_writable
hadoop fs -cat output1_writable/*
```

Résultat :
```
1    2,5|NOIR|0
2    2|GRIS|1
3    6|BLANC|-1
4    |BLANC|-1
5    5|GRIS|1
6    |BLANC|-1
```

---

### Deuxième itération
```bash
hadoop jar graph-bfs-writable-1.0-SNAPSHOT.jar org.mbds.GraphDriver output1_writable output2_writable
hadoop fs -cat output2_writable/*
```

Résultat :
```
1    2,5|NOIR|0
2    2|NOIR|1
3    6|BLANC|-1
4    |BLANC|-1
5    5|NOIR|1
6    |BLANC|-1
```

---

### Troisième itération
```bash
hadoop jar graph-bfs-writable-1.0-SNAPSHOT.jar org.mbds.GraphDriver output2_writable output3_writable
hadoop fs -cat output3_writable/*
```

Résultat final :
```
1    2,5|NOIR|0
2    2|NOIR|1
3    6|BLANC|-1
4    |BLANC|-1
5    5|NOIR|1
6    |BLANC|-1
```

---

## Résultat attendu
- Les nœuds atteignables depuis la source (`1`) deviennent **NOIR**.  
- Les nœuds inaccessibles (`3, 4, 6`) restent **BLANC** avec distance `-1`.  
- La condition d’arrêt est atteinte quand il n’y a plus de nœuds GRIS.

---

## Tests et résultats:
- ![Vous pouvez voir ici les résultats des tests de l'application: ](tests/7.png) 

---
## Conclusion
Cet exercice démontre :
- La création et l’utilisation d’un **Writable personnalisé** pour représenter un nœud de graphe.  
- L’utilisation de **formats d’entrée/sortie customisés** pour gérer la sérialisation/désérialisation.  
- La propagation itérative du BFS avec Hadoop MapReduce.  
- Le comportement attendu : seuls les nœuds connectés à la source sont explorés et deviennent NOIR.
