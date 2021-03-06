package com;


import com.graph.Graph;
import com.graph.Reductioner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class PhaseOne {
    public static void main(String[] args){
        JOptionPane jOptionPane=new JOptionPane();
        Graph graph=new Graph(55000);
//        graph.setEdge(0,2);
//        graph.setEdge(0,3);
//        graph.setEdge(1,2);
//        graph.setEdge(1,3);
        String csvFile = "C:\\Users\\DearYou\\Desktop\\dataset\\HR_edges.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                graph.setEdge(Integer.parseInt(country[0]),Integer.parseInt(country[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reductioner reductioner=new Reductioner(graph);

        reductioner.DegOneReduction();
        System.out.print(reductioner.seekToCombine().size());


    }
}
