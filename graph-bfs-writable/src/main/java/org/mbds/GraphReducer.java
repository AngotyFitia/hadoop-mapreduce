package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class GraphReducer extends Reducer<Text, GraphNodeWritable, Text, GraphNodeWritable> {
    @Override
    public void reduce(Text key, Iterable<GraphNodeWritable> values, Context context)
            throws IOException, InterruptedException {

        String neighbors = "";
        String color = "BLANC";
        int distance = Integer.MAX_VALUE;

        // Fusionner les informations reçues pour ce nœud
        for (GraphNodeWritable node : values) {
            // Garder la liste des voisins si elle est présente
            if (node.getNeighbors() != null && !node.getNeighbors().equals("NULL")) {
                neighbors = node.getNeighbors();
            }

            // Choisir la distance minimale
            int d = node.getDistance();
            if (d != -1 && d < distance) {
                distance = d;
            }

            // Déterminer la couleur finale
            String c = node.getColor();
            if (c.equals("NOIR")) {
                color = "NOIR";
            } else if (c.equals("GRIS") && !color.equals("NOIR")) {
                color = "GRIS";
            }
        }

        // Si aucune distance n’a été trouvée, mettre -1
        if (distance == Integer.MAX_VALUE) {
            distance = -1;
        }

        // Construire le nœud final
        GraphNodeWritable result = new GraphNodeWritable(neighbors + "|" + color + "|" + distance);

        // Émettre le résultat
        context.write(key, result);
    }
}
