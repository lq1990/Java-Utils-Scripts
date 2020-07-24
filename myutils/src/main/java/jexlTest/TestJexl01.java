package jexlTest;

import org.apache.commons.jexl3.*;

/**
 * 表达式里有 运算，
 * 解析，计算
 *
 * @author liuqiang
 * @since 7/21/20 3:12 PM
 */
public class TestJexl01 {
    public static void main(String[] args) {
       String expr = "1+2+3";
        testExpr(expr);

       expr = "2*3.0 / 5";
        testExpr(expr);


        testExpr02("");

    }

    public static void testExpr(final String expr) {
        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlExpression expression = jexlEngine.createExpression(expr);
        Object res = expression.evaluate(null);
        System.out.println(res);
    }

    public static void testExpr02(final String expr) {
        JexlContext jc = new MapContext();
        jc.set("pi", Math.PI);

        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlExpression e = jexlEngine.createExpression("math:cos(pi)");
        Object res = e.evaluate(jc);
        System.out.println(res);


    }

}
