package math0.filters.fir1;

/**
 * 1. matlab.fir1() 的结果就是： 求解 weights。
 * 有了weights之后，
 * <p>
 * 2. matlab.filter() 差分方程，对time domain的输入 进行滤波。  注意：由于差分方程的原因，out会有delay
 * <p>
 * 见网址 http://www.labbookpages.co.uk/audio/firWindowing.html#code
 *
 *
 *
 * exd中有了 FirwinHelper，可以生成 window， 即final filter weights 用于filter（） 差分，滤波时序数据
 *
 * @author liuqiang
 * @since 6/30/20 2:38 PM
 */
public class MyFir1 {

    public static void main(String[] args) {
        lowpassWeighs();
    }

    public static void lowpassWeighs() {
        // sinc * hamming

        final int filterLength = 21; // number of weights
        final double sampleFreq = 2000; // Hz
        final double cutoff = 460; // Hz

        final int M = filterLength - 1; // filter order
        final double ft = cutoff / sampleFreq;

        final double[] weights = new double[filterLength];

        for (int n = 0; n < filterLength; ++n) {
            if (n != M / 2) {
                weights[n] = Math.sin(2d * Math.PI * ft * (n - M / 2d)) / Math.PI / (n - M / 2d);
            } else {
                weights[n] = 2d * ft;
            }
        }

        for (double weight : weights) {
            System.out.println(weight);
        }
    }

    public static void highpassWeighs(final double cutoff, final int order) {


    }

    public static void bandpassWeighs(final double cutoff, final int order) {


    }

    public static void bandstopWeighs(final double cutoff, final int order) {


    }
}
