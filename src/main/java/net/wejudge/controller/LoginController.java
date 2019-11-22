package net.wejudge.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.wejudge.dto.Response;
import net.wejudge.utils.PracticeProperties;
import net.wejudge.utils.Requests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML public TextField contest_id;
    @FXML public TextField access_token;
    @FXML private Text test_result;
    @FXML private Button confirm;
    public String contestName;
    public PracticeProperties practiceProperties = new PracticeProperties();

    private static LoginController instance;

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    /**
     * 测试连接
     */
    public void testConnection() {

        Response response = Requests.get(String.format("%s/contests/%s/only_read/%s/test_conn", practiceProperties.getValue("baseUrl"), contest_id.getText(), access_token.getText()));
        if (response.getStatus() == HttpURLConnection.HTTP_OK) {
            test_result.setText("连接正常");
        } else {
            test_result.setText("比赛不存在或数据令牌错误");
        }
    }

    /**
     * 确认连接，跳转页面
     * @throws Exception
     */
    public void confirm_conn() throws Exception {

        // 权限验证
        if (contest_id.getText().equalsIgnoreCase("") || access_token.getText().equalsIgnoreCase("")) {
            test_result.setText("请填写比赛id和数据令牌!");
            return;
        }
        Response response = Requests.get(String.format("%s/contests/%s/only_read/%s/test_conn", practiceProperties.getValue("baseUrl"), contest_id.getText(), access_token.getText()));
        if (response.getStatus() != HttpURLConnection.HTTP_OK) {
            test_result.setText("比赛不存在或数据令牌错误");
            return;
        }
        // 记录比赛名
        contestName = String.valueOf(((JSONObject)response.getJson().get("data")).get("contest_name"));

        // 渲染新页面
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/listen.fxml"));
        Stage dh = new Stage();
        dh.setTitle("监听");
        Scene scene = new Scene(root);
        dh.setScene(scene);
        dh.show();
        Stage main = (Stage) confirm.getScene().getWindow();
        main.close();
    }


    public void initialize(URL location, ResourceBundle resources) {
        // 提前载入页面，这样进入监听页面就不那么卡了
        try {
            FXMLLoader.load(getClass().getClassLoader().getResource("fxml/listen.fxml"));
            FXMLLoader.load(getClass().getClassLoader().getResource("fxml/edit.fxml"));
            EditController editController = EditController.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
