package org.mbds;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SalesMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    private Text keyOut = new Text();
    private DoubleWritable valueOut = new DoubleWritable();
    private String analysisType;

    @Override
    protected void setup(Context context) {
        Configuration conf = context.getConfiguration();
        analysisType = conf.get("analysis.type", "region");
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        // Ignorer l'en-tête
        if (line.startsWith("Region")) return;

        String[] fields = line.split(",");
        if (fields.length < 14) return; // sécurité

        String region = fields[0];
        String country = fields[1];
        String itemType = fields[2];
        String profitStr = fields[13]; // colonne Total Profit

        double profit;
        try {
            profit = Double.parseDouble(profitStr);
        } catch (NumberFormatException e) {
            return; // ignorer si non numérique
        }

        switch (analysisType) {
            case "region":
                keyOut.set(region);
                break;
            case "country":
                keyOut.set(country);
                break;
            case "item":
                keyOut.set(itemType);
                break;
            default:
                keyOut.set(region);
        }

        valueOut.set(profit);
        context.write(keyOut, valueOut);
    }
}
