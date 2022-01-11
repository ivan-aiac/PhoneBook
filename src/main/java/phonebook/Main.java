package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String TITLE = "Start searching (%s)...%n";

    public static void main(String[] args) {
        if (args[0] != null && args[1] != null) {
            try {
                List<Record> directory = readLinesFromFile(args[0]).stream()
                        .map(l -> {
                            String[] split = l.split("\\s+", 2);
                            return new Record(split[0], split[1]);
                        }).collect(Collectors.toList());
                List<String> searchNames = readLinesFromFile(args[1]);

                linearSearchTest(directory, searchNames);
                bubbleSortJumpTest(directory, searchNames);
                quickSortBinaryTest(directory, searchNames);
                hashtableTest(directory, searchNames);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Two files are required as arguments. (Phone Book file and Search List file)");
        }
    }

    private static void hashtableTest(List<Record> directory, List<String> names) {
        System.out.println("Start searching (hash table)...");
        Hashtable<String, String> table = new Hashtable<>();
        Instant start = Instant.now();
        for (Record r : directory) {
            table.put(r.getName(), r.getNumber());
        }
        Duration creationTime = Duration.between(start, Instant.now());
        Instant searchStart = Instant.now();
        int found = 0;
        for (String name : names) {
            if (table.get(name) != null) {
                found++;
            }
        }
        Duration searchDuration = Duration.between(searchStart, Instant.now());
        Duration duration = searchDuration.plus(creationTime);
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", found, names.size(), duration.toMinutesPart(), duration.toSecondsPart(), duration.toMillisPart());
        System.out.printf("Creating time: %d min. %d sec. %d ms. %n", creationTime.toMinutesPart(), creationTime.toSecondsPart(), creationTime.toMillisPart());
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n", searchDuration.toMinutesPart(), searchDuration.toSecondsPart(), searchDuration.toMillisPart());
    }

    private static void quickSortBinaryTest(List<Record> directory, List<String> names) {
        List<Record> directoryCopy = new ArrayList<>(directory);
        Statistic stats = Statistic.calculate(directoryCopy, names, SortAlgorithm::quick, SearchAlgorithm::binary);
        printStatistics(stats, false);
    }

    private static void linearSearchTest(List<Record> directory, List<String> names) {
        printAlgorithmName("linear search");
        Statistic stats = Statistic.calculate(directory, names, null, SearchAlgorithm::linear);
        printStatistics(stats, false);
    }

    private static void bubbleSortJumpTest(List<Record> directory, List<String> names) {
        List<Record> directoryCopy = new ArrayList<>(directory);
        printAlgorithmName("bubble sort + jump search");
        Statistic stats = Statistic.calculate(directoryCopy, names, SortAlgorithm::bubble, SearchAlgorithm::jump);
        printStatistics(stats, true);
    }

    private static void printAlgorithmName(String algorithmName) {
        System.out.printf(TITLE, algorithmName);
    }

    private static void printStatistics(Statistic stats, boolean sortInterrupted) {
        Duration total = stats.getTotalTime();
        Duration sort = stats.getSortTime();
        Duration search = stats.getSearchTime();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", stats.getFoundElements(), stats.getSearchedElements(), total.toMinutesPart(), total.toSecondsPart(), total.toMillisPart());
        System.out.printf("Sorting time: %d min. %d sec. %d ms. %s%n", sort.toMinutesPart(), sort.toSecondsPart(), sort.toMillisPart(), sortInterrupted ? "- STOPPED, moved to search" : "");
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n", search.toMinutesPart(), search.toSecondsPart(), search.toMillisPart());
    }

    private static List<String> readLinesFromFile(String file) throws IOException {
        return Files.readAllLines(Paths.get(file));
    }
}
