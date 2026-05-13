package org.mbds;

import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GraphNodeWritable implements Writable {
    private String neighbors;
    private String color;
    private int distance;

    // Constructeur vide (obligatoire pour Writable)
    public GraphNodeWritable() {}

    // Constructeur depuis une ligne (désérialisation)
    public GraphNodeWritable(String data) {
        String[] parts = data.split("\\|");
        this.neighbors = parts[0];
        this.color = parts[1];
        this.distance = Integer.parseInt(parts[2]);
    }

    // Sérialisation en String
    public String get_serialized() {
        return neighbors + "|" + color + "|" + distance;
    }

    // Méthodes Writable
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(neighbors);
        out.writeUTF(color);
        out.writeInt(distance);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        neighbors = in.readUTF();
        color = in.readUTF();
        distance = in.readInt();
    }

    // Getters/Setters
    public String getNeighbors() { return neighbors; }
    public String getColor() { return color; }
    public int getDistance() { return distance; }

    public void setNeighbors(String n) { neighbors = n; }
    public void setColor(String c) { color = c; }
    public void setDistance(int d) { distance = d; }
}
