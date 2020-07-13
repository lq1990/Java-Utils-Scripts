package filters;


import filters.FFT01.FFT01;
import filters.FFT02.FFT02;

import java.util.Arrays;

/**
 * test FFT
 * <p>
 * FFT01 is right.
 *
 * @author liuqiang
 * @since 6/29/20 4:04 PM
 */
public class Client {
    private static final double sampleFreq = 100;

    final static int SIZE = (int) Math.pow(2, 3);

    public static void main(String[] args) {


        Complex[] x = new Complex[SIZE];

        for (int i = 0; i < SIZE; ++i) {
            // h = 5*sin(2*pi*x) + 4*sin(2*2*pi*x) + 3*sin(3*2*pi*x);
            x[i] = new Complex(0, 0);
            double t = i / sampleFreq;
            x[i].re = 10 * Math.sin(2 * Math.PI * 7 * t) + 5 * Math.cos(2 * Math.PI * 20 * t) + 2 * Math.cos(2 * Math.PI * 35 * t);
        }

        test01(x); // right
        System.out.println("==========================");
        test02(x);

    }

    public static void test01(Complex[] x) {
        System.out.println("test01");

        Complex[] fft = FFT01.fft(x);

        double[] amps = new double[x.length];

        int count = 0;
        for (Complex complex : fft) {
//            System.out.println("re: "+complex.re);
//            System.out.println("im: "+complex.im);
            amps[count++] = Math.sqrt(Math.pow(complex.re, 2) + Math.pow(complex.im, 2));
        }

        System.out.println("re, im: ");
        for (Complex complex : fft) {
            System.out.print(complex.re + ", " + complex.im + ", ");
        }


        System.out.println("amps: ");
        System.out.println(Arrays.toString(amps));

        System.out.println("magdb:");
        double[] magdb = new double[amps.length];
        for (int i = 0; i < amps.length; ++i) {
            magdb[i] = 20 * Math.log10(amps[i]); // log10
        }
        System.out.println(Arrays.toString(magdb));

        System.out.println("phase:");
        double[] phase = new double[amps.length];
        for (int i = 0; i < amps.length; ++i) {
            phase[i] = Math.atan2(fft[i].im, fft[i].re); // b/a, if a+bi
        }
        System.out.println(Arrays.toString(phase));

        // freqs = np.array( list(range(0, int(N/2+1))) )* fs / N
        double[] freqs = new double[amps.length];
        for (int i = 0; i < amps.length; ++i) {
            freqs[i] = i * sampleFreq / SIZE;
        }

        System.out.println("freqs: ");
        System.out.println(Arrays.toString(freqs));
    }

    public static void test02(Complex[] x) {
        System.out.println("test02");

        Complex[] fft = FFT02.fft(x);

        for (Complex complex : fft) {
            System.out.println("re: " + complex.re);
            System.out.println("im: " + complex.im);
        }
    }
}
