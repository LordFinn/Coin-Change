import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

        List<Integer> cents = List.of(1,2,5,10,20,50);

        System.out.println(calculateMinimalCoins(8, cents));
        System.out.println(calculateMinimalCoins(42, cents));
        System.out.println(calculateMinimalCoins(273, cents));

        System.out.println(calculateAllMinimalCoins(8, cents));
        System.out.println(calculateAllMinimalCoins(42, cents));
        System.out.println(calculateAllMinimalCoins(273, cents));

        List<Integer> notCents = List.of(12,1,18,3,5);
        System.out.println(calculateMinimalCoins(8, notCents));
        System.out.println(calculateMinimalCoins(42, notCents));
        System.out.println(calculateMinimalCoins(273, notCents));

        System.out.println(calculateAllMinimalCoins(8, notCents));
        System.out.println(calculateAllMinimalCoins(42, notCents));
        System.out.println(calculateAllMinimalCoins(273, notCents));

    }

}