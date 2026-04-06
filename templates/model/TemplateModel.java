package templates.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateModel {

    public TemplateModel(){}


    private static final String BASEPATH = "templates/mocks/";



    private static void saveToFile(String fileName, String content){
        File file = new File(BASEPATH + fileName);
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadFile(String fileName){
        try {
            return new String(Files.readAllBytes(Paths.get(BASEPATH + fileName)));
        }
        catch (Exception e){
            System.err.println("Could not find template file. " + e.getMessage());
        }
        return fileName;
    }

    public static String loadTemplate(String templateName){
        return loadFile(templateName+".txt");
    }

    public static void saveTemplate(String templateName, String content) {
        saveToFile(templateName +".txt",content);
    }

    ///CONDENSE THE SAVE METHODS SO THAT THEY CALL A SEPRATE SAVE METHOD TO NOT REPEAT CODE AND MAYBE FOR LOAD
    public static void saveMerchantDetails(String name, String email, String address, String phone, String logoPath) {
        String details = String.join("%", name, email, address, phone, logoPath);
        saveToFile("merchantDetails.txt",details);
    }


    public static String[] loadMerchantDetails() {
        String content = loadFile("merchantDetails.txt");
        return content.split("%",-1);
    }

    public Object getTemplates() {

        return null;
    }
}
