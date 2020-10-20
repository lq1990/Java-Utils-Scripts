package math0.filters.iir;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 7/24/20 3:53 PM
 */
public class Client {
    public static void main(String[] args) {
        int count;

        DesignSpecification iirFilter = new DesignSpecification();
        iirFilter.cutoff = Math.PI / 2;
        iirFilter.stopband = Math.PI * 3. / 4;
        iirFilter.stopbandAttenuation = 30;

        int N;

        iirFilter.cutoff = 2 * Math.tan(iirFilter.cutoff / 2.);
        iirFilter.stopband = 2 * Math.tan(iirFilter.stopband / 2.);

        N = buttord(iirFilter.cutoff, iirFilter.stopband, iirFilter.stopbandAttenuation);
        System.out.println("N: "+N);

        double[] as = new double[N+1] , bs = new double[N+1];
        double[][] butterRes = butter(N,
                iirFilter.cutoff);
        as = butterRes[0];
        bs = butterRes[1];

        double[] az = new double[N+1], bz = new double[N+1];

        bilinear(N,
                as,bs,
                az,bz);

        System.out.println("as: "+ Arrays.toString(as));
        System.out.println("bs: "+ Arrays.toString(bs));
        System.out.println("az: "+ Arrays.toString(az));
        System.out.println("bz: "+ Arrays.toString(bz));


    }

    private static void fn(double[] arr) {
        arr[0] = 10;
        arr[1] = 11;
        arr[2] = 12;
    }

    private static Complex complexMult(final Complex a, final Complex b) {
        final Complex res = new Complex();
        res.real = a.real * b.real - a.imag * b.imag;
        res.imag = a.real * b.imag + a.imag * b.real;

        return res;
    }

    private static int buttord(final double cutoff, final double stopband, final double stopbandAttenuation) {
        return (int) Math.ceil(0.5 * (Math.log10(Math.pow(10, stopbandAttenuation / 10d) - 1) /
                Math.log10(stopband / cutoff)));
    }

    /**
     * output[0] = a;
     * output[1] = b;
     *
     * @param n      order
     * @param cutoff
     * @return double[][]
     */
    private static double[][] butter(int n, double cutoff) {
        double[] a = new double[n + 1];
        double[] b = new double[n + 1];

        double dk = 0d;
        int k = 0;
        int count = 0, count_1 = 0;
        Complex[] poles = new Complex[n];
        Complex[] res = new Complex[n + 1], resSave = new Complex[n + 1];

        if ((n & 1) == 0) {
            dk = 0.5;
        } else {
            dk = 0d;
        }

        for (k = 0; k <= 2 * n - 1; ++k) {
            if (cutoff * Math.cos((k + dk) * Math.PI / n) < 0) {
                poles[count].real = -cutoff * Math.cos((k + dk) * Math.PI / n);
                poles[count].imag = -cutoff * Math.sin((k + dk) * Math.PI / n);
                count++;
                if (count == n) break;
                ;
            }
        }

        res[0].real = poles[0].real;
        res[0].imag = poles[0].imag;
        res[1].real = 1;
        res[1].imag = 0;

        for (count_1 = 0; count_1 < n - 1; count_1++) {
            for (count = 0; count <= count_1 + 2; count++) {
                if (0 == count) {
                    Complex complex = complexMult(res[count], poles[count_1 + 1]);
                    resSave[count].real = complex.real;
                    resSave[count].imag = complex.imag;
                } else if ((count_1 + 2) == count) {
                    resSave[count].real += res[count - 1].real;
                    resSave[count].imag += res[count - 1].imag;
                } else {
                    Complex complex = complexMult(res[count], poles[count_1 + 1]);
                    resSave[count].real = complex.real;
                    resSave[count].imag = complex.imag;

                    resSave[count].real += res[count - 1].real;
                    resSave[count].imag += res[count - 1].imag;
                }
            }

            for (count = 0; count <= n; count++) {
                res[count].real = resSave[count].real;
                res[count].imag = resSave[count].imag;

                a[n - count] = res[count].real;
            }

        }

        b[n] = a[n];

        double[][] output = new double[2][];
        output[0] = a;
        output[1] = b;
        return output;
    }

    private static void bilinear(int N, double[] as, double[] bs, double[] az, double[] bz) {
        int Count = 0, Count_1 = 0, Count_2 = 0, Count_Z = 0;
        double[] Res = new double[N + 1];
        double[] Res_Save = new double[N + 1];

        for (Count_Z = 0; Count_Z <= N; Count_Z++) {
            az[Count_Z] = 0;
            bz[Count_Z] = 0;
        }


        for (Count = 0; Count <= N; Count++) {
            for (Count_Z = 0; Count_Z <= N; Count_Z++) {
                Res[Count_Z] = 0;
                Res_Save[Count_Z] = 0;
            }
            Res_Save[0] = 1;

            for (Count_1 = 0; Count_1 < N - Count; Count_1++) {
                for (Count_2 = 0; Count_2 <= Count_1 + 1; Count_2++) {
                    if (Count_2 == 0) {
                        Res[Count_2] += Res_Save[Count_2];
                        //printf( "Res[%d] %lf  \n" , Count_2 ,Res[Count_2]);
                    } else if ((Count_2 == (Count_1 + 1)) && (Count_1 != 0)) {
                        Res[Count_2] += -Res_Save[Count_2 - 1];
                        //printf( "Res[%d] %lf  \n" , Count_2 ,Res[Count_2]);
                    } else {
                        Res[Count_2] += Res_Save[Count_2] - Res_Save[Count_2 - 1];
                        //printf( "Res[%d] %lf  \n" , Count_2 ,Res[Count_2]);
                    }
                }

                //printf( "Res : ");
                for (Count_Z = 0; Count_Z <= N; Count_Z++) {
                    Res_Save[Count_Z] = Res[Count_Z];
                    Res[Count_Z] = 0;
                    //printf( "[%d]  %lf  " ,Count_Z, Res_Save[Count_Z]);
                }
                //printf(" \n" );

            }

            for (Count_1 = (N - Count); Count_1 < N; Count_1++) {
                for (Count_2 = 0; Count_2 <= Count_1 + 1; Count_2++) {
                    if (Count_2 == 0) {
                        Res[Count_2] += Res_Save[Count_2];
                        //printf( "Res[%d] %lf  \n" , Count_2 ,Res[Count_2]);
                    } else if ((Count_2 == (Count_1 + 1)) && (Count_1 != 0)) {
                        Res[Count_2] += Res_Save[Count_2 - 1];
                        //printf( "Res[%d] %lf  \n" , Count_2 ,Res[Count_2]);
                    } else {
                        Res[Count_2] += Res_Save[Count_2] + Res_Save[Count_2 - 1];
                        //printf( "Res[%d] %lf  \n" , Count_2 ,Res[Count_2]);
                    }
                }

                //	printf( "Res : ");
                for (Count_Z = 0; Count_Z <= N; Count_Z++) {
                    Res_Save[Count_Z] = Res[Count_Z];
                    Res[Count_Z] = 0;
                    //printf( "[%d]  %lf  " ,Count_Z, Res_Save[Count_Z]);
                }
                //printf(" \n" );
            }


            //printf( "Res : ");
            for (Count_Z = 0; Count_Z <= N; Count_Z++) {
                az[Count_Z] += Math.pow(2, N - Count) * (as[Count]) * Res_Save[Count_Z];
                bz[Count_Z] += bs[Count] * Res_Save[Count_Z];
            }

        }


        for (Count_Z = N; Count_Z >= 0; Count_Z--) {
            bz[Count_Z] = bz[Count_Z] / az[0];
            az[Count_Z] = az[Count_Z] / az[0];
        }
    }

}
