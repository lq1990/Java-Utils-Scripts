package com.exceeddata.sklearnmodel.loadpredict.bean;

/**
 * @author lq
 * @since 2020-03-29 21:13
 */
public class Params {
    private String ccp_alpha;
    private Integer class_weight;
    private String criterion;
    private Integer max_depth;
    private Integer max_features;
    private Integer max_leaf_nodes;
    private Double min_impurity_decrease;
    private Double min_impurity_split;
    private Integer min_samples_leaf;
    private Integer min_samples_split;
    private Double min_weight_fraction_leaf;
    private String presort;
    private Integer random_state;
    private String splitter;

    public String getCcp_alpha() {
        return ccp_alpha;
    }

    public void setCcp_alpha(String ccp_alpha) {
        this.ccp_alpha = ccp_alpha;
    }

    public Integer getClass_weight() {
        return class_weight;
    }

    public void setClass_weight(Integer class_weight) {
        this.class_weight = class_weight;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public Integer getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(Integer max_depth) {
        this.max_depth = max_depth;
    }

    public Integer getMax_features() {
        return max_features;
    }

    public void setMax_features(Integer max_features) {
        this.max_features = max_features;
    }

    public Integer getMax_leaf_nodes() {
        return max_leaf_nodes;
    }

    public void setMax_leaf_nodes(Integer max_leaf_nodes) {
        this.max_leaf_nodes = max_leaf_nodes;
    }

    public Double getMin_impurity_decrease() {
        return min_impurity_decrease;
    }

    public void setMin_impurity_decrease(Double min_impurity_decrease) {
        this.min_impurity_decrease = min_impurity_decrease;
    }

    public Double getMin_impurity_split() {
        return min_impurity_split;
    }

    public void setMin_impurity_split(Double min_impurity_split) {
        this.min_impurity_split = min_impurity_split;
    }

    public Integer getMin_samples_leaf() {
        return min_samples_leaf;
    }

    public void setMin_samples_leaf(Integer min_samples_leaf) {
        this.min_samples_leaf = min_samples_leaf;
    }

    public Integer getMin_samples_split() {
        return min_samples_split;
    }

    public void setMin_samples_split(Integer min_samples_split) {
        this.min_samples_split = min_samples_split;
    }

    public Double getMin_weight_fraction_leaf() {
        return min_weight_fraction_leaf;
    }

    public void setMin_weight_fraction_leaf(Double min_weight_fraction_leaf) {
        this.min_weight_fraction_leaf = min_weight_fraction_leaf;
    }

    public String getPresort() {
        return presort;
    }

    public void setPresort(String presort) {
        this.presort = presort;
    }

    public Integer getRandom_state() {
        return random_state;
    }

    public void setRandom_state(Integer random_state) {
        this.random_state = random_state;
    }

    public String getSplitter() {
        return splitter;
    }

    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    @Override
    public String toString() {
        return "Params{" +
                "ccp_alpha='" + ccp_alpha + '\'' +
                ", class_weight=" + class_weight +
                ", criterion='" + criterion + '\'' +
                ", max_depth=" + max_depth +
                ", max_features=" + max_features +
                ", max_leaf_nodes=" + max_leaf_nodes +
                ", min_impurity_decrease=" + min_impurity_decrease +
                ", min_impurity_split=" + min_impurity_split +
                ", min_samples_leaf=" + min_samples_leaf +
                ", min_samples_split=" + min_samples_split +
                ", min_weight_fraction_leaf=" + min_weight_fraction_leaf +
                ", presort='" + presort + '\'' +
                ", random_state=" + random_state +
                ", splitter='" + splitter + '\'' +
                '}';
    }
}
