package com.exceeddata.sklearnmodel.loadpredict.bean;

import java.util.List;

/**
 * @author lq
 * @since 2020-03-29 21:10
 */
public class Tree_ {
    private Integer max_depth;
    private Integer node_count;
    private List<List<Double>> nodes;
    private List<List<List<Double>>> values;
    private List<String> nodes_dtype;

    public Integer getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(Integer max_depth) {
        this.max_depth = max_depth;
    }

    public Integer getNode_count() {
        return node_count;
    }

    public void setNode_count(Integer node_count) {
        this.node_count = node_count;
    }

    public List<List<Double>> getNodes() {
        return nodes;
    }

    public void setNodes(List<List<Double>> nodes) {
        this.nodes = nodes;
    }

    public List<List<List<Double>>> getValues() {
        return values;
    }

    public void setValues(List<List<List<Double>>> values) {
        this.values = values;
    }

    public List<String> getNodes_dtype() {
        return nodes_dtype;
    }

    public void setNodes_dtype(List<String> nodes_dtype) {
        this.nodes_dtype = nodes_dtype;
    }

    @Override
    public String toString() {
        return "Tree_{" +
                "max_depth=" + max_depth +
                ", node_count=" + node_count +
                ", nodes=" + nodes +
                ", values=" + values +
                ", nodes_dtype=" + nodes_dtype +
                '}';
    }
}

