import java.util.Arrays;
import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) {
        String function = "x(t) = -6 * cos(0.63t + 3.14159)";
        double samplingPeriod = 0.5;
        int sampleCount = 10;
        AnalogFunction analogFunction = new AnalogFunction(function, samplingPeriod, sampleCount);
        System.out.println(analogFunction);
        System.out.println("Recovered Signal Values = " + Arrays.toString(analogFunction.recover()));
        analogFunction.calculateApproximationError(1.8);
    }
}


class AnalogFunction {

    private double amplitude; // амплітуда
    private double period; // період коливань
    private double initialPhase; // початкова фаза
    private final double samplingPeriod; // період дискретизації
    private final int sampleCount; // кількість відліків

    double frequency;
    double angularFrequency;
    double samplingFrequency;

    int[] samplingCountDiapason;
    double[] samplingTimeDiapason;
    double[] analogSignalValues;
    double[] digitalSignalValues;

    @Override
    public String toString() {
        return "Analog Function:" +
                "\nAmplitude A = " + amplitude +
                "\nPeriod T = " + period +
                "\nInitial phase φ = " + initialPhase +
                "\nSampling period T_s = " + samplingPeriod +
                "\nSample Count n = " + sampleCount +
                "\n\nFrequency = " + frequency +
                "\nAngular Frequency = " + angularFrequency +
                "\nSampling Frequency = " + samplingFrequency +
                "\n\nSampling Count Diapason n = " + Arrays.toString(samplingCountDiapason) +
                "\nSampling Time Diapason t = " + Arrays.toString(samplingTimeDiapason) +
                "\n\nAnalog Signal Values = " + Arrays.toString(analogSignalValues) +
                "\nDigital Signal Values = " + Arrays.toString(digitalSignalValues);
    }

    private void sample() {
        frequency = 1 / period;
        angularFrequency = 2 * Math.PI * frequency;
        samplingFrequency = 1 / samplingPeriod;
        samplingCountDiapason = IntStream.range(0, sampleCount + 1).toArray();
        samplingTimeDiapason = Arrays
                .stream(samplingCountDiapason)
                .mapToDouble(i -> i * samplingPeriod)
                .toArray();
        analogSignalValues = Arrays
                .stream(samplingTimeDiapason)
                .map(this::x)
                .toArray();
        digitalSignalValues = Arrays
                .stream(samplingCountDiapason)
                .mapToDouble(this::s)
                .toArray();
    }

    public double[] recover() {
        return Arrays.stream(samplingCountDiapason)
                .mapToDouble(this::y)
                .toArray();
    }

    public AnalogFunction(String equation, double T_s, int n) {
        extractValues(equation);
        this.samplingPeriod = T_s;
        this.sampleCount = n;
        sample();
    }

    private void extractValues(String equation) {
        // Розділити рівняння на окремі частини
        String[] parts = equation.split(" ");
        double A, w, phi;

        // Отримати значення A, w, phi з рівняння
        A = Double.parseDouble(parts[2]); // A знаходиться після "x(t) ="
        String numericPart = parts[4].substring(4, parts[4].length() - 1); // видалити "cos(" і "t" з кінця рядка
        w = Double.parseDouble(numericPart); // перетворити числову частину на тип double
        String phiStr = parts[6].substring(0, parts[6].length() - 1); // phi знаходиться перед закриваючою дужкою ")"
        phi = Double.parseDouble(phiStr);

        this.amplitude = A;
        this.period = 2 * Math.PI / w;
        this.initialPhase = phi;
    }

    private static double sinc(double x) {
        if (x == 0) {
            return 1.0;
        } else {
            return Math.sin(Math.PI * x) / (Math.PI * x);
        }
    }

    public double x(double a) {
        return amplitude * Math.cos(angularFrequency * a + initialPhase);
    }

    public double s(double a) {
        return Math.round(a) == a ? x(a * samplingPeriod) : 0;
    }

    public double y(double a) {
        return Arrays.stream(samplingCountDiapason)
                .mapToDouble(k -> s(k) * sinc(Math.PI * (a - k)))
                .sum();
    }

    public void calculateApproximationError(double t) {

        double percentError = Math.abs((x(t) - y(t / samplingPeriod)) / x(t)) * 100;

        System.out.println("\nt = " + t
                + "\nx(t) = " + x(t)
                + "\ns(t/T_s) = " + s(t / samplingPeriod)
                + "\ny(t/T_s) = " + y(t / samplingPeriod)
        );
        System.out.printf("Relative error = %.2f %%", percentError);
    }
}