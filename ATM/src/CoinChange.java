import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Scanner; 


public class CoinChange { 
    private int[] coins = { 20,50,100,500,1000 };  
    private int[] countCoins = {14,5,3,2,1};
    static TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(Collections.reverseOrder());
    static {
     map.put(1000, 3);
     map.put(500, 4);
     map.put(100, 5);
     map.put(50, 5);
     map.put(20, 3);
 }
//    static {
//        map.put(1000, 200);
//        map.put(500, 30);
//        map.put(100, 40);
//        map.put(50, 50);
//        map.put(20, 10);
//    }

    /**
     * Uses dynamic programming to find the possible combinations
     * 
     * @param n - the target
     * @return
     */
    public List<List<Integer>> findPossibleCombinationsDP(int n) {
        /**
         * Cell is a class to represent each cell in the n*m grid
         * 
         * @author Eric
         *
         */
        class Cell {
            // All of the possible combinations at each cell
            private List<List<Integer>> combinations = new ArrayList<List<Integer>>();

            List<List<Integer>> getCombinations() {
                return combinations;
            }

            void setCombinations(List<List<Integer>> combinations) {
                this.combinations = combinations;
            }

            void addCombination(List<Integer> combination) {
                if (combination != null) {
                    combinations.add(new ArrayList<Integer>(combination));
                }
            }

        }

        // Create the grid
        Cell[][] sol = new Cell[coins.length + 1][n + 1];

        // Create new cells for the boundary values
        for (int i = 0; i < coins.length + 1; i++) {
            sol[i][0] = new Cell();
        }
        for (int i = 1; i < n + 1; i++) {
            sol[0][i] = new Cell();
        }

        for (int i = 1; i < coins.length + 1; i++) {
            int coin = coins[i - 1];
            for (int j = 1; j < n + 1; j++) {
                Cell cell = new Cell();

                Cell prevCoinCell = sol[i - 1][j];
                // Copy the combinations
                cell.setCombinations(prevCoinCell.getCombinations());

                if (j == coin) {
                    // Only need to add the coin as a combination by itself in this case
                    cell.addCombination(new ArrayList<Integer>(Arrays.asList(coin)));
                } else if (coin < j) {
                    // In this case we need to get the previous cell minus the
                    // size of the coin. Each combination needs to have
                    // the current combination added to it
                    Cell prevCell = sol[i][j - coin];
                    for (List<Integer> prevCombination : prevCell.getCombinations()) {
                        List<Integer> combination = new ArrayList<Integer>(prevCombination);
                        combination.add(coin);
                        cell.addCombination(combination);
                    }
                }
                sol[i][j] = cell;
            }
        }
        return sol[coins.length][n].getCombinations();
    }

    public static void main(String[] args) {
        CoinChange cc = new CoinChange();
        TreeMap<Integer, Integer> tempMap = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        TreeMap<Integer, Integer> countMap = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        tempMap.putAll(map);
        Scanner scan = new Scanner(System.in);
        
        while(true) {
        	int maximumWithdraw = 0;
            System.out.println("========  Available Cash  ========");
            for(Map.Entry<Integer,Integer> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                maximumWithdraw += (value*key);
                System.out.println(key + " => " + value);
            }
            System.out.println( "===============================");
            System.out.println( "Maximum withdraw : " + maximumWithdraw);
            System.out.println( "===============================");
            int n = scan.nextInt();

            List<List<Integer>> combinations = cc.findPossibleCombinationsDP(n);
            for(int i = combinations.size()-1;i>=0;i--) {
                countMap.put(1000,0);
                countMap.put(500,0);
                countMap.put(100,0);
                countMap.put(50,0);
                countMap.put(20,0);
                int[] tempCountCoins = cc.countCoins.clone();
                for(int j = 0;j<combinations.get(i).size();j++) {

                    if (tempMap.get(combinations.get(i). get(j))<=0){
                        tempMap.putAll(map);
                        countMap.clear();
                        break;
                    }
                    else if(tempMap.get(combinations.get(i).get(j))>0) {
                    	tempMap.put(combinations.get(i).get(j), tempMap.get(combinations.get(i).get(j)).intValue()-1);
                       countMap.put(combinations.get(i).get(j), countMap.get(combinations.get(i).get(j)).intValue()+1);
                   }
                   if(j==combinations.get(i).size()-1) {
                    i=0;
                    break;
                }

            }
        }
        map.putAll(tempMap);
        if(countMap.isEmpty()== false) {
        	System.out.println( "-------------------------------");
            System.out.println("Cash withdraw");
            System.out.println( "-------------------------------");
            for(Map.Entry<Integer,Integer> entry : countMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key + " => " + value);
            }
            System.out.println( "-------------------------------");
        }
        else {
           System.out.println("Cannot Withdraw");
       }

   }
}
}