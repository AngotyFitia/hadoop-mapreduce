package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import java.io.DataOutputStream;
import java.io.IOException;

public class GraphRecordWriter extends RecordWriter<Text, GraphNodeWritable> {
    private DataOutputStream out;

    public GraphRecordWriter(DataOutputStream stream) {
        out = stream;
    }

    @Override
    public void write(Text key, GraphNodeWritable value) throws IOException, InterruptedException {
        out.writeBytes(key.toString() + "\t" + value.get_serialized() + "\n");
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        out.close();
    }
}
