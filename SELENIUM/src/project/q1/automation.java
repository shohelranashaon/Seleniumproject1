package project.q1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class automation {
    public static void main(String[] args) {
    	
        // File paths
    	
        String geckoDriverPath = "L:\\Java\\Intern work 2024 4 beats\\geckodriver-v0.35.0-win64\\geckodriver.exe";
        String excelFilePath = "L:\\Java\\Intern work 2024 4 beats\\Files\\keywords.xlsx";

       
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        try {
            
            String day = LocalDate.now().getDayOfWeek().toString().toLowerCase();

            
            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(day);

            if (sheet == null) {
                System.out.println("No sheet found for the day: " + day);
                workbook.close();
                fileInputStream.close();
                return;
            }

            // Process the sheet
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 

                Cell keywordCell = row.getCell(0); // Assuming keywords are in column A
                if (keywordCell == null) continue;

                String keyword = keywordCell.getStringCellValue();

                
                driver.get("https://www.google.com");
                WebElement searchBox = driver.findElement(By.name("q"));
                searchBox.sendKeys(keyword);
                searchBox.submit();

                
                Thread.sleep(1500);

                
                List<WebElement> suggestions = driver.findElements(By.cssSelector(".s75CSd"));
                String longest = "", shortest = "";

                for (WebElement suggestion : suggestions) {
                    String option = suggestion.getText();
                    if (shortest.isEmpty() || option.length() < shortest.length()) {
                        shortest = option;
                    }
                    if (option.length() > longest.length()) {
                        longest = option;
                    }
                }

                
                row.createCell(1).setCellValue(longest); // Longest option in column B
                row.createCell(2).setCellValue(shortest); // Shortest option in column C
            }

            
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(excelFilePath));
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();

            System.out.println("Results updated in the Excel file.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
