package Api;

import java.util.Arrays;

public class CatalogTest {
    public static void main(String[] args) {
        try {
            CaCatalogAPI_Implementation api = new CaCatalogAPI_Implementation();

            System.out.println("--- Testing getCatalogue ---");
            String[] localResult = api.getCatalogue();

            // Check if local array is populated
            System.out.println("Items in local catalogue array: " + localResult.length);
            for (String row : localResult) {
                System.out.println("Row Data: " + row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}