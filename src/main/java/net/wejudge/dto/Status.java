package net.wejudge.dto;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;

/**
 * 数据类
 */
public class Status {
    private final SimpleStringProperty id;
    private final SimpleStringProperty nickname;
    private final SimpleStringProperty problem;
    private final SimpleStringProperty judgeTime;
    private final SimpleStringProperty print;
    private final Button manage;

    public Status(String id, String nickname, String problem, String judgeTime, String print, Button manage) {
        this.id = new SimpleStringProperty(id);
        this.nickname = new SimpleStringProperty(nickname);
        this.problem = new SimpleStringProperty(problem);
        this.judgeTime = new SimpleStringProperty(judgeTime);
        this.print = new SimpleStringProperty(print);
        this.manage = manage;
    }

    public String getJudgeTime() {
        return judgeTime.get();
    }

    public SimpleStringProperty judgeTimeProperty() {
        return judgeTime;
    }

    public void setJudgeTime(String judgeTime) {
        this.judgeTime.set(judgeTime);
    }

    public String getPrint() {
        return print.get();
    }

    public SimpleStringProperty printProperty() {
        return print;
    }

    public void setPrint(String print) {
        this.print.set(print);
    }

    public Button getManage() {
        return manage;
    }

    public ObservableValue<Button> manageProperty() {
        return new ObservableValue<Button>() {
            @Override
            public void addListener(ChangeListener<? super Button> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Button> listener) {

            }

            @Override
            public Button getValue() {
                return manage;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getNickname() {
        return nickname.get();
    }

    public SimpleStringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public String getProblem() {
        return problem.get();
    }

    public SimpleStringProperty problemProperty() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem.set(problem);
    }
}