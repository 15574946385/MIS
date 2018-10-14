package com.premilinary;

import java.io.*;
import java.util.*;

/**
 *  gujiewei
 *  2018/10/10
 *   experiment subject: 点带权无向图。
 **/
public class PhaseTwo {
    public static void main(String[] args){
        int vertexNum=200000;
        String dirPath="C:\\Users\\DearYou\\Desktop\\dataset\\tmp";
        File[] fileList=new File(dirPath).listFiles();

        for(int k=0;k<fileList.length;k++){
            System.out.println(fileList[k].getName());
            Graph graph = new Graph(vertexNum);
            Vertex[] head=graph.getHead();
            int vertexSum=0;
            try(BufferedReader br=new BufferedReader(new FileReader(fileList[k].getAbsolutePath()))){
                String line;
                String tag=",";
                //读取所有边  并且为每个新出现的点附一个新的权值。
                while((line=br.readLine())!=null){
                    String[] content=line.split(tag);
                    int from=Integer.parseInt(content[0]);
                    int to=Integer.parseInt(content[1]);
                    graph.setEdge(from,to);

                    //对图中点进行随机赋值
                    Random random=new Random();
                    if(head[from].weight==0){
                        head[from].weight= random.nextInt(100)+1;
                        vertexSum++;
                    }
                    if(head[to].weight==0) {
                        head[to].weight= random.nextInt(100)+1;
                        vertexSum++;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //计算出符合条件的点的个数 ，并且删除这些点
            int  index=1;
            while(true){
                int count=0;
                for (int i = 0; i <vertexNum; i++) {
                    Set<Integer> set=head[i].adjacent;
                    if(set!=null){
                        int sum=0;
                        for (Integer value: set){
                            sum+=head[value].weight;
                        }
                        if(sum<=head[i].weight) {
                            count++;
                            graph.delDegOneVer(i);
                        }
                    }
                }
                if(count==0) break;
                System.out.println("第"+index+"轮删除"+count+"个结点。");
                System.out.println("此轮一共"+vertexSum+"个结点。");
                vertexSum-=count;
                index++;
            }

            //寻找邻居完全相同的子图
            HashMap<String,ArrayList<Integer>> map=new HashMap<>();
            for(int i=0;i<vertexNum;i++){
                Set<Integer> set=head[i].adjacent;
                if(set!=null){
                    String tmp=set.toString();

                    if(!map.containsKey(tmp)){
                        map.put(tmp,new ArrayList<Integer>());
                    }
                    map.get(tmp).add(i);
                }
            }

            int sameNeiClass=0;
            int sameNeiAllVer=0;
            Iterator<Map.Entry<String,ArrayList<Integer>>> iter=map.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String, ArrayList<Integer>> e = iter.next();
                if(e.getValue().size()>1){
                    sameNeiClass++;
                    sameNeiAllVer+=e.getValue().size();
                }
            }
            System.out.println(sameNeiClass);
            System.out.println(sameNeiAllVer);

            System.out.println();
        }

    }
}
