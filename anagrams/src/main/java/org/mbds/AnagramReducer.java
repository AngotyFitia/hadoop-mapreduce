package org.mbds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AnagramReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String> words = new ArrayList<>();
        for (Text val : values) {
            words.add(val.toString());
        }
        if (words.size() > 1) { // seulement si plusieurs mots partagent la même clé
            context.write(key, new Text(String.join(", ", words)));
        }
    }
}
