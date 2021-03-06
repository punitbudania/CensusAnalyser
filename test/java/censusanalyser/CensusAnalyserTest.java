package censusanalyser;

import com.google.gson.Gson;
import com.jarfile.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\PUNIT BUDANIA\\IdeaProjects\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "C:\\Users\\PUNIT BUDANIA\\IdeaProjects\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData.txt";
    private static final String INDIA_STATE_CSV_FILE_PATH = "C:\\Users\\PUNIT BUDANIA\\IdeaProjects\\CensusAnalyser\\src\\test\\resources\\IndiaStateCode.csv";
    private static final String INCORRECT_DELIMITER_FILE = "C:\\Users\\PUNIT BUDANIA\\IdeaProjects\\CensusAnalyser\\src\\main\\resources\\InorrectDelimiter.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CSVBuilderException e) { }
    }
    
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_TYPE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndiaCensusData(INCORRECT_DELIMITER_FILE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_HEADER,e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount()
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfStateCode);
        }
        catch (CSVBuilderException e) { }
    }

    @Test
    public void givenIndianStateData_WithWrongFile_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianStateData_WithWrongType_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_TYPE,e.type);
        }
    }

    @Test
    public void givenIndianStateData_WithIncorrectDelimiter_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndiaCensusData(INCORRECT_DELIMITER_FILE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER,e.type);
        }
    }
    
    @Test
    public void givenIndianStateData_WithIncorrectHeader_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_HEADER,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        }
        catch (CSVBuilderException | IOException e) {}
    }

    @Test
    public void givenIndianStateCensusData_whenSortedOnState_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedIndianStateData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("AN", censusCSV[0].state);
            Assert.assertEquals("WB", censusCSV[36].state);
        }
        catch (CSVBuilderException e) {}
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
            Assert.assertEquals("Sikkim", censusCSV[28].state);
        }
        catch (CSVBuilderException | IOException e) {}
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulationDensity_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
            Assert.assertEquals("Arunachal Pradesh", censusCSV[28].state);
        }
        catch (CSVBuilderException e) {}
    }

    @Test
    public void givenIndianCensusData_whenSortedAreaWise_ShouldReturnSortedResult()
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
            Assert.assertEquals("Goa", censusCSV[28].state);
        }
        catch (CSVBuilderException | IOException e) {}
    }
}
