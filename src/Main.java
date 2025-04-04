package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read input scores
        System.out.println("Enter student grades (0-100) separated by spaces:");
        String input = scanner.nextLine();

        // Parse scores into array
        String[] scoreStrings = input.trim().split("\\s+");
        int[] scores = new int[scoreStrings.length];

        for (int i = 0; i < scoreStrings.length; i++) {
            try {
                scores[i] = Integer.parseInt(scoreStrings[i]);
                // Validate score range
                if (scores[i] < 0 || scores[i] > 100) {
                    System.out.println("Error: Grades must be between 0 and 100. Found: " + scores[i]);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter numbers only.");
                return;
            }
        }

        // Calculate statistics
        int max = findMaximum(scores);
        int min = findMinimum(scores);
        double average = calculateAverage(scores);

        // Create stats array for distribution with correct ranges:
        // stats[0]: grades from 0 to 20
        // stats[1]: grades from 21 to 40
        // stats[2]: grades from 41 to 60
        // stats[3]: grades from 61 to 80
        // stats[4]: grades from 81 to 100
        int[] stats = new int[5];
        for (int score : scores) {
            if (score >= 0 && score <= 20) {
                stats[0]++;
            } else if (score >= 21 && score <= 40) {
                stats[1]++;
            } else if (score >= 41 && score <= 60) {
                stats[2]++;
            } else if (score >= 61 && score <= 80) {
                stats[3]++;
            } else { // score >= 81 && score <= 100
                stats[4]++;
            }
        }

        // Display results
        displayResults(scores, max, min, average, stats);

        scanner.close();
    }

    /**
     * Finds the maximum value in the array
     */
    private static int findMaximum(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Finds the minimum value in the array
     */
    private static int findMinimum(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Calculates the average of array values
     */
    private static double calculateAverage(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return (double) sum / array.length;
    }

    /**
     * Displays the results including statistics and graph
     */
    private static void displayResults(int[] scores, int max, int min, double average, int[] stats) {
        // Display values section
        System.out.println("Values:");

        for (int score : scores) {
            System.out.print(score + " ");
        }
        System.out.println("\n");

        System.out.println("The maximum grade is " + max);
        System.out.println("The minimum grade is " + min);
        System.out.printf("The average grade is %.6f\n\n", average);

        // Display graph section
        System.out.println("Graph:");
        System.out.println();

        // Find maximum frequency for scaling
        int maxFrequency = 0;
        for (int count : stats) {
            if (count > maxFrequency) {
                maxFrequency = count;
            }
        }

        // If no students in any range, set max to 1 to avoid division by zero
        if (maxFrequency == 0) {
            maxFrequency = 1;
        }

        // Calculate how many rows we need (min 1, max 6)
        int maxRows = Math.min(6, maxFrequency);

        // Draw graph rows from top to bottom
        for (int row = maxRows; row >= 1; row--) {
            System.out.printf("%d > ", row);

            for (int stat : stats) {
                // Calculate threshold for displaying # at this row
                double threshold = (double) maxFrequency / maxRows * row;

                // Display # if the frequency in this range exceeds the threshold
                if (stat >= threshold) {
                    System.out.print("########");
                } else {
                    System.out.print("        ");
                }

                // Add space between columns
                System.out.print(" ");
            }

            System.out.println();
        }

        // Draw x-axis
        System.out.print("  +");
        for (int i = 0; i < stats.length; i++) {
            System.out.print("--------+");
        }
        System.out.println();

        // Draw range labels
        System.out.print("  I");
        for (int i = 0; i < stats.length; i++) {
            int start = i * 20 + (i > 0 ? 1 : 0);
            int end = (i + 1) * 20;
            System.out.printf(" %2d-%-3d I", start, end);
        }
        System.out.println();
    }
}
