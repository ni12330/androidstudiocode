package com.example.android_project.reply;

public class ReplyDTO {

    private String reply_content, user_id, reply_date;
    private String reply_id, board_id;

    public ReplyDTO(String reply_content, String user_id, String reply_date, String reply_id, String board_id) {
        this.reply_content = reply_content;
        this.user_id = user_id;
        this.reply_date = reply_date;
        this.reply_id = reply_id;
        this.board_id = board_id;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }
}