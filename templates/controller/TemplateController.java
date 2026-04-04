package templates.controller;

import custom.ViewJFrame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateController {

    private static final String BASEPATH = "templates/mocks/";


    public static String loadTemplate(String templateName){
       try {
           return new String(Files.readAllBytes(Paths.get(BASEPATH + templateName + ".txt")));

       } catch (Exception e){
           System.err.println("Could not find template file. " + e.getMessage());
       }
        return templateName;
    }

    public static void saveTemplate(String templateName, String content) {

        try (FileWriter writer = new FileWriter(BASEPATH + templateName + ".txt")) {
            writer.write(content);

        } catch (IOException e) {
            System.err.println("Could not save template file. " + e.getMessage());
        }
    }

    ///CONDENSE THE SAVE METHODS SO THAT THEY CALL A SEPRATE SAVE METHOD TO NOT REPEAT CODE AND MAYBE FOR LOAD
    public static void saveMerchantDetails(String name, String email, String address, String phone, String logoPath) {
        File file = new File(BASEPATH + "merchantDetails.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String details = String.join("%", name, email, address, phone, logoPath);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(details);

        } catch (IOException e) {
            System.err.println("Error saving merchant details: " + e.getMessage());
        }
    }


    public static String[] loadMerchantDetails() {

        try {
            String content = new String(Files.readAllBytes(Paths.get(BASEPATH + "merchantDetails.txt")));

            return content.split("%");
        } catch (IOException e) {
            System.err.println("Merchant file not found");
            return new String[]{"", "", "", "", ""};
        }
    }




}
