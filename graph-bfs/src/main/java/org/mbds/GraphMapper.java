package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class GraphMapper extends Mapper<Text, Text, Text, Text> {
    @Override
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // Format attendu: voisins|couleur|distance
        String[] parts = value.toString().split("\\|");
        String neighbors = parts[0];
        String color = parts[1];
        int distance = Integer.parseInt(parts[2]);

        // Réémettre le nœud lui-même
        context.write(key, new Text(neighbors + "|" + color + "|" + distance));

        // Si le nœud est GRIS, propager aux voisins
        if (color.equals("GRIS")) {
            for (String neighbor : neighbors.split(",")) {
                if (!neighbor.isEmpty()) {
                    int newDist = distance + 1;
                    context.write(new Text(neighbor), new Text("NULL|GRIS|" + newDist));
                }
            }
            // Le nœud devient NOIR
            context.write(key, new Text(neighbors + "|NOIR|" + distance));
        }
    }
}
