package com.haogrgr.buka.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class RobotExp {
    public static void pressKey(Robot robot, int keyvalue) {
        robot.keyPress(keyvalue);
        robot.keyRelease(keyvalue);
    }

    public static void pressKeyWithShift(Robot robot, int keyvalue) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(keyvalue);
        robot.keyRelease(keyvalue);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    public static void closeApplication(Robot robot) {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_F4);
        //linux系统下使用下面的命令关闭程序
        // robot.keyPress(KeyEvent.VK_ALT);
        // robot.keyPress(KeyEvent.VK_W);
        // robot.keyRelease(KeyEvent.VK_ALT);
        // robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
    }

    public static void main(String[] args) throws IOException {
        try {
            Robot robot = new Robot();
            Runtime.getRuntime().exec("notepad");
            //linux系统下使用下面的命令打开文本编辑器
            // Runtime.getRuntime().exec("gedit");
            //定义3秒的延迟以便打开notepad   
            robot.delay(10000);
            //Robot开始写
            for (int i = 0; i < 50; i++) {
                pressKeyWithShift(robot, KeyEvent.VK_H);
                pressKey(robot, KeyEvent.VK_I);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKeyWithShift(robot, KeyEvent.VK_H);
                pressKeyWithShift(robot, KeyEvent.VK_I);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_A);
                pressKey(robot, KeyEvent.VK_M);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_T);
                pressKey(robot, KeyEvent.VK_H);
                pressKey(robot, KeyEvent.VK_E);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_J);
                pressKey(robot, KeyEvent.VK_A);
                pressKey(robot, KeyEvent.VK_V);
                pressKey(robot, KeyEvent.VK_A);
                pressKey(robot, KeyEvent.VK_SPACE);
                pressKey(robot, KeyEvent.VK_R);
                pressKey(robot, KeyEvent.VK_O);
                pressKey(robot, KeyEvent.VK_B);
                pressKey(robot, KeyEvent.VK_O);
                pressKey(robot, KeyEvent.VK_T);
                pressKey(robot, KeyEvent.VK_ENTER);
            }
            closeApplication(robot);//关闭
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
