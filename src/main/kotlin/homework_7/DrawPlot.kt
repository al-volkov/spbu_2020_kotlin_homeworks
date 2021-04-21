package homework_7

import homework_7.asyncMergeSort
import kotlinx.coroutines.runBlocking
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.title.TextTitle
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Dimension
import kotlin.random.Random

class DrawPlot(numberOfThreads: Int, maxNumberOfElements: Int, step: Int) {
    private companion object {
        const val HEIGHT = 1000
        const val WIDTH = 1000
    }

    init {
        val dataset = createDataset(numberOfThreads, maxNumberOfElements, step)
        val chart = ChartFactory.createXYLineChart(
            "dependence of sorting time on the number of elements",
            "number of elements",
            "time",
            dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL,
            false,
            false,
            false
        )
        val subtitle = TextTitle("number of threads - $numberOfThreads")
        chart.addSubtitle(subtitle)
        displayChart(chart)
    }

    private fun createXYSeries(array: LongArray, step: Int): XYSeries {
        val xySeries = XYSeries("merge sort")
        array.forEachIndexed { index, element -> xySeries.add(index * step, element) }
        return xySeries
    }

    private fun measureTime(numberOfThreads: Int, numberOfElements: Int): Long {
        val array = IntArray(numberOfElements) { Random.nextInt() }
        array.shuffle()
        val temporaryArray = IntArray(numberOfElements) { 0 }
        var time = System.nanoTime()
        runBlocking { array.asyncMergeSort(resultArray = temporaryArray, numberOfCoroutines = numberOfThreads) }
        time = System.nanoTime() - time
        return time
    }

    private fun createDataset(numberOfThreads: Int, maxNumberOfElements: Int, step: Int): XYDataset {
        val time = LongArray(maxNumberOfElements / step) { 0 }
        for (index in 1 until maxNumberOfElements / step) {
            time[index] = measureTime(numberOfThreads, step * index)
        }
        val xySeriesCollection = XYSeriesCollection()
        xySeriesCollection.addSeries(createXYSeries(time, step))
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
