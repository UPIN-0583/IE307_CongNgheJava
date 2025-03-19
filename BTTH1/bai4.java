import java.io.*;
import java.util.*;

public class bai4 {

    private Map<String, Integer> wordCounts = new HashMap<>();
    private Map<String, Map<String, Integer>> bigramCounts = new HashMap<>();
    private Set<String> vocabulary = new HashSet<>();

    public void train(String datasetPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(datasetPath));
        String line;
        String previousWord = null;

        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.length() == 0) continue;
                vocabulary.add(word);
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);

                if (previousWord != null) {
                    bigramCounts.putIfAbsent(previousWord, new HashMap<>());
                    bigramCounts.get(previousWord).put(word, bigramCounts.get(previousWord).getOrDefault(word, 0) + 1);
                }
                previousWord = word;
            }
        }
        reader.close();
    }

    private double getProbability(String word) {
        int totalWords = wordCounts.values().stream().mapToInt(Integer::intValue).sum();
        return (double) wordCounts.getOrDefault(word, 0) / totalWords;
    }

    private double getConditionalProbability(String word, String previousWord) {
        if (!bigramCounts.containsKey(previousWord)) {
            return 0.0;
        }
        int previousWordCount = wordCounts.getOrDefault(previousWord, 0);
        if (previousWordCount == 0) {
            return 0.0;
        }
        return (double) bigramCounts.get(previousWord).getOrDefault(word, 0) / previousWordCount;
    }

    public String generateText(String startWord, int maxLength) {
        List<String> text = new ArrayList<>();
        text.add(startWord);
        String currentWord = startWord;

        while (text.size() < maxLength) {
            String nextWord = getNextWord(currentWord);
            if (nextWord == null) break;
            text.add(nextWord);
            currentWord = nextWord;
        }

        return String.join(" ", text);
    }

    private String getNextWord(String currentWord) {
        if (!bigramCounts.containsKey(currentWord)) {
            return null;
        }
        Map<String, Integer> counts = bigramCounts.get(currentWord);
        String nextWord = null;
        double maxProb = -1;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            double prob = (double) entry.getValue() / wordCounts.get(currentWord);
            if (prob > maxProb) {
                maxProb = prob;
                nextWord = entry.getKey();
            }
        }

        return nextWord;
    }

    public static void main(String[] args) throws IOException {
        bai4 generator = new bai4();
        try {
            generator.train("UIT-ViOCD.txt");
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp: " + e.getMessage());
            return;
        }

        String startWord = "nhanh"; 
        String generatedText = generator.generateText(startWord, 5);
        System.out.println(generatedText);
    }
}