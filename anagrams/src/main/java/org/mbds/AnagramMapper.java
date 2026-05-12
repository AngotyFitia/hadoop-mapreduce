package org.mbds;

import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AnagramMapper extends Mapper<Object, Text, Text, Text> {
    private Text sortedKey = new Text();
    private Text wordValue = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String word = value.toString().trim().toLowerCase();
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        String sorted = new String(letters);

        sortedKey.set(sorted);
        wordValue.set(word);
        context.write(sortedKey, wordValue);
    }
}
