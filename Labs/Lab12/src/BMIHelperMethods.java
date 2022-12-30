import java.text.DecimalFormat;

public class BMIHelperMethods {
    // This file is complete for you. Use these in BMIDisplay

    public static double getBMI(double weight, double height) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(weight / height / height * 703));
    }

    public static String getHealth(double bmi, int age, boolean isMale) {
        // BMI calculation is simple for adults (those 20 and older), but complicated for children and teenagers
        if (age >= 20) {
            if (bmi < 18.5) return " Underweight";
            if (bmi < 24.9) return " Normal Weight";
            if (bmi < 29.9) return " Overweight";
            if (bmi < 34.9) return " Obese";
            return " Severely Obese";
        }

        // There are graphs of typical age vs BMI for males and females, but as a massive simplification we just assume the
        // different percentile lines are parabolas. Don't take the results of this too seriously (to be fair BMI is simplistic anyway)
        // but this is mostly accurate for ages 4-5 and 19-20
        if (bmi < normalWeightPercentileEquation(age, isMale)) return "Underweight";
        if (bmi < overweightPercentileEquation(age, isMale)) return "Normal Weight";
        if (bmi < obesePercentileEquation(age, isMale)) return "Overweight";
        return "Obese";
    }

    private static double normalWeightPercentileEquation(int age, boolean isMale) {
        return .025 * Math.pow(age - 5, 2) + 13 + (isMale ? 1 : 0);
    }

    private static double overweightPercentileEquation(int age, boolean isMale) {
        return .025 * Math.pow(age - 5, 2) + 21 + (isMale ? 1 : 0);
    }

    private static double obesePercentileEquation(int age, boolean isMale) {
        return .025 * Math.pow(age - 5, 2) + 23.5 + (isMale ? 1 : 0);
    }
}
