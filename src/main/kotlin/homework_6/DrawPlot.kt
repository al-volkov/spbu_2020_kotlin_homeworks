package homework_6

import homework_6.MergeSort.mergeSortMT
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Color
import java.awt.Dimension
import kotlin.random.Random

class DrawPlot(numbersOfThreads: List<Int>, maxNumberOfElements: Int, step: Int) {
    private companion object {
        const val HEIGHT = 1000
        const val WIDTH = 1000
        val listOfColours = listOf(
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.GRAY,
            Color.BLUE,
            Color.CYAN,
            Color.DARK_GRAY,
            Color.MAGENTA,
            Color.PINK,
            Color.DARK_GRAY
        )
    }

    init {
        val dataset = createDataset(numbersOfThreads, maxNumberOfElements, step)
        val chart = ChartFactory.createXYLineChart(
            "dependence of sorting time on the number of elements",
            "number of elements",
            "time",
            dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL,
            true,
            false,
            false
        )
        val rendererForPlot = XYLineAndShapeRenderer()
        listOfColours.forEachIndexed { index, element -> rendererForPlot.setSeriesPaint(index, element) }
        chart.xyPlot.renderer = rendererForPlot
        displayChart(chart)
    }

    private fun createXYSeries(array: LongArray, step: Int, numberOfThreads: Int): XYSeries {
        val xySeries = XYSeries("number of threads - $numberOfThreads")
        array.forEachIndexed { index, element -> xySeries.add(index * step, element) }
        return xySeries
    }

    private fun measureTime(numberOfThreads: Int, numberOfElements: Int): Long {
        val array = IntArray(numberOfElements) { Random.nextInt() }
        var time = System.nanoTime()
        array.mergeSortMT(numberOfThreads)
        time = System.nanoTime() - time
        return time
    }

    private fun createDataset(numbersOfThreads: List<Int>, maxNumberOfElements: Int, step: Int): XYDataset {
        val xySeriesCollection = XYSeriesCollection()
        numbersOfThreads.forEach {
            val time = LongArray(maxNumberOfElements / step) { 0 }
            for (index in 1 until maxNumberOfElements / step) {
                time[index] = measureTime(it, step * index)
            }
            xySeriesCollection.addSeries(createXYSeries(time, step, it))
        }
        return xySeriesCollection
    }

    private fun displayChart(chart: JFreeChart) {
        val frame = ApplicationFrame("Merge sort")
        frame.contentPane = ChartPanel(chart).apply {
            fillZoomRectangle = true
            isMouseWheelEnabled = true
            preferredSize = Dimension(WIDTH, HEIGHT)
        }
        frame.pack()
        frame.isVisible = true
    }
}
