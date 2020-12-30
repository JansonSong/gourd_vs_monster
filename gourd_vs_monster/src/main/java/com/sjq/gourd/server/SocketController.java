package com.sjq.gourd.server;

import java.net.*;
import java.io.*;

import com.sjq.gourd.constant.Constant;
import com.sjq.gourd.protocol.*;

public class SocketController {
    private Socket socketPlayerGourd;
    private Socket socketPlayerMonster;
    private ObjectInputStream inGourd;
    private ObjectOutputStream outGourd;
    private ObjectInputStream inMonster;
    private ObjectOutputStream outMonster;

    boolean gourdFinishFlag = false;
    boolean monsterFinishFlag = false;

    public SocketController() {
    }

    public void addGourdPlayer(Socket socket) {
        socketPlayerGourd = socket;
        try {
            System.out.println("分配葫芦娃" + socketPlayerGourd);
            outGourd = new ObjectOutputStream(socketPlayerGourd.getOutputStream());
            inGourd = new ObjectInputStream(socketPlayerGourd.getInputStream());
            new DistributionCampMsg(Constant.CampType.GOURD).sendMsg(outGourd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMonsterPlayer(Socket socket) {
        socketPlayerMonster = socket;
        try {
            outMonster = new ObjectOutputStream(socketPlayerMonster.getOutputStream());
            inMonster = new ObjectInputStream(socketPlayerMonster.getInputStream());
            new DistributionCampMsg(Constant.CampType.MONSTER).sendMsg(outMonster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void prepareFight(){
        System.out.println("prepareFight");
        new NoParseMsg(Msg.PREPARE_GAME_MSG).sendMsg(outGourd);
        new NoParseMsg(Msg.PREPARE_GAME_MSG).sendMsg(outMonster);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        int msgType = inGourd.readInt();
                        if(msgType == Msg.FINISH_FLAG_MSG) {
                            gourdFinishFlag = true;
                            System.out.println("gourdFinishFlag");
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        int msgType = inMonster.readInt();
                        if(msgType == Msg.FINISH_FLAG_MSG) {
                            monsterFinishFlag = true;
                            System.out.println("monsterFinishFlag");
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        while(true) {
            if(gourdFinishFlag && monsterFinishFlag) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Server countDown");
        for(int i = 0; i < 1; i++) {
            new CountDownMsg(30 - i).sendMsg(outGourd);
            new CountDownMsg(30 - i).sendMsg(outMonster);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new NoParseMsg(Msg.START_GAME_MSG).sendMsg(outGourd);
        new NoParseMsg(Msg.START_GAME_MSG).sendMsg(outMonster);
        new ServerScene(inGourd, outGourd, inMonster, outMonster).startGame();
    }

}
