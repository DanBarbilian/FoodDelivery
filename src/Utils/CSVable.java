package Utils;

public interface CSVable <T>{
    String[] convertToString();
    <T>T parser(String csv);
}
