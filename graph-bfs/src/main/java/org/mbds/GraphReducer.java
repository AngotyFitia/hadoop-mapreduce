package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class GraphReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String neighbors = "";
        String color = "BLANC";
        int distance = Integer.MAX_VALUE;

        for (Text val : values) {
            String[] parts = val.toString().split("\\|");
            if (!parts[0].equals("NULL")) {
                neighbors = parts[0];
            }
            String c = parts[1];
            int d = Integer.parseInt(parts[2]);

            if (d < distance) {
                distance = d;
            }
            if (c.equals("NOIR")) {
                color = "NOIR";
            } else if (c.equals("GRIS") && !color.equals("NOIR")) {
                color = "GRIS";
            }
        }

        if (distance == Integer.MAX_VALUE) distance = -1;
        context.write(key, new Text(neighbors + "|" + color + "|" + distance));
    }
}
