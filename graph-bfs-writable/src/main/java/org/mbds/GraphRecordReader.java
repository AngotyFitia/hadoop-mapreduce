package org.mbds;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class GraphRecordReader extends RecordReader<Text, GraphNodeWritable> {
    private LineRecordReader lineRecordReader = null;
    private Text key = null;
    private GraphNodeWritable value = null;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        close();
        lineRecordReader = new LineRecordReader();
        lineRecordReader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (!lineRecordReader.nextKeyValue()) {
            key = null;
            value = null;
            return false;
        }
        Text line = lineRecordReader.getCurrentValue();
        String[] arr = line.toString().split("\\t");
        key = new Text(arr[0]);
        value = new GraphNodeWritable(arr[1]);
        return true;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public GraphNodeWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        if (lineRecordReader != null) {
            lineRecordReader.close();
            lineRecordReader = null;
        }
        key = null;
        value = null;
    }
}
