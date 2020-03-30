package com.exceeddata.sklearnmodel.loadpredict.bean;

import java.util.List;

/**
 * tree_model 转成 POJO。
 *
 * 包含了整个树的信息
 *
 * @author lq
 * @since 2020-03-28 13:28
 */
public class TreeModel {

    private String meta;
    private List<Double> feature_importances_;
    private Integer max_features_;
    private Integer n_classes_;
    private Integer n_features_;
    private Integer n_outputs_;
    private Tree_ tree_;
    private List<Integer> classes_;
    private Params params;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public List<Double> getFeature_importances_() {
        return feature_importances_;
    }

    public void setFeature_importances_(List<Double> feature_importances_) {
        this.feature_importances_ = feature_importances_;
    }

    public Integer getMax_features_() {
        return max_features_;
    }

    public void setMax_features_(Integer max_features_) {
        this.max_features_ = max_features_;
    }

    public Integer getN_classes_() {
        return n_classes_;
    }

    public void setN_classes_(Integer n_classes_) {
        this.n_classes_ = n_classes_;
    }

    public Integer getN_features_() {
        return n_features_;
    }

    public void setN_features_(Integer n_features_) {
        this.n_features_ = n_features_;
    }

    public Integer getN_outputs_() {
        return n_outputs_;
    }

    public void setN_outputs_(Integer n_outputs_) {
        this.n_outputs_ = n_outputs_;
    }

    public Tree_ getTree_() {
        return tree_;
    }

    public void setTree_(Tree_ tree_) {
        this.tree_ = tree_;
    }

    public List<Integer> getClasses_() {
        return classes_;
    }

    public void setClasses_(List<Integer> classes_) {
        this.classes_ = classes_;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TreeModel{" +
                "meta='" + meta + '\'' +
                ", feature_importances_=" + feature_importances_ +
                ", max_features_=" + max_features_ +
                ", n_classes_=" + n_classes_ +
                ", n_features_=" + n_features_ +
                ", n_outputs_=" + n_outputs_ +
                ", tree_=" + tree_ +
                ", classes_=" + classes_ +
                ", params=" + params +
                '}';
    }
}


