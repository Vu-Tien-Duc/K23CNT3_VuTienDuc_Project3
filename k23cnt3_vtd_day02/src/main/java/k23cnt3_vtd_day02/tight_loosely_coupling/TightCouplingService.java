package k23cnt3_vtd_day02.tight_loosely_coupling;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TightCouplingService {
    private BubbleSortAlgorithm bubbleSortAlgorithm
            = new BubbleSortAlgorithm();

    public TightCouplingService(){}
    public  TightCouplingService(BubbleSortAlgorithm bubbleSortAlgorithm){
        this.bubbleSortAlgorithm = bubbleSortAlgorithm;
    }
    public void comolexBusinessSort(int[] arr){
        bubbleSortAlgorithm.sort(arr);
        Arrays.stream(arr).forEach(
                System.out::println
        );
    }

    public static void main(String[] args)
    {
        TightCouplingService tightCouplingService
                = new TightCouplingService();
        tightCouplingService.comolexBusinessSort(
                new int[]{11,21,13,42,15}
        );
    }

}