package com.exceeddata.sklearnmodel.loadpredict;

import com.exceeddata.sklearnmodel.loadpredict.bean.DecisionTreeNode;
import com.exceeddata.sklearnmodel.loadpredict.bean.TreeModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Date;

/**
 * script
 * @author lq
 * @since 2020-03-28 10:50
 */
public class Scripts {
    public static void main(String[] args) {

        try {
//            testJackson01();
            testJackson02();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private static void testNode() {
        DecisionTreeNode.CompareType eq = DecisionTreeNode.CompareType.FEATURE_GRATER_THAN_THRESHOLD;
        System.out.println(eq);
        System.out.println(eq.getTypeID());
    }

    private static void testJackson01() throws Exception {
        // json to javaobj
        String json = "{\"name\":\"zhangsan\",\"age\":20,\"birthday\":844099200000,\"email\":\"zhangsan@163.com\"}";

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        System.out.println(user);
        System.out.println(user.name);
        System.out.println(user.age);
        System.out.println(user.email);
        System.out.println(user.birthday);

    }

    private static void testJackson02() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TreeModel tree_model = mapper.readValue(
                new File("D:\\myBitbucket\\myJava\\sklearnmodel\\sklearnmodel-json-load-predict\\src\\main\\java\\com\\exceeddata\\sklearnmodel\\loadpredict\\tree_model"),
                TreeModel.class);

        System.out.println(tree_model);
        System.out.println(tree_model.getMeta());
        System.out.println(tree_model.getFeature_importances_());
        System.out.println(tree_model.getMax_features_());
        System.out.println(tree_model.getN_classes_());
        System.out.println(tree_model.getN_features_());
        System.out.println(tree_model.getN_outputs_());
        System.out.println("-----------tree_:");
        System.out.println(tree_model.getTree_());
        System.out.println(tree_model.getTree_().getMax_depth());
        System.out.println(tree_model.getTree_().getNode_count());
        System.out.println(tree_model.getTree_().getNodes());
        System.out.println(tree_model.getTree_().getValues());
        System.out.println(tree_model.getTree_().getNodes_dtype());
        System.out.println("---------------------");

        System.out.println(tree_model.getClasses_());
        System.out.println(tree_model.getParams());

    }



}

/**
 * get/set 必须写
 */
class User {
    String name;
    Integer age;
    Date birthday;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
