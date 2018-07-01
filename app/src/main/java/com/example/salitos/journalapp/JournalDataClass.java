package com.example.salitos.journalapp;

import android.os.Parcel;
import android.os.Parcelable;

public class JournalDataClass implements Parcelable {

        protected JournalDataClass(Parcel in) {
            event = in.readString();
            details = in.readString();
            date = in.readString();
            id = in.readString();
        }

        public  JournalDataClass(){

        }
//Array that is easily converible to other type  and spans a lot of data structures
        public static final Creator<JournalDataClass> CREATOR = new Creator<JournalDataClass>() {
            @Override
            public JournalDataClass createFromParcel(Parcel in) {
                return new JournalDataClass(in);
            }

            @Override
            public JournalDataClass[] newArray(int size) {
                return new JournalDataClass[size];
            }
        };

        String date;
        String event;
        String details;
        String id;
//The getters and setters and Overriden methods makke sure our variables are initialized
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        int cid;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }


        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String datum) {
            this.date = datum;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(event);
            parcel.writeString(details);
            parcel.writeString(date);
            parcel.writeString(id);
        }
    }
