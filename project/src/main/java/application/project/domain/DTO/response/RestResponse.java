package application.project.domain.dto.response;

public class RestResponse<T> {

    private int statusCode;
    private String error;
    private Object message;
    private T data;

    private RestResponse(int statusCode, T data, Object message, String error) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData(){
        return this.data;
    }
    public static <U> Builder<U> builder(){
        return new Builder<>();
    }

    public static class Builder<U> {
        private int statusCode;
        private String error;
        private Object message;
        private U data;

        public Builder<U> setStatusCode(int statusCode){
            this.statusCode = statusCode;
            return this;
        }

        public Builder<U> setError(String error){
            this.error = error;
            return this;
        }
        public Builder<U> setMessage(Object message){
            this.message = message;
            return this;
        }
        public Builder<U> setData(U data){
            this.data = data;
            return this;
        }

        public RestResponse<U> build() {
            return new RestResponse<>(
                this.statusCode,
                this.data,
                this.message,
                this.error
            );
        
        }


    }

    // -------------------------------------------------------------------
    // toString, equals, hashCode (optional, but often helpful)
    // -------------------------------------------------------------------

    @Override
    public String toString() {
        return "RestResponse{" +
                "statusCode=" + statusCode +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RestResponse<?> that = (RestResponse<?>) o;

        if (statusCode != that.statusCode)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null)
            return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }

}
