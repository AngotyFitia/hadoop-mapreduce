package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.fs.FSDataOutputStream;
import java.io.IOException;

public class GraphOutputFormat extends FileOutputFormat<Text, GraphNodeWritable> {
    @Override
    public RecordWriter<Text, GraphNodeWritable> getRecordWriter(TaskAttemptContext context)
            throws IOException, InterruptedException {
        Path path = FileOutputFormat.getOutputPath(context);
        Path fullPath = new Path(path, FileOutputFormat.getUniqueFile(context, "RESULTATS", ".txt"));
        FileSystem fs = path.getFileSystem(context.getConfiguration());
        FSDataOutputStream fileOut = fs.create(fullPath, context);
        return new GraphRecordWriter(fileOut);
    }
}
