package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap mapper;

    public HistoryHandler(NGramMap map) {
        mapper = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int endYear = q.endYear();
        int startYear = q.startYear();


        ArrayList<String> labels = new ArrayList<>();
        ArrayList<TimeSeries> lts = new ArrayList<>();


        for (String entry : words) {
            labels.add(entry);
            TimeSeries dataList = mapper.weightHistory(entry, startYear, endYear);
            lts.add(dataList);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
