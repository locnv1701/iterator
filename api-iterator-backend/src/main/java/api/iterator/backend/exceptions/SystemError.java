package api.iterator.backend.exceptions;

import java.util.Date;

public class SystemError {
    private Date timestamp;
    private Integer code;
    private String message;
    private String details;
    private String path;

    public SystemError() {
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return "SystemError [timestamp=" + this.timestamp + ", code=" + this.code + ", message=" + this.message + ", details=" + this.details + ", path=" + this.path + "]";
    }

    public static class Builder {
        private Integer code;
        private String message;
        private String details;
        private String path;

        public Builder(Integer code) {
            this.code = code;
        }

        public Builder() {
        }

        public SystemError.Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public SystemError.Builder details(String details) {
            this.details = details;
            return this;
        }

        public SystemError.Builder fromPath(String path) {
            this.path = path;
            return this;
        }

        public SystemError build() {
            SystemError error = new SystemError();
            error.setCode(this.code);
            error.setMessage(this.message);
            error.setTimestamp(new Date());
            error.setDetails(this.details);
            error.setPath(this.path);
            return error;
        }
    }
}
