package com.example.android_project.board;

import java.io.Serializable;

/**
 * Created by LG on 2017-02-08.
 */

public class BoardDTO implements Serializable {

    private String board_title,board_content,user_id,comp_id,board_date,mycar,filepath,filename,board_user_nick;
    private int board_id,readcnt,board_reply_cnt,sympathy_cnt;



    public  BoardDTO(String board_title, String board_content, String user_id){
        this.board_title = board_title;
        this.board_content = board_content;
        this.user_id = user_id;
    }

    public BoardDTO(String board_title, String board_content, String user_id, String comp_id, String board_date, int board_id, int readcnt,String mycar,String filepath,String filename,int board_reply_cnt,int sympathy_cnt,String board_user_nick) {
        this.board_title = board_title;
        this.board_content = board_content;
        this.user_id = user_id;
        this.comp_id = comp_id;
        this.board_date = board_date;
        this.board_id = board_id;
        this.readcnt = readcnt;
        this.user_id = user_id;
        this.mycar = mycar;
        this.filepath = filepath;
        this.filename = filename;
        this.board_reply_cnt = board_reply_cnt;
        this.sympathy_cnt = sympathy_cnt;
        this.board_user_nick = board_user_nick;
    }

    public String getBoard_user_nick() {
        return board_user_nick;
    }

    public void setBoard_user_nick(String board_user_nick) {
        this.board_user_nick = board_user_nick;
    }

    public int getSympathy_cnt() {
        return sympathy_cnt;
    }

    public void setSympathy_cnt(int sympathy_cnt) {
        this.sympathy_cnt = sympathy_cnt;
    }

    public int getBoard_reply_cnt() {
        return board_reply_cnt;
    }

    public void setBoard_reply_cnt(int board_reply_cnt) {
        this.board_reply_cnt = board_reply_cnt;
    }


    public String getMycar() {
        return mycar;
    }

    public void setMycar(String mycar) {
        this.mycar = mycar;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getBoard_date() {
        return board_date;
    }

    public void setBoard_date(String board_date) {
        this.board_date = board_date;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getReadcnt() {
        return readcnt;
    }

    public void setReadcnt(int readcnt) {
        this.readcnt = readcnt;
    }
}
