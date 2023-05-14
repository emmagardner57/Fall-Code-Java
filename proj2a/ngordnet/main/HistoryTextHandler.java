package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap mapper;
    public HistoryTextHandler(NGramMap map) {
        mapper = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = null;
        for (String entry : words) {
            if (response == null) {
                response = entry + ": " + mapper.weightHistory(entry, startYear, endYear).toString() + "\n";
            } else {
                response += entry + ": " + mapper.weightHistory(entry, startYear, endYear).toString() + "\n";
            }
        }
        return response;
    }
}

