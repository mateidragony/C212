import me.tongfei.progressbar.ProgressBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FunTest {



    public static List<Integer> mergeSort(List<Integer> arr){

       if(arr.size() == 1) {
           return arr;
       }

       int mid = arr.size()/2;

       List<Integer> left = mergeSort(arr.subList(0,mid));
       List<Integer> right = mergeSort(arr.subList(mid,arr.size()));

        return merge(left,right);
    }
    public static List<Integer> merge(List<Integer> arr1, List<Integer> arr2){

        List<Integer> sorted = new ArrayList<>();

        int arr1Index = 0;
        int arr2Index = 0;

        for(int i=0; i<arr1.size()+arr2.size(); i++){

            if(arr1Index == arr1.size()) {
                sorted.add(arr2.get(arr2Index));
                arr2Index++;
            } else if(arr2Index == arr2.size()) {
                sorted.add(arr1.get(arr1Index));
                arr1Index++;
            }else if(arr1.get(arr1Index) < arr2.get(arr2Index)) {
                sorted.add(arr1.get(arr1Index));
                arr1Index++;
            } else {
                sorted.add(arr2.get(arr2Index));
                arr2Index++;
            }
        }

        return sorted;

    }

    public static void bubbleSort(List<Integer> arr){

        for(int i=0; i<arr.size()-1; i++){
            for(int j=0; j<arr.size()-1-i; j++){
                if(arr.get(j) > arr.get(j+1)){
                    int temp = arr.get(j+1);
                    arr.set(j+1, arr.get(j));
                    arr.set(j, temp);
                }
            }
        }

    }


    public static int searchArray(int[] arr, int elem) {

        for (int i = 0; i < arr.length; ) {
            if (arr[i] == elem) {
                return i;
            }
            i += Math.abs(elem - arr[i]);
        }
        return -1;
    }

    public static void main(String[] args) {

        ProgressBar pb = new ProgressBar("Test", 100000000);


        int sum = 0;
        for(int i=0; i<100000000; i++){
            sum += i * Math.pow(-1,i);

            pb.step();
        }

        System.out.println(sum);

    }


}
