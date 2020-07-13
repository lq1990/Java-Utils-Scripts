package exd_fir1filter_streaming_bk;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.exceeddata.ac.common.exception.EngineException;

/**
 * @update liuqiang
 * @update 6/29/20
 */
public class Fir1Helper implements Serializable {
    private int taps = 0; // the filter length = #weights = order + 1
    private int offset = 0;
    private double[] coefficients = null; // the final filter weights
    private double[] buffer = null;
    private double mu = 0d;

    private int count; // number of inputted valued
    private boolean flag; // for enqueue
    private final Queue<Double> outputQueue; // filtered value enqueue or dequeue

    /**
     * taps is the filter length and #weights = n+1
     *
     * @param n filter order
     */
    public Fir1Helper(final int n) throws EngineException {
        if (n < 0) {
            throw new EngineException("FUNCTION_FIL1_N_INVALID");
        }
        this.taps = n + 1;
        this.coefficients = new double[this.taps];
        this.buffer = new double[this.taps];
        this.outputQueue  = new LinkedList<>();
    }

    public Fir1Helper(final double[] coefficients) throws EngineException {
        if (coefficients == null || coefficients.length == 0) {
            throw new EngineException("FUNCTION_FIL1_COEFFICIENTS_EMPTY");
        }
        this.taps = coefficients.length;
        this.coefficients = coefficients;
        this.buffer = new double[this.taps];
        this.outputQueue  = new LinkedList<>();
    }

    public Fir1Helper(final double[] coefficients, final int n) throws EngineException {
        if (n < 0) {
            throw new EngineException("FUNCTION_FIL1_N_INVALID");
        }
        if (coefficients == null || coefficients.length == 0) {
            throw new EngineException("FUNCTION_FIL1_COEFFICIENTS_EMPTY");
        }
        this.taps = n + 1;

        if (coefficients.length < this.taps) {
            throw new EngineException("FUNCTION_FIL1_COEFFICIENTS_LESS_THAN_N");
        } else if (coefficients.length == this.taps) {
            this.coefficients = coefficients;
        } else {
            this.coefficients = new double[this.taps];
            System.arraycopy(coefficients, 0, this.coefficients, 0, this.taps);
        }
        this.buffer = new double[this.taps];
        this.outputQueue  = new LinkedList<>();
    }

    public Fir1Helper(final Fir1Helper fir) {
        this.taps = fir.taps;
        this.offset = fir.offset;
        this.coefficients = Arrays.copyOf(fir.coefficients, fir.coefficients.length);
        this.buffer = Arrays.copyOf(fir.buffer, fir.buffer.length);
        this.mu = fir.mu;
        this.outputQueue  = new LinkedList<>();
    }

    @Override
    public Fir1Helper clone() {
        return new Fir1Helper(this);
    }

    public void setLearningRate(final double mu) {
        this.mu = mu;
    }

    public double getLearningRate() {
        return mu;
    }

    public void lmsUpdate(final double error) {
        int pos = 0;
        for (int i = offset; i >= 0; --i) {
            coefficients[pos++] += buffer[i] * error * mu;
        }

        for (int i = pos, j = taps - 1; i < taps; ++i) {
            coefficients[i] += buffer[j--] * error * mu;
        }
    }

    public double getTapInputPower() {
        double p = 0;
        for (int i = 0; i < taps; ++i) {
            p += buffer[i] * buffer[i];
        }

        return p;
    }

    /**
     * filter(), but the output needs to shifts and the shifted size is order/2.
     * <p>
     * advantage: fast and get output in real time
     * disadvan.: results of real time are actually not right, need to shift; the last #taps/2 value are not accurate even after shifting.
     */
    public double filter(final double input) {
        double output = 0d;
        int pos = 0;

        buffer[offset] = input;
        for (int i = offset; i >= 0; --i) { // 从offset处开始，减少index 遍历buffer。大于offset的index处的数据在第二个for遍
            output += buffer[i] * coefficients[pos++]; // pos递增的另一个妙用：在第二个for里，控制结束条件。当每个coef都拿到时 意味着 buffer也取完了
        }

        for (int j = taps - 1; pos < taps; ) { // j 都是从最大index开始
            output += buffer[j--] * coefficients[pos++]; // 单对coef而言，这一路 pos是 不断增大的，可以取到每一个coef
        }

        if (++offset >= taps) {
            offset = 0;
        }

        return output;
    }

    /**
     * filter, no need to shift
     */
    public void filterIntoQueue(final double input) {
        final double output = this.filter(input); // buffer inside.

        // In buffer already exists some values, before output enqueues
        if (flag || ++count > coefficients.length / 2) {
            this.outputQueue.offer(output);
            flag = true;
        }
    }

    /**
     * for filterNoShift(),
     * the output can be obtained after at least half the buffer are not zeros.
     *
     * @return can be null
     */
    public Double getOutput() {
        return outputQueue.poll();
    }

    /**
     * as matlab.filter(weights, 1, reals)
     * use difference equation to filter input in time domain.
     *
     * @param reals   data array with only real value, the imaginary parts are all 0.
     * @param weights the final filter weights
     * @return filtered data array
     */
    public static double[] filter(final double[] reals, final double[] weights) throws EngineException {
        if (reals == null || weights == null || reals.length == 0 || weights.length == 0)
            throw new EngineException("INVALID_PARAMS");
        if (weights.length % 2 == 0) throw new EngineException("WEIGHTS_LENGTH_SHOULD_BE_ODD");
        final int realsLen = reals.length;
        if (realsLen == 1) return reals;

        final int halfWLen = (weights.length - 1) / 2;
        final double[] filtered = new double[realsLen];
        double w;
        for (int i = 0; i < realsLen; ++i) {
            filtered[i] += reals[i] * weights[halfWLen];

            for (int j = 1; j <= halfWLen; ++j) {
                w = weights[halfWLen - j];
                if (i + j < realsLen) filtered[i] += reals[i + j] * w;
                if (i - j >= 0) filtered[i] += reals[i - j] * w;
            }
        }

        return filtered;
    }

    public void reset() {
        Arrays.fill(buffer, 0d);
        offset = 0;
    }

    public int getTaps() {
        return taps;
    }
}
