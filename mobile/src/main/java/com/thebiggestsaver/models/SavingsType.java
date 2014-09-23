package com.thebiggestsaver.models;

import android.graphics.drawable.Drawable;

/**
 * Created by patriciaestridge on 7/21/14.
 */
public class SavingsType {
    private String id;
    private String title;
    private Drawable icon;
    private Drawable add;
    private Drawable accept;
    private Drawable next;
    private Drawable back;
   // private Drawable edit;
    private Drawable delete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


//    public Drawable getEdit() {
//        return edit;
//    }
//
//    public void setEdit(Drawable edit) {
//        this.edit = edit;
//    }

    public Drawable getDelete() {
        return delete;
    }

    public void setDelete(Drawable delete) {
        this.delete = delete;
    }

    public Drawable getAccept() {
        return accept;
    }

    public void setAccept(Drawable accept) {
        this.accept = accept;
    }

    public Drawable getNext() {
        return next;
    }

    public void setNext(Drawable next) {
        this.next = next;
    }

    public Drawable getBack() {
        return back;
    }

    public void setBack(Drawable back) {
        this.back = back;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setIcon(Drawable icon)
    {
        this.icon = icon;
    }

    public Drawable getIcon()
    {
        return icon;
    }

    public void setAdd(Drawable add)
    {
        this.add = add;
    }

    public Drawable getAdd()
    {
        return add;
    }


}
