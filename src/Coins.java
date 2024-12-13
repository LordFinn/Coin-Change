import java.util.*;

public class Coins {

    // calculates one optimal solution
    private static List<Integer> calculateMinimalCoins(int n, List<Integer> coins) {

        int[] numberCoins = new int[n + 1];
        int[] nextCoins = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int bestNumber = Integer.MAX_VALUE;
            int bestCoin = 0;
            for (int coin : coins) {
                if ((i >= coin) && (numberCoins[i - coin] + 1 < bestNumber)) {
                    bestNumber = numberCoins[i - coin] + 1;
                    bestCoin = coin;
                }
            }
            numberCoins[i] = bestNumber;
            nextCoins[i] = bestCoin;
        }

        List<Integer> returnList = new ArrayList<>();
        while (n != 0) {
            returnList.add(nextCoins[n]);
            n = n - nextCoins[n];
        }
        return returnList;
    }

    // calculates all optimal solutions
    private static List<List<Integer>> calculateAllMinimalCoins(int n, List<Integer> coins) {

        int[] numberCoins = new int[n + 1];
        List<List<Integer>> nextCoins = new ArrayList<>();
        nextCoins.add(new ArrayList<>());

        for (int i = 1; i <= n; i++) {
            int bestNumber = Integer.MAX_VALUE;
            List<Integer> bestCoins = new ArrayList<>();
            for (int coin : coins) {
                if ((i >= coin) && (numberCoins[i - coin] + 1 < bestNumber)) {
                    bestNumber = numberCoins[i - coin] + 1;
                    bestCoins.clear();
                    bestCoins.add(coin);
                } else if ((i >= coin) && (numberCoins[i - coin] + 1 == bestNumber)) {
                    bestCoins.add(coin);
                }
            }
            numberCoins[i] = bestNumber;
            nextCoins.add(bestCoins);
        }

        List<List<Integer>> returnList = collectSolutions(n, nextCoins); // includes duplicates
        for (List<Integer> list : returnList) {
            list.sort(Integer::compareTo);
        } // sort them all (probably way too expensive xD)
        return new HashSet<>(returnList).stream().toList(); // removes duplicates
    }

    // collects solutions recursively (it would probably be better to do that dynamically too)
    private static List<List<Integer>> collectSolutions(int n, List<List<Integer>> nextCoins) {
        if (n == 0) {
            return new ArrayList<>();
        } else {
            List<List<Integer>> returnList = new ArrayList<>();
            List<Integer> possibleNextCoins = nextCoins.get(n);
            for (int coin : possibleNextCoins) {
                List<List<Integer>> allSolutionsForRest = collectSolutions(n - coin, nextCoins);
                if (!allSolutionsForRest.isEmpty()) {
                    for (List<Integer> solutionForRest : allSolutionsForRest) {
                        List<Integer> updatedSolution = new ArrayList<>(solutionForRest);
                        updatedSolution.add(coin);
                        returnList.add(updatedSolution);
                    }
                } else {
                    returnList.add(possibleNextCoins);
                }
            }
            return returnList;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please note that calculating all different solutions is a bit buggy for high values (>10k);");
        System.out.println("for a more consistent analysis one may modify line 104 to calculate only one solution.");
        System.out.println("Also note that user inputs are not checked for sanity, beware exceptions!\n");

        while (true) {
            System.out.println("Please enter the different coin values (seperated by ,):");
            List<Integer> coins = Arrays.stream(scanner.nextLine().split(","))
                    .map(String::trim)
                    .map(Integer::valueOf)
                    .toList();
            System.out.println("Your coins: " + coins);
            System.out.println("Please enter a number to analyze or press enter to choose new coins:");
            String number = scanner.nextLine();
            while (!number.isEmpty()) {
                int n = Integer.parseInt(number);
                System.out.println("All optimal solutions for n = " + n + ":");
                System.out.println(calculateAllMinimalCoins(n, coins));
                System.out.println();
                System.out.println("Please enter a number to analyze or press enter to choose new coins:");
                number = scanner.nextLine();
            }
            System.out.println();
        }

    }

}