package org.mbds;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class GraphDriver {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: GraphDriver <input> <output>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Graph BFS Writable");
        job.setJarByClass(GraphDriver.class);

        job.setMapperClass(GraphMapper.class);
        job.setReducerClass(GraphReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(GraphNodeWritable.class);

        job.setInputFormatClass(GraphInputFormat.class);
        job.setOutputFormatClass(GraphOutputFormat.class);

        GraphInputFormat.addInputPath(job, new Path(args[0]));
        GraphOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
