package Utils;

import java.time.ZonedDateTime;

public class Log implements CSVable<Log> {
    private ZonedDateTime timestamp;
    private String problemDesc;

    public Log(ZonedDateTime timestamp, String text) {
        this.timestamp = timestamp;
        this.problemDesc = text;
    }

    @Override
    public String[] convertToString() {
        return new String[]{this.timestamp.toString(), this.problemDesc};
    }

    @Override
    public <T> T parser(String csv) {
        return null;
    }
}
