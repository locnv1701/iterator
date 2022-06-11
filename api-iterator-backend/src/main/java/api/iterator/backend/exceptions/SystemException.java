package api.iterator.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SystemException extends Exception {
    private static final long serialVersionUID = 1223590032959805186L;
    private Integer code;
    private String userMessage;
    private String detailMessage;

    public SystemException() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserMessage() {
        return this.userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String toString() {
        return "SystemException{code=" + this.code + ", userMessage='" + this.userMessage + '\'' + ", detailMessage='" + this.detailMessage + '\'' + "}";
    }

    public static class Builder {
        private Integer code;
        private String userMessage;
        private String detailMessage;

        public Builder() {
        }

        public Builder(Integer code) {
            this.code = code;
        }

        public SystemException.Builder withMessage(String mess) {
            this.userMessage = mess;
            return this;
        }

        public SystemException.Builder withDetail(String detail) {
            this.detailMessage = detail;
            return this;
        }

        public SystemException build() {
            SystemException e = new SystemException();
            e.code = this.code;
            e.userMessage = this.userMessage;
            e.detailMessage = this.detailMessage;
            return e;
        }
    }
}
