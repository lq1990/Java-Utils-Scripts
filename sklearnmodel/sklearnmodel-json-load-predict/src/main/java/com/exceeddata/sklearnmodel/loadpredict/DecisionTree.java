package com.exceeddata.sklearnmodel.loadpredict;

import com.exceeddata.sklearnmodel.loadpredict.bean.DecisionTreeNode;
import com.exceeddata.sklearnmodel.loadpredict.bean.TreeModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * load json from sklearn-json
 * build
 * predict
 *
 * @author lq
 * @since 2020-03-28 10:39
 */
public class DecisionTree {

    private TreeModel treeModel;

    final private DecisionTreeNode root = new DecisionTreeNode();

    public DecisionTree() {
    }



    public void load(String path) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TreeModel tree_model = mapper.readValue(
//                new File(
//                        "D:\\myBitbucket\\myJava\\sklearnmodel\\sklearnmodel-json-load-predict\\src\\main\\java\\com\\exceeddata\\sklearnmodel\\loadpredict\\tree_model")
                new File(path)
                , TreeModel.class);

        this.treeModel = tree_model;
    }

    /**
     * build tree with treeModel,
     * add all to root
     */
    public void build() {
        List<List<Double>> nodes = treeModel.getTree_().getNodes();
        List<List<List<Double>>> values = treeModel.getTree_().getValues();

        this.buildANode(root, 0, nodes, values);

//        List<Double> first = nodes.get(0);
//
//        this.root = new DecisionTreeNode();
//        root.setId(0);
//
//        root.setLeftid(first.get(0).intValue());
//        root.setRightid(first.get(1).intValue());
//        root.setFeature(first.get(2).intValue());
//        root.setThreshold(first.get(3));
//        if (first.get(0).intValue() == -1) {
//            // leaf
//            root.setPredict(getMaxIdx(values.get(0).get(0)));
//        }

//        root.setLeftNode();
//        root.setRightNode();
    }

//    private DecisionTreeNode buildANode(DecisionTreeNode node, int id,
//                                        List<List<Double>> nodes,
//                                        List<List<List<Double>>> values) {
//
////        System.out.println("id: "+id);
////        System.out.print("node --- ");
////        System.out.println(node);
//
//        // build itself
//        List<Double> cur = nodes.get(id);
//
//        node = new DecisionTreeNode();
//
//        node.setId(id);
//        node.setLeftid(cur.get(0).intValue());
//        node.setRightid(cur.get(1).intValue());
//        node.setFeature(cur.get(2).intValue());
//        node.setThreshold(cur.get(3));
//        if (cur.get(0).intValue() == -1) {
//            // leaf
//            node.setPredict(getMaxIdx(values.get(id).get(0)));
//
//            // stop condition
//            System.out.print("1 --- ");
//            System.out.println(node);
//            return node;
//        }
//
//        // 调用自己，创建左右子树
//        node.setLeftNode(buildANode(node.getLeftNode(), node.getLeftid(), nodes, values));
//        node.setRightNode(buildANode(node.getRightNode(), node.getRightid(), nodes, values));
//
//        System.out.print("2 --- ");
//        System.out.println(node);
//        return node;
//    }
//


    // 递归
    private void buildANode(DecisionTreeNode node, int id,
                                        List<List<Double>> nodes,
                            List<List<List<Double>>> values) {

        // build itself
        List<Double> cur = nodes.get(id);

        node.setId(id);
        node.setLeftid(cur.get(0).intValue());
        node.setRightid(cur.get(1).intValue());
        node.setFeature(cur.get(2).intValue());
        node.setThreshold(cur.get(3));

        // if leaf
        if (cur.get(0).intValue() == -1) {
            node.setPredict(getMaxIdx(values.get(id).get(0)));

            // stop condition
            System.out.print("1 --- ");
            System.out.println(node);
            return;
        }

        // else, not leaf

        // 调用自己，创建左右子树
        node.setLeftNode(new DecisionTreeNode());
        buildANode(node.getLeftNode(), node.getLeftid(), nodes, values);

        node.setRightNode(new DecisionTreeNode());
        buildANode(node.getRightNode(), node.getRightid(), nodes, values);

        System.out.print("2 --- ");
        System.out.println(node);

//        return;
    }



    public Integer predict(List<Double> list) {
//        System.out.print("--- predict: ");
//        System.out.println(predict0(root, list));
        return predict0(root, list);
    }

    private Integer predict0(DecisionTreeNode node, List<Double> list) {
        // stop condition
        if(node.isLeaf()) {
            return node.getPredict().intValue();
        }


        int feature = node.getFeature();
        double threshold = node.getThreshold();

        if(list.get(feature) <= threshold ) {
            // go left
            return predict0(node.getLeftNode(), list);

        } else {
            // go right
            return predict0(node.getRightNode(), list);
        }

    }

    private Double getMaxIdx(final List<Double> list) {
        int idx = -1;
        Double maxVal = Double.MIN_VALUE;

        for (int i=0; i<list.size(); ++i) {
            Double tmp = list.get(i);
            if (tmp > maxVal) {
                maxVal = tmp;
                idx = i;
            }
        }

        return (double)idx;
    }


    public DecisionTreeNode getRoot() {
        return root;
    }

    public TreeModel getTreeModel() {
        return treeModel;
    }
}
