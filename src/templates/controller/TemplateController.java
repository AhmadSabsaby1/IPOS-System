package templates.controller;

import main.Global;
import templates.model.TemplateModel;
import templates.view.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateController {
    private MerchantSettingView merchantSettingView;
    private TemplateManagerView templateMangerView;
    private TemplateMainView mainView;

    public TemplateController(){

        templateMangerView = new TemplateManagerView(this);
        merchantSettingView = new MerchantSettingView(this);
        mainView = new TemplateMainView();

        mainView.addCardLayout(templateMangerView, TemplateManagerView.cardId());
        mainView.addCardLayout(merchantSettingView, MerchantSettingView.cardId());
    }

    //////////////SCREEN CHANGES//////////

    public void goToMainMenu(){
        Global.get().goToMainMenu();
        mainView.dispose();
    }

    public void goToTemplateManagerScreen(){
        mainView.changeCardView(TemplateManagerView.cardId());
    }
    public void goToMerchantSettings() {
        mainView.changeCardView(MerchantSettingView.cardId());
    }

    ///////////////////////////////////////////////////////////


    public static void saveTemplate(String selectedType, String updatedTemplate) {
        TemplateModel.saveTemplate(selectedType, updatedTemplate);
    }

    public static String loadTemplate(String templateName) {
        return TemplateModel.loadTemplate(templateName);
    }


    public static void saveMerchantDetails(String name, String email, String address, String phone, String currentLogoPath) {
        TemplateModel.saveMerchantDetails(name, email, address, phone, currentLogoPath);
    }

    public static void loadMerchantDetails(String name, String email, String address, String phone, String currentLogoPath){
        TemplateModel.loadMerchantDetails();
    }

   /* public void goToHubScreen() {
        mainView.changeCardView(HubView.cardId());
    }*/
}
