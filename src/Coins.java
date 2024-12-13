import java.util.ArrayList;
import java.util.List;

public class Coins {

    private static List<Integer> calculateMinimalCoins(int n, List<Integer> coins) {

        int[] numberCoins = new int[n + 1];
        int[] nextCoins  = new int[n + 1];

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

    public static void main(String[] args) {

        List<Integer> cents = List.of(1,2,5,10,20,50);
        System.out.println(calculateMinimalCoins(8, cents));
        System.out.println(calculateMinimalCoins(42, cents));
        System.out.println(calculateMinimalCoins(273, cents));

        List<Integer> notCents = List.of(12,1,18,3,5);
        System.out.println(calculateMinimalCoins(8, notCents));
        System.out.println(calculateMinimalCoins(42, notCents));
        System.out.println(calculateMinimalCoins(273, notCents));

    }

}