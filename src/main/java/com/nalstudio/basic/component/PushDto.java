package com.nalstudio.basic.component;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class PushDto {

    public static interface MarketPushType {
        int ADVERTISE = 1000; // 광고
        int EVENT = 1001; //이벤트
        int ETC = 1002; //기타
        int NT = 1003; //공지
        int OTHER = 1004;//
    }

    public static String fMessageKey = "foreGroundMessage";
    public static String bTitleKey = "backGroundTitle";
    public static String bMessageKey = "backGroundMessage";
    public static String url = "url";

    @Getter
    @Setter
    @ToString
    @Builder
    class FCMInfo{
        @SerializedName("pushType")
        private String pushType;

        @SerializedName("foreGroundTitle")
        private String foreGroundTitle;

        @SerializedName("foreGroundMessage")
        private String foreGroundMessage;

        @SerializedName("backGroundTitle")
        private String backGroundTitle;

        @SerializedName("backGroundMessage")
        private String backGroundMessage;

        @SerializedName("url")
        private String url;
    }

    @Getter
    @Setter
    @ToString
    public static class FcmResponseInfo {
        private int statusCode;
        private long multicast_id;
        private int success;
        private int failure;
        private int canonical_ids;
        private List<FcmResponseInfoResult> results;

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public long getMulticast_id() {
            return multicast_id;
        }

        public void setMulticast_id(long multicast_id) {
            this.multicast_id = multicast_id;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public int getFailure() {
            return failure;
        }

        public void setFailure(int failure) {
            this.failure = failure;
        }

        public int getCanonical_ids() {
            return canonical_ids;
        }

        public void setCanonical_ids(int canonical_ids) {
            this.canonical_ids = canonical_ids;
        }

        public List<FcmResponseInfoResult> getResults() {
            return results;
        }

        public void setResults(List<FcmResponseInfoResult> results) {
            this.results = results;
        }

        @Getter
        @Setter
        @ToString
        public static class FcmResponseInfoResult {
            private String message_id;
            private String error;
            private String registration_id;
            public String getMessage_id() {
                return message_id;
            }
            public void setMessage_id(String message_id) {
                this.message_id = message_id;
            }
            public String getError() {
                return error;
            }
            public void setError(String error) {
                this.error = error;
            }
            public String getRegistration_id() {
                return registration_id;
            }
            public void setRegistration_id(String registration_id) {
                this.registration_id = registration_id;
            }

        }
    }
}
