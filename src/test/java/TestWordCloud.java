import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import wordcloud.CollisionType;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.bg.PixelBoundryBackground;
import wordcloud.bg.RectangleBackground;
import wordcloud.font.LinearFontScalar;
import wordcloud.nlp.FrequencyAnalizer;
import wordcloud.palette.ColorPalette;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by kenny on 6/29/14.
 */
public class TestWordCloud {

    private static final Logger LOGGER = Logger.getLogger(TestWordCloud.class);

    private static final Random RANDOM = new Random();

    @Test
    public void simpleCircleTest() throws IOException {
        final List<WordFrequency> wordFrequencies = buildWordFrequences().subList(0, 150);

        final WordCloud wordCloud = new WordCloud(600, 600, CollisionType.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(buildRandomColorPallete(20));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("output/wordcloud_circle.png");
    }

    @Test
    public void simpleRectangleTest() throws IOException {
        final List<WordFrequency> wordFrequencies = buildWordFrequences().subList(0, 150);

        final WordCloud wordCloud = new WordCloud(600, 600, CollisionType.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(600, 600));
        wordCloud.setColorPalette(buildRandomColorPallete(20));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("output/wordcloud_rectangle.png");
    }

    @Test
    public void whaleImgLargeTest() throws IOException {
        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(750);
        frequencyAnalizer.setMinWordLength(4);
        frequencyAnalizer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalizer.load(getInputStream("text/datarank.txt"));
        final WordCloud wordCloud = new WordCloud(990, 618, CollisionType.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new PixelBoundryBackground(getInputStream("backgrounds/whale.png")));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("output/whale_wordcloud_large3.png");
    }

    @Test
    public void whaleImgSmallTest() throws IOException {
        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(300);
        frequencyAnalizer.setMinWordLength(4);
        frequencyAnalizer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalizer.load(getInputStream("text/datarank.txt"));
        final WordCloud wordCloud = new WordCloud(500, 312, CollisionType.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new PixelBoundryBackground(getInputStream("backgrounds/whale_small.png")));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("output/whale_wordcloud_small.png");
    }

    @Test
    public void whaleImgSmallAnglesTest() throws IOException {
        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(100);
        frequencyAnalizer.setMinWordLength(4);
        frequencyAnalizer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalizer.load(getInputStream("text/datarank.txt"));
        final WordCloud wordCloud = new WordCloud(500, 312, CollisionType.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setThetas(new double[] {0, -Math.PI / 4, -Math.PI / 2, -Math.PI / 3, Math.PI / 3, Math.PI / 2, Math.PI / 4});
        wordCloud.setBackground(new PixelBoundryBackground(getInputStream("backgrounds/whale_small.png")));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(20, 35));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("output/whale_wordcloud_small_angles2.png");
    }

    @Test
    public void whaleImgLargeAnglesTest() throws IOException {
        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(600);
        frequencyAnalizer.setMinWordLength(4);
        frequencyAnalizer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalizer.load(getInputStream("text/datarank.txt"));
        final WordCloud wordCloud = new WordCloud(990, 618, CollisionType.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setThetas(new double[]{0, -Math.PI / 4, -Math.PI / 2, -Math.PI / 3, Math.PI / 3, Math.PI / 2, Math.PI / 4});
        wordCloud.setBackground(new PixelBoundryBackground(getInputStream("backgrounds/whale.png")));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(20, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("output/whale_wordcloud_large_angles2.png");
    }

    @Test
    public void datarankCircle() throws IOException {
        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(500);
        frequencyAnalizer.setMinWordLength(4);
        frequencyAnalizer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalizer.load(getInputStream("text/datarank.txt"));
        final WordCloud wordCloud = new WordCloud(500, 500, CollisionType.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(250));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        final long startTime = System.currentTimeMillis();
        wordCloud.build(wordFrequencies);
        LOGGER.info("Took " + (System.currentTimeMillis() - startTime) + "ms to build");
        wordCloud.writeToFile("output/datarank_wordcloud_circle4.png");
    }

    @Test
    public void datarankCircleLarge() throws IOException {
        final FrequencyAnalizer frequencyAnalizer = new FrequencyAnalizer();
        frequencyAnalizer.setWordFrequencesToReturn(750);
        frequencyAnalizer.setMinWordLength(4);
        frequencyAnalizer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalizer.load(getInputStream("text/datarank.txt"));
        final WordCloud wordCloud = new WordCloud(1000, 1000, CollisionType.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(500));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 50));
        final long startTime = System.currentTimeMillis();
        wordCloud.build(wordFrequencies);
        LOGGER.info("Took " + (System.currentTimeMillis() - startTime) + "ms to build");
        wordCloud.writeToFile("output/datarank_wordcloud_circle_large2.png");
    }

    private static ColorPalette buildRandomColorPallete(int n) {
        final Color[] colors = new Color[n];
        for(int i = 0; i < colors.length; i++) {
            colors[i] = new Color(RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25);
        }
        return new ColorPalette(colors);
    }

    private static List<WordFrequency> buildWordFrequences() throws IOException {
        final List<String> pokemonNames = getPokemonNames();
        final List<WordFrequency> wordFrequencies = new ArrayList<>();
        for(String pokemon : pokemonNames) {
            wordFrequencies.add(new WordFrequency(pokemon, RANDOM.nextInt(100) + 1));
        }
        return wordFrequencies;
    }

    private static List<String> getPokemonNames() throws IOException {
        return IOUtils.readLines(getInputStream("pokemon.txt"));
    }

    private static Set<String> loadStopWords() {
        try {
            final List<String> lines = IOUtils.readLines(getInputStream("text/stop_words.txt"));
            return new HashSet<>(lines);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptySet();
    }

    private static InputStream getInputStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}