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
        // TODO: create some examples here
    }

}