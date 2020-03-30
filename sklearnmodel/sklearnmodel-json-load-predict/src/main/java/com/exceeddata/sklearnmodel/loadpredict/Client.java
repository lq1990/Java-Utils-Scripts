package com.exceeddata.sklearnmodel.loadpredict;

import com.exceeddata.sklearnmodel.loadpredict.bean.DecisionTreeNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author lq
 * @since 2020-03-28 11:03
 */
public class Client {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
        // ===============================================


        DecisionTree tree = new DecisionTree();
        tree.load("D:\\myBitbucket\\myJava\\sklearnmodel\\sklearnmodel-json-load-predict\\src\\main\\java\\com\\exceeddata\\sklearnmodel\\loadpredict\\tree_model");
        tree.build();

        Double[] arr0 = {14.23,  1.71,  2.43,  15.6,  127.0,  2.80,  3.06,  0.28,  2.29,  5.64,  1.04,  3.92, 1065.0};
        Double[] arr = {13.20,  1.78,  2.14,  11.2,  100.0,  2.65,  2.76,  0.26,  1.28,  4.38,  1.05,  3.40, 1050.};


        ArrayList<Double> list = new ArrayList<Double>(Arrays.asList(arr));
        System.out.print("predict: ");
        System.out.println(tree.predict(list));

//        System.out.println(tree.getTreeModel());

//        DecisionTreeNode root = tree.getRoot();
//        System.out.println("---");
//        System.out.println(root);



        // ==============================================
        long endTime = System.currentTimeMillis();
        System.out.println("---\ntime: "+(endTime-startTime));


    }
}
