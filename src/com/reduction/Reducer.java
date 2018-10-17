package com.reduction;

import com.premilinary.Graph;
import com.premilinary.Vertex;

import java.util.*;

/**
 * @author gujiewei
 * @create 2018/10/17
 * @desc
 **/
public class Reducer {
    private Graph graph;
    private Vertex[] head;
    private int vertexSum;
    private int vertexNum;

    public Reducer(Graph graph){
        this.graph=graph;
        head=graph.getHead();
    }

    public void setVertexSum(int vertexSum,int vertexNum){
        this.vertexSum=vertexSum;
        this.vertexNum=vertexNum;
    }

    //singleVertex 时，迭代删除该点权 > 邻居权之和；
    public void singleVerNoEdge(){
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
                        //删除这个点
                        graph.delVer(i);
                        //删除它的邻居
                        for (Integer value:set){
                            graph.delVer(value);
                            count++;
                        }
                    }
                }else if(set==null && head[i].weight!=0){
                    count++;
                    graph.delVer(i);
                }
            }

            if(count==0) break;
            System.out.println("第"+index+"轮删除"+count+"个结点。");
            System.out.println("此轮一共"+vertexSum+"个结点。");
            vertexSum-=count;
            index++;
        }
    }

    //singleVertex 迭代邻居中一条边
    public void count(){
        HashMap<Integer,Integer> res=new HashMap<>();
        for(int i=0;i<vertexNum;i++){
            int sum=0;
            Set<Integer> set=head[i].adjacent;
            if(set!=null){
                HashMap<String, HashSet<Integer>> map=new HashMap<>();
                for(Integer nei:set){
                    Set<Integer> set1=head[nei].adjacent;
                    for (Integer neiOfNei:set1) {
                        String edge;
                        if(nei<neiOfNei) edge=""+nei+neiOfNei;
                        else edge=""+neiOfNei+nei;
                        if(map.containsKey(edge)){
                            sum++;
                            map.get(edge).add(nei);
                        }
                        else
                            map.put(edge,new HashSet<Integer>());
                    }
                }
                if(res.get(sum)==null)
                    res.put(sum,0);
                res.put(sum,res.get(sum)+1);
            }
        }

        Iterator<Map.Entry<Integer,Integer>> iter=res.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Integer,Integer> e=iter.next();
            System.out.print(e.getKey()+" ");
            System.out.println(e.getValue());
        }
    }
}
