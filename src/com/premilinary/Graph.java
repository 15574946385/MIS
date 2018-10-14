package com.premilinary;

import java.util.Set;
import java.util.TreeSet;

public class Graph {

    /**
     * 指向顶点表的引用
     */
    private Vertex[] head;

    /*
     * 当前顶点的个数
     */
    private int vertexNum,edgeNum;

    /**
     * 构造方法
     */
    public Graph(int vertexNum){
        this.vertexNum=vertexNum;
        head=new Vertex[vertexNum];
        //顶点表的初始化
        for(int i=0;i<this.vertexNum;i++){
            head[i]=new Vertex();
            head[i].headNum=i;
            head[i].adjacent=null;
        }
    }

    //边的输入
    public void setEdge(int fromNode,int toNode){
//      weight=sc.nextInt();
        if(fromNode!=toNode){
            setEdgeHelper(fromNode,toNode);
            setEdgeHelper(toNode,fromNode);
        }
    }

    public void setEdgeHelper(int fromNode,int toNode){
        if(head[fromNode].adjacent==null){
            head[fromNode].adjacent=new TreeSet<Integer>();
        }
        head[fromNode].adjacent.add(toNode);
    }

    //output the Graph in the form of list.
    public void outputGraph(){
        for(int i=0;i<vertexNum;i++){
            Set<Integer> p=head[i].adjacent;
            System.out.print(head[i].headNum+" ");
            if(p!=null){
                for(Integer value:p){
                    System.out.print(value+" ");
                }
            }
            System.out.println();
        }
    }

    //删除一个点
    public void delDegOneVer(int id){
        Set<Integer> set=head[id].adjacent;
        if(set==null) return ;
        else{
            for(Integer nei:set){
                head[nei].adjacent.remove(id);
                if(head[nei].adjacent.size()==0) head[nei].adjacent=null;
            }
            head[id].adjacent=null;
        }
    }

    public Vertex[] getHead(){
        return head;
    }

    public int getVertexNum(){
        return vertexNum;
    }

}
