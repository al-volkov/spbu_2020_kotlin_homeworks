package homework_6

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.title.TextTitle
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Dimension

const val NUMBER_OF_FUNCTIONS = 4
const val STEP = 1000
const val MAX_NUMBER_OF_ELEMENTS = 300000
const val HEIGHT = 1000
const val WIDTH = 1000

fun drawPlot() {
    val dataset = createDataset1(MAX_NUMBER_OF_ELEMENTS)
    val chart = ChartFactory.createXYLineChart(
        "comparison of functions",
        "number of elements",
        "time",
        dataset,
        org.jfree.chart.plot.PlotOrientation.VERTICAL,
        false,
        false,
        false
    )
    val subtitle = TextTitle("red - merge mt4, blue - merge mt2, green - merge, yellow - built in,")
    chart.addSubtitle(subtitle)
    displayChart(chart)
}

fun createXYSeries(array: LongArray, key: String): XYSeries {
    val xySeries = XYSeries(key)
    for (i in array.indices) {
        xySeries.add(i * STEP, array[i])
    }
    return xySeries
}

fun createDataset1(maxNumberOfElements: Int): XYDataset {
    val builtInTime = LongArray(maxNumberOfElements / STEP) { 0 }
    val mergeTime = LongArray(maxNumberOfElements / STEP) { 0 }
    val mergeMt2Time = LongArray(maxNumberOfElements / STEP) { 0 }
    val mergeMt4Time = LongArray(maxNumberOfElements / STEP) { 0 }
    val array = Array(NUMBER_OF_FUNCTIONS) { intArrayOf() }
    for (index in 1 until maxNumberOfElements/ STEP) {
        array[0] = IntArray(index * STEP) { it }
        array[0].shuffle()
        for (i in 1 until NUMBER_OF_FUNCTIONS) {
            array[i] = array[0].copyOf()
        }
        builtInTime[index] = System.nanoTime()
        array[0].sort()
        builtInTime[index] = System.nanoTime() - builtInTime[index]
        mergeTime[index] = System.nanoTime()
        array[1].mergeSort()
        mergeTime[index] = System.nanoTime() - mergeTime[index]
        mergeMt2Time[index] = System.nanoTime()
        array[2].mergeSortMt2()
        mergeMt2Time[index] = System.nanoTime() - mergeMt2Time[index]
        mergeMt4Time[index] = System.nanoTime()
        array[3].mergeSortMt4()
        mergeMt4Time[index] = System.nanoTime() - mergeMt4Time[index]
    }
    val xySeriesCollection = XYSeriesCollection()
    xySeriesCollection.addSeries(createXYSeries(builtInTime, "built in"))
    xySeriesCollection.addSeries(createXYSeries(mergeTime, "merge"))
    xySeriesCollection.addSeries(createXYSeries(mergeMt2Time, "merge mt2"))
    xySeriesCollection.addSeries(createXYSeries(mergeMt4Time, "merge mt4"))
    return xySeriesCollection
}

fun displayChart(chart: JFreeChart) {
    val frame = ApplicationFrame("Functions comparison")
    frame.contentPane = ChartPanel(chart).apply {
        fillZoomRectangle = true
        isMouseWheelEnabled = true
        preferredSize = Dimension(WIDTH, HEIGHT)
    }
    frame.pack()
    frame.isVisible = true
}
