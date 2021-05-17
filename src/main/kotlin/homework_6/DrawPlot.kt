package homework_6

import homework_6.MergeSort.mergeSortAsync
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

class DrawPlot(numbersOfThreadsOrCoroutines: List<Int>, maxNumberOfElements: Int, step: Int, isForMT: Boolean) {
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

    private val isForMt = isForMT

    init {
        val dataset = createDataset(numbersOfThreadsOrCoroutines, maxNumberOfElements, step)
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

    private fun createXYSeries(array: LongArray, step: Int, numberOfThreadsOrCoroutines: Int): XYSeries {
        val xySeries = XYSeries(
            if (isForMt) {
                "number of threads"
            } else {
                "number of coroutines"
            } + " - $numberOfThreadsOrCoroutines"
        )
        array.forEachIndexed { index, element -> xySeries.add(index * step, element) }
        return xySeries
    }

    private fun measureTime(numberOfThreadsOrCoroutines: Int, numberOfElements: Int): Long {
        val array = IntArray(numberOfElements) { Random.nextInt() }
        var time = System.nanoTime()
        if (isForMt) {
            array.mergeSortMT(numberOfThreadsOrCoroutines)
        } else {
            array.mergeSortAsync(numberOfThreadsOrCoroutines)
        }
        time = System.nanoTime() - time
        return time
    }

    private fun createDataset(numbersOfThreadsOrCoroutines: List<Int>, maxNumberOfElements: Int, step: Int): XYDataset {
        val xySeriesCollection = XYSeriesCollection()
        numbersOfThreadsOrCoroutines.forEach {
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
