import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AggregatedDataCalculator {

    private static final int NUM_EXPERIMENTS =  1;
    private static final Random random = new Random();

    public static StringBuilder calculateAggregatedData() {
        StringBuilder result = new StringBuilder();
        long iterationTotalTime5000 = 0;
        long iterationTotalTime50000 = 0;
        long iterationTotalTime250000 = 0;


        long streamAPITotalTime5000 = 0;
        long streamAPITotalTime50000 = 0;
        long streamAPITotalTime250000 = 0;

        long customCollectorTotalTime5000 = 0;
        long customCollectorTotalTime50000 = 0;
        long customCollectorTotalTime250000 = 0;

        for (int i =0; i < NUM_EXPERIMENTS; i++){
            List<Commit> commitList5000 = CollectionFill.collectionFill(5000);
            List<Commit> commitList50000 = CollectionFill.collectionFill(50000);
            List<Commit> commitList250000 = CollectionFill.collectionFill(250000);

            iterationTotalTime5000 += calculateWithIteration(commitList5000);
            iterationTotalTime50000 += calculateWithIteration(commitList50000);
            iterationTotalTime250000 += calculateWithIteration(commitList250000);

            streamAPITotalTime5000 += calculateWithStreamAPI(commitList5000);
            streamAPITotalTime50000 += calculateWithStreamAPI(commitList50000);
            streamAPITotalTime250000 += calculateWithStreamAPI(commitList250000);

            customCollectorTotalTime5000 += calculateWithCustomCollector(commitList5000);
            customCollectorTotalTime50000 += calculateWithCustomCollector(commitList50000);
            customCollectorTotalTime250000 += calculateWithCustomCollector(commitList250000);
        }

        result.append("Среднее время выполнения итерационного метода для 5000 элементов: "
                + iterationTotalTime5000/NUM_EXPERIMENTS + "мс\n");
        result.append("Среднее время выполнения итерационного метода для 50000 элементов: "
                + iterationTotalTime50000/NUM_EXPERIMENTS + "мс\n");
        result.append("Среднее время выполнения итерационного метода для 250000 элементов: "
                + iterationTotalTime250000/NUM_EXPERIMENTS + "мс\n\n");

        result.append("Среднее время выполнения метода Stream API для 5000 элементов: "
                + streamAPITotalTime5000/NUM_EXPERIMENTS + "мс\n");
        result.append("Среднее время выполнения метода Stream API для 50000 элементов: "
                + streamAPITotalTime50000/NUM_EXPERIMENTS + "мс\n");
        result.append("Среднее время выполнения метода Stream API для 250000 элементов: "
                + streamAPITotalTime250000/NUM_EXPERIMENTS + "мс\n\n");

        result.append("Среднее время выполнения итерационного метода для 5000 элементов: "
                + customCollectorTotalTime5000/NUM_EXPERIMENTS + "мс\n");
        result.append("Среднее время выполнения итерационного метода для 50000 элементов: "
                + customCollectorTotalTime50000/NUM_EXPERIMENTS + "мс\n");
        result.append("Среднее время выполнения итерационного метода для 250000 элементов: "
                + customCollectorTotalTime250000/NUM_EXPERIMENTS + "мс\n");
        return result;
    }

    public static Long calculateWithIteration(List<Commit> commitList) {
        Instant start = Instant.now();
        long countedCommits = 0;
        Iterator<Commit> iter = commitList.listIterator();
        Commit.Author author = commitList.get(0).getAuthor();
        while (iter.hasNext()) {
            Commit commit = iter.next();
            if (commit.getAuthor().equals(author)
                    && commit.getCreationTime(1).isAfter(LocalDateTime.parse("2023-01-01T01:00:00"))
                    && commit.getCreationTime(1).isBefore(LocalDateTime.parse("2024-01-01T01:00:00"))
                    && (commit.getStatus() == CommitStatus.COMPLETED || commit.getStatus() == CommitStatus.PENDING)
                    && commit.getChangedFiles().size() > 2
                    && commit.getAuthor().email().contains("@")) {
                countedCommits++;
            }
        }
        //System.out.println(countedCommits);
        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();
    }

    public static Long calculateWithStreamAPI(List<Commit> commitList) {
        Instant start = Instant.now();
        Commit.Author author = commitList.get(0).getAuthor();
        long countedCommits = commitList.stream()
                .filter(commit -> commit.getAuthor().equals(author))
                .filter(commit -> commit.getCreationTime(0).isAfter(LocalDateTime.parse("2023-01-01T01:00:00")))
                .filter(commit -> commit.getCreationTime(0).isBefore(LocalDateTime.parse("2024-01-01T01:00:00")))
                .filter(commit -> (commit.getStatus() == CommitStatus.COMPLETED || commit.getStatus() == CommitStatus.PENDING))
                .filter(commit -> commit.getChangedFiles().size() > 2)
                .filter(commit -> commit.getAuthor().email().contains("@"))
                .count();
        //System.out.println(countedCommits);

        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();

    }

    public static Long calculateWithCustomCollector(List<Commit> commitList) {
        Instant start = Instant.now();

        Commit.Author author = commitList.get(0).getAuthor();
        long countedCommits = commitList.stream()
                .filter(commit -> commit.getAuthor().equals(author))
                .collect(new CustomCommitCounter());
        //System.out.println(countedCommits);

        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();

    }
}
