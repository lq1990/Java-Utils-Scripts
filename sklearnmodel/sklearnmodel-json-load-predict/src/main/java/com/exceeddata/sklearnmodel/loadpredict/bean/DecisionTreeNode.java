package com.exceeddata.sklearnmodel.loadpredict.bean;

import java.io.Serializable;


public final class DecisionTreeNode implements Serializable, Comparable<DecisionTreeNode> {
    private static final long serialVersionUID = 1L;

    public enum CompareType implements Serializable {
        FEATURE_EQUAL_THRESHOLD(0), // feature == threshold
        FEATURE_GRATER_THAN_THRESHOLD(1), // feature >= threshold
        FEATURE_SMALLER_THAN_THRESHOLD(2),// feature <= threshold
        ;
        private final int typeid;

        private CompareType(Integer typeid) {
            this.typeid = typeid;
        }

        public int getTypeID() {
            return this.typeid;
        }

        public static CompareType getByID(int id) {
            switch (id) {
            case 0:
                return FEATURE_EQUAL_THRESHOLD;
            case 1:
                return FEATURE_GRATER_THAN_THRESHOLD;
            case 2:
                return FEATURE_SMALLER_THAN_THRESHOLD;
            default:
                return null;
            }
        }
    }

    private int id; // index from 0
    private int feature; // feature id index
    private double threshold; // threshold of feature
    private CompareType comparetype; // 它本身是如何从 feather过来的
    private Double predict;// if predict is null , it means this Node is not a leaf.
    private int leftid; // left child id index
    private int rightid; // right child id index
    private DecisionTreeNode leftNode;
    private DecisionTreeNode rightNode;
    private boolean leaf = false; // if leaf

    public DecisionTreeNode() {
        this.id = -1;
        this.feature = -1;
        this.threshold = 0d;
        this.comparetype = CompareType.FEATURE_EQUAL_THRESHOLD;
        this.predict = null;
        this.leftid = -1;
        this.rightid = -1;
        this.leftNode = null;
        this.rightNode = null;
        this.leaf = false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeature() {
        return feature;
    }

    public void setFeature(final int feature) {
        this.feature = feature;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(final double threshold) {
        this.threshold = threshold;
    }

    public CompareType getComparetype() {
        return comparetype;
    }

    public void setComparetype(final CompareType comparetype) {
        this.comparetype = comparetype;
    }

    public Double getPredict() {
        return predict;
    }

    public void setPredict(final Double predict) {
        this.predict = predict;
//        this.leaf = !predict.isNull();
        this.leaf = !(predict == null); // with prediction, this leaf is not a leaf node
    }

    public boolean isLeaf() {
        return leaf;
    }

    public int getLeftid() {
        return leftid;
    }

    public void setLeftid(final int leftid) {
        this.leftid = leftid;
    }

    public int getRightid() {
        return rightid;
    }

    public void setRightid(final int rightid) {
        this.rightid = rightid;
    }

    public DecisionTreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(final DecisionTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public DecisionTreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(final DecisionTreeNode rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public int compareTo(final DecisionTreeNode paramT) {
        return 0;
    }

    @Override
    public String toString() {
        return "DecisionTreeNode{" +
                "id=" + id +
                ", feature=" + feature +
                ", threshold=" + threshold +
                ", comparetype=" + comparetype +
                ", predict=" + predict +
                ", leftid=" + leftid +
                ", rightid=" + rightid +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                ", leaf=" + leaf +
                '}';
    }
}
