package net.wejudge.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import net.wejudge.dto.Status;
import net.wejudge.utils.Printer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditController implements Initializable{

    @FXML
    private HTMLEditor editor;
    private ListenController listenController = ListenController.getInstance();
    private LoginController loginController = LoginController.getInstance();

    public static String printStyleDescription = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Title</title><style>html{width: 250px;}</style></head><body contenteditable=\"true\"><p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"8\"><b>WeJudge</b></font></p><p style=\"text-align: center;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\">{title}</font></p><p style=\"text-align: center;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\";>--------------------------------</font></p><p style=\"text-align: left;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\"><b>【过题队伍】</b></font></p><p style=\"text-align: left;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\"><b>{nickname}</b></font></p><p style=\"text-align: left;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\"><b>【过题题目】</b></font></p><p style=\"text-align: left;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\"><b>{problem}</b></font></p><p style=\"text-align: left;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\"><b>【通过情况】</b></font></p><p style=\"text-align: left;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\"><b>已通过</b></font></p><p style=\"text-align: center;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\";>--------------------------------</font></p><p style=\"text-align: center;width: 200px;\"><font face=\"Lucida Grande\" size=\"6\">{judge_time}</font></p></body></html>";
    private static EditController instance;
    public EditController() {
        instance = this;
    }
    public static EditController getInstance() {
        return instance;
    }

    public void cancel() {
        Stage main = (Stage) editor.getScene().getWindow();
        main.close();
    }

    public void save() {
        // 更新图
        printStyleDescription = editor.getHtmlText();
        listenController.printerStyle.setImage(Printer.generatePDF(printStyleDescription));
        // 关闭视图
        Stage main = (Stage) editor.getScene().getWindow();
        main.close();
    }

    public String generateHtmlStr(Status status) {
        printStyleDescription = printStyleDescription.replaceAll("\\{title\\}", loginController.contestName);
        String temp = printStyleDescription.replaceAll("\\{nickname\\}", status.getNickname());
        temp = temp.replaceAll("\\{problem\\}", status.getProblem());
        temp = temp.replaceAll("\\{judge_time\\}", status.getJudgeTime());
        return temp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editor.setHtmlText(printStyleDescription);
        listenController.printerStyle.setImage(Printer.generatePDF(printStyleDescription));
    }
}
