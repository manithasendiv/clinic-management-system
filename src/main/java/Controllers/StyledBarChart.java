package Controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StyledBarChart {

    /**
     * Create a styled bar chart for GUI display (returns JPanel)
     */
    public static JPanel createStyledBarChart(String title, String xLabel, String yLabel, HashMap<String, Integer> data) {
        JFreeChart chart = createBarChart(title, xLabel, yLabel, data);
        return new ChartPanel(chart);
    }

    /**
     * Create a styled bar chart for PDF export (returns JFreeChart)
     */
    public static JFreeChart createBarChartForPDF(String title, String xLabel, String yLabel, HashMap<String, Integer> data) {
        return createBarChart(title, xLabel, yLabel, data);
    }

    /**
     * Internal method: builds a JFreeChart with styling
     */
    private static JFreeChart createBarChart(String title, String xLabel, String yLabel, HashMap<String, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach((k, v) -> dataset.addValue(v, "Patients", k));

        JFreeChart chart = ChartFactory.createBarChart(title, xLabel, yLabel, dataset);

        // Styling
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(79, 129, 189));
        renderer.setDrawBarOutline(false);
        renderer.setMaximumBarWidth(0.1);

        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 11));
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 12));

        return chart;
    }
}
