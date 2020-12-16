package censusanalyser;

import com.google.gson.Gson;
import com.jarfile.CSVBuilderException;
import com.jarfile.CSVBuilderFactory;
import com.jarfile.ICSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCodeCSV> stateCSVIList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException
    {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            if(!(csvFilePath.split("\\.")[1].equals("csv")))
            {
                throw new CSVBuilderException("Incorrect File Type", CSVBuilderException.ExceptionType.INCORRECT_TYPE);
            }
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvFilePath));
            String[] headerColumns = bufferedReader.readLine().split(",");
            if (headerColumns.length < 4)
            {
                throw new CSVBuilderException("File contains Invalid Delimiter", CSVBuilderException.ExceptionType.INCORRECT_DELIMITER);
            }
            if (!headerColumns[0].equals("State") || !headerColumns[1].equals("Population") || !headerColumns[2].equals("AreaInSqKm") || !headerColumns[3].equals("DensityPerSqKm"))
            {
                throw new CSVBuilderException("Incorrect Header", CSVBuilderException.ExceptionType.INCORRECT_HEADER);
            }
            bufferedReader.close();
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
        }
        catch (IOException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CSVBuilderException
    {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            if(!(csvFilePath.split("\\.")[1].equals("csv")))
            {
                throw new CSVBuilderException("Incorrect File Type", CSVBuilderException.ExceptionType.INCORRECT_TYPE);
            }
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvFilePath));
            String[] headerColumns = bufferedReader.readLine().split(",");
            if (headerColumns.length < 4)
            {
                throw new CSVBuilderException("File contains Invalid Delimiter", CSVBuilderException.ExceptionType.INCORRECT_DELIMITER);
            }
            if (!headerColumns[1].equals("State") || !headerColumns[3].equals("State Code")) {
                throw new CSVBuilderException("Incorrect Header", CSVBuilderException.ExceptionType.INCORRECT_HEADER);
            }
            bufferedReader.close();
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVIList = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return stateCSVIList.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensusData() throws CSVBuilderException {
        if(censusCSVList == null || censusCSVList.size() == 0)
        {
            throw new CSVBuilderException("No Census Data" , CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator censusComparator)
    {
        for (int i=0; i<censusCSVList.size()-1; i++)
        {
            for (int j=0; j<censusCSVList.size()-i-1; j++)
            {
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if(censusComparator.compare(census1, census2) > 0)
                {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }
}
