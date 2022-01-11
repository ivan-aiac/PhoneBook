package phonebook;

import java.util.List;

public class SearchAlgorithm {

    public static int linear(List<Record> directory, String name) {
        for (int i = 0; i < directory.size(); i++) {
            if (directory.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int jump(List<Record> directory, String name) {
        int step = (int) Math.floor(Math.sqrt(directory.size()));
        int index;
        int current = 0;

        while (current < directory.size()) {
            if (directory.get(current).getName().equals(name)) {
                return current;
            } else if (directory.get(current).getName().compareToIgnoreCase(name) > 0) {
                index = current - 1;

                while (index > current - step && index >= 0) {
                    if (directory.get(index).getName().equals(name)) {
                        return index;
                    }
                    index--;
                }
                return -1;
            }
            current += step;
        }
        index = directory.size() - 1;

        while (index > current - step) {
            if (directory.get(index).getName().equals(name)) {
                return index;
            }
            index--;
        }
        return -1;
    }

    public static int binary(List<Record> directory, String name) {
        int left = 0;
        int right = directory.size() - 1;
        int middle;
        while (left <= right) {
            middle = left + (right - left) / 2;
            if (directory.get(middle).getName().equalsIgnoreCase(name)) {
                return middle;
            } else if (directory.get(middle).getName().compareToIgnoreCase(name) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }
}
