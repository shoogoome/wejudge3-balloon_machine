package net.wejudge.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.wejudge.dto.Response;
import net.wejudge.dto.Status;
import net.wejudge.utils.PracticeProperties;
import net.wejudge.utils.Printer;
import net.wejudge.utils.Requests;

import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import net.wejudge.utils.DateFormat;

import javax.print.PrintService;


public class ListenController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Status, String> id;
    @FXML
    private TableColumn<Status, String> nickname;
    @FXML
    private TableColumn<Status, String> problem;
    @FXML
    private TableColumn<Status, String> judgeTime;
    @FXML
    private TableColumn<Status, String> print;
    @FXML
    private TableColumn<Status, Button> manage;
    @FXML
    private ProgressBar progressBar;
    @FXML
    public ImageView printerStyle;
    @FXML
    public ComboBox printList;
    private LoginController loginController = LoginController.getInstance();
    private EditController editController = EditController.getInstance();
    private ObservableList<Status> judgeData = FXCollections.observableArrayList();
    private double last_time = 0.0;
    public PracticeProperties practiceProperties = new PracticeProperties();

    private static ListenController instance;

    public ListenController() {
        instance = this;
    }

    public static ListenController getInstance() {
        return instance;
    }

    /**
     * 定时刷新评测信息
     */
    public void updateView() {
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                int max = 10000;
                while (true) {
                    // 获取数据
                    Response response = Requests.get(
                            String.format("%s/contests/%s/only_read/%s/judge_status?create_time=%f",
                                    practiceProperties.getValue("baseUrl"),
                                    loginController.contest_id.getText(),
                                    loginController.access_token.getText(),
                                    last_time));
                    Map json = response.getJson();
                    // 解析填充数据
                    for (Object data1 : ((JSONArray) json.get("data"))) {
                        String jid = String.valueOf(((JSONObject) data1).get("id"));
                        String nickname = String.format("%s(%s)",
                                String.valueOf(((JSONObject) data1).get("nickname")),
                                String.valueOf(((JSONObject) data1).get("username")));
                        String problem = String.valueOf(((JSONObject) data1).get("problem"));
                        String judgeTime = DateFormat.getFormatTime(Long.parseLong(String.valueOf(((JSONObject) data1).get("create_time")).replaceAll("\\.", "")) / 100);
                        Button print = new Button("打印");
                        Status a = new Status(jid, nickname, problem, judgeTime, "未打印", print);
                        print.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    Printer.printerStatus(editController.generateHtmlStr(a));
                                    a.setPrint("打印成功");
                                } catch (Exception e) {
                                    a.setPrint(e.getMessage());
                                }
                            }
                        });

                        judgeData.add(0, a);
                        last_time = Double.valueOf(String.valueOf(((JSONObject) data1).get("create_time")));
                    }
                    // 等待10s，更新进度条
                    for (int i = 1; i <= 10000; i++) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateProgress(i, max);
                    }
                }
            }
        };
        // 进度条事件绑定
        progressBar.progressProperty().bind(task.progressProperty());
        // 启动任务
        new Thread(task).start();
    }

    /**
     * 打开编辑样式页面
     * @throws IOException
     */
    public void edit() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/edit.fxml"));
        Stage dh = new Stage();
        dh.setTitle("编辑样式");
        Scene scene = new Scene(root);
        dh.setScene(scene);
        dh.show();
        editController = EditController.getInstance();
    }

    /**
     * 测试打印
     */
    public void testPrint() {
        try {
            Printer.printerStatus(EditController.printStyleDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化打印机列表
     */
    public void initPrintListI() {
        Map<String, PrintService> printServiceMap = Printer.findPrinterServices();
        ObservableList<String> options =
                FXCollections.observableArrayList(printServiceMap.keySet());
        printList.setItems(options);
    }


    public void initialize(URL location, ResourceBundle resources) {
        // 绑定字段
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nickname.setCellValueFactory(cellData -> cellData.getValue().nicknameProperty());
        problem.setCellValueFactory(cellData -> cellData.getValue().problemProperty());
        judgeTime.setCellValueFactory(cellData -> cellData.getValue().judgeTimeProperty());
        print.setCellValueFactory(cellData -> cellData.getValue().printProperty());
        manage.setCellValueFactory(cellData -> cellData.getValue().manageProperty());

        // 填充初始化数据
        tableView.setItems(judgeData);
        // 初始化打印机数据
        this.initPrintListI();
    }
}


