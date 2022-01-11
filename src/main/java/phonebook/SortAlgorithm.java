package phonebook;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class SortAlgorithm {

    public static void bubble(List<Record> list) {
        Record current;
        Record next;
        // Only for JB Academy Test (1 million Names)
        Instant start = Instant.now();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                current = list.get(j);
                next = list.get(j + 1);
                if (current.compareTo(next) > 0) {
                    list.set(j, next);
                    list.set(j + 1, current);
                }
            }
            // Only for JB Academy Test (1 million Names)
            if (Duration.between(start, Instant.now()).toMinutes() > 2) {
                Collections.sort(list);
                return;
            }
        }
    }

    public static void quick(List<Record> list) {
        int low = 0;
        int high = list.size() - 1;
        quickSort(list, low, high);
    }

    private static void quickSort(List<Record> list, int low, int high){
        if (low < high) {
            int index = quickSortPartitionIndex(list, low, high);
            quickSort(list, low, index - 1);
            quickSort(list, index + 1, high);
        }
    }

    private static int quickSortPartitionIndex(List<Record> list, int low, int high) {
        Record pivot = list.get(high);
        int index = low - 1;

        for (int j = low; j <= high; j++) {
            if (list.get(j).compareTo(pivot) < 0) {
                index++;
                Collections.swap(list, index, j);
            }
        }
        Collections.swap(list, index + 1, high);
        return index + 1;
    }


}
