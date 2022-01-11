package phonebook;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Statistic {

    private Duration sortTime;
    private Duration searchTime;
    private int foundElements;
    private int searchedElements;

    private Statistic() {
    }

    public Duration getSortTime() {
        return sortTime;
    }

    public void setSortTime(Duration sortTime) {
        this.sortTime = sortTime;
    }

    public Duration getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Duration searchTime) {
        this.searchTime = searchTime;
    }

    public int getFoundElements() {
        return foundElements;
    }

    public void setFoundElements(int foundElements) {
        this.foundElements = foundElements;
    }

    public int getSearchedElements() {
        return searchedElements;
    }

    public void setSearchedElements(int searchedElements) {
        this.searchedElements = searchedElements;
    }

    public Duration getTotalTime() {
        return searchTime.plus(sortTime);
    }

    public static <T,U> Statistic calculate(List<T> list, List<U> searchList, Consumer<List<T>> sortFunction, BiFunction<List<T>, U, Integer> searchFunction){
        Statistic statistic = new Statistic();

        statistic.setSearchedElements(searchList.size());

        if (sortFunction != null) {
            Instant startSort = Instant.now();
            sortFunction.accept(list);
            statistic.setSortTime(Duration.between(startSort, Instant.now()));
        } else {
            statistic.setSortTime(Duration.ZERO);
        }

        Instant startSearch = Instant.now();
        int found = 0;
        for (U element : searchList) {
            if (searchFunction.apply(list, element) >= 0) {
                found++;
            }
        }
        statistic.setSearchTime(Duration.between(startSearch, Instant.now()));
        statistic.setFoundElements(found);
        return statistic;
    }

}
