package Controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StyledPieChart {

    /**
     * Create a styled pie chart for GUI display (returns JPanel)
     */
    public static JPanel createStyledPieChart(String title, HashMap<String, Integer> data) {
        JFreeChart chart = createPieChart(title, data);
        return new ChartPanel(chart);
    }

    /**
     * Create a styled pie chart for PDF export (returns JFreeChart)
     */
    public static JFreeChart createPieChartForPDF(String title, HashMap<String, Integer> data) {
        return createPieChart(title, data);
    }

    /**
     * Internal method to build JFreeChart with styling
     */
    private static JFreeChart createPieChart(String title, HashMap<String, Integer> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.setLabelGap(0.02);
        plot.setInteriorGap(0.04);

        // Optional: custom colors
        Color[] colors = {new Color(79, 129, 189), new Color(192, 80, 77), new Color(155, 187, 89), new Color(128, 100, 162)};
        int i = 0;
        for (Object key : dataset.getKeys()) {
            plot.setSectionPaint((Comparable<?>) key, colors[i % colors.length]);
            i++;
        }

        return chart;
    }
}
