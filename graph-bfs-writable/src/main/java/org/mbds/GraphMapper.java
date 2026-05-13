package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class GraphMapper extends Mapper<Text, GraphNodeWritable, Text, GraphNodeWritable> {
    @Override
    public void map(Text key, GraphNodeWritable node, Context context) throws IOException, InterruptedException {
        // Réémettre le nœud lui-même
        context.write(key, node);

        // Si le nœud est GRIS, propager aux voisins
        if (node.getColor().equals("GRIS")) {
            for (String neighbor : node.getNeighbors().split(",")) {
                if (!neighbor.isEmpty()) {
                    GraphNodeWritable newNode = new GraphNodeWritable(
                        neighbor + "|GRIS|" + (node.getDistance() + 1)
                    );
                    context.write(new Text(neighbor), newNode);
                }
            }
            // Le nœud devient NOIR
            node.setColor("NOIR");
            context.write(key, node);
        }
    }
}
